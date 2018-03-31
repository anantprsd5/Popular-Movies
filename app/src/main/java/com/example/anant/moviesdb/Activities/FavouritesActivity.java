package com.example.anant.moviesdb.Activities;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anant.moviesdb.Adapters.ReviewsAdapter;
import com.example.anant.moviesdb.Adapters.TrailerAdapter;
import com.example.anant.moviesdb.Data.FavouriteMoviesContract;
import com.example.anant.moviesdb.R;
import com.example.anant.moviesdb.Utilities.Constants;
import com.example.anant.moviesdb.Utilities.MovieDetails;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavouritesActivity extends AppCompatActivity implements TrailerAdapter.ListItemClickListener {

    private Cursor mCursor;
    private String mName;
    private String mDate;
    private String mOverview;
    private String mRating;
    private String posterPath;
    private String backgroundPath;
    private String trailerPaths;
    private String reviewsPaths;

    @BindView(R.id.background_image)
    ImageView backgroundImage;
    @BindView(R.id.overview_movie)
    TextView movieOverview;
    @BindView(R.id.movie_name)
    TextView movieName;
    @BindView(R.id.movie_date)
    TextView movieDate;
    @BindView(R.id.poster_image)
    ImageView posterImage;
    @BindView(R.id.movie_rating)
    TextView movieRating;
    @BindView(R.id.trailer_recycler_view)
    RecyclerView trailerRecyclerView;
    @BindView(R.id.reviews_recycler_view)
    RecyclerView reviewsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        ButterKnife.bind(this);
        String name = getIntent().getExtras().getString(getString(R.string.posters_image));
        Uri movieUri = Uri.parse(FavouriteMoviesContract.FavouriteEntry.CONTENT_URI.toString() + name);
        mCursor = getContentResolver().query(movieUri,
                null,
                null,
                null,
                null);

        getValues();
        setValues();
        setTrailerRecyclerView();
        setReviewsRecyclerView();
    }

    private void getValues() {
        while (mCursor.moveToNext()) {
            mName = mCursor.getString(mCursor.getColumnIndex(FavouriteMoviesContract.FavouriteEntry.COLUMN_MOVIE_NAME));
            mDate = mCursor.getString(mCursor.getColumnIndex(FavouriteMoviesContract.FavouriteEntry.COLUMN_MOVIE_DATE));
            mOverview = mCursor.getString(mCursor.getColumnIndex(FavouriteMoviesContract.FavouriteEntry.COLUMN_MOVIE_DETAILS));
            mRating = mCursor.getString(mCursor.getColumnIndex(FavouriteMoviesContract.FavouriteEntry.COLUMN_MOVIE_RATING));
            posterPath = mCursor.getString(mCursor.getColumnIndex(FavouriteMoviesContract.FavouriteEntry.COLUMN_POSTER_IMAGE));
            backgroundPath = mCursor.getString(mCursor.getColumnIndex(FavouriteMoviesContract.FavouriteEntry.COLUMN_BACKGROUND_IMAGE));
            trailerPaths = mCursor.getString(mCursor.getColumnIndex(FavouriteMoviesContract.FavouriteEntry.COLUMN_MOVIE_TRAILERS));
            reviewsPaths = mCursor.getString(mCursor.getColumnIndex(FavouriteMoviesContract.FavouriteEntry.COLUMN_REVIEWS));
        }
    }

    private void setValues() {

        if (isNetworkOnline()) {

            Picasso.with(this)
                    .load(Constants.IMAGE_BASE_URL + Constants.FILE_SIZE_BACKGROUND + posterPath)
                    .placeholder(R.mipmap.ic_launcher).into(posterImage);

            Picasso.with(this)
                    .load(Constants.IMAGE_BASE_URL + Constants.FILE_SIZE_BACKGROUND + backgroundPath)
                    .placeholder(R.mipmap.ic_launcher).into(backgroundImage);
        } else {
            posterImage.setVisibility(View.GONE);
            backgroundImage.setVisibility(View.GONE);
        }

        movieDate.setText(mDate);
        movieName.setText(mName);
        movieOverview.setText(mOverview);
        movieRating.setText(mRating);

    }

    private ArrayList<String> convertToList(String array, String type) {
        ArrayList<String> arrayList = new ArrayList<>();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray = jsonObject.optJSONArray(type);
        for (int i = 0; i <= jsonArray.length(); i++) {
            try {
                arrayList.add(jsonArray.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return arrayList;
    }

    private void setTrailerRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        trailerRecyclerView.setLayoutManager(linearLayoutManager);
        trailerRecyclerView.setHasFixedSize(true);
        TrailerAdapter adapter = new TrailerAdapter(convertToList(trailerPaths, "trailerArrays"), new MovieDetails(), this);
        trailerRecyclerView.setAdapter(adapter);
    }

    private void setReviewsRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        reviewsRecyclerView.setLayoutManager(linearLayoutManager);
        reviewsRecyclerView.setHasFixedSize(true);
        reviewsRecyclerView.setNestedScrollingEnabled(false);
        ReviewsAdapter reviewsAdapter = new ReviewsAdapter(convertToList(reviewsPaths, "reviewsArrays"));
        reviewsRecyclerView.setAdapter(reviewsAdapter);
    }

    private boolean isNetworkOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

    }

    @Override
    public void listItemClicked(int index, ArrayList<String> keys) {

    }
}
