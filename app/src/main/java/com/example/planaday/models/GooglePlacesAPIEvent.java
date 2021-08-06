package com.example.planaday.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GooglePlacesAPIEvent {

    @SerializedName("candidates")
    @Expose
    private List<GooglePlacesAPIEventCandidate> candidates = null;
    @SerializedName("status")
    @Expose
    private String status;

    public List<GooglePlacesAPIEventCandidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<GooglePlacesAPIEventCandidate> candidates) {
        this.candidates = candidates;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}