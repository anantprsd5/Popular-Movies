package com.example.anant.moviesdb.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.anant.moviesdb.R;
import com.example.anant.moviesdb.Utilities.Constants;
import com.example.anant.moviesdb.Utilities.MoviesList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by anant on 16/9/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private Context context;
    private ImageView mMoviesImage;
    private ArrayList<String> mList;
    private final ListItemClickListener mListItemClicked;

    public MoviesAdapter(ArrayList<String> list, ListItemClickListener listItemClickListener) {

        mList = list;
        mListItemClicked = listItemClickListener;
    }

    public interface ListItemClickListener {
        void listItemClicked(int index);
    }

    public void setData(ArrayList<String> newList){
        mList = newList;
        notifyDataSetChanged();
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.movie_card_view, parent, false);
        return new MoviesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, final int position) {
        Picasso.with(context)
                .load(Constants.IMAGE_BASE_URL + Constants.FILE_SIZE + mList.get(position))
                .placeholder(R.drawable.loading_image).resize(500, 750)
                .into(mMoviesImage);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public MoviesViewHolder(View v) {
            super(v);
            mMoviesImage = (ImageView) v.findViewById(R.id.item_image);
            mMoviesImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mListItemClicked.listItemClicked(position);
        }
    }
}
