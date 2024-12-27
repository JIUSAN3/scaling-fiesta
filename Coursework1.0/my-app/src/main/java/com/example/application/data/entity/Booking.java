package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Booking extends AbstractEntity {

    private String userName;
    private String phone;

    private String cityName;
    private String cinemaName;
    private String screenName;
    private String filmName;
//    private LocalDateTime showingDateTime;
    private String showingDate;
    private String showingTime;
    private Integer seatQuantityUpperHall = 0;
    private Integer seatQuantityLowerHall = 0;
    private Integer seatQuantityCentreHall = 0;
    private Integer ticketNumber = 0;
    private Double totalCost = 0.0;
    private String bookingStatus = "No booking"; // Default value

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

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
    public String getFilmName() {
        return filmName;
    }
    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }
//    public LocalDateTime getShowingDateTime() {
//        return showingDateTime;
//    }
//    public void setShowingDateTime(LocalDateTime showingDateTime) {
//        this.showingDateTime = showingDateTime;
//    }

    public String getShowingDate() {
    return showingDate;
}
    public void setShowingDate(String showingDate) {
        this.showingDate = showingDate;
    }
    public String getShowingTime() {
        return showingTime;
    }
    public void setShowingTime(String showingTime) {
        this.showingTime = showingTime;
    }


    public Integer getSeatQuantityUpperHall() {
        return seatQuantityUpperHall;
    }
    public void setSeatQuantityUpperHall(Integer seatQuantityUpperHall) {
        this.seatQuantityUpperHall = seatQuantityUpperHall;
    }
    public Integer getSeatQuantityLowerHall() {
        return seatQuantityLowerHall;
    }
    public void setSeatQuantityLowerHall(Integer seatQuantityLowerHall) {
        this.seatQuantityLowerHall = seatQuantityLowerHall;
    }
    public Integer getSeatQuantityCentreHall() {
        return seatQuantityCentreHall;
    }
    public void setSeatQuantityCentreHall(Integer seatQuantityCentreHall) {
        this.seatQuantityCentreHall = seatQuantityCentreHall;
    }
    public Integer getTicketNumber() {
        return ticketNumber;
    }
    public void setTicketNumber(Integer ticketNumber) {
        this.ticketNumber = ticketNumber;
    }
    public Double getTotalCost() {
        return totalCost;
    }
    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }
    public String getBookingStatus() {
        return bookingStatus;
    }
    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}
