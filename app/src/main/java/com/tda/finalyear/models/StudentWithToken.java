package com.tda.finalyear.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("serial")
public class StudentWithToken implements Serializable {
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("student")
    @Expose
    private Student student;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "StudentWithToken{" +
                "token='" + token + '\'' +
                ", student=" + student +
                '}';
    }
}
