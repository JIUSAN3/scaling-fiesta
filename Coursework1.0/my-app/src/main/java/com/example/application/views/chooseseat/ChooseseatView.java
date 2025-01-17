package com.example.application.views.chooseseat;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import jakarta.annotation.security.RolesAllowed;

@PageTitle("Choose seat")
@Route(value = "Choose-seat", layout = MainLayout.class)
@RolesAllowed("USER")
@Uses(Icon.class)
public class ChooseseatView extends Composite<VerticalLayout> {

    public ChooseseatView() {
        H2 h2 = new H2();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        VerticalLayout layoutColumn3 = new VerticalLayout();
        H6 h6 = new H6();
        VerticalLayout layoutColumn4 = new VerticalLayout();
        H4 h4 = new H4();
        H6 h62 = new H6();
        HorizontalLayout layoutRow = new HorizontalLayout();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        Checkbox checkbox = new Checkbox();
        Checkbox checkbox2 = new Checkbox();
        Checkbox checkbox3 = new Checkbox();
        Checkbox checkbox4 = new Checkbox();
        Checkbox checkbox5 = new Checkbox();
        Checkbox checkbox6 = new Checkbox();
        Checkbox checkbox7 = new Checkbox();
        Checkbox checkbox8 = new Checkbox();
        HorizontalLayout layoutRow3 = new HorizontalLayout();
        HorizontalLayout layoutRow4 = new HorizontalLayout();
        Checkbox checkbox9 = new Checkbox();
        Checkbox checkbox10 = new Checkbox();
        Checkbox checkbox11 = new Checkbox();
        Checkbox checkbox12 = new Checkbox();
        Checkbox checkbox13 = new Checkbox();
        Checkbox checkbox14 = new Checkbox();
        Checkbox checkbox15 = new Checkbox();
        Checkbox checkbox16 = new Checkbox();
        HorizontalLayout layoutRow5 = new HorizontalLayout();
        HorizontalLayout layoutRow6 = new HorizontalLayout();
        Checkbox checkbox17 = new Checkbox();
        Checkbox checkbox18 = new Checkbox();
        Checkbox checkbox19 = new Checkbox();
        Checkbox checkbox20 = new Checkbox();
        Checkbox checkbox21 = new Checkbox();
        Checkbox checkbox22 = new Checkbox();
        Checkbox checkbox23 = new Checkbox();
        Checkbox checkbox24 = new Checkbox();
        Hr hr = new Hr();
        H6 h63 = new H6();
        HorizontalLayout layoutRow7 = new HorizontalLayout();
        HorizontalLayout layoutRow8 = new HorizontalLayout();
        Checkbox checkbox25 = new Checkbox();
        Checkbox checkbox26 = new Checkbox();
        Checkbox checkbox27 = new Checkbox();
        Checkbox checkbox28 = new Checkbox();
        Checkbox checkbox29 = new Checkbox();
        Checkbox checkbox30 = new Checkbox();
        Checkbox checkbox31 = new Checkbox();
        Checkbox checkbox32 = new Checkbox();
        HorizontalLayout layoutRow9 = new HorizontalLayout();
        HorizontalLayout layoutRow10 = new HorizontalLayout();
        Checkbox checkbox33 = new Checkbox();
        Checkbox checkbox34 = new Checkbox();
        Checkbox checkbox35 = new Checkbox();
        Checkbox checkbox36 = new Checkbox();
        Checkbox checkbox37 = new Checkbox();
        Checkbox checkbox38 = new Checkbox();
        Checkbox checkbox39 = new Checkbox();
        Checkbox checkbox40 = new Checkbox();
        HorizontalLayout layoutRow11 = new HorizontalLayout();
        HorizontalLayout layoutRow12 = new HorizontalLayout();
        Checkbox checkbox41 = new Checkbox();
        Checkbox checkbox42 = new Checkbox();
        Checkbox checkbox43 = new Checkbox();
        Checkbox checkbox44 = new Checkbox();
        Checkbox checkbox45 = new Checkbox();
        Checkbox checkbox46 = new Checkbox();
        Checkbox checkbox47 = new Checkbox();
        Checkbox checkbox48 = new Checkbox();
        HorizontalLayout layoutRow13 = new HorizontalLayout();
        HorizontalLayout layoutRow14 = new HorizontalLayout();
        Checkbox checkbox49 = new Checkbox();
        Checkbox checkbox50 = new Checkbox();
        Checkbox checkbox51 = new Checkbox();
        Checkbox checkbox52 = new Checkbox();
        Checkbox checkbox53 = new Checkbox();
        Checkbox checkbox54 = new Checkbox();
        Checkbox checkbox55 = new Checkbox();
        Checkbox checkbox56 = new Checkbox();
        HorizontalLayout layoutRow15 = new HorizontalLayout();
        HorizontalLayout layoutRow16 = new HorizontalLayout();
        Checkbox checkbox57 = new Checkbox();
        Checkbox checkbox58 = new Checkbox();
        Checkbox checkbox59 = new Checkbox();
        Checkbox checkbox60 = new Checkbox();
        Checkbox checkbox61 = new Checkbox();
        VerticalLayout layoutColumn5 = new VerticalLayout();
        HorizontalLayout layoutRow17 = new HorizontalLayout();
        Checkbox checkbox62 = new Checkbox();
        Checkbox checkbox63 = new Checkbox();
        Checkbox checkbox64 = new Checkbox();
        Checkbox checkbox65 = new Checkbox();
        Checkbox checkbox66 = new Checkbox();
        Button buttonPrimary = new Button();
        getContent().addClassName(Gap.XSMALL);
        getContent().addClassName(Padding.XSMALL);
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        h2.setText("Film Name");
        getContent().setAlignSelf(FlexComponent.Alignment.CENTER, h2);
        h2.setWidth("max-content");
        layoutColumn2.setWidthFull();
        getContent().setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.addClassName(Gap.XSMALL);
        layoutColumn2.addClassName(Padding.XSMALL);
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        layoutColumn3.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutColumn3);
        layoutColumn3.setSpacing(false);
        layoutColumn3.addClassName(Padding.SMALL);
        layoutColumn3.setWidth("100%");
        layoutColumn3.setHeight("min-content");
        h6.setText("Disabled means that the seat is not available, please select an available seat!");
        layoutColumn3.setAlignSelf(FlexComponent.Alignment.CENTER, h6);
        h6.setWidth("max-content");
        layoutColumn4.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutColumn4);
        layoutColumn4.addClassName(Padding.XSMALL);
        layoutColumn4.setWidth("100%");
        layoutColumn4.setHeight("min-content");
        h4.setText("Screen Orientation");
        layoutColumn4.setAlignSelf(FlexComponent.Alignment.CENTER, h4);
        h4.setWidth("max-content");
        h62.setText("lower hall");
        h62.setWidth("max-content");
        layoutRow.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        layoutRow2.setHeightFull();
        layoutRow.setFlexGrow(1.0, layoutRow2);
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.getStyle().set("flex-grow", "1");
        layoutRow2.setAlignItems(Alignment.CENTER);
        layoutRow2.setJustifyContentMode(JustifyContentMode.CENTER);
        checkbox.setLabel("1-1");
        checkbox.setWidth("min-content");
        checkbox.setMinWidth("75px");
        checkbox2.setLabel("1-2");
        checkbox2.setWidth("min-content");
        checkbox2.setMinWidth("75px");
        checkbox3.setLabel("1-3");
        checkbox3.setWidth("min-content");
        checkbox3.setMinWidth("75px");
        checkbox4.setLabel("1-4");
        checkbox4.setWidth("min-content");
        checkbox4.setMinWidth("75px");
        checkbox5.setLabel("1-5");
        checkbox5.setWidth("min-content");
        checkbox5.setMinWidth("75px");
        checkbox6.setLabel("1-6");
        checkbox6.setWidth("min-content");
        checkbox6.setMinWidth("75px");
        checkbox7.setLabel("1-7");
        checkbox7.setWidth("min-content");
        checkbox7.setMinWidth("75px");
        checkbox8.setLabel("1-8");
        checkbox8.setWidth("min-content");
        checkbox8.setMinWidth("75px");
        layoutRow3.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow3);
        layoutRow3.addClassName(Gap.MEDIUM);
        layoutRow3.setWidth("100%");
        layoutRow3.getStyle().set("flex-grow", "1");
        layoutRow4.setHeightFull();
        layoutRow3.setFlexGrow(1.0, layoutRow4);
        layoutRow4.addClassName(Gap.MEDIUM);
        layoutRow4.setWidth("100%");
        layoutRow4.getStyle().set("flex-grow", "1");
        layoutRow4.setAlignItems(Alignment.CENTER);
        layoutRow4.setJustifyContentMode(JustifyContentMode.CENTER);
        checkbox9.setLabel("2-1");
        checkbox9.setWidth("min-content");
        checkbox9.setMinWidth("75px");
        checkbox10.setLabel("2-2");
        checkbox10.setWidth("min-content");
        checkbox10.setMinWidth("75px");
        checkbox11.setLabel("2-3");
        checkbox11.setWidth("min-content");
        checkbox11.setMinWidth("75px");
        checkbox12.setLabel("2-4");
        checkbox12.setWidth("min-content");
        checkbox12.setMinWidth("75px");
        checkbox13.setLabel("2-5");
        checkbox13.setWidth("min-content");
        checkbox13.setMinWidth("75px");
        checkbox14.setLabel("2-6");
        checkbox14.setWidth("min-content");
        checkbox14.setMinWidth("75px");
        checkbox15.setLabel("2-7");
        checkbox15.setWidth("min-content");
        checkbox15.setMinWidth("75px");
        checkbox16.setLabel("2-8");
        checkbox16.setWidth("min-content");
        checkbox16.setMinWidth("75px");
        layoutRow5.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow5);
        layoutRow5.addClassName(Gap.MEDIUM);
        layoutRow5.setWidth("100%");
        layoutRow5.getStyle().set("flex-grow", "1");
        layoutRow6.setHeightFull();
        layoutRow5.setFlexGrow(1.0, layoutRow6);
        layoutRow6.addClassName(Gap.MEDIUM);
        layoutRow6.setWidth("100%");
        layoutRow6.getStyle().set("flex-grow", "1");
        layoutRow6.setAlignItems(Alignment.CENTER);
        layoutRow6.setJustifyContentMode(JustifyContentMode.CENTER);
        checkbox17.setLabel("3-1");
        checkbox17.setWidth("min-content");
        checkbox17.setMinWidth("75px");
        checkbox18.setLabel("3-2");
        checkbox18.setWidth("min-content");
        checkbox18.setMinWidth("75px");
        checkbox19.setLabel("3-3");
        checkbox19.setWidth("min-content");
        checkbox19.setMinWidth("75px");
        checkbox20.setLabel("3-4");
        checkbox20.setWidth("min-content");
        checkbox20.setMinWidth("75px");
        checkbox21.setLabel("3-5");
        checkbox21.setWidth("min-content");
        checkbox21.setMinWidth("75px");
        checkbox22.setLabel("3-6");
        checkbox22.setWidth("min-content");
        checkbox22.setMinWidth("75px");
        checkbox23.setLabel("3-7");
        checkbox23.setWidth("min-content");
        checkbox23.setMinWidth("75px");
        checkbox24.setLabel("3-8");
        checkbox24.setWidth("min-content");
        checkbox24.setMinWidth("75px");
        h63.setText("upper gallery");
        h63.setWidth("max-content");
        layoutRow7.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow7);
        layoutRow7.addClassName(Gap.MEDIUM);
        layoutRow7.setWidth("100%");
        layoutRow7.getStyle().set("flex-grow", "1");
        layoutRow8.setHeightFull();
        layoutRow7.setFlexGrow(1.0, layoutRow8);
        layoutRow8.addClassName(Gap.MEDIUM);
        layoutRow8.setWidth("100%");
        layoutRow8.getStyle().set("flex-grow", "1");
        layoutRow8.setAlignItems(Alignment.CENTER);
        layoutRow8.setJustifyContentMode(JustifyContentMode.CENTER);
        checkbox25.setLabel("4-1");
        checkbox25.setWidth("min-content");
        checkbox25.setMinWidth("75px");
        checkbox26.setLabel("4-2");
        checkbox26.setWidth("min-content");
        checkbox26.setMinWidth("75px");
        checkbox27.setLabel("4-3");
        checkbox27.setWidth("min-content");
        checkbox27.setMinWidth("75px");
        checkbox28.setLabel("4-4");
        checkbox28.setWidth("min-content");
        checkbox28.setMinWidth("75px");
        checkbox29.setLabel("4-5");
        checkbox29.setWidth("min-content");
        checkbox29.setMinWidth("75px");
        checkbox30.setLabel("4-6");
        checkbox30.setWidth("min-content");
        checkbox30.setMinWidth("75px");
        checkbox31.setLabel("4-7");
        checkbox31.setWidth("min-content");
        checkbox31.setMinWidth("75px");
        checkbox32.setLabel("4-8");
        checkbox32.setWidth("min-content");
        checkbox32.setMinWidth("75px");
        layoutRow9.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow9);
        layoutRow9.addClassName(Gap.MEDIUM);
        layoutRow9.setWidth("100%");
        layoutRow9.getStyle().set("flex-grow", "1");
        layoutRow10.setHeightFull();
        layoutRow9.setFlexGrow(1.0, layoutRow10);
        layoutRow10.addClassName(Gap.MEDIUM);
        layoutRow10.setWidth("100%");
        layoutRow10.getStyle().set("flex-grow", "1");
        layoutRow10.setAlignItems(Alignment.CENTER);
        layoutRow10.setJustifyContentMode(JustifyContentMode.CENTER);
        checkbox33.setLabel("5-1");
        checkbox33.setWidth("min-content");
        checkbox33.setMinWidth("75px");
        checkbox34.setLabel("5-2");
        checkbox34.setWidth("min-content");
        checkbox34.setMinWidth("75px");
        checkbox35.setLabel("5-3");
        checkbox35.setWidth("min-content");
        checkbox35.setMinWidth("75px");
        checkbox36.setLabel("5-4");
        checkbox36.setWidth("min-content");
        checkbox36.setMinWidth("75px");
        checkbox37.setLabel("5-5");
        checkbox37.setWidth("min-content");
        checkbox37.setMinWidth("75px");
        checkbox38.setLabel("5-6");
        checkbox38.setWidth("min-content");
        checkbox38.setMinWidth("75px");
        checkbox39.setLabel("5-7");
        checkbox39.setWidth("min-content");
        checkbox39.setMinWidth("75px");
        checkbox40.setLabel("5-8");
        checkbox40.setWidth("min-content");
        checkbox40.setMinWidth("75px");
        layoutRow11.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow11);
        layoutRow11.addClassName(Gap.MEDIUM);
        layoutRow11.setWidth("100%");
        layoutRow11.getStyle().set("flex-grow", "1");
        layoutRow12.setHeightFull();
        layoutRow11.setFlexGrow(1.0, layoutRow12);
        layoutRow12.addClassName(Gap.MEDIUM);
        layoutRow12.setWidth("100%");
        layoutRow12.getStyle().set("flex-grow", "1");
        layoutRow12.setAlignItems(Alignment.CENTER);
        layoutRow12.setJustifyContentMode(JustifyContentMode.CENTER);
        checkbox41.setLabel("6-1");
        checkbox41.setWidth("min-content");
        checkbox41.setMinWidth("75px");
        checkbox42.setLabel("6-2");
        checkbox42.setWidth("min-content");
        checkbox42.setMinWidth("75px");
        checkbox43.setLabel("6-3");
        checkbox43.setWidth("min-content");
        checkbox43.setMinWidth("75px");
        checkbox44.setLabel("6-4");
        checkbox44.setWidth("min-content");
        checkbox44.setMinWidth("75px");
        checkbox45.setLabel("6-5");
        checkbox45.setWidth("min-content");
        checkbox45.setMinWidth("75px");
        checkbox46.setLabel("6-6");
        checkbox46.setWidth("min-content");
        checkbox46.setMinWidth("75px");
        checkbox47.setLabel("6-7");
        checkbox47.setWidth("min-content");
        checkbox47.setMinWidth("75px");
        checkbox48.setLabel("6-8");
        checkbox48.setWidth("min-content");
        checkbox48.setMinWidth("75px");
        layoutRow13.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow13);
        layoutRow13.addClassName(Gap.MEDIUM);
        layoutRow13.setWidth("100%");
        layoutRow13.getStyle().set("flex-grow", "1");
        layoutRow14.setHeightFull();
        layoutRow13.setFlexGrow(1.0, layoutRow14);
        layoutRow14.addClassName(Gap.MEDIUM);
        layoutRow14.setWidth("100%");
        layoutRow14.getStyle().set("flex-grow", "1");
        layoutRow14.setAlignItems(Alignment.CENTER);
        layoutRow14.setJustifyContentMode(JustifyContentMode.CENTER);
        checkbox49.setLabel("7-1");
        checkbox49.setWidth("min-content");
        checkbox49.setMinWidth("75px");
        checkbox50.setLabel("7-2");
        checkbox50.setWidth("min-content");
        checkbox50.setMinWidth("75px");
        checkbox51.setLabel("7-3");
        checkbox51.setWidth("min-content");
        checkbox51.setMinWidth("75px");
        checkbox52.setLabel("7-4");
        checkbox52.setWidth("min-content");
        checkbox52.setMinWidth("75px");
        checkbox53.setLabel("7-5");
        checkbox53.setWidth("min-content");
        checkbox53.setMinWidth("75px");
        checkbox54.setLabel("7-6");
        checkbox54.setWidth("min-content");
        checkbox54.setMinWidth("75px");
        checkbox55.setLabel("7-7");
        checkbox55.setWidth("min-content");
        checkbox55.setMinWidth("75px");
        checkbox56.setLabel("7-8");
        checkbox56.setWidth("min-content");
        checkbox56.setMinWidth("75px");
        layoutRow15.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow15);
        layoutRow15.addClassName(Gap.MEDIUM);
        layoutRow15.setWidth("100%");
        layoutRow15.getStyle().set("flex-grow", "1");
        layoutRow16.setHeightFull();
        layoutRow15.setFlexGrow(1.0, layoutRow16);
        layoutRow16.addClassName(Gap.MEDIUM);
        layoutRow16.setWidth("100%");
        layoutRow16.getStyle().set("flex-grow", "1");
        layoutRow16.setAlignItems(Alignment.CENTER);
        layoutRow16.setJustifyContentMode(JustifyContentMode.CENTER);
        checkbox57.setLabel("8-1");
        checkbox57.setWidth("min-content");
        checkbox57.setMinWidth("75px");
        checkbox57.getStyle().set("background-color", "yellow");
        checkbox58.setLabel("8-2");
        checkbox58.setWidth("min-content");
        checkbox58.setMinWidth("75px");
        checkbox58.getStyle().set("background-color", "yellow");
        checkbox59.setLabel("8-3");
        checkbox59.setWidth("min-content");
        checkbox59.setMinWidth("75px");
        checkbox59.getStyle().set("background-color", "yellow");
        checkbox60.setLabel("8-4");
        checkbox60.setWidth("min-content");
        checkbox60.setMinWidth("75px");
        checkbox60.getStyle().set("background-color", "yellow");
        checkbox61.setLabel("8-5");
        checkbox61.setWidth("min-content");
        checkbox61.setMinWidth("75px");
        checkbox61.getStyle().set("background-color", "yellow");
        layoutColumn5.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutColumn5);
        layoutColumn5.setWidth("100%");
        layoutColumn5.getStyle().set("flex-grow", "1");
        layoutRow17.setWidthFull();
        layoutColumn5.setFlexGrow(1.0, layoutRow17);
        layoutRow17.addClassName(Gap.MEDIUM);
        layoutRow17.setWidth("100%");
        layoutRow17.getStyle().set("flex-grow", "1");
        layoutRow17.setAlignItems(Alignment.CENTER);
        layoutRow17.setJustifyContentMode(JustifyContentMode.CENTER);
        checkbox62.setLabel("9-1");
        checkbox62.setWidth("min-content");
        checkbox62.setMinWidth("75px");
        checkbox62.getStyle().set("background-color", "yellow");
        checkbox63.setLabel("9-2");
        checkbox63.setWidth("min-content");
        checkbox63.setMinWidth("75px");
        checkbox63.getStyle().set("background-color", "yellow");
        checkbox64.setLabel("9-3");
        checkbox64.setWidth("min-content");
        checkbox64.setMinWidth("75px");
        checkbox64.getStyle().set("background-color", "yellow");
        checkbox65.setLabel("9-4");
        checkbox65.setWidth("min-content");
        checkbox65.setMinWidth("75px");
        checkbox65.getStyle().set("background-color", "yellow");
        checkbox66.setLabel("9-5");
        checkbox66.setWidth("min-content");
        checkbox66.setMinWidth("75px");
        checkbox66.getStyle().set("background-color", "yellow");
        buttonPrimary.setText("Confirm Seat");
        layoutColumn2.setAlignSelf(FlexComponent.Alignment.CENTER, buttonPrimary);
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        getContent().add(h2);
        getContent().add(layoutColumn2);
        layoutColumn2.add(layoutColumn3);
        layoutColumn3.add(h6);
        layoutColumn2.add(layoutColumn4);
        layoutColumn4.add(h4);
        layoutColumn2.add(h62);
        layoutColumn2.add(layoutRow);
        layoutRow.add(layoutRow2);
        layoutRow2.add(checkbox);
        layoutRow2.add(checkbox2);
        layoutRow2.add(checkbox3);
        layoutRow2.add(checkbox4);
        layoutRow2.add(checkbox5);
        layoutRow2.add(checkbox6);
        layoutRow2.add(checkbox7);
        layoutRow2.add(checkbox8);
        layoutColumn2.add(layoutRow3);
        layoutRow3.add(layoutRow4);
        layoutRow4.add(checkbox9);
        layoutRow4.add(checkbox10);
        layoutRow4.add(checkbox11);
        layoutRow4.add(checkbox12);
        layoutRow4.add(checkbox13);
        layoutRow4.add(checkbox14);
        layoutRow4.add(checkbox15);
        layoutRow4.add(checkbox16);
        layoutColumn2.add(layoutRow5);
        layoutRow5.add(layoutRow6);
        layoutRow6.add(checkbox17);
        layoutRow6.add(checkbox18);
        layoutRow6.add(checkbox19);
        layoutRow6.add(checkbox20);
        layoutRow6.add(checkbox21);
        layoutRow6.add(checkbox22);
        layoutRow6.add(checkbox23);
        layoutRow6.add(checkbox24);
        layoutColumn2.add(hr);
        layoutColumn2.add(h63);
        layoutColumn2.add(layoutRow7);
        layoutRow7.add(layoutRow8);
        layoutRow8.add(checkbox25);
        layoutRow8.add(checkbox26);
        layoutRow8.add(checkbox27);
        layoutRow8.add(checkbox28);
        layoutRow8.add(checkbox29);
        layoutRow8.add(checkbox30);
        layoutRow8.add(checkbox31);
        layoutRow8.add(checkbox32);
        layoutColumn2.add(layoutRow9);
        layoutRow9.add(layoutRow10);
        layoutRow10.add(checkbox33);
        layoutRow10.add(checkbox34);
        layoutRow10.add(checkbox35);
        layoutRow10.add(checkbox36);
        layoutRow10.add(checkbox37);
        layoutRow10.add(checkbox38);
        layoutRow10.add(checkbox39);
        layoutRow10.add(checkbox40);
        layoutColumn2.add(layoutRow11);
        layoutRow11.add(layoutRow12);
        layoutRow12.add(checkbox41);
        layoutRow12.add(checkbox42);
        layoutRow12.add(checkbox43);
        layoutRow12.add(checkbox44);
        layoutRow12.add(checkbox45);
        layoutRow12.add(checkbox46);
        layoutRow12.add(checkbox47);
        layoutRow12.add(checkbox48);
        layoutColumn2.add(layoutRow13);
        layoutRow13.add(layoutRow14);
        layoutRow14.add(checkbox49);
        layoutRow14.add(checkbox50);
        layoutRow14.add(checkbox51);
        layoutRow14.add(checkbox52);
        layoutRow14.add(checkbox53);
        layoutRow14.add(checkbox54);
        layoutRow14.add(checkbox55);
        layoutRow14.add(checkbox56);
        layoutColumn2.add(layoutRow15);
        layoutRow15.add(layoutRow16);
        layoutRow16.add(checkbox57);
        layoutRow16.add(checkbox58);
        layoutRow16.add(checkbox59);
        layoutRow16.add(checkbox60);
        layoutRow16.add(checkbox61);
        layoutColumn2.add(layoutColumn5);
        layoutColumn5.add(layoutRow17);
        layoutRow17.add(checkbox62);
        layoutRow17.add(checkbox63);
        layoutRow17.add(checkbox64);
        layoutRow17.add(checkbox65);
        layoutRow17.add(checkbox66);
        layoutColumn2.add(buttonPrimary);
    }
}
