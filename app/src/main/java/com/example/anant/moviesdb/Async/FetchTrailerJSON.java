package com.example.anant.moviesdb.Async;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.example.anant.moviesdb.Adapters.TrailerAdapter;
import com.example.anant.moviesdb.Utilities.TrailerList;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by anant on 28/2/18.
 */

public class FetchTrailerJSON extends AsyncTask<String, Void, String> {

    private Context mContext;
    private TrailerList mTrailerList;
    private String mID;
    private ArrayList<String> trailerKeys;
    private RecyclerView mRecyclerView;

    public FetchTrailerJSON(Context context, TrailerList trailerList, String id, RecyclerView recyclerView) {
        mContext = context;
        mTrailerList = trailerList;
        mID = id;
        mRecyclerView = recyclerView;
    }

    @Override
    protected String doInBackground(String... strings) {
        String s = null;
        if (isNetworkOnline()) {
            try {
                s = mTrailerList.getJSONResponse(strings[0], mID);
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
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        TrailerAdapter adapter = new TrailerAdapter(trailerKeys, mTrailerList, (TrailerAdapter.ListItemClickListener) mContext);
        mRecyclerView.setAdapter(adapter);
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
