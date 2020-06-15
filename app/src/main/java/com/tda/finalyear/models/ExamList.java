package com.tda.finalyear.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExamList {
    @SerializedName("exams")
    @Expose
    private List<Exam> exams = null;

    public ExamList(List<Exam> exams) {
        this.exams = exams;
    }

    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }

    @Override
    public String toString() {
        return "ExamList{" +
                "exams=" + exams +
                '}';
    }
}
