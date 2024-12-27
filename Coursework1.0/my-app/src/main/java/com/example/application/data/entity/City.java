package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;
import jakarta.persistence.Entity;

@Entity
public class City extends AbstractEntity {

    private String cityName;

    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

}
