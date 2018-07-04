package com.example.android.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.utilities.GetAttributesFromJSONObject;

import org.json.JSONObject;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterHolder>{
    public ReviewAdapter(){}

    private  JSONObject[] mMovieReviews;


    public class ReviewAdapterHolder extends RecyclerView.ViewHolder{
        final TextView mMovieReview;
        private ReviewAdapterHolder(View view){
            super(view);
            mMovieReview = view.findViewById(R.id.movie_review);
        }
    }

    @NonNull
    @Override
    public ReviewAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_review;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new ReviewAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapterHolder holder, int position) {
        JSONObject mMovieReviewJSONObject = mMovieReviews[position];
        String mMovieReview = GetAttributesFromJSONObject.getMovieReviewFromJSONObject(mMovieReviewJSONObject);
        holder.mMovieReview.setText(mMovieReview);
    }

    @Override
    public int getItemCount() {
        if(null != mMovieReviews)
            return mMovieReviews.length;
        return 0;
    }

    public void setMovieReview(JSONObject[] jsonObjects){
        mMovieReviews = jsonObjects;
        notifyDataSetChanged();
    }
}
