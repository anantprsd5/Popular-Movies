package com.example.anant.moviesdb.Utilities;

import android.content.Context;
import android.util.DisplayMetrics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by anant on 8/3/18.
 */

public class Helper {

    private Context mContext;
    private ArrayList<String> trailers;
    private ArrayList<String> reviews;

    public Helper(Context context) {
        mContext = context;
    }

    public int calculateNoOfColumns() {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int numColumns = (int) (dpWidth / 180);
        return numColumns > 2 ? numColumns : 2;
    }

    public String convertTrailerArrayToString(ArrayList<String> items) {
        JSONObject json = new JSONObject();
        try {
            json.put("trailerArrays", new JSONArray(items));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String arrayList = json.toString();
        return arrayList;
    }

    public String convertArrayToString(ArrayList<String> items) {
        JSONObject json = new JSONObject();
        try {
            json.put("reviewsArrays", new JSONArray(items));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String arrayList = json.toString();
        return arrayList;
    }

    public void setTrailerKeys(ArrayList<String> trailerKeys) {
        trailers = trailerKeys;
    }

    public void setReviewsArray(ArrayList<String> reviewsArray) {
        reviews = reviewsArray;
    }

    public String getReviews() {
        return convertArrayToString(reviews);
    }

    public String getTrailerKeys() {
        return convertTrailerArrayToString(trailers);
    }
}
