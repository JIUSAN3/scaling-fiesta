package com.example.application.views.receipt;

import com.example.application.components.avataritem.AvatarItem;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.RolesAllowed;


@PageTitle("Booking Cancellation")
@Route(value = "Booking-Cancel", layout = MainLayout.class)
//@RouteAlias(value = "BookingCancel", layout = MainLayout.class)
@RolesAllowed("USER")
@Uses(Icon.class)
public class ReceiptView extends Composite<VerticalLayout> {

    public ReceiptView() {
        VerticalLayout layoutColumn2 = new VerticalLayout();
        HorizontalLayout layoutRow = new HorizontalLayout();
        H1 h1 = new H1();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        AvatarItem avatarItem = new AvatarItem();
        FormLayout formLayout3Col = new FormLayout();
        TextField textField = new TextField();
        Button buttonPrimary = new Button();
        HorizontalLayout layoutRow3 = new HorizontalLayout();
        Button buttonSecondary = new Button();
        TextField textField2 = new TextField();
        Hr hr = new Hr();
        H2 h2 = new H2();
        VerticalLayout layoutColumn3 = new VerticalLayout();
        FormLayout formLayout3Col2 = new FormLayout();
        TextField textField3 = new TextField();
        TextField textField4 = new TextField();
        TextField textField5 = new TextField();
        TextField textField6 = new TextField();
        TextField textField7 = new TextField();
        TextField textField8 = new TextField();
        TextField textField9 = new TextField();
        TextField textField10 = new TextField();
        TextField textField11 = new TextField();
        TextField textField12 = new TextField();
        TextField textField13 = new TextField();
//        Button buttonPrimary2 = new Button();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        layoutRow.addClassName(LumoUtility.Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.setHeight("min-content");
        h1.setText("Horizon System");
        h1.setWidth("max-content");
        layoutRow2.setHeightFull();
        layoutRow.setFlexGrow(1.0, layoutRow2);
        layoutRow2.addClassName(LumoUtility.Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.getStyle().set("flex-grow", "1");
        layoutRow2.setAlignItems(FlexComponent.Alignment.CENTER);
        layoutRow2.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        avatarItem.setWidth("min-content");
        setAvatarItemSampleData(avatarItem);
        formLayout3Col.setWidth("100%");
        formLayout3Col.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1), new FormLayout.ResponsiveStep("250px", 2),
                new FormLayout.ResponsiveStep("500px", 3));
        textField.setLabel("BookingID：");
        textField.setWidth("min-content");
        buttonPrimary.setText("Search");
        buttonPrimary.setWidth("100%");
        buttonPrimary.setMinWidth("80px");
        buttonPrimary.setMaxWidth("50px");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        layoutRow3.setHeightFull();
//        formLayout3Col.setFlexGrow(1.0, layoutRow3);
        layoutRow3.addClassName(LumoUtility.Gap.MEDIUM);
        layoutRow3.setWidth("100%");
        layoutRow3.getStyle().set("flex-grow", "1");
        buttonSecondary.setText("Cancel Booking");
        layoutRow3.setAlignSelf(FlexComponent.Alignment.END, buttonSecondary);
        buttonSecondary.setWidth("min-content");
        buttonSecondary.setMinWidth("280px");
        buttonSecondary.setMaxWidth("280px");

        //取消预订按钮弹出确认对话框
        buttonSecondary.addClickListener(event -> {
            ConfirmDialog dialog = new ConfirmDialog();
            dialog.setHeader("Confirm Booking Cancellation");
            dialog.setText("Are you sure you want to cancel this booking?");

            dialog.setConfirmText("Yes, Cancel");
            dialog.addConfirmListener(e -> {
                // 在这里执行取消预订操作，比如发送请求到后端
                setStatus("Canceled"); // 设置状态为已取消
//                getPage().reload(); // 刷新页面
            });

            dialog.setCancelText("No, Keep Booking");
            dialog.addCancelListener(e -> {
                dialog.close(); // 关闭对话框
            });

            dialog.open(); // 打开确认对话框
        });

        textField2.setLabel("Booking Status：");
        textField2.setWidth("min-content");
        h2.setText("Booking Receipt");
        h2.setWidth("max-content");
        layoutColumn3.setWidth("100%");
        layoutColumn3.getStyle().set("flex-grow", "1");
        formLayout3Col2.setWidth("100%");
        formLayout3Col2.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1), new FormLayout.ResponsiveStep("250px", 2),
                new FormLayout.ResponsiveStep("500px", 3));
        textField3.setLabel("Booking ReferenceID：");
        textField3.setWidth("min-content");
        textField4.setLabel("Film Name：");
        textField4.setWidth("min-content");
        textField12.setLabel("City："); // 新增城市文本框
        textField12.setWidth("min-content");
        textField13.setLabel("Cinema："); // 新增影院文本框
        textField13.setWidth("min-content");
        textField5.setLabel("Film Date：");
        textField5.setWidth("min-content");
        textField6.setLabel("Showing Time：");
        textField6.setWidth("min-content");
        textField7.setLabel("Screen：");
        textField7.setWidth("min-content");
        textField8.setLabel("Ticket Number：");
        textField8.setWidth("min-content");
        textField9.setLabel("Seat Number:");
        textField9.setWidth("min-content");
        textField10.setLabel("Booking Date:");
        textField10.setWidth("min-content");
        textField11.setLabel("Total Booking Cost：");
        textField11.setWidth("min-content");
//        buttonPrimary2.setText("Download Receipts");
//        buttonPrimary2.setWidth("100%");
//        buttonPrimary2.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // 其他文本框设置为只读模式
        textField2.setReadOnly(true);
        textField3.setReadOnly(true);
        textField4.setReadOnly(true);
        textField5.setReadOnly(true);
        textField6.setReadOnly(true);
        textField7.setReadOnly(true);
        textField8.setReadOnly(true);
        textField9.setReadOnly(true);
        textField10.setReadOnly(true);
        textField11.setReadOnly(true);
        textField12.setReadOnly(true);
        textField13.setReadOnly(true);

        getContent().add(layoutColumn2);
        layoutColumn2.add(layoutRow);
        layoutRow.add(h1);
        layoutRow.add(layoutRow2);
        layoutRow2.add(avatarItem);
        layoutColumn2.add(formLayout3Col);
        formLayout3Col.add(textField);
        formLayout3Col.add(buttonPrimary);
        formLayout3Col.add(layoutRow3);
        layoutRow3.add(buttonSecondary);
        layoutRow3.add(textField2);
        layoutColumn2.add(hr);
        layoutColumn2.add(h2);
        layoutColumn2.add(layoutColumn3);
        layoutColumn3.add(formLayout3Col2);
        formLayout3Col2.add(textField3);
        formLayout3Col2.add(textField4);
        formLayout3Col2.add(textField12);
        formLayout3Col2.add(textField13);
        formLayout3Col2.add(textField5);
        formLayout3Col2.add(textField6);
        formLayout3Col2.add(textField7);
        formLayout3Col2.add(textField8);
        formLayout3Col2.add(textField9);
        formLayout3Col2.add(textField10);
        formLayout3Col2.add(textField11);
//        layoutColumn2.add(buttonPrimary2);
    }

    //刷新页面代码
//    private IFrame getPage() {
//        return null;
//    }

    //更新订单状态
    private void setStatus(String canceled) {
    }

    private void setAvatarItemSampleData(AvatarItem avatarItem) {
        avatarItem.setHeading("Aria Bailey");
        avatarItem.setDescription("Endocrinologist");
        avatarItem.setAvatar(new Avatar("Aria Bailey"));
    }
}
