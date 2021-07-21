package com.example.planaday.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

@ParseClassName("Plan")
public class Plan extends ParseObject {

    private static final String KEY_USER = "user";
    private static final String KEY_NAME = "name";
    private static final String KEY_DURATION = "duration";
    private static final String KEY_DATE = "date";
    private static final String KEY_EVENTS = "events";
    private static final String KEY_TYPES = "types"; // categories of types that this event falls under
    private static final String KEY_ENVIRONMENT = "environment"; //indoor or outdoor
    private static final String KEY_SETTING = "setting"; //individual or group
    private static final String KEY_PRICE = "price";

    public Plan() {}

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

    public String getDuration() {
        return getString(KEY_DURATION);
    }

    public void setDuration(int duration) {
        put(KEY_DURATION, duration);
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

    public double getPrice() {
        return getDouble(KEY_PRICE);
    }

    public void setPrice(int price) {
        put(KEY_PRICE, price);
    }
}
