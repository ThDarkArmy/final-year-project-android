package com.tda.finalyear.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NoticeList {
    @SerializedName("notices")
    @Expose
    private List<Notice> notices = null;

    public List<Notice> getNotices() {
        return notices;
    }

    public void setNotices(List<Notice> notices) {
        this.notices = notices;
    }

    @Override
    public String toString() {
        return "NoticeList{" +
                "notices=" + notices +
                '}';
    }
}
