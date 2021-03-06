package com.example.planaday.models;

import android.util.Log;

import com.google.gson.Gson;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ParseClassName("Plan")
public class Plan extends ParseObject {

    public static final String KEY_AUTHOR = "author"; // public to filter query results
    private static final String KEY_NAME = "name";
    private static final String KEY_DURATION = "duration";
    private static final String KEY_TIME = "time";
    public static final String KEY_PRICE = "price";
    public static final String KEY_EVENTS = "events";
    public static final String KEY_DATE_STRING = "dateString";
    public static final String KEY_DATE = "date";

    public Plan() {}

    public ParseUser getUser() {
        return getParseUser(KEY_AUTHOR);
    }

    public void setUser(ParseUser parseUser) {
        put(KEY_AUTHOR, parseUser);
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
    public void setPlanDate(Object date) {
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

    public void setPrice(double price) {
        put(KEY_PRICE, price);
    }

    public List<PlanadayEvent> getEvents() throws JSONException {
        JSONArray results = getJSONArray(KEY_EVENTS);
        return PlanadayEvent.fromJSONArray(results, getUser());
    }

    public void setEvents(List<PlanadayEvent> events) {
        Log.i("Plan", "Putting events, size: " + events.size());
        Gson gson = new Gson();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < events.size(); i++) {
            String jsonEvent = gson.toJson(events.get(i));
            Log.i("Plan", jsonEvent);
            jsonArray.put(jsonEvent);
        }
        put(KEY_EVENTS, jsonArray);
    }

    public String getPlanDateString() {
        return getString(KEY_DATE_STRING);
    }

    public void setPlanDateString(String dateString) {
        put(KEY_DATE_STRING, dateString);
    }

}
