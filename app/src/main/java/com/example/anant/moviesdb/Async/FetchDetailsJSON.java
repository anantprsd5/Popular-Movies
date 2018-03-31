package com.example.anant.moviesdb.Async;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.anant.moviesdb.Adapters.ReviewsAdapter;
import com.example.anant.moviesdb.Adapters.TrailerAdapter;
import com.example.anant.moviesdb.Utilities.Helper;
import com.example.anant.moviesdb.Utilities.MovieDetails;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by anant on 28/2/18.
 */

public class FetchDetailsJSON extends AsyncTask<String, Void, String> {

    private Context mContext;
    private MovieDetails mTrailerList;
    private String mID;
    private ArrayList<String> trailerKeys;
    private RecyclerView mRecyclerView;
    private String type;
    private Helper mHelper;

    public FetchDetailsJSON(Context context, MovieDetails movieDetails, String id, RecyclerView recyclerView, String type, Helper helper) {
        mContext = context;
        mTrailerList = movieDetails;
        mID = id;
        mRecyclerView = recyclerView;
        this.type = type;
        mHelper = helper;
    }

    @Override
    protected String doInBackground(String... strings) {
        String s = null;
        if (isNetworkOnline()) {
            try {
                s = mTrailerList.getJSONResponse(strings[0], mID, type);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return s;
    }

    @Override
    protected void onPostExecute(String s) {
        if (s != null) {
            try {
                trailerKeys = mTrailerList.parseJSONLists();
                TrailerAdapter adapter = new TrailerAdapter(trailerKeys, mTrailerList, (TrailerAdapter.ListItemClickListener) mContext);
                mRecyclerView.setAdapter(adapter);
                mHelper.setTrailerKeys(trailerKeys);
            } catch (JSONException e) {
                e.printStackTrace();
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
