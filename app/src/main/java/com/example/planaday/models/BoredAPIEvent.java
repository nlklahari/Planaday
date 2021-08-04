package com.example.planaday.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BoredAPIEvent {

    @SerializedName("activity")
    @Expose
    private String activity;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("participants")
    @Expose
    private Integer participants;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("accessibility")
    @Expose
    private Double accessibility;
    @SerializedName("error")
    @Expose
    private String error;

    public String getActivity() {
        return activity;
    }

    public String getType() {
        return type;
    }

    public Integer getParticipants() {
        return participants;
    }

    public Double getPrice() {
        return price;
    }

    public Double getAccessibility() {
        return accessibility;
    }

    public String getError() {
        return error;
    }
}