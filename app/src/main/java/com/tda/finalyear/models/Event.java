package com.tda.finalyear.models;

import java.util.Date;

public class Event {
    private String title;
    private String startDate;
    private String duration;
    private String description;

    public Event() {
    }

    public Event(String title, String startDate, String duration, String description) {
        this.title = title;
        this.startDate = startDate;
        this.duration = duration;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
