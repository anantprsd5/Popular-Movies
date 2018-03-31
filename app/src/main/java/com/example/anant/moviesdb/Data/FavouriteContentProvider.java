package com.example.anant.moviesdb.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by anant on 12/3/18.
 */

public class FavouriteContentProvider extends ContentProvider {

    private FavouritesDbHelper mDbHelper;

    public static final int FAVOURITES = 100;
    public static final int SINGLE_FAVOURITE = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();


    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(FavouriteMoviesContract.AUTHORITY, FavouriteMoviesContract.path, FAVOURITES);
        uriMatcher.addURI(FavouriteMoviesContract.AUTHORITY, FavouriteMoviesContract.path + "/*", SINGLE_FAVOURITE);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new FavouritesDbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final SQLiteDatabase db = mDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);

        Cursor retCursor = null;

        switch (match) {

            case FAVOURITES:
                retCursor = db.query(FavouriteMoviesContract.FavouriteEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        FavouriteMoviesContract.FavouriteEntry._ID);
                break;

            case SINGLE_FAVOURITE:
                //to get the name of the movie which is appended at the last
                String movie = uri.getPathSegments().get(1);
                String mSelection = "moviePoster=?";
                String[] mSelectionArgs = new String[]{"/" + movie};
                retCursor = db.query(FavouriteMoviesContract.FavouriteEntry.TABLE_NAME,
                        null,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null, null);
                break;

            default:
                throw new UnsupportedOperationException("Uri:" + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        int match = sUriMatcher.match(uri);
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        Uri returnUri = null;

        switch (match) {

            case FAVOURITES:

                long id = db.insert(FavouriteMoviesContract.FavouriteEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(FavouriteMoviesContract.FavouriteEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert" + uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown Uri:" + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int index;
        index = db.delete(FavouriteMoviesContract.FavouriteEntry.TABLE_NAME,
                selection,
                selectionArgs);

        getContext().getContentResolver().notifyChange(uri, null);
        return index;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        return 0;
    }
}
