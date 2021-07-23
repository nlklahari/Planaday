package com.example.planaday.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Date;

@ParseClassName("Plan")
public class Plan extends ParseObject {

    public static final String KEY_USER = "user"; // public to filter query results
    private static final String KEY_NAME = "name";
    private static final String KEY_DATE = "date";
    private static final String KEY_DURATION = "duration";
    private static final String KEY_TIME = "time";
    private static final String KEY_PRICE = "price";
    private static final String KEY_EVENTS = "events";
    private static final String KEY_TYPES = "types"; // categories of types that this event falls under
    private static final String KEY_ENVIRONMENT = "environment"; //indoor or outdoor
    private static final String KEY_SETTING = "setting"; //individual or group


    public Plan() {}

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser parseUser) {
        put(KEY_USER, parseUser);
    }

    public String getPlanName() {
        return getString(KEY_NAME);
    }

    public void setPlanName(String name) {
        put(KEY_NAME, name);
    }

    public Date getPlanDate() {
        return getDate(KEY_DATE);
    }

    // Modify type to just String?
    public void setPlanDate(Date date) {
        put(KEY_DATE, date);
    }

    public int getDuration() {
        return getInt(KEY_DURATION);
    }

    public void setDuration(int duration) {
        put(KEY_DURATION, duration);
    }

    public String getStringTime() {
        return getString(KEY_TIME);
    }

    public void setStringTime(String time) {
        put(KEY_TIME, time);
    }

    public double getPrice() {
        return getDouble(KEY_PRICE);
    }

    public void setPrice(int price) {
        put(KEY_PRICE, price);
    }

    public PlanadayEvent[] getEvents() throws JSONException {
        JSONArray results = getJSONArray(KEY_EVENTS);
        return PlanadayEvent.fromJSONArray(results, getUser());
    }

    public String[] getTypes() throws JSONException {
        JSONArray results = getJSONArray(KEY_TYPES);
        String[] types = new String[results.length()];
        for (int i = 0; i < results.length(); i++) {
            types[i] = results.get(i).toString();
        }
        return types;
    }

    public void setTypes(String[] types) {
        put(KEY_TYPES, types);
    }

    public String getEnvironment() {
        return getString(KEY_ENVIRONMENT);
    }

    public void setEnvironment(String environment) {
        put(KEY_ENVIRONMENT, environment);
    }

    public String getSetting() {
        return getString(KEY_SETTING);
    }

    public void setSetting(String setting) {
        put(KEY_SETTING, setting);
    }

}
