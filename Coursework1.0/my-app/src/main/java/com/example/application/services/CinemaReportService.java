package com.example.application.services;

import com.example.application.data.entity.CinemaReport;
import com.example.application.data.entity.Booking;
import com.example.application.data.entity.FilmReport;
import com.example.application.data.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CinemaReportService {

    private final BookingRepository bookingRepository;

    @Autowired
    public CinemaReportService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<CinemaReport> getCinemaReports() {
        // 获取所有预定
        List<Booking> bookings = bookingRepository.findAll();

        // 按影院名称分组并计算统计数据
        return bookings.stream()
                .collect(Collectors.groupingBy(Booking::getCinemaName))
                .entrySet().stream()
                .map(entry -> {
                    String cinemaName = entry.getKey();
                    String city = entry.getValue().isEmpty() ? "" : entry.getValue().get(0).getCityName();
                    int orderCount = entry.getValue().size();
                    double totalRevenue = entry.getValue().stream().mapToDouble(Booking::getTotalCost).sum();

                    return new CinemaReport(city, cinemaName, orderCount, totalRevenue);
                })
                .collect(Collectors.toList()); // 将流转换为列表
    }

    public List<FilmReport> getFilmReports() {
        List<Booking> bookings = bookingRepository.findAll();

        return bookings.stream()
                .collect(Collectors.groupingBy(Booking::getFilmName))
                .entrySet().stream()
                .map(entry -> {
                    String filmName = entry.getKey();
                    int orderCount = entry.getValue().size();
                    double totalRevenue = entry.getValue().stream().mapToDouble(Booking::getTotalCost).sum();

                    return new FilmReport(filmName, orderCount, totalRevenue);
                })
                .collect(Collectors.toList());
    }
}
