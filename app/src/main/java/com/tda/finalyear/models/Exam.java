package com.tda.finalyear.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Exam {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("std")
    @Expose
    private String std;
    @SerializedName("routine")
    @Expose
    private String routine;

    public Exam(String title, String std) {
        this.title = title;
        this.std = std;
    }

    public Exam(String title, String std, String routine) {
        this.title = title;
        this.std = std;
        this.routine = routine;
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

    public String getRoutine() {
        return routine;
    }

    public void setRoutine(String routine) {
        this.routine = routine;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", std='" + std + '\'' +
                ", routine='" + routine + '\'' +
                '}';
    }
}
