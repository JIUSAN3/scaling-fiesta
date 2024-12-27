package com.example.application.views.flimlist;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.theme.lumo.LumoUtility.*;


public class FlimListViewCard extends ListItem {

    public FlimListViewCard(Long filmId,String imageText, String imageUrl, String title, String subTitle, String filmDescription) {
        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM,
                BorderRadius.LARGE);

        Div div = new Div();
        div.addClassNames(Background.CONTRAST, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                Margin.Bottom.MEDIUM, Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL);
        div.setHeight("450px");

        Image image = new Image();
        image.setHeight("100%");
        image.setSrc(imageUrl);
        image.setAlt(imageText);

        div.add(image);

        Span header = new Span();
        header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        header.setText(title);

        Span subtitle = new Span();
        subtitle.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subtitle.setText(subTitle);

        Paragraph description = new Paragraph(filmDescription);
        description.addClassName(Margin.Vertical.MEDIUM);

        Span badge = new Span();
        badge.getElement().setAttribute("theme", "badge");
        badge.setText("View Screening Info");

        badge.addClickListener(e -> {
            String target = "booking-film/" + filmId.toString();
            UI.getCurrent().navigate(target);
        });

        add(div, header, subtitle, description, badge);

    }
}
