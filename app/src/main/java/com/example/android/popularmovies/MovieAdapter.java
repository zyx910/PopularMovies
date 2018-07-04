package com.example.android.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.data.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MoveAdapterHolder>{
    private List<Movie> mMovie;
    private MovieAdapterOnClickHandler mMovieAdapterOnClickHandler;

    public interface MovieAdapterOnClickHandler{
        void MovieOnClick(Movie movieDetail);
    }

    public MovieAdapter(MovieAdapterOnClickHandler movieAdapterOnClickHandler){
        mMovieAdapterOnClickHandler = movieAdapterOnClickHandler;
    }

    public class MoveAdapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final ImageView movieImage;
        private MoveAdapterHolder(View view){
            super(view);
            movieImage = view.findViewById(R.id.movie_image);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();

            Movie movieDetail = mMovie.get(adapterPosition);
            mMovieAdapterOnClickHandler.MovieOnClick(movieDetail);
        }
    }

    @NonNull
    @Override
    public MoveAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_image;
        LayoutInflater inflater = LayoutInflater.from(context);
//      boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent,false);
        return new MoveAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoveAdapterHolder holder, int position) {
        Movie fuck = mMovie.get(position);
        String url = fuck.getMoviePostUrl();
        Picasso.get().load(url).into(holder.movieImage);
    }


    @Override
    public int getItemCount() {
        if(null == mMovie) return 0;
        return mMovie.size();
    }

    public void setMovieImageUris(List<Movie> movie){
        mMovie = movie;
        notifyDataSetChanged();
    }
}
