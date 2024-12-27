package com.example.application.views.flimlist;

import com.example.application.data.entity.Film;
import com.example.application.services.FilmService;
import com.example.application.views.MainLayout;
import com.example.application.views.flimlist.FlimListViewCard;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility.*;
import jakarta.annotation.security.PermitAll;

import java.util.Base64;
import java.util.List;
import java.util.Optional;


@PageTitle("Flim List")
@Route(value = "", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
public class FlimListView extends Main implements HasComponents, HasStyle {

    private OrderedList imageContainer;
    private FilmService filmService;

    // 接收FilmService的实例数据
    public FlimListView(FilmService filmService) {
        this.filmService = filmService;
        constructUI(); //

        // 从FilmService获取所有电影信息
        List<Film> films = filmService.findAll();
        populateFilmList(films); //
    }

    private void constructUI() {
        addClassNames("flim-list-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Horizon Cinemas Booking System");
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
        Paragraph description = new Paragraph("Have a great time at the movies!");
        description.addClassNames(Margin.Bottom.XLARGE, Margin.Top.NONE, TextColor.SECONDARY);
        headerContainer.add(header, description);

        // 设计选择框来根据电影类型显示对应电影
        Select<String> sortBy = new Select<>();
        sortBy.setLabel("Sort by type");
        sortBy.setItems("Drama", "Thriller", "Biography", "History", "Sports", "Homosexual", "household");
        sortBy.setValue("Drama");
        sortBy.addValueChangeListener(event -> {
            String selectedType = event.getValue();
            List<Film> filteredFilms = filmService.findByFilmType(selectedType);
            populateFilmList(filteredFilms); // 根据选择的类型更新电影列表
        });

        // 创建一个容器,来显示filmcard
        imageContainer = new OrderedList();
        imageContainer.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);
        imageContainer.getStyle().set("grid-template-columns", "repeat(3, 1fr)"); // 设置一排三个
        imageContainer.getStyle().set("grid-auto-rows", "auto"); // 设置自动行高

        container.add(headerContainer, sortBy);
        add(container, imageContainer);
    }

    private void populateFilmList(List<Film> films) {
        imageContainer.removeAll(); // 清空页面列表
        films.forEach(film -> {
            imageContainer.add(new FlimListViewCard(
                    film.getFilmById(),
                    film.getFilmName(),
                    getFilmPictureUrl(film), // 获取电影图片
                    film.getFilmName(),
                    film.getSubtitle(),
                    film.getDescription()));
        });
    }

    // 用base64来获取图片显示
    private String getFilmPictureUrl(Film film) {
        Optional<byte[]> filmPicture = Optional.ofNullable(film.getFilmPicture());
        return filmPicture.map(bytes -> "data:image;base64," + Base64.getEncoder().encodeToString(bytes))
                .orElse("");
    }
}