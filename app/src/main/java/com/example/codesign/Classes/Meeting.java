package com.example.codesign.Classes;

import com.google.firebase.firestore.GeoPoint;

import java.sql.Timestamp;

public class Meeting {

    private String title;
    private String description;
    private Timestamp time;
    private GeoPoint location;

    public Meeting(String title, String description, Timestamp time, GeoPoint location){
        this.title = title;
        this.description = description;
        this.time = time;
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getTime() {
        return time;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }
}
