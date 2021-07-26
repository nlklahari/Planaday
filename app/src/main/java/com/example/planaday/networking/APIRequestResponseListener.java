package com.example.planaday.networking;

import com.example.planaday.models.PlanadayEvent;

import java.util.List;

/**
 * Interface that executes when an API request is complete
 */
public interface APIRequestResponseListener {

    public void onComplete(String key, List<PlanadayEvent> planadayEvents);
}
