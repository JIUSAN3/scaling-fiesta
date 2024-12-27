package com.example.application.data.repository;


import com.example.application.data.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CinemaRepository extends JpaRepository<Cinema, Long>, JpaSpecificationExecutor<Cinema> {

    @Query("SELECT DISTINCT c.cityName FROM City c")
    List<String> findAllCityNames();
}
