package com.example.application.data.repository;


import com.example.application.data.entity.Showing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShowingRepository extends JpaRepository<Showing, Long>, JpaSpecificationExecutor<Showing> {

    @Query("SELECT DISTINCT f.filmName FROM Film f")
    List<String> findAllFilmNames();

}
