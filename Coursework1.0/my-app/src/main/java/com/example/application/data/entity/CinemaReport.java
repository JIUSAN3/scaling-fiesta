package com.example.application.data.entity;

public class CinemaReport {
    private String city;
    private String cinemaName;
    private int orderCount;
    private double totalRevenue;

    public CinemaReport(String city, String cinemaName, int orderCount, double totalRevenue) {
        this.city = city;
        this.cinemaName = cinemaName;
        this.orderCount = orderCount;
        this.totalRevenue = totalRevenue;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

}
