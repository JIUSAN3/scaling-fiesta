package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;
import jakarta.persistence.Entity;

@Entity
public class Screen extends AbstractEntity {

    private String cityName;
    private String screenName;
    private String cinemaName;
    private Integer upperHallCapacity;
    private Integer lowerHallCapacity;
    private Integer centreHallCapacity;
    private Integer totalCapacity;
    private boolean isAvailable;

    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public String getScreenName() {
        return screenName;
    }
    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }
    public String getCinemaName() {
        return cinemaName;
    }
    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }
    public Integer getUpperHallCapacity() {
        return upperHallCapacity;
    }
    public void setUpperHallCapacity(Integer upperHallCapacity) {
        this.upperHallCapacity = upperHallCapacity;
    }
    public Integer getLowerHallCapacity() {
        return lowerHallCapacity;
    }
    public void setLowerHallCapacity(Integer lowerHallCapacity) {
        this.lowerHallCapacity = lowerHallCapacity;
    }
    public Integer getCentreHallCapacity() {
        return centreHallCapacity;
    }
    public void setCentreHallCapacity(Integer centreHallCapacity) {
        this.centreHallCapacity = centreHallCapacity;
    }
    public Integer getTotalCapacity() {
        return totalCapacity;
    }
    public void setTotalCapacity(Integer totalCapacity) {
        this.totalCapacity = totalCapacity;
    }
    public boolean isIsAvailable() {
        return isAvailable;
    }
    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

}
