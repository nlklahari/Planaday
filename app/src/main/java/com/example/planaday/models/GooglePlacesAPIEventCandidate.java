package com.example.planaday.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GooglePlacesAPIEventCandidate {

    @SerializedName("formatted_address")
    @Expose
    private String formattedAddress;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("types")
    @Expose
    private String[] types;

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

}