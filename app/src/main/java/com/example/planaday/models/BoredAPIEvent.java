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

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getParticipants() {
        return participants;
    }

    public void setParticipants(Integer participants) {
        this.participants = participants;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Double getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(Double accessibility) {
        this.accessibility = accessibility;
    }

}