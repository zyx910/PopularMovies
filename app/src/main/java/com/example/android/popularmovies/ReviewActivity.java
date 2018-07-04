package com.example.android.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.popularmovies.utilities.MovieJSONObject;
import com.example.android.popularmovies.utilities.NetWorkUtils;

import org.json.JSONObject;

public class ReviewActivity extends AppCompatActivity {
    private RecyclerView mReviewRecycleView;
    private ReviewAdapter movieReviewAdapter;

    private static final String MOVIE_REVIEW = "http://api.themoviedb.org/3/movie/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_recycle);

        String movieId;

        Intent intent = getIntent();
        movieId = intent.getStringExtra("id");
        mReviewRecycleView = findViewById(R.id.recycle_review);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mReviewRecycleView.setLayoutManager(linearLayoutManager);
        mReviewRecycleView.setHasFixedSize(false);
        movieReviewAdapter = new ReviewAdapter();
        mReviewRecycleView.setAdapter(movieReviewAdapter);
        new FetchMovieReviewAsync().execute(movieId);
    }

    class FetchMovieReviewAsync extends AsyncTask<String,Void,JSONObject[]> {
        @Override
        protected JSONObject[] doInBackground(String... strings) {
            if(strings.length == 0) return null;
            try{
                String movieReviewUrlLocation = MOVIE_REVIEW+strings[0]+"/reviews";
                String moViewReviewJSON = NetWorkUtils.getResponseFromHttpUrl(NetWorkUtils.buildUrl(movieReviewUrlLocation));
                return MovieJSONObject.getSimpleMovieStringsFromJson(ReviewActivity.this,moViewReviewJSON);

            }catch (Exception e){
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(JSONObject[] jsonObjects) {
            if(jsonObjects != null){
                movieReviewAdapter.setMovieReview(jsonObjects);
            }
        }
    }
}