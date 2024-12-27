package com.example.application.views.managebooking;

import com.example.application.data.entity.Booking;
import com.example.application.services.BookingService;
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
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.validator.IntegerRangeValidator;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import jakarta.annotation.security.RolesAllowed;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@PageTitle("ManageBooking")
@Route(value = "manageBooking/:bookingID?/:action?(edit)", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class ManageBookingView extends Div implements BeforeEnterObserver {

    private final String BOOKING_ID = "bookingID";
    private final String BOOKING_EDIT_ROUTE_TEMPLATE = "manageBooking/%s/edit";

    private final Grid<Booking> grid = new Grid<>(Booking.class, false);

    private final int basePrice = 10;
    private final int totalSeatCount = 100;
    private final int lowerHallSeatCount = (int) (totalSeatCount * 0.3);
    private final int upperHallSeatCount = totalSeatCount - lowerHallSeatCount;
    private final int vipSeatCount = 10;
    private TextField userName;
    private TextField phone;
    private ComboBox<String> cityName;
    private ComboBox<String> cinemaName;
    private ComboBox<String> screenName;
    private ComboBox<String> filmName;
    private ComboBox<String> showingDate;
    private ComboBox<String> showingTime;
    private TextField seatQuantityUpperHall;
    private TextField seatQuantityLowerHall;
    private TextField seatQuantityCentreHall;
    private TextField ticketNumber;
    private TextField totalCost;
    private TextField bookingStatus;

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

    private final Button clear = new Button("Clear");
    private final BeanValidationBinder<Booking> binder;

    private Booking booking;

    private final BookingService bookingService;

    public ManageBookingView(BookingService bookingService) {
        this.bookingService = bookingService;
        addClassNames("manage-booking-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("userName").setAutoWidth(true);
        grid.addColumn("phone").setAutoWidth(true);
        grid.addColumn("cityName").setAutoWidth(true);
        grid.addColumn("cinemaName").setAutoWidth(true);
        grid.addColumn("screenName").setAutoWidth(true);
        grid.addColumn("filmName").setAutoWidth(true);
        grid.addColumn("showingDate").setAutoWidth(true);
        grid.addColumn("showingTime").setAutoWidth(true);
        grid.addColumn("seatQuantityUpperHall").setAutoWidth(true);
        grid.addColumn("seatQuantityLowerHall").setAutoWidth(true);
        grid.addColumn("seatQuantityCentreHall").setAutoWidth(true);
        grid.addColumn("ticketNumber").setAutoWidth(true);
        grid.addColumn("totalCost").setAutoWidth(true);
        grid.addColumn("bookingStatus").setAutoWidth(true);
        grid.setItems(query -> bookingService.list(
                        PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(BOOKING_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(ManageBookingView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Booking.class);

        // Bind fields. This is where you'd define e.g. validation rules
        binder.forField(seatQuantityUpperHall)
                .asRequired("Seat quantity is required")
                .withConverter(new StringToIntegerConverter("Must be a number"))
                .withValidator(new IntegerRangeValidator("Must be non-negative", 0, null))
                .bind("seatQuantityUpperHall");

        binder.forField(seatQuantityLowerHall)
                .asRequired("Seat quantity is required")
                .withConverter(new StringToIntegerConverter("Must be a number"))
                .withValidator(new IntegerRangeValidator("Must be non-negative", 0, null))
                .bind("seatQuantityLowerHall");

        binder.forField(seatQuantityCentreHall)
                .asRequired("Seat quantity is required")
                .withConverter(new StringToIntegerConverter("Must be a number"))
                .withValidator(new IntegerRangeValidator("Must be non-negative", 0, null))
                .bind("seatQuantityCentreHall");

        binder.forField(ticketNumber)
                .withConverter(new StringToIntegerConverter("Must be a number"))
                .withValidator(new IntegerRangeValidator("Must be non-negative", 0, null))
                .bind("ticketNumber");

        binder.forField(totalCost)
                .withConverter(new StringToDoubleConverter("Must be a number"))
                .withValidator(value -> value >= 0, "Total cost must be non-negative")
                .bind("totalCost");

        binder.bindInstanceFields(this);

        clear.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        cancel.addClickListener(e -> {
            try {
                if (this.booking != null) {
                    if ("Booked".equals(this.booking.getBookingStatus())) {
                        this.booking.setBookingStatus("Canceled");
                        bookingService.update(this.booking);
                        Notification.show("Booking cancelled");
                        refreshGrid();
                    } else {
                        Notification.show("Booking is not in a cancellable state");
                    }
                } else {
                    Notification.show("No booking selected to cancel");
                }
            } catch (Exception ex) {
                Notification.show("An error occurred while trying to cancel the booking");
                ex.printStackTrace();
            }
        });

        save.addClickListener(e -> {
            try {
                if (this.booking == null) {
                    this.booking = new Booking();
                }
                binder.writeBean(this.booking);

                // Set the booking status to "Booked" before saving
                this.booking.setBookingStatus("Booked");
                bookingService.update(this.booking);
                clearForm();
                refreshGrid();
                Notification.show("Data updated");
                UI.getCurrent().navigate(ManageBookingView.class);
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

    // view is initialised and populates the drop-down box with data.
    private void populateComboBoxes() {
        cityName.setItems(bookingService.getAllCityNames());
        // cinemaName listener
        cityName.addValueChangeListener(event -> {
            String selectedCity = event.getValue();
            if (selectedCity != null) {
                cinemaName.setItems(bookingService.getCinemaNamesByCityName(selectedCity));
            } else {
                cinemaName.clear();
            }
        });
        // screenName listener
        cinemaName.addValueChangeListener(event -> {
            String selectedCinema = event.getValue();
            if (selectedCinema != null) {
                screenName.setItems(bookingService.getScreenNamesByCinemaName(selectedCinema));
            } else {
                screenName.clear();
            }
        });
        filmName.setItems(bookingService.getAllFilmNames());
        showingDate.setItems(bookingService.getAllShowingDates());
        showingTime.setItems(bookingService.getAllShowingTimes());
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> bookingId = event.getRouteParameters().get(BOOKING_ID).map(Long::parseLong);
        if (bookingId.isPresent()) {
            Optional<Booking> bookingFromBackend = bookingService.get(bookingId.get());
            if (bookingFromBackend.isPresent()) {
                populateForm(bookingFromBackend.get());
            } else {
                Notification.show(String.format("The requested booking was not found, ID = %s", bookingId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(ManageBookingView.class);
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
        userName = new TextField("User Name");
        phone = new TextField("Phone");
        cityName = new ComboBox<>("City Name");
        cinemaName = new ComboBox<>("Cinema Name");
        screenName = new ComboBox<>("Screen Name");
        filmName = new ComboBox<>("Film Name");
        showingDate = new ComboBox<>("Showing Date");
        showingTime = new ComboBox<>("Showing Time");
        seatQuantityUpperHall = new TextField("Seat Quantity Upper Hall");
        seatQuantityUpperHall.setRequiredIndicatorVisible(true);

        seatQuantityLowerHall = new TextField("Seat Quantity Lower Hall");
        seatQuantityLowerHall.setRequiredIndicatorVisible(true);

        seatQuantityCentreHall = new TextField("Seat Quantity Centre Hall");
        seatQuantityCentreHall.setRequiredIndicatorVisible(true);

        ticketNumber = new TextField("Ticket Number");
        ticketNumber.setReadOnly(true);

        totalCost = new TextField("Total Cost");
        totalCost.setReadOnly(true);

        bookingStatus = new TextField("Booking Status");
        bookingStatus.setReadOnly(true);
        formLayout.add(userName, phone, cityName, cinemaName, screenName, filmName, showingDate, showingTime,
                seatQuantityUpperHall, seatQuantityLowerHall, seatQuantityCentreHall, ticketNumber, totalCost, bookingStatus);

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
        seatQuantityUpperHall.addValueChangeListener(e -> updateTotalCost());
        seatQuantityLowerHall.addValueChangeListener(e -> updateTotalCost());
        seatQuantityCentreHall.addValueChangeListener(e -> updateTotalCost());
    }

    private void updateTotalCost() {
        int lowerHallSelectedCount = getIntValue(seatQuantityLowerHall.getValue());
        int upperHallSelectedCount = getIntValue(seatQuantityUpperHall.getValue());
        int vipSelectedCount = getIntValue(seatQuantityCentreHall.getValue());

        int totalTickets = lowerHallSelectedCount + upperHallSelectedCount + vipSelectedCount;
        ticketNumber.setValue(String.valueOf(totalTickets));

        double lowerHallCost = lowerHallSelectedCount * basePrice;
        double upperHallCost = upperHallSelectedCount * (basePrice + basePrice * 0.2);
        double vipCost = vipSelectedCount * ((basePrice + basePrice * 0.2) * 1.2);
        double totalCost = lowerHallCost + upperHallCost + vipCost;
        this.totalCost.setValue(String.valueOf(totalCost));
    }

    private int getIntValue(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        clear.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        buttonLayout.add(save, clear, cancel);
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

    private void populateForm(Booking value) {
        this.booking = value;
        binder.readBean(this.booking);

        if (this.booking != null) {
            bookingStatus.setValue(this.booking.getBookingStatus());
        } else {
            bookingStatus.setValue("");
        }
    }
}
