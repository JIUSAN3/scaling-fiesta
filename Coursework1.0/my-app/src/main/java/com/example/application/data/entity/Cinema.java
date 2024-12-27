package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Cinema extends AbstractEntity {

    private String cinemaCity;
    private String cinemaName;

    public String getCinemaCity() {
        return cinemaCity;
    }
    public void setCinemaCity(String cinemaCity) {
        this.cinemaCity = cinemaCity;
    }
    public String getCinemaName() {
        return cinemaName;
    }
    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }
}
