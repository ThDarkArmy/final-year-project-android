package com.tda.finalyear.models;

public class Admin {
    private String name;
    private String email;
    private String mobile;
    private String schoolName;
    private String password;

    public Admin() {
    }

    public Admin(String name, String email, String mobile, String schoolName, String password) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.schoolName = schoolName;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
