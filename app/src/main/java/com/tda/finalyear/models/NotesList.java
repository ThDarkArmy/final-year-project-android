package com.tda.finalyear.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotesList {
    @SerializedName("notes")
    @Expose
    private List<Notes> notes = null;

    public List<Notes> getNotes() {
        return notes;
    }

    public void setNotes(List<Notes> notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "NotesList{" +
                "notes=" + notes +
                '}';
    }
}
