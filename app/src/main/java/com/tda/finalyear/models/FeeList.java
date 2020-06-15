package com.tda.finalyear.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeeList {
    @SerializedName("fees")
    @Expose
    private List<Fee> fees = null;

    public FeeList(List<Fee> fees) {
        this.fees = fees;
    }

    public List<Fee> getFees() {
        return fees;
    }

    public void setFees(List<Fee> fees) {
        this.fees = fees;
    }

    @Override
    public String toString() {
        return "FeeList{" +
                "fees=" + fees +
                '}';
    }
}
