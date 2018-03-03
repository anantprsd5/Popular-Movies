package com.example.anant.moviesdb.Activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
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
import com.example.anant.moviesdb.Async.FetchDetailsJSON;
import com.example.anant.moviesdb.Async.FetchReviewsJSON;
import com.example.anant.moviesdb.R;
import com.example.anant.moviesdb.Utilities.Constants;
import com.example.anant.moviesdb.Utilities.MovieDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity implements TrailerAdapter.ListItemClickListener {

    private String mName;
    private String mOverview;
    private String mRating;
    private String posterPath;
    private String backgroundPath;
    private String datePath;
    private String movieId;

    Context context;

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
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getOrientation();
        getIntentValues();
        setValues();
        setRecyclerView();
        setReviewsRecyclerView();
        fetchTrailers();
        fetchReviews();

    }

    private void fetchReviews() {
        MovieDetails movieDetails = new MovieDetails();
        new FetchReviewsJSON(this, movieDetails, movieId, trailerRecyclerView, "reviews", reviewsRecyclerView).execute(Constants.TRAILER_BASE_URL);
    }

    private void fetchTrailers() {
        MovieDetails movieDetails = new MovieDetails();
        new FetchDetailsJSON(this, movieDetails, movieId, trailerRecyclerView, "videos", reviewsRecyclerView).execute(Constants.TRAILER_BASE_URL);
    }

    private void setReviewsRecyclerView() {
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        reviewsRecyclerView.setLayoutManager(linearLayoutManager);
        reviewsRecyclerView.setHasFixedSize(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void setRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        trailerRecyclerView.setLayoutManager(linearLayoutManager);
        trailerRecyclerView.setHasFixedSize(true);
    }

    private void getOrientation() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            backgroundImage.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }

    private void setValues() {

        if (isNetworkOnline()) {
            context = this;

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

        movieDate.setText(datePath);
        movieName.setText(mName);
        movieOverview.setText(mOverview);
        movieRating.setText(mRating);

    }

    private void getIntentValues() {

        movieId = getIntent().getExtras().getString(getString(R.string.movie_id));
        mName = getIntent().getExtras().getString(getString(R.string.name_movie));
        posterPath = getIntent().getExtras().getString(getString(R.string.posters_image));
        mOverview = getIntent().getExtras().getString(getString(R.string.overview_plot));
        mRating = getIntent().getExtras().getString(getString(R.string.rating_movie));
        backgroundPath = getIntent().getExtras().getString(getString(R.string.background_image));
        datePath = getIntent().getExtras().getString(getString(R.string.release_date));
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
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.YOUTUBE_APP_BASE_URL + keys.get(index)));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(Constants.YOUTUBE_WEB_BASE_URL + keys.get(index)));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }
}