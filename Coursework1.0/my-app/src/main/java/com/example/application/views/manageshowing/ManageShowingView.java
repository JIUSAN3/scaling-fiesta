package com.example.application.views.manageshowing;

import com.example.application.data.entity.Showing;
import com.example.application.services.ShowingService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import jakarta.annotation.security.RolesAllowed;
import java.time.Duration;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@PageTitle("ManageShowing")
@Route(value = "manageShowing/:showingID?/:action?(edit)", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class ManageShowingView extends Div implements BeforeEnterObserver {

    private final String SHOWING_ID = "showingID";
    private final String SHOWING_EDIT_ROUTE_TEMPLATE = "manageShowing/%s/edit";

    private final Grid<Showing> grid = new Grid<>(Showing.class, false);

    private ComboBox<String> filmName;
    private ComboBox<String> showingDate;
    private ComboBox<String> showingTime;
    private TextField showingScreen;

    private final Button clear = new Button("Clear");
    private final Button save = new Button("Save");
    private final Button delete = new Button("Delete");

    private final BeanValidationBinder<Showing> binder;

    private Showing showing;

    private final ShowingService showingService;

    public ManageShowingView(ShowingService showingService) {
        this.showingService = showingService;
        addClassNames("manage-showing-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("filmName").setAutoWidth(true);
        grid.addColumn("showingDate").setAutoWidth(true);
        grid.addColumn("showingTime").setAutoWidth(true);
        grid.addColumn("showingScreen").setAutoWidth(true);
        grid.setItems(query -> showingService.list(
                        PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(SHOWING_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(ManageShowingView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Showing.class);

        // Bind fields. This is where you'd define e.g. validation rules

        binder.bindInstanceFields(this);

        clear.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        delete.addClickListener(e -> {
            try {
                if (this.showing != null) {
                    binder.writeBean(this.showing);
                    showingService.delete(this.showing);
                    Notification.show("Data deleted");
                    clearForm();
                    refreshGrid();
                }
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Error deleting the data. Somebody else has modified the record while you were trying to delete it.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (Exception e1) {
                Notification.show("An error occurred while trying to delete the data.");
                e1.printStackTrace();
            }
        });
        save.addClickListener(e -> {
            try {
                if (this.showing == null) {
                    this.showing = new Showing();
                }
                binder.writeBean(this.showing);
                showingService.update(this.showing);
                clearForm();
                refreshGrid();
                Notification.show("Data updated");
                UI.getCurrent().navigate(ManageShowingView.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Error updating the data. Somebody else has updated the record while you were making changes.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (ValidationException validationException) {
                Notification.show("Failed to update the data. Check again that all values are valid");
            }
        });

        // Populate ComboBox items
        populateComboBoxes();
    }

    //view is initialised and populates the drop-down box with data.
    private void populateComboBoxes() {
        filmName.setItems(showingService.getAllFilmNames());

        showingDate.setItems("2024-06-01", "2024-06-02", "2024-06-03"); // Add fixed dates
        showingTime.setItems("Morning 8 AM to Noon", "Noon to 5 PM", "5 PM to Midnight"); // Add fixed time slots
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> showingId = event.getRouteParameters().get(SHOWING_ID).map(Long::parseLong);
        if (showingId.isPresent()) {
            Optional<Showing> showingFromBackend = showingService.get(showingId.get());
            if (showingFromBackend.isPresent()) {
                populateForm(showingFromBackend.get());
            } else {
                Notification.show(String.format("The requested showing was not found, ID = %s", showingId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(ManageShowingView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        filmName = new ComboBox<>("Film Name");
        showingDate = new ComboBox<>("Showing Date");
        showingTime = new ComboBox<>("Showing Time");
        showingScreen = new TextField("Showing Screen");
        formLayout.add(filmName, showingDate, showingTime, showingScreen);

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        clear.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, clear, delete);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Showing value) {
        this.showing = value;
        binder.readBean(this.showing);
    }
}
