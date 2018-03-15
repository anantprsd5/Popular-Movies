package com.example.anant.moviesdb.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anant.moviesdb.Adapters.MoviesAdapter;
import com.example.anant.moviesdb.Async.FetchJSON;
import com.example.anant.moviesdb.Data.FavouriteMoviesContract;
import com.example.anant.moviesdb.R;
import com.example.anant.moviesdb.Utilities.Constants;
import com.example.anant.moviesdb.Utilities.Helper;
import com.example.anant.moviesdb.Utilities.MoviesList;

import org.json.JSONException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.ListItemClickListener {

    @BindView(R.id.rvNumbersRecycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.dialog_progress)
    ProgressBar mProgress;
    @BindView(R.id.error_network)
    TextView mErrorNetwork;

    private MoviesList mMoviesList;

    private MoviesAdapter moviesAdapter;
    private boolean favList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mMoviesList = new MoviesList();

        Helper helper = new Helper(this);

        GridLayoutManager layoutManager = new GridLayoutManager(this, helper.calculateNoOfColumns());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        new FetchJSON(mRecyclerView, mErrorNetwork, mProgress, mMoviesList, this).execute(Constants.POPULAR_BASE_URL);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.popular_sort_action) {
            item.setChecked(true);
            new FetchJSON(mRecyclerView, mErrorNetwork, mProgress, mMoviesList, this).execute(Constants.POPULAR_BASE_URL);
        } else if (item.getItemId() == R.id.top_sort_action) {
            item.setChecked(true);
            new FetchJSON(mRecyclerView, mErrorNetwork, mProgress, mMoviesList, this).execute(Constants.TOP_RATED);
        } else if (item.getItemId() == R.id.favourite_sort_action) {
            getFavMoviesList();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getFavMoviesList() {
        Cursor cursor = getContentResolver().query(FavouriteMoviesContract.FavouriteEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
        ArrayList<String> favMovies = new ArrayList<>();
        while (cursor.moveToNext()) {
            favMovies.add(cursor.getString(cursor.getColumnIndex(FavouriteMoviesContract.FavouriteEntry.COLUMN_POSTER_IMAGE)));
        }
        moviesAdapter = new MoviesAdapter(favMovies, this);
        mRecyclerView.setAdapter(moviesAdapter);
        favList = true;
    }

    @Override
    public void listItemClicked(int index) {
        if (!favList) {
            Intent intent = new Intent(this, DetailsActivity.class);
            intentValues(intent, index);
            startActivity(intent);
        } else {
        }
    }

    private void intentValues(Intent intent, int index) {
        try {
            intent.putExtra(getString(R.string.movie_id), mMoviesList.parseJSONLists(Constants.MOVIE_ID).get(index));
            intent.putExtra(getString(R.string.overview_plot), mMoviesList.parseJSONLists(Constants.OVERVIEW).get(index));
            intent.putExtra(getString(R.string.background_image), mMoviesList.parseJSONLists(Constants.BACKGROUND).get(index));
            intent.putExtra(getString(R.string.rating_movie), mMoviesList.parseJSONLists(Constants.RATING).get(index));
            intent.putExtra(getString(R.string.posters_image), mMoviesList.parseJSONLists(Constants.POSTER_PATH).get(index));
            intent.putExtra(getString(R.string.name_movie), mMoviesList.parseJSONLists(Constants.NAME).get(index));
            intent.putExtra(getString(R.string.release_date), mMoviesList.parseJSONLists(Constants.DATE).get(index));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
