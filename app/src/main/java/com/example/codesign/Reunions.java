package com.example.codesign;

import com.google.firebase.firestore.GeoPoint;

import java.sql.Timestamp;

public class Reunions {

    private Timestamp data;
    private String descripcio;
    private GeoPoint llocReunio;
    private String titol;

    public Reunions(Timestamp data, String descripcio, GeoPoint llocReunio, String titol) {
        this.data = data;
        this.descripcio = descripcio;
        this.llocReunio = llocReunio;
        this.titol = titol;
    }

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public GeoPoint getLlocReunio() {
        return llocReunio;
    }

    public void setLlocReunio(GeoPoint llocReunio) {
        this.llocReunio = llocReunio;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }
}
