package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;

@Entity
public class Film extends AbstractEntity {

    @Lob
    @Column(length = 1000000)
    private byte[] filmPicture;
    private String filmName;
    private String subtitle;
    private String filmType;
    private String description;

    public byte[] getFilmPicture() {
        return filmPicture;
    }
    public void setFilmPicture(byte[] filmPicture) {
        this.filmPicture = filmPicture;
    }
    public String getFilmName() {
        return filmName;
    }
    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }
    public String getSubtitle() {
        return subtitle;
    }
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
    public String getFilmType() {
        return filmType;
    }
    public void setFilmType(String filmType) {
        this.filmType = filmType;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Long getFilmById() {
        return getId();
    }
}
