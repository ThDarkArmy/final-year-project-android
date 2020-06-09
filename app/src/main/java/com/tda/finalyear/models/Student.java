package com.tda.finalyear.models;

public class Student {
    private String name;
    private String std;
    private String roll;
    private String email;
    private String mobile;
    private String password;

    public Student(String name, String std, String roll, String email, String mobile, String password) {
        this.name = name;
        this.std = std;
        this.roll = roll;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStd() {
        return std;
    }

    public void setStd(String std) {
        this.std = std;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
