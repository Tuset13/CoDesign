package com.example.codesign;

import com.example.codesign.Projecte.MyCanvas;

public class Projectes {

    private String projectName;
    private boolean administrator;
    private MyCanvas canvas;
    private String[] notes;

    public Projectes(String projectName, boolean administrator, MyCanvas canvas, String[] notes){
        this.projectName = projectName;
        this. administrator = administrator;
        this.canvas = canvas;
        this.notes = notes;
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

    public String[] getNotes() {
        return notes;
    }

    public void setNotes(String[] notes) {
        this.notes = notes;
    }
}
