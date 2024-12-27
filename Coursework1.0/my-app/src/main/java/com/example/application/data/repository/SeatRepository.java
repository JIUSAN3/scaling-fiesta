package com.example.application.data.repository;


import com.example.application.data.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long>, JpaSpecificationExecutor<Seat> {

    @Query("SELECT DISTINCT c.cityName FROM City c")
    List<String> findAllCityNames();

    @Query("SELECT c.cinemaName FROM Cinema c WHERE c.cinemaCity = :cinemaCity")
    List<String> findCinemaNamesByCityName(String cinemaCity);

    @Query("SELECT s.screenName FROM Screen s WHERE s.cinemaName = :cinemaName")
    List<String> findScreenNamesByCinemaName(String cinemaName);

}
