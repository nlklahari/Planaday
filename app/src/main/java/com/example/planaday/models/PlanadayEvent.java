package com.example.planaday.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

@ParseClassName("Event")
public class PlanadayEvent extends ParseObject {

    private static final String KEY_USER = "user";
    private static final String KEY_NAME = "name";
    private static final String KEY_DURATION = "duration";
    private static final String KEY_TYPES = "types"; // categories of types that this event falls under
    private static final String KEY_LOCATION = "location"; // could be stored as lat and long
    private static final String KEY_ENVIRONMENT = "environment"; //indoor or outdoor
    private static final String KEY_SETTING = "setting"; //individual or group
    private static final String KEY_PRICE = "price";

    public PlanadayEvent() {}

    public static List<PlanadayEvent> fromJSONArray(JSONArray results, ParseUser user) throws JSONException {
        List<PlanadayEvent> events = new ArrayList<>();
        for (int i = 0; i < results.length(); i++) {
            PlanadayEvent newEvent = new PlanadayEvent();
            newEvent.setUser(user);
            newEvent.setDuration(results.getInt(i));
            // TODO
        }
        return events;
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser parseUser) {
        put(KEY_USER, parseUser);
    }

    public String getEventName() {
        return getString(KEY_NAME);
    }

    public void setEventName(String name) {
        put(KEY_NAME, name);
    }

    public int getDuration() {
        return getInt(KEY_DURATION);
    }

    public void setDuration(int duration) {
        put(KEY_DURATION, duration);
    }

    public String[] getTypes() throws JSONException {
        JSONArray array = getJSONArray(KEY_TYPES);
        String[] types = new String[array.length()];
        for (int i = 0; i < array.length(); i++) {
            types[i] = array.get(i).toString();
        }
        return types;
    }

    public void setTypes(String[] types) {
        put(KEY_TYPES, types);
    }

    public String getLocation() {
        return getString(KEY_LOCATION);
    }

    public void setLocation(String location) {
        put(KEY_LOCATION, location);
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

    public double getPrice() {
        return getDouble(KEY_PRICE);
    }

    public void setPrice(int price) {
        put(KEY_PRICE, price);
    }
}
