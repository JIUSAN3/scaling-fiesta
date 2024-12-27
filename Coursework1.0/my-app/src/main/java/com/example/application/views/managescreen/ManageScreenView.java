package com.example.application.views.managescreen;

import com.example.application.data.entity.Screen;
import com.example.application.services.ScreenService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import jakarta.annotation.security.RolesAllowed;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@PageTitle("ManageScreen")
@Route(value = "manageScreen/:screenID?/:action?(edit)", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@Uses(Icon.class)
public class ManageScreenView extends Div implements BeforeEnterObserver {

    private final String SCREEN_ID = "screenID";
    private final String SCREEN_EDIT_ROUTE_TEMPLATE = "manageScreen/%s/edit";

    private final Grid<Screen> grid = new Grid<>(Screen.class, false);

    private ComboBox<String> cityName;
    private ComboBox<String> cinemaName;
    private TextField screenName;
    private TextField upperHallCapacity;
    private TextField lowerHallCapacity;
    private TextField centreHallCapacity;
    private TextField totalCapacity;
    private Checkbox isAvailable;

    private final Button clear = new Button("Clear");
    private final Button save = new Button("Save");
    private final Button delete = new Button("Delete");

    private final BeanValidationBinder<Screen> binder;

    private Screen screen;

    private final ScreenService screenService;

    public ManageScreenView(ScreenService screenService) {
        this.screenService = screenService;
        addClassNames("manage-screen-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("cityName").setAutoWidth(true);
        grid.addColumn("cinemaName").setAutoWidth(true);
        grid.addColumn("screenName").setAutoWidth(true);
        grid.addColumn("upperHallCapacity").setAutoWidth(true);
        grid.addColumn("lowerHallCapacity").setAutoWidth(true);
        grid.addColumn("centreHallCapacity").setAutoWidth(true);
        grid.addColumn("totalCapacity").setAutoWidth(true);
        LitRenderer<Screen> isAvailableRenderer = LitRenderer.<Screen>of(
                "<vaadin-icon icon='vaadin:${item.icon}' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: ${item.color};'></vaadin-icon>")
                .withProperty("icon", isAvailable -> isAvailable.isIsAvailable() ? "check" : "minus")
                .withProperty("color",
                        isAvailable -> isAvailable.isIsAvailable()
                                ? "var(--lumo-primary-text-color)"
                                : "var(--lumo-disabled-text-color)");

        grid.addColumn(isAvailableRenderer).setHeader("Is Available").setAutoWidth(true);

        grid.setItems(query -> screenService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(SCREEN_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(ManageScreenView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Screen.class);

        // Bind fields. This is where you'd define e.g. validation rules
        binder.forField(upperHallCapacity).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("upperHallCapacity");
        binder.forField(lowerHallCapacity).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("lowerHallCapacity");
        binder.forField(centreHallCapacity).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("centreHallCapacity");
        binder.forField(totalCapacity).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("totalCapacity");

        binder.bindInstanceFields(this);

        clear.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        delete.addClickListener(e -> {
            try {
                if (this.screen != null) {
                    binder.writeBean(this.screen);
                    screenService.delete(this.screen);
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
                if (this.screen == null) {
                    this.screen = new Screen();
                }
                binder.writeBean(this.screen);
                screenService.update(this.screen);
                clearForm();
                refreshGrid();
                Notification.show("Data updated");
                UI.getCurrent().navigate(ManageScreenView.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Error updating the data. Somebody else has updated the record while you were making changes.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (ValidationException validationException) {
                Notification.show("Failed to update the data. Check again that all values are valid");
            }
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> screenId = event.getRouteParameters().get(SCREEN_ID).map(Long::parseLong);
        if (screenId.isPresent()) {
            Optional<Screen> screenFromBackend = screenService.get(screenId.get());
            if (screenFromBackend.isPresent()) {
                populateForm(screenFromBackend.get());
            } else {
                Notification.show(String.format("The requested screen was not found, ID = %s", screenId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(ManageScreenView.class);
            }
        }
        // Populate ComboBox items
        populateComboBoxes();
    }

    //view is initialised and populates the drop-down box with data.
    private void populateComboBoxes() {
        cityName.setItems(screenService.getAllCityNames());
        //cinemaName listener
        cityName.addValueChangeListener(event -> {
            String selectedCity = event.getValue();
            if (selectedCity != null) {
                cinemaName.setItems(screenService.getCinemaNamesByCityName(selectedCity));
            } else {
                cinemaName.clear();
            }
        });
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        cityName = new ComboBox<>("City Name");
        cinemaName = new ComboBox<>("Cinema Name");
        screenName = new TextField("Screen Name");
        upperHallCapacity = new TextField("Upper Hall Capacity");
        lowerHallCapacity = new TextField("Lower Hall Capacity");
        centreHallCapacity = new TextField("Centre Hall Capacity");
        totalCapacity = new TextField("Total Capacity");
        isAvailable = new Checkbox("Is Available");
        formLayout.add(cityName, cinemaName, screenName, upperHallCapacity, lowerHallCapacity, centreHallCapacity, totalCapacity,
                isAvailable);

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        clear.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
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

    private void populateForm(Screen value) {
        this.screen = value;
        binder.readBean(this.screen);

    }
}
