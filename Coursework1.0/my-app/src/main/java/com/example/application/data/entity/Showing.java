package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;
import jakarta.persistence.Entity;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Showing extends AbstractEntity {

    private String filmName;
//    private LocalDate showingDate;
//    private LocalDateTime showingDateTime;
    private String showingDate;
    private String showingTime;
    private String showingScreen;

    public String getFilmName() {
        return filmName;
    }
    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

//    public LocalDate getShowingDate() {
//        return showingDate;
//    }
//    public void setShowingDate(LocalDate showingDate) {
//        this.showingDate = showingDate;
//    }
//    public LocalDateTime getShowingDateTime() {
//        return showingDateTime;
//    }
//    public void setShowingDateTime(LocalDateTime showingDateTime) {
//        this.showingDateTime = showingDateTime;
//    }
    public void setShowingDate(String showingDate) {
    this.showingDate = showingDate;
}
    public String getShowingDate() {
        return showingDate;
    }
    public String getShowingTime() {
        return showingTime;
    }
    public void setShowingTime(String showingTime) {
        this.showingTime = showingTime;
    }

    public String getShowingScreen() {
        return showingScreen;
    }
    public void setShowingScreen(String showingScreen) {
        this.showingScreen = showingScreen;
    }

}
