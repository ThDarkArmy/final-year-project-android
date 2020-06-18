package com.tda.finalyear.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TeacherResponse implements Serializable {
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("teacher")
    @Expose
    private Teacher teacher;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "TeacherResponse{" +
                "token='" + token + '\'' +
                ", teacher=" + teacher +
                '}';
    }
}
