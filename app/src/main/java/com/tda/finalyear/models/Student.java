package com.tda.finalyear.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Student {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("roll")
    @Expose
    private Integer roll;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("std")
    @Expose
    private String std;
    @SerializedName("attendanceHistory")
    @Expose
    private List<AttendanceHistory> attendanceHistory = null;
    @SerializedName("feeHistory")
    @Expose
    private List<FeeHistory> feeHistory = null;



    public Student(String name, Integer roll, String email, String mobile, String std, String password) {
        this.name = name;
        this.roll = roll;
        this.email = email;
        this.mobile = mobile;
        this.std = std;
        this.password = password;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRoll() {
        return roll;
    }

    public void setRoll(Integer roll) {
        this.roll = roll;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStd() {
        return std;
    }

    public void setStd(String std) {
        this.std = std;
    }

    public List<AttendanceHistory> getAttendanceHistory() {
        return attendanceHistory;
    }

    public void setAttendanceHistory(List<AttendanceHistory> attendanceHistory) {
        this.attendanceHistory = attendanceHistory;
    }

    public List<FeeHistory> getFeeHistory() {
        return feeHistory;
    }

    public void setFeeHistory(List<FeeHistory> feeHistory) {
        this.feeHistory = feeHistory;
    }

}