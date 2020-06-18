package com.tda.finalyear.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SolutionList {

    @SerializedName("solutions")
    @Expose
    private List<Solution> solutions = null;

    public List<Solution> getSolutions() {
        return solutions;
    }

    public void setSolutions(List<Solution> solutions) {
        this.solutions = solutions;
    }

}
