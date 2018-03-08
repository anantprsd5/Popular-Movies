package com.example.anant.moviesdb.Async;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.anant.moviesdb.Adapters.MoviesAdapter;
import com.example.anant.moviesdb.Utilities.Constants;
import com.example.anant.moviesdb.Utilities.MoviesList;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by anant on 21/9/17.
 */

public class FetchJSON extends AsyncTask<String, Void, String>{

    MoviesList mMoviesList;
    RecyclerView mRecyclerView;
    ProgressBar mProgress;
    TextView mErrorNetwork;
    Context mContext;

    public FetchJSON(RecyclerView recyclerView, TextView errorNetwork, ProgressBar progressBar,MoviesList moviesList, Context context){
        mRecyclerView = recyclerView;
        mProgress = progressBar;
        mErrorNetwork = errorNetwork;
        mContext = context;
        mMoviesList = moviesList;

    }

    @Override
    protected void onPreExecute() {
        if(isNetworkOnline()) {
            mProgress.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.INVISIBLE);
            mErrorNetwork.setVisibility(View.INVISIBLE);
        }
        else {
            mProgress.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.INVISIBLE);
            mErrorNetwork.setVisibility(View.VISIBLE);
        }
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String s = null;
        try {
            s =  mMoviesList.getJSONResponse(params[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    @Override
    protected void onPostExecute(String s) {
        if(s!=null && isNetworkOnline()){
            try {
                ArrayList<String> posters = mMoviesList.parseJSONLists(Constants.POSTER_PATH);
                MoviesAdapter mMoviesAdapter = new MoviesAdapter(posters, (MoviesAdapter.ListItemClickListener) mContext);
                mRecyclerView.setAdapter(mMoviesAdapter);
                mRecyclerView.setVisibility(View.VISIBLE);
                mProgress.setVisibility(View.INVISIBLE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        super.onPostExecute(s);
    }

    private boolean isNetworkOnline() {
        ConnectivityManager cm =
                (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;

    }
}
