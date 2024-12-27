package com.example.application.views.managefilm;

import com.example.application.data.entity.Film;
import com.example.application.services.FilmService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import jakarta.annotation.security.RolesAllowed;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@PageTitle("ManageFilm")
@Route(value = "managefilm/:filmID?/:action?(edit)", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class ManageFilmView extends Div implements BeforeEnterObserver {

    private final String FILM_ID = "filmID";
    private final String FILM_EDIT_ROUTE_TEMPLATE = "managefilm/%s/edit";

    private final Grid<Film> grid = new Grid<>(Film.class, false);

    private Upload filmPicture;
    private Image filmPicturePreview;
    private TextField filmName;
    private TextField subtitle;
    private ComboBox<String> filmType;
    private TextField description;

    private final Button clear = new Button("Clear");
    private final Button save = new Button("Save");
    private final Button delete = new Button("Delete");

    private final BeanValidationBinder<Film> binder;

    private Film film;

    private final FilmService filmService;

    public ManageFilmView(FilmService filmService) {
        this.filmService = filmService;
        addClassNames("manage-film-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        LitRenderer<Film> filmPictureRenderer = LitRenderer
                .<Film>of("<img style='height: 64px' src=${item.filmPicture} />").withProperty("filmPicture", item -> {
                    if (item != null && item.getFilmPicture() != null) {
                        return "data:image;base64," + Base64.getEncoder().encodeToString(item.getFilmPicture());
                    } else {
                        return "";
                    }
                });
        grid.addColumn(filmPictureRenderer).setHeader("Film Picture").setWidth("68px").setFlexGrow(0);

        grid.addColumn("filmName").setAutoWidth(true);
        grid.addColumn("subtitle").setAutoWidth(true);
        grid.addColumn("filmType").setAutoWidth(true);
        grid.addColumn("description").setAutoWidth(true);
        grid.setItems(query -> filmService.list(
                        PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(FILM_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(ManageFilmView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Film.class);

        // Bind fields. This is where you'd define e.g. validation rules

        binder.bindInstanceFields(this);

        attachImageUpload(filmPicture, filmPicturePreview);

        clear.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        delete.addClickListener(e -> {
            try {
                if (this.film != null) {
                    binder.writeBean(this.film);
                    filmService.delete(this.film);
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
                if (this.film == null) {
                    this.film = new Film();
                }
                binder.writeBean(this.film);
                filmService.update(this.film);
                clearForm();
                refreshGrid();
                Notification.show("Data updated");
                UI.getCurrent().navigate(ManageFilmView.class);
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

    private void populateComboBoxes() {
        filmType.setItems("Drama", "Thriller", "Biography", "History", "Sports", "Homosexual", "Household");
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> filmId = event.getRouteParameters().get(FILM_ID).map(Long::parseLong);
        if (filmId.isPresent()) {
            Optional<Film> filmFromBackend = filmService.get(filmId.get());
            if (filmFromBackend.isPresent()) {
                populateForm(filmFromBackend.get());
            } else {
                Notification.show(String.format("The requested film was not found, ID = %s", filmId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(ManageFilmView.class);
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
        NativeLabel filmPictureLabel = new NativeLabel("Film Picture");
        filmPicturePreview = new Image();
        filmPicturePreview.setWidth("100%");
        filmPicture = new Upload();
        filmPicture.getStyle().set("box-sizing", "border-box");
        filmPicture.getElement().appendChild(filmPicturePreview.getElement());
        filmName = new TextField("Film Name");
        subtitle = new TextField("Subtitle");
        filmType = new ComboBox<>("Film Type");
        description = new TextField("Description");
        formLayout.add(filmPictureLabel, filmPicture, filmName, subtitle, filmType, description);

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        clear.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        buttonLayout.add(save, clear, delete);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void attachImageUpload(Upload upload, Image preview) {
        ByteArrayOutputStream uploadBuffer = new ByteArrayOutputStream();
        upload.setAcceptedFileTypes("image/*");
        upload.setReceiver((fileName, mimeType) -> {
            uploadBuffer.reset();
            return uploadBuffer;
        });
        upload.addSucceededListener(e -> {
            StreamResource resource = new StreamResource(e.getFileName(),
                    () -> new ByteArrayInputStream(uploadBuffer.toByteArray()));
            preview.setSrc(resource);
            preview.setVisible(true);
            if (this.film == null) {
                this.film = new Film();
            }
            this.film.setFilmPicture(uploadBuffer.toByteArray());
        });
        preview.setVisible(false);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Film value) {
        this.film = value;
        binder.readBean(this.film);
        this.filmPicturePreview.setVisible(value != null);
        if (value == null || value.getFilmPicture() == null) {
            this.filmPicture.clearFileList();
            this.filmPicturePreview.setSrc("");
        } else {
            this.filmPicturePreview
                    .setSrc("data:image;base64," + Base64.getEncoder().encodeToString(value.getFilmPicture()));
        }

    }
}
