package com.example.application.views.managecinema;

import com.example.application.data.entity.Cinema;
import com.example.application.services.CinemaService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
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
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@PageTitle("ManageCinema")
@Route(value = "manageCinema/:cinemaID?/:action?(edit)", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class ManageCinemaView extends Div implements BeforeEnterObserver {

    private final String CINEMA_ID = "cinemaID";
    private final String CINEMA_EDIT_ROUTE_TEMPLATE = "manageCinema/%s/edit";

    private final Grid<Cinema> grid = new Grid<>(Cinema.class, false);

    private ComboBox<String> cinemaCity;
    private TextField cinemaName;


    private final Button clear = new Button("Clear");
    private final Button save = new Button("Save");
    private final Button delete = new Button("Delete");


    private final BeanValidationBinder<Cinema> binder;

    private Cinema cinema;

    private final CinemaService cinemaService;

    public ManageCinemaView(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
        addClassNames("manage-cinema-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("cinemaCity").setAutoWidth(true);
        grid.addColumn("cinemaName").setAutoWidth(true);
        grid.setItems(query -> cinemaService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(CINEMA_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(ManageCinemaView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Cinema.class);

        // Bind fields. This is where you'd define e.g. validation rules

        binder.bindInstanceFields(this);

        clear.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        delete.addClickListener(e -> {
            try {
                if (this.cinema != null) {
                    binder.writeBean(this.cinema);
                    cinemaService.delete(this.cinema);
                    Notification.show("Data deleted");
                    clearForm();
                    refreshGrid();
                }
            }catch (ObjectOptimisticLockingFailureException exception) {
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
                if (this.cinema == null) {
                    this.cinema = new Cinema();
                }
                binder.writeBean(this.cinema);
                cinemaService.update(this.cinema);
                clearForm();
                refreshGrid();
                Notification.show("Data updated");
                UI.getCurrent().navigate(ManageCinemaView.class);
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
        cinemaCity.setItems(cinemaService.getAllCityNames());
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> cinemaId = event.getRouteParameters().get(CINEMA_ID).map(Long::parseLong);
        if (cinemaId.isPresent()) {
            Optional<Cinema> cinemaFromBackend = cinemaService.get(cinemaId.get());
            if (cinemaFromBackend.isPresent()) {
                populateForm(cinemaFromBackend.get());
            } else {
                Notification.show(String.format("The requested cinema was not found, ID = %s", cinemaId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(ManageCinemaView.class);
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
        cinemaCity = new ComboBox<String>("Cinema City");
        cinemaName = new TextField("Cinema Name");
        formLayout.add(cinemaCity, cinemaName);

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

    private void populateForm(Cinema value) {
        this.cinema = value;
        binder.readBean(this.cinema);

    }
}
