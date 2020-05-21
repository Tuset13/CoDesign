package com.example.codesign;

import androidx.annotation.NonNull;

public class ProjecteId {
    public String projecteId;

    public <T extends ProjecteId> T withId(@NonNull final String id){
        this.projecteId = id;
        return (T) this;
    }
}
