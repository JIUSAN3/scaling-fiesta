package com.example.application.views.bookingfilm;

import com.example.application.data.entity.Film;
import com.example.application.services.BookingService;
import com.example.application.services.FilmService;
import com.example.application.services.UserService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import jakarta.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@PageTitle("Booking Film")
@Route(value = "booking-film", layout = MainLayout.class)
//@RouteAlias(value = "booking", layout = MainLayout.class)
@RolesAllowed("USER")
@Uses(Icon.class)
public class BookingFilmView extends Composite<VerticalLayout> implements  HasUrlParameter<Long> {
    RadioButtonGroup<String> ticketTypeGroup = new RadioButtonGroup<>();
    private final BookingService bookingService;
    private final FilmService filmService;
    private UserService userService;
    private String userId;
    private final TextField readonlyField;
    private final ComboBox<String> cinemaComboBox;

    public BookingFilmView(BookingService bookingService, FilmService filmService, UserService userDetailService) {
        this.bookingService = bookingService;
        this.filmService = filmService;
        this.userService = userService;
        this.readonlyField = new TextField();
        this.cinemaComboBox = new ComboBox<>();
        constructUI();
    }

    private void constructUI(){
        // 创建水平布局，用于页面顶部内容
        HorizontalLayout layoutRow = new HorizontalLayout();
        // 创建大标题
        H1 h1 = new H1();
        // 创建垂直布局，用于页面主要内容
        VerticalLayout layoutColumn2 = new VerticalLayout();
        // 创建次级标题
        H3 h3 = new H3();
        // 创建另一个垂直布局，用于容纳输入表单和选择框等元素
        VerticalLayout layoutColumn3 = new VerticalLayout();
        // 创建文本输入框，用于输入电影名称
//        TextField readonlyField = new TextField();
        // 创建表单布局，用于容纳后续的表单元素
        FormLayout formLayout2Col = new FormLayout();
        // 创建一个下拉框，用于选择cinema
//        ComboBox comboBox = new ComboBox();
        // 创建两个下拉框，用于选择日期和时间
        ComboBox comboBox3 = new ComboBox();
        ComboBox comboBox4 = new ComboBox();
        // 创建两个文本输入框，用于输入姓名和电话号码
        TextField textField2 = new TextField();
        TextField textField3 = new TextField();
        // 创建三个可输入文本框，用于选择上层，中层，下层座位数量
        TextField seatLowerHall = new TextField();
        TextField seatCentreHall = new TextField();
        TextField seatUpperHall = new TextField();
        // 创建单选框组
        RadioButtonGroup<String> radioButtonGroup = new RadioButtonGroup<>();
        // 创建另一个水平布局，用于容纳保存按钮
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        // 创建保存按钮
        Button buttonPrimary = new Button();

        // 设置页面布局的样式和属性
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);

