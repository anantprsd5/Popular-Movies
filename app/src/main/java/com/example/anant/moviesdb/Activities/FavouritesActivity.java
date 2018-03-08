package com.example.anant.moviesdb.Activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.anant.moviesdb.Adapters.MoviesAdapter;
import com.example.anant.moviesdb.Data.FavouriteMoviesContract;
import com.example.anant.moviesdb.Data.FavouritesDbHelper;
import com.example.anant.moviesdb.R;
import com.example.anant.moviesdb.Utilities.Helper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavouritesActivity extends AppCompatActivity implements MoviesAdapter.ListItemClickListener {

    @BindView(R.id.favRecycler)
    RecyclerView mRecyclerView;

    private SQLiteDatabase db;
    private ArrayList<String> favMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        ButterKnife.bind(this);
        FavouritesDbHelper favouritesDbHelper = new FavouritesDbHelper(this);
        db = favouritesDbHelper.getReadableDatabase();
        Helper helper = new Helper(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, helper.calculateNoOfColumns());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        Cursor cursor = helper.getFavMovies(db);
        favMovies = new ArrayList<>();
        while (cursor.moveToNext()) {
            favMovies.add(cursor.getString(cursor.getColumnIndex(FavouriteMoviesContract.FavouriteEntry.COLUMN_POSTER_IMAGE)));
        }
        MoviesAdapter mMoviesAdapter = new MoviesAdapter(favMovies, this);
        mRecyclerView.setAdapter(mMoviesAdapter);

    }


    @Override
    public void listItemClicked(int index) {

    }
}
