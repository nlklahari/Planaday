package com.example.planaday.models.TicketMasterAPIEvent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TMEvent {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("_embedded")
    //@Expose
    //private List<Venue> eventVenues; // TODO get venue array, create venue object

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public List<Venue> getVenues() {
        return eventVenues;
    }

    public void setVenues(List<Venue> eventVenues) {
        this.eventVenues = eventVenues;
    }
    */

}