        // 设置顶部水平布局的样式和属性
        layoutRow.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.XLARGE);
        layoutRow.setWidth("100%");
        layoutRow.setHeight("min-content");

        // 设置大标题的文本内容和样式
        h1.setText("Horizon System");
        layoutRow.setAlignSelf(Alignment.CENTER, h1);
        h1.getStyle().set("flex-grow", "1");

        // 设置垂直布局的样式和属性
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");
        h3.setText("Booking Film");
        h3.setWidth("100%");

        // 设置次级标题的文本内容和样式
        layoutColumn3.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutColumn3);
        layoutColumn3.addClassName(Gap.XSMALL);
        layoutColumn3.addClassName(Padding.XSMALL);
        layoutColumn3.setWidth("100%");
        layoutColumn3.getStyle().set("flex-grow", "1");

        // 设置另一个垂直布局的样式和属性
        readonlyField.setReadOnly(true);
        readonlyField.setLabel("Film Name");
        readonlyField.setWidth("372px");

        // 设置文本输入框的标签和宽度
        formLayout2Col.setWidth("100%");

        cinemaComboBox.setLabel("Select Cinema");
        cinemaComboBox.setWidth("min-content");
        setComboBoxSampleData(cinemaComboBox);


        comboBox3.setLabel("Select Date");
        comboBox3.setWidth("min-content");
        setComboBoxSampleData(comboBox3);

        comboBox4.setLabel("Select Time");
        comboBox4.setWidth("min-content");
        setComboBoxSampleData(comboBox4);

        // 设置文本输入框的标签和宽度
        textField2.setLabel("Name");
        textField2.setWidth("min-content");

        textField3.setLabel("Phone");
        textField3.setWidth("min-content");

        seatLowerHall.setLabel("Seat Quantity Lower Hall");
        seatLowerHall.setWidth("min-content");

        seatCentreHall.setLabel("Seat Quantity Centre Hall");
        seatCentreHall.setWidth("min-content");

        seatUpperHall.setLabel("Seat Quantity Upper Hall");
        seatUpperHall.setWidth("min-content");


        // 设置水平布局行2的样式和属性
        layoutRow2.addClassName(Gap.MEDIUM);//添加间距类名，设置间距为中等大小
        layoutRow2.setWidth("100%");// 设置宽度为100%
        layoutRow2.getStyle().set("flex-grow", "1");// 设置flex-grow属性为1，使布局自动扩展
        layoutRow2.setAlignItems(Alignment.CENTER);// 设置垂直居中对齐
        layoutRow2.setJustifyContentMode(JustifyContentMode.CENTER);// 设置水平居中对齐
        // 设置保存按钮的文本内容和样式
        buttonPrimary.setText("Save");// 设置按钮文本为"Save"
        layoutRow2.setAlignSelf(Alignment.CENTER, buttonPrimary);// 设置按钮在布局中的对齐方式为水平居中
        buttonPrimary.setWidth("min-content"); // 设置按钮宽度为最小内容宽度
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY); // 添加Lumo主题变体，使按钮具有主题色（蓝色）

        // 将各组件添加到页面布局中
        getContent().add(layoutRow);
        layoutRow.add(h1);
        getContent().add(layoutColumn2);
        layoutColumn2.add(h3);
        layoutColumn2.add(layoutColumn3);
        layoutColumn3.add(readonlyField);

        layoutColumn2.add(formLayout2Col);
//        formLayout2Col.add(readonlyField);
        formLayout2Col.add(cinemaComboBox);
        formLayout2Col.add(comboBox3);
        formLayout2Col.add(comboBox4);
        formLayout2Col.add(textField2);
        formLayout2Col.add(textField3);
        formLayout2Col.add(seatLowerHall);
        formLayout2Col.add(seatCentreHall);
        formLayout2Col.add(seatUpperHall);
        layoutColumn2.add(layoutRow2);
        layoutRow2.add(buttonPrimary);
    }

//    @Override
//    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String s) {
//
//    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter Long filmId) {
        Optional<Film> film = filmService.getFilmById(filmId);
        if (film != null) {
            readonlyField.setValue(film.get().getFilmName());
//            // 获取当前用户的 userId
//            List<String> cinemas = filmService.getCinemasByFilmId(filmId);
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            this.userId = (String) authentication.getPrincipal(); // 假设 userId 存储在 Principal 中
//            cinemaComboBox.setItems(cinemas);
        } else {
            readonlyField.setValue("Film not found");
            cinemaComboBox.setItems(Collections.emptyList());
        }
    }
    record SampleItem(String value, String label, Boolean disabled) {
    }


    private void setComboBoxSampleData(ComboBox comboBox) {
        List<SampleItem> sampleItems = new ArrayList<>();
        sampleItems.add(new SampleItem("first", "First", null));
        sampleItems.add(new SampleItem("second", "Second", null));
        sampleItems.add(new SampleItem("third", "Third", Boolean.TRUE));
        sampleItems.add(new SampleItem("fourth", "Fourth", null));
        comboBox.setItems(sampleItems);
        comboBox.setItemLabelGenerator(item -> ((SampleItem) item).label());
    }



}

