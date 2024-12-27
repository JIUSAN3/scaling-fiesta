package com.example.application.data.entity;

public class FilmReport {
    private String filmName;
    private int orderCount;
    private double totalRevenue;

    public FilmReport(String filmName, int orderCount, double totalRevenue) {
        this.filmName = filmName;
        this.orderCount = orderCount;
        this.totalRevenue = totalRevenue;
    }

    // Getters and Setters
    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
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
