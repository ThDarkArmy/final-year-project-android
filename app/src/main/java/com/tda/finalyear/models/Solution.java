package com.tda.finalyear.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Solution {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("solutionFile")
    @Expose
    private String solutionFile;
    @SerializedName("assignment")
    @Expose
    private String assignment;
    @SerializedName("student")
    @Expose
    private String student;
    @SerializedName("studentName")
    @Expose
    private String studentName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSolutionFile() {
        return solutionFile;
    }

    public void setSolutionFile(String solutionFile) {
        this.solutionFile = solutionFile;
    }

    public String getAssignment() {
        return assignment;
    }

    public void setAssignment(String assignment) {
        this.assignment = assignment;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "id='" + id + '\'' +
                ", solutionFile='" + solutionFile + '\'' +
                ", assignment='" + assignment + '\'' +
                ", student='" + student + '\'' +
                ", studentName='" + studentName + '\'' +
                '}';
    }
}
