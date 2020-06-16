package com.tda.finalyear.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttendanceHistory {

    @SerializedName("isPresent")
    @Expose
    private Boolean isPresent;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("date")
    @Expose
    private String date;

    public AttendanceHistory(Boolean isPresent, String id, String date) {
        this.isPresent = isPresent;
        this.id = id;
        this.date = date;
    }

    public Boolean getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(Boolean isPresent) {
        this.isPresent = isPresent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
