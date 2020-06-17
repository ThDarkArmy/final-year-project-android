package com.tda.finalyear.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notes {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("std")
    @Expose
    private String std;
    @SerializedName("notesFile")
    @Expose
    private String notesFile;

    public Notes(String title, String std, String notesFile) {
        this.title = title;
        this.std = std;
        this.notesFile = notesFile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStd() {
        return std;
    }

    public void setStd(String std) {
        this.std = std;
    }

    public String getNotesFile() {
        return notesFile;
    }

    public void setNotesFile(String notesFile) {
        this.notesFile = notesFile;
    }

    @Override
    public String toString() {
        return "Notes{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", std='" + std + '\'' +
                ", notesFile='" + notesFile + '\'' +
                '}';
    }
}

