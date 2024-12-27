package com.example.application.views.report;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.example.application.data.entity.CinemaReport;
import com.example.application.data.entity.FilmReport;
import com.example.application.services.CinemaReportService;
import com.example.application.views.MainLayout;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Cinema Report")
@Route(value = "report/cinema", layout = MainLayout.class)
@RolesAllowed({"ADMIN", "MANAGER"})
public class CinemaReportView extends VerticalLayout {

    private final CinemaReportService cinemaReportService;
    private Grid<CinemaReport> cinemaGrid = new Grid<>(CinemaReport.class);
    private Grid<FilmReport> filmGrid = new Grid<>(FilmReport.class);
    private ComboBox<String> reportTypeComboBox = new ComboBox<>("Select Report Type");

    @Autowired
    public CinemaReportView(CinemaReportService cinemaReportService) {
        this.cinemaReportService = cinemaReportService;
        addClassName("cinema-report-view");

        reportTypeComboBox.setItems("Cinema Orders Report", "Film Orders Report");
        reportTypeComboBox.addValueChangeListener(e -> updateView());

        add(reportTypeComboBox, cinemaGrid, filmGrid);
        updateView();
    }

    private void updateView() {
        String reportType = reportTypeComboBox.getValue();
        cinemaGrid.setVisible(false);
        filmGrid.setVisible(false);

        if ("Cinema Orders Report".equals(reportType)) {
            cinemaGrid.setItems(cinemaReportService.getCinemaReports());
            cinemaGrid.setColumns("city", "cinemaName", "orderCount", "totalRevenue");
            cinemaGrid.setVisible(true);
        } else if ("Film Orders Report".equals(reportType)) {
            filmGrid.setItems(cinemaReportService.getFilmReports());
            filmGrid.setColumns("filmName", "orderCount", "totalRevenue");
            filmGrid.setVisible(true);
        }
    }
}
