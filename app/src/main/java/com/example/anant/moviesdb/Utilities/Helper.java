package com.example.anant.moviesdb.Utilities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.DisplayMetrics;

import com.example.anant.moviesdb.Data.FavouriteMoviesContract;

/**
 * Created by anant on 8/3/18.
 */

public class Helper {

    private Context mContext;

    public Helper(Context context){
        mContext = context;
    }

    public int calculateNoOfColumns() {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int numColumns = (int) (dpWidth / 180);
        return numColumns > 2 ? numColumns : 2;
    }

    public Cursor getFavMovies(SQLiteDatabase db) {

        return db.query(FavouriteMoviesContract.FavouriteEntry.TABLE_NAME,
                new String[]{FavouriteMoviesContract.FavouriteEntry.COLUMN_POSTER_IMAGE, FavouriteMoviesContract.FavouriteEntry.COLUMN_MOVIE_NAME},
                null,
                null,
                null,
                null,
                FavouriteMoviesContract.FavouriteEntry._ID);

    }
}
