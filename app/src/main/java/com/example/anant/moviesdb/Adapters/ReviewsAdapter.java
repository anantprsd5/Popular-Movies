package com.example.anant.moviesdb.Adapters;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.anant.moviesdb.R;

import java.util.ArrayList;
import java.util.regex.Matcher;

/**
 * Created by anant on 28/2/18.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {

    private ArrayList<String> mReviews;
    private TextView mAuthor;
    private TextView mContent;
    private TextView mLinkText;

    public ReviewsAdapter(ArrayList<String> reviews) {
        mReviews = reviews;
    }

    @Override
    public ReviewsAdapter.ReviewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.reviews_view, parent, false);
        return new ReviewsAdapter.ReviewsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ReviewsAdapter.ReviewsViewHolder holder, int position) {
        /* Since the list contains the author and the reviews one after the other.
        Setting the index to position*2 solves the issue of fetching data from the list.
         */
        int pos = position * 2;
        mAuthor.setText(mReviews.get(pos));
        if (getLinksFromText(mReviews.get(pos + 1)) != null) {
            mLinkText.setVisibility(View.VISIBLE);
            mLinkText.setText(getLinksFromText(mReviews.get(pos + 1)));
            mContent.setText(mReviews.get(pos + 1).replace(getLinksFromText(mReviews.get(pos + 1)), ""));
        } else {
            mContent.setText(mReviews.get(pos + 1));
        }
    }

    @Override
    public int getItemCount() {
        return mReviews.size() / 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ReviewsViewHolder extends RecyclerView.ViewHolder {

        public ReviewsViewHolder(View v) {
            super(v);
            mAuthor = (TextView) v.findViewById(R.id.review_name);
            mContent = (TextView) v.findViewById(R.id.review_detail);
            mLinkText = (TextView) v.findViewById(R.id.link_text_view);
            mAuthor.setPaintFlags(mAuthor.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }
    }

    private String getLinksFromText(String text) {
        String url = null;
        Matcher m = Patterns.WEB_URL.matcher(text);
        while (m.find()) {
            url = m.group();
        }
        return url;
    }
}
