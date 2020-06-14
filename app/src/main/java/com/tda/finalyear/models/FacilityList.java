package com.tda.finalyear.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FacilityList {
    @SerializedName("facilities")
    @Expose
    private List<Facility> facilities = null;

    public FacilityList(List<Facility> facilities) {
        this.facilities = facilities;
    }

    public List<Facility> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<Facility> facilities) {
        this.facilities = facilities;
    }

    @Override
    public String toString() {
        return "FacilityList{" +
                "facilities=" + facilities +
                '}';
    }
}
