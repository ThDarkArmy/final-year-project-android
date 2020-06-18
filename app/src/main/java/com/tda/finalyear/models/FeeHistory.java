package com.tda.finalyear.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FeeHistory implements Serializable {

    @SerializedName("isPaid")
    @Expose
    private Boolean isPaid;
    @SerializedName("datePaid")
    @Expose
    private Object datePaid;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("tuitionFee")
    @Expose
    private Integer tuitionFee;
    @SerializedName("examFee")
    @Expose
    private Integer examFee;
    @SerializedName("admissionFee")
    @Expose
    private Integer admissionFee;

    public FeeHistory(Boolean isPaid, Object datePaid, String month, String year, Integer tuitionFee, Integer examFee, Integer admissionFee) {
        this.isPaid = isPaid;
        this.datePaid = datePaid;
        this.month = month;
        this.year = year;
        this.tuitionFee = tuitionFee;
        this.examFee = examFee;
        this.admissionFee = admissionFee;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public Object getDatePaid() {
        return datePaid;
    }

    public void setDatePaid(Object datePaid) {
        this.datePaid = datePaid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
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

}
