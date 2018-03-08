package com.example.anant.moviesdb.Utilities;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by anant on 28/2/18.
 */

public class MovieDetails {

    private String mJSONResults;

    private URL buildURL(String URL, String id, String type) {

        URL url = null;

        Uri builtUri = Uri.parse(URL).buildUpon().
                appendPath(id).
                appendPath(type).
                appendQueryParameter("api_key", com.example.anant.moviesdb.BuildConfig.YOU_API_KEY).
                appendQueryParameter("language", "en-US").
                build();

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public String getJSONResponse(String url, String id, String type) throws IOException {

        mJSONResults = null;

        HttpURLConnection urlConnection = (HttpURLConnection) buildURL(url, id, type).openConnection();

        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            if (scanner.hasNext()) {
                mJSONResults = scanner.next();
                return mJSONResults;
            } else return null;
        } finally {
            urlConnection.disconnect();
        }
    }

    public ArrayList<String> parseJSONLists() throws JSONException {

        ArrayList<String> mylist = new ArrayList<String>();

        JSONObject jsonObject = new JSONObject(mJSONResults);
        JSONArray array = jsonObject.getJSONArray("results");
        for (int i = 0; i < array.length(); i++) {
            JSONObject path = array.getJSONObject(i);
            if (path.getString("type").equals("Trailer")) {
                mylist.add(path.getString("key"));
            }
        }
        return mylist;
    }


    //To get the author and the Reviews. if 0th index contains the author, the next one is the review.

    public ArrayList<String> parseJSONReviews() throws JSONException {

        ArrayList<String> mylist = new ArrayList<String>();
        JSONObject jsonObject = new JSONObject(mJSONResults);
        JSONArray array = jsonObject.getJSONArray("results");
        for (int i = 0; i < array.length(); i++) {
            JSONObject path = array.getJSONObject(i);
            mylist.add(path.getString("author"));
            mylist.add(path.getString("content"));
        }
        return mylist;
    }

    public URL buildThumbnailURL(String URL, String key) {

        URL url = null;
        Uri builtUri = Uri.parse(URL).buildUpon().
                appendPath(key).
                appendPath(Constants.THUMBNAIL_QUALITY).
                build();
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
