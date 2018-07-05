package com.example.android.popularmovies.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class MovieJSONObject {
    public static JSONObject[] getSimpleMovieStringsFromJson(String movieJSONStr)
            throws JSONException{
        final String OWM_LIST = "results";
        JSONObject forecastJson = new JSONObject(movieJSONStr);
        JSONArray movieArray = forecastJson.getJSONArray(OWM_LIST);
        JSONObject movieJSONObjects[] = new JSONObject[movieArray.length()];
        for(int i = 0; i < movieArray.length();i++){
            movieJSONObjects[i] = movieArray.getJSONObject(i);
        }
        return movieJSONObjects;
    }
}

