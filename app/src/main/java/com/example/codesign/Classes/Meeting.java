package com.example.codesign.Classes;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

public class Meeting {

    private String title;
    private String description;
    private com.google.firebase.Timestamp time;
    private GeoPoint location;

    public Meeting(){}

    public Meeting(String title, String description, com.google.firebase.Timestamp time, GeoPoint location){
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

    public void setTime(com.google.firebase.Timestamp time) {
        this.time = time;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }
}
