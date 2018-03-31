package com.example.anant.moviesdb.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by anant on 5/3/18.
 */

public class FavouritesDbHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "favourites.db";
    private static final int DATABASE_VERSION = 10;

    public FavouritesDbHelper(Context context) {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_FAVOURITES_TABLE = "CREATE TABLE " +
                FavouriteMoviesContract.FavouriteEntry.TABLE_NAME + " (" +
                FavouriteMoviesContract.FavouriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FavouriteMoviesContract.FavouriteEntry.COLUMN_MOVIE_NAME + " TEXT NOT NULL, " +
                FavouriteMoviesContract.FavouriteEntry.COLUMN_MOVIE_RATING + " TEXT NOT NULL, " +
                FavouriteMoviesContract.FavouriteEntry.COLUMN_MOVIE_DATE + " TEXT NOT NULL, " +
                FavouriteMoviesContract.FavouriteEntry.COLUMN_BACKGROUND_IMAGE+ " TEXT NOT NULL, "+
                FavouriteMoviesContract.FavouriteEntry.COLUMN_MOVIE_TRAILERS+ " TEXT NOT NULL, "+
                FavouriteMoviesContract.FavouriteEntry.COLUMN_REVIEWS+ " TEXT NOT NULL, "+
                FavouriteMoviesContract.FavouriteEntry.COLUMN_MOVIE_DETAILS+ " TEXT NOT NULL, "+
                FavouriteMoviesContract.FavouriteEntry.COLUMN_POSTER_IMAGE+ " TEXT NOT NULL" +
                ")";

        db.execSQL(SQL_CREATE_FAVOURITES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + FavouriteMoviesContract.FavouriteEntry.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
