package com.example.application.data.repository;


import com.example.application.data.entity.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScreenRepository extends JpaRepository<Screen, Long>, JpaSpecificationExecutor<Screen> {
    @Query("SELECT DISTINCT c.cityName FROM City c")
    List<String> findAllCityNames();

    @Query("SELECT c.cinemaName FROM Cinema c WHERE c.cinemaCity = :cinemaCity")
    List<String> findCinemaNamesByCityName(String cinemaCity);

}
