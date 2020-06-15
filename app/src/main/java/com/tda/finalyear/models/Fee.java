package com.tda.finalyear.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fee {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("std")
    @Expose
    private String std;
    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("year")
    @Expose
    private Integer year;
    @SerializedName("tuitionFee")
    @Expose
    private Integer tuitionFee;
    @SerializedName("examFee")
    @Expose
    private Integer examFee;
    @SerializedName("admissionFee")
    @Expose
    private Integer admissionFee;

    public Fee(String std, Integer tuitionFee, Integer examFee, Integer admissionFee) {
        this.std = std;
        this.tuitionFee = tuitionFee;
        this.examFee = examFee;
        this.admissionFee = admissionFee;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStd() {
        return std;
    }

    public void setStd(String std) {
        this.std = std;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getTuitionFee() {
        return tuitionFee;
    }

    public void setTuitionFee(Integer tuitionFee) {
        this.tuitionFee = tuitionFee;
    }

    public Integer getExamFee() {
        return examFee;
    }

    public void setExamFee(Integer examFee) {
        this.examFee = examFee;
    }

    public Integer getAdmissionFee() {
        return admissionFee;
    }

    public void setAdmissionFee(Integer admissionFee) {
        this.admissionFee = admissionFee;
    }

    @Override
    public String toString() {
        return "Fee{" +
                "id='" + id + '\'' +
                ", std='" + std + '\'' +
                ", month='" + month + '\'' +
                ", year=" + year +
                ", tuitionFee=" + tuitionFee +
                ", examFee=" + examFee +
                ", admissionFee=" + admissionFee +
                '}';
    }
}
