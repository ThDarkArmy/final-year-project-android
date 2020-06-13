package com.tda.finalyear.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HolidayList {
    @SerializedName("holidays")
    @Expose
    private List<Holiday> holidays = null;

    public List<Holiday> getHolidays() {
        return holidays;
    }

    public void setHolidays(List<Holiday> holidays) {
        this.holidays = holidays;
    }

    @Override
    public String toString() {
        return "HolidayList{" +
                "holidays=" + holidays +
                '}';
    }
}
