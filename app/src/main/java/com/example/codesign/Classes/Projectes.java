package com.example.codesign.Classes;

import com.example.codesign.Projecte.MyCanvas;

import java.util.List;

public class Projectes extends ProjecteId {

    private boolean administrator;
    private MyCanvas canvas;
    private List<String> notes;
    private String projectName;
    //private Reunions reunio;

    public Projectes(){}

    public Projectes(boolean administrator, MyCanvas canvas, List<String> notes, String projectName){
        this.projectName = projectName;
        this.administrator = administrator;
        this.canvas = canvas;
        this.notes = notes;
        //this.reunio = reunio;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }

    public MyCanvas getCanvas() {
        return canvas;
    }

    public void setCanvas(MyCanvas canvas) {
        this.canvas = canvas;
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    /*public Reunions getReunio() {
        return reunio;
    }

    public void setReunio(Reunions reunio) {
        this.reunio = reunio;
    }*/
}
