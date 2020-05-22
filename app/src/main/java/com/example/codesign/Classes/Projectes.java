package com.example.codesign.Classes;

import com.example.codesign.Projecte.MyCanvas;

import java.util.List;

public class Projectes extends ProjecteId {

    private boolean administrator;
    //private MyCanvas canvas;
    private List<String> notes;
    private String projectName;
    private Meeting reunio;
    private List<String> participants;

    public Projectes(){}

    public Projectes(boolean administrator, List<String> participants, List<String> notes, String projectName, Meeting reunio){
        this.projectName = projectName;
        this.administrator = administrator;
        //this.canvas = canvas;
        this.notes = notes;
        this.reunio = reunio;
        this.participants = participants;
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

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

/*public MyCanvas getCanvas() {
        return canvas;
    }

    public void setCanvas(MyCanvas canvas) {
        this.canvas = canvas;
    }*/

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    public Meeting getReunio() {
        return reunio;
    }

    public void setReunio(Meeting reunio) {
        this.reunio = reunio;
    }
}
