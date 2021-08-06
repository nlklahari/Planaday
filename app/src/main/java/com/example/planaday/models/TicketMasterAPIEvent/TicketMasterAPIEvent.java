package com.example.planaday.models.TicketMasterAPIEvent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TicketMasterAPIEvent {
    @SerializedName("page")
    @Expose
    private Page page;
    @SerializedName("_embedded")
    @Expose
    private List<TMEvent> tmEvents;


    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public List<TMEvent> getTMEvents() {
        return tmEvents;
    }

    public void setTmEvents(List<TMEvent> tmEvents) {
        this.tmEvents = tmEvents;
    }
}
