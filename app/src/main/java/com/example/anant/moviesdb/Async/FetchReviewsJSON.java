package com.example.anant.moviesdb.Async;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.example.anant.moviesdb.Adapters.ReviewsAdapter;
import com.example.anant.moviesdb.Utilities.MovieDetails;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by anant on 2/3/18.
 */

public class FetchReviewsJSON extends AsyncTask<String, Void, String> {

    private Context mContext;
    private MovieDetails mTrailerList;
    private String mID;
    private String type;
    private ArrayList<String> reviews;
    private RecyclerView mReviewsRecyclerView;

    public FetchReviewsJSON(Context context, MovieDetails movieDetails, String id, String type, RecyclerView reviewsRecyclerView) {
        mContext = context;
        mTrailerList = movieDetails;
        mID = id;
        this.type = type;
        mReviewsRecyclerView = reviewsRecyclerView;
    }

    @Override
    protected String doInBackground(String... strings) {

        String s = null;

        try {
            s = mTrailerList.getJSONResponse(strings[0], mID, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    @Override
    protected void onPostExecute(String s) {
        if (isNetworkOnline()) {
            if (s != null) {
                try {
                    reviews = mTrailerList.parseJSONReviews();
                    ReviewsAdapter reviewsAdapter = new ReviewsAdapter(reviews);
                    mReviewsRecyclerView.setAdapter(reviewsAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        super.onPostExecute(s);

    }

    private boolean isNetworkOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}
