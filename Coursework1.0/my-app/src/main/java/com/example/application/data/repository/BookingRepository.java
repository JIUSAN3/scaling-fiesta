package com.example.application.data.repository;


import com.example.application.data.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {

    @Query("SELECT DISTINCT c.cityName FROM City c")
    List<String> findAllCityNames();

    @Query("SELECT c.cinemaName FROM Cinema c WHERE c.cinemaCity = :cinemaCity")
    List<String> findCinemaNamesByCityName(String cinemaCity);

    @Query("SELECT s.screenName FROM Screen s WHERE s.cinemaName = :cinemaName")
    List<String> findScreenNamesByCinemaName(String cinemaName);

    @Query("SELECT DISTINCT f.filmName FROM Film f")
    List<String> findAllFilmNames();

    @Query("SELECT DISTINCT s.showingDate FROM Showing s")
    List<String> findAllShowingDates();

    @Query("SELECT DISTINCT s.showingTime FROM Showing s")
    List<String> findAllShowingTimes();

}
