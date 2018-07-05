package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.MovieAdapter.MovieAdapterOnClickHandler;
import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.utilities.FetchMovieAsyncTask;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapterOnClickHandler, FetchMovieAsyncTask.OnTaskCompleted {
    private MovieAdapter mMovieAdapter;
    private RecyclerView mRecyclerView;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    private final List<Movie> mMovie = new ArrayList<>();

    private static final String MOST_POPULAR_MOVIE_URL =
            "http://api.themoviedb.org/3/movie/popular";
    private static final String HIGHEST_RATED_MOVIE_URL =
            "http://api.themoviedb.org/3/movie/top_rated";
    //  imageUrl  = {"http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycle_movie);

        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);

        GridLayoutManager layoutManager
                = new GridLayoutManager(this, 2);

        mRecyclerView.setLayoutManager(layoutManager);

        /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        mRecyclerView.setHasFixedSize(true);
        mMovieAdapter =  new MovieAdapter(this);

        mRecyclerView.setAdapter(mMovieAdapter);
        loadMovieData();



    }

    private void loadMovieData(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            showMovieDataView();
            fetchMovieData(MOST_POPULAR_MOVIE_URL);
        }else {
            showErrorMessage();
        }
    }

    private void showMovieDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the error message visible and hide the weather
     * View.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }



    @Override
    public void MovieOnClick(Movie movieDetail) {
        Intent intent =  new Intent(MainActivity.this,MovieDetail.class);
        intent.putExtra("Movie",movieDetail);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuID = item.getItemId();
        if(menuID == R.id.most_popular){
            mMovieAdapter.setMovieImageUris(null);
            fetchMovieData(MOST_POPULAR_MOVIE_URL);
        }else if(menuID == R.id.highest_rated){
            mMovieAdapter.setMovieImageUris(null);
            fetchMovieData(HIGHEST_RATED_MOVIE_URL);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTaskCompleted(List<Movie> movieData) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        if(movieData!=null){
            showMovieDataView();
            mMovieAdapter.setMovieImageUris(movieData);
        } else{
            showErrorMessage();
        }
    }

    private void fetchMovieData(String urlString){
        new FetchMovieAsyncTask(MainActivity.this, mMovie, mLoadingIndicator).execute(urlString);

    }
}
