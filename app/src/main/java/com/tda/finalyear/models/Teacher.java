package com.tda.finalyear.models;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Teacher implements Serializable {
    private String name;
    private String classTeacherOfClass;
    private String email;
    private String mobile;
    private String password;

    public Teacher() {
    }

    public Teacher(String name, String classTeacherOfClass, String email, String mobile, String password) {
        this.name = name;
        this.classTeacherOfClass = classTeacherOfClass;
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

    public String getClassTeacherOfClass() {
        return classTeacherOfClass;
    }

    public void setClassTeacherOfClass(String classTeacherOfClass) {
        this.classTeacherOfClass = classTeacherOfClass;
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

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                ", classTeacherOfClass='" + classTeacherOfClass + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
