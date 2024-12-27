package com.example.application.views.manageseat;

import com.example.application.data.entity.Seat;
import com.example.application.services.SeatService;
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

@PageTitle("ManageSeat")
@Route(value = "manageSeat/:seatID?/:action?(edit)", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@Uses(Icon.class)
public class ManageSeatView extends Div implements BeforeEnterObserver {

    private final String SEAT_ID = "seatID";
    private final String SEAT_EDIT_ROUTE_TEMPLATE = "manageSeat/%s/edit";

    private final Grid<Seat> grid = new Grid<>(Seat.class, false);

    private ComboBox<String> cityName;
    private ComboBox<String> cinemaName;
    private ComboBox<String> screenName;
    private TextField seatRow;
    private TextField seatColumn;
    private Checkbox seatAvailability;

    private final Button clear = new Button("Clear");
    private final Button save = new Button("Save");

    private final BeanValidationBinder<Seat> binder;

    private Seat seat;

    private final SeatService seatService;

    public ManageSeatView(SeatService seatService) {
        this.seatService = seatService;
        addClassNames("manage-seat-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("cityName").setAutoWidth(true);
        grid.addColumn("cinemaName").setAutoWidth(true);
        grid.addColumn("screenName").setAutoWidth(true);
        grid.addColumn("seatRow").setAutoWidth(true);
        grid.addColumn("seatColumn").setAutoWidth(true);
        LitRenderer<Seat> seatAvailabilityRenderer = LitRenderer.<Seat>of(
                "<vaadin-icon icon='vaadin:${item.icon}' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: ${item.color};'></vaadin-icon>")
                .withProperty("icon", seatAvailability -> seatAvailability.isSeatAvailability() ? "check" : "minus")
                .withProperty("color",
                        seatAvailability -> seatAvailability.isSeatAvailability()
                                ? "var(--lumo-primary-text-color)"
                                : "var(--lumo-disabled-text-color)");

        grid.addColumn(seatAvailabilityRenderer).setHeader("Seat Availability").setAutoWidth(true);

        grid.setItems(query -> seatService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(SEAT_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(ManageSeatView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Seat.class);

        // Bind fields. This is where you'd define e.g. validation rules
        binder.forField(seatRow).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("seatRow");
        binder.forField(seatColumn).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("seatColumn");

        binder.bindInstanceFields(this);

        clear.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.seat == null) {
                    this.seat = new Seat();
                }
                binder.writeBean(this.seat);
                seatService.update(this.seat);
                clearForm();
                refreshGrid();
                Notification.show("Data updated");
                UI.getCurrent().navigate(ManageSeatView.class);
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
        cityName.setItems(seatService.getAllCityNames());
        //cinemaName listener
        cityName.addValueChangeListener(event -> {
            String selectedCity = event.getValue();
            if (selectedCity != null) {
                cinemaName.setItems(seatService.getCinemaNamesByCityName(selectedCity));
            } else {
                cinemaName.clear();
            }
        });
        //screenName listener
        cinemaName.addValueChangeListener(event -> {
            String selectedCity = event.getValue();
            if (selectedCity != null) {
                screenName.setItems(seatService.getScreenNamesByCinemaName(selectedCity));
            } else {
                screenName.clear();
            }
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> seatId = event.getRouteParameters().get(SEAT_ID).map(Long::parseLong);
        if (seatId.isPresent()) {
            Optional<Seat> seatFromBackend = seatService.get(seatId.get());
            if (seatFromBackend.isPresent()) {
                populateForm(seatFromBackend.get());
            } else {
                Notification.show(String.format("The requested seat was not found, ID = %s", seatId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(ManageSeatView.class);
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
        cityName = new ComboBox<>("City Name");
        cinemaName = new ComboBox<>("Cinema Name");
        screenName = new ComboBox<>("Screen Name");
        seatRow = new TextField("Seat Row");
        seatColumn = new TextField("Seat Column");
        seatAvailability = new Checkbox("Seat Availability");
        formLayout.add(cityName, cinemaName, screenName, seatRow, seatColumn, seatAvailability);

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        clear.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, clear);
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

    private void populateForm(Seat value) {
        this.seat = value;
        binder.readBean(this.seat);

    }
}
