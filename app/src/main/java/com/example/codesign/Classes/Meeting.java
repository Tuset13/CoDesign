package com.example.codesign.Classes;

import java.sql.Timestamp;

public class Meeting {

    private String title;
    private String description;
    private Timestamp time;

    public Meeting(String title, String description, Timestamp time){
        this.title = title;
        this.description = description;
        this.time = time;
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
}
