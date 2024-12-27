package com.example.application.views.paymentform;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.BoxSizing;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.Flex;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.Height;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.ListStyleType;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.MaxWidth;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.Position;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashSet;
import java.util.Set;

@PageTitle("Payment Form")
@Route(value = "payment-form", layout = MainLayout.class)
@RolesAllowed("USER")
public class PaymentFormView extends Div {

    private static final Set<String> states = new LinkedHashSet<>();
    private static final Set<String> countries = new LinkedHashSet<>();

    private TextField cardNumber;
    private TextField cardholderName;
    private Select<Integer> month;
    private Select<Integer> year;
    private ExpirationDateField expiration;
    private PasswordField csc;
//    private Button cancel;
//    private Button submit;

    /**
     * Matches Visa, MasterCard, American Express, Diners Club, Discover, and JCB
     * cards. See https://stackoverflow.com/a/9315696
     */
    private String CARD_REGEX = "^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35d{3})d{11})$";



    public PaymentFormView() {
        addClassNames("payment-form-view");
        addClassNames(Display.FLEX, FlexDirection.ROW, Height.FULL);

        Main content = new Main();
        content.addClassNames(Display.GRID, Gap.XLARGE, AlignItems.START, JustifyContent.CENTER, MaxWidth.SCREEN_MEDIUM,
                Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

        content.add(createCheckoutForm());
        content.add(createAside());
        add(content);
    }

    private Component createCheckoutForm() {
        Section checkoutForm = new Section();
        checkoutForm.addClassNames(Display.FLEX, FlexDirection.COLUMN, Flex.GROW);


        H2 header = new H2("Payment");
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
        Paragraph note = new Paragraph("All fields are required unless otherwise noted");
        note.addClassNames(Margin.Bottom.XLARGE, Margin.Top.NONE, TextColor.SECONDARY);
        checkoutForm.add(header, note);

        checkoutForm.add(createPersonalDetailsSection());
        checkoutForm.add(createPaymentInformationSection());
        checkoutForm.add(new Hr());
        checkoutForm.add(createFooter());

        return checkoutForm;
    }

    private Section createPersonalDetailsSection() {
        Section personalDetails = new Section();
        personalDetails.addClassNames(Display.FLEX, FlexDirection.COLUMN, Margin.Bottom.XLARGE, Margin.Top.MEDIUM);

        Paragraph stepOne = new Paragraph("Step 1/2");
        stepOne.addClassNames(Margin.NONE, FontSize.SMALL, TextColor.SECONDARY);

        H3 header = new H3("Personal details");
        header.addClassNames(Margin.Bottom.MEDIUM, Margin.Top.SMALL, FontSize.XXLARGE);

        TextField price = new TextField("Total Booking Cost");
        price.setRequiredIndicatorVisible(true);
//        price.setPattern("[\\p{L} \\-]+");
        price.addClassNames(Margin.Bottom.SMALL);
        // 其他文本框设置为只读模式
        price.setReadOnly(true);

//        EmailField email = new EmailField("Email address");
//        email.setRequiredIndicatorVisible(true);
//        email.addClassNames(Margin.Bottom.SMALL);
//
//        TextField phone = new TextField("Phone number");
//        phone.setRequiredIndicatorVisible(true);
//        phone.setPattern("[\\d \\-\\+]+");
//        phone.addClassNames(Margin.Bottom.SMALL);
//
//        Checkbox rememberDetails = new Checkbox("Remember personal details for next time");
//        rememberDetails.addClassNames(Margin.Top.SMALL);

        Checkbox checkbox1 = new Checkbox();
        checkbox1.setLabel("Please check that the booking information on the right is correct");


        personalDetails.add(stepOne, header, price, checkbox1);
        return personalDetails;
    }


//    private Component createPaymentInformationSection() {
//        Section paymentInfo = new Section();
//        paymentInfo.addClassNames(Display.FLEX, FlexDirection.COLUMN, Margin.Bottom.XLARGE, Margin.Top.MEDIUM);
//
//        Paragraph stepThree = new Paragraph("Checkout 3/3");
//        stepThree.addClassNames(Margin.NONE, FontSize.SMALL, TextColor.SECONDARY);
//
//        H3 header = new H3("Personal details");
//        header.addClassNames(Margin.Bottom.MEDIUM, Margin.Top.SMALL, FontSize.XXLARGE);
//
//        TextField cardHolder = new TextField("Cardholder name");
//        cardHolder.setRequiredIndicatorVisible(true);
//        cardHolder.setPattern("[\\p{L} \\-]+");
//        cardHolder.addClassNames(Margin.Bottom.SMALL);
//
//        Div subSectionOne = new Div();
//        subSectionOne.addClassNames(Display.FLEX, FlexWrap.WRAP, Gap.MEDIUM);
//
//        TextField cardNumber = new TextField("Card Number");
//        cardNumber.setRequiredIndicatorVisible(true);
//        cardNumber.setPattern("[\\d ]{12,23}");
//        cardNumber.addClassNames(Margin.Bottom.SMALL);
//
//        TextField securityCode = new TextField("Security Code");
//        securityCode.setRequiredIndicatorVisible(true);
//        securityCode.setPattern("[0-9]{3,4}");
//        securityCode.addClassNames(Flex.GROW, Margin.Bottom.SMALL);
//        securityCode.setHelperText("What is this?");
//
//        subSectionOne.add(cardNumber, securityCode);
//
//        Div subSectionTwo = new Div();
//        subSectionTwo.addClassNames(Display.FLEX, FlexWrap.WRAP, Gap.MEDIUM);
//
//        Select<String> expirationMonth = new Select<>();
//        expirationMonth.setLabel("Expiration month");
//        expirationMonth.setRequiredIndicatorVisible(true);
//        expirationMonth.setItems("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
//
//        Select<String> expirationYear = new Select<>();
//        expirationYear.setLabel("Expiration year");
//        expirationYear.setRequiredIndicatorVisible(true);
//        expirationYear.setItems("22", "23", "24", "25", "26");
//
//        subSectionTwo.add(expirationMonth, expirationYear);
//
//        paymentInfo.add(stepThree, header, cardHolder, subSectionTwo);
//        return paymentInfo;
//    }
    private Component createPaymentInformationSection() {
        Section paymentInfo = new Section();
        paymentInfo.addClassNames(Display.FLEX, FlexDirection.COLUMN, Margin.Bottom.XLARGE, Margin.Top.MEDIUM);

        Paragraph stepThree = new Paragraph("Step 2/2");
        stepThree.addClassNames(Margin.NONE, FontSize.SMALL, TextColor.SECONDARY);

        H3 header = new H3("Payment information");
        header.addClassNames(Margin.Bottom.MEDIUM, Margin.Top.SMALL, FontSize.XXLARGE);

        cardNumber = new TextField("Credit card number");
        cardNumber.setPlaceholder("1234 5678 9123 4567");
        cardNumber.setPattern(CARD_REGEX);
        cardNumber.setAllowedCharPattern("[\\d ]");
        cardNumber.setRequired(true);
        cardNumber.setErrorMessage("Please enter a valid credit card number");

        cardholderName = new TextField("Cardholder name");

        month = new Select<>();
        month.setPlaceholder("Month");
        month.setItems(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);

        year = new Select<>();
        year.setPlaceholder("Year");
        year.setItems(20, 21, 22, 23, 24, 25);

        expiration = new ExpirationDateField("Expiration date", month, year);
        csc = new PasswordField("CSC");

        Checkbox checkbox2 = new Checkbox();
        checkbox2.setLabel("Please check the payment information carefully, as tickets cannot be refunded or changed after they have been issued.");

        FormLayout formLayout = new FormLayout();
        formLayout.add(stepThree, header, cardNumber, cardholderName, expiration, csc, checkbox2);

        paymentInfo.add(stepThree, header, formLayout);
        return paymentInfo;

    }

    private Footer createFooter() {
        Footer footer = new Footer();
        footer.addClassNames(Display.FLEX, AlignItems.CENTER, JustifyContent.BETWEEN, Margin.Vertical.MEDIUM);

        Button cancel = new Button("Cancel booking");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        Button pay = new Button("Pay securely", new Icon(VaadinIcon.LOCK));
        pay.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

        footer.add(cancel, pay);
        return footer;
    }

    private Aside createAside() {
        Aside aside = new Aside();
        aside.addClassNames(Background.CONTRAST_5, BoxSizing.BORDER, Padding.LARGE, BorderRadius.LARGE,
                Position.STICKY);


        Header headerSection = new Header();
        headerSection.addClassNames(Display.FLEX, AlignItems.CENTER, JustifyContent.BETWEEN, Margin.Bottom.MEDIUM);
        H3 header = new H3("Order Detail");
        header.addClassNames(Margin.NONE);
        Button edit = new Button("Edit");
        edit.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        headerSection.add(header, edit);

        UnorderedList ul = new UnorderedList();
        ul.addClassNames(ListStyleType.NONE, Margin.NONE, Padding.NONE, Display.FLEX, FlexDirection.ROW, Gap.MEDIUM);

        ul.add(createListItem("Tetris", "Screen 1", "2024-05-15 15:00", "VIP", "2", "$28.80"));
//        ul.add(createListItem("NYAD", "Screen 2", "2024-05-16 18:00", "Regular", "1", "$10.00"));

        aside.add(headerSection, ul);
        return aside;
    }

    private ListItem createListItem(String filmName, String cinema, String filmDateTime, String ticketType, String ticketNumber, String totalBookingCost) {
        ListItem item = new ListItem();
        item.addClassNames(Display.FLEX, JustifyContent.BETWEEN);

        Div subSection = new Div();
        subSection.addClassNames(Display.FLEX, FlexDirection.COLUMN);

        subSection.add(new Span("Film Name: " + filmName));
        subSection.add(new Span("Cinema: " + cinema));
        subSection.add(new Span("Film DateTime: " + filmDateTime));
        subSection.add(new Span("Ticket Type: " + ticketType));
        subSection.add(new Span("Ticket Number: " + ticketNumber));
        subSection.add(new Span("Total Booking Cost: " + totalBookingCost));

        item.add(subSection);
        return item;
    }

    private class ExpirationDateField extends CustomField<String> {
        public ExpirationDateField(String label, Select<Integer> month, Select<Integer> year) {
            setLabel(label);
            HorizontalLayout layout = new HorizontalLayout(month, year);
            layout.setFlexGrow(1.0, month, year);
            month.setWidth("100px");
            year.setWidth("100px");
            add(layout);
        }

        @Override
        protected String generateModelValue() {
            // Unused as month and year fields part are of the outer class
            return "";
        }

        @Override
        protected void setPresentationValue(String newPresentationValue) {
            // Unused as month and year fields part are of the outer class
        }

    }


}
