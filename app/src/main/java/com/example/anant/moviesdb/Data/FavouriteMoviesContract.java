package com.example.anant.moviesdb.Data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by anant on 5/3/18.
 */

public final class FavouriteMoviesContract {

    public static final String AUTHORITY = "com.example.anant.moviesdb";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String path = "favourites";


    public static final class FavouriteEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(path).build();

        public static final String TABLE_NAME = "favourites";
        public static final String COLUMN_MOVIE_NAME = "movieName";
        public static final String COLUMN_MOVIE_RATING = "movieRating";
        public static final String COLUMN_MOVIE_DATE = "movieDate";
        public static final String COLUMN_MOVIE_TRAILERS = "movieTrailers";
        public static final String COLUMN_REVIEWS = "movieReview";
        public static final String COLUMN_BACKGROUND_IMAGE = "movieBackground";
        public static final String COLUMN_MOVIE_DETAILS = "movieDetails";
        public static final String COLUMN_POSTER_IMAGE = "moviePoster";

    }
}
