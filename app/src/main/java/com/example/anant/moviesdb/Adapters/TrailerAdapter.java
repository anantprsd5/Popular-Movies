package com.example.anant.moviesdb.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.anant.moviesdb.R;
import com.example.anant.moviesdb.Utilities.Constants;
import com.example.anant.moviesdb.Utilities.TrailerList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by anant on 27/2/18.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    Context context;
    private ImageButton trailerImageView;
    private ArrayList<String> mKeys;
    private TrailerList mTrailerList;
    private final ListItemClickListener mListItemClicked;

    public TrailerAdapter(ArrayList<String> keys, TrailerList trailerList, ListItemClickListener itemClickListener) {
        mTrailerList = trailerList;
        mKeys = keys;
        mListItemClicked = itemClickListener;
    }

    public interface ListItemClickListener {
        void listItemClicked(int index, ArrayList<String> keys);
    }

    @Override
    public TrailerAdapter.TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.trailer_view, parent, false);
        return new TrailerAdapter.TrailerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        Picasso.with(context)
                .load(mTrailerList.buildThumbnailURL(Constants.YOUTUBE_THUMBNAIL_BASE_URL, mKeys.get(position)).toString())
                .placeholder(R.drawable.loading_image)
                .into(trailerImageView);
    }

    @Override
    public int getItemCount() {
        return mKeys != null ? mKeys.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TrailerViewHolder(View v) {
            super(v);
            trailerImageView = (ImageButton) v.findViewById(R.id.trailer_image_view);
            trailerImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mListItemClicked.listItemClicked(position, mKeys);
        }
    }
}
