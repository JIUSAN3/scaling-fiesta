package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;
import jakarta.persistence.Entity;

@Entity
public class Seat extends AbstractEntity {

    private String cityName;
    private String cinemaName;
    private String screenName;

    private Integer seatRow;
    private Integer seatColumn;
    private boolean seatAvailability;

    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public String getCinemaName() {
        return cinemaName;
    }
    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }
    public String getScreenName() {
        return screenName;
    }
    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public Integer getSeatRow() {
        return seatRow;
    }
    public void setSeatRow(Integer seatRow) {
        this.seatRow = seatRow;
    }
    public Integer getSeatColumn() {
        return seatColumn;
    }
    public void setSeatColumn(Integer seatColumn) {
        this.seatColumn = seatColumn;
    }
    public boolean isSeatAvailability() {
        return seatAvailability;
    }
    public void setSeatAvailability(boolean seatAvailability) {
        this.seatAvailability = seatAvailability;
    }

}
