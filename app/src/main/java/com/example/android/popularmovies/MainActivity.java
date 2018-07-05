package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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
import com.example.android.popularmovies.utilities.GetAttributesFromJSONObject;
import com.example.android.popularmovies.utilities.MovieJSONObject;
import com.example.android.popularmovies.utilities.NetWorkUtils;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapterOnClickHandler {
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
            new FetchMovieTask().execute(MOST_POPULAR_MOVIE_URL);
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


    private   class  FetchMovieTask extends AsyncTask<String,Void,List<Movie>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);

        }

        @Override
        protected List<Movie> doInBackground(String... strings) {
            if (strings.length==0){
                return null;
            }
            mMovie.clear();
            String location = strings[0];
            URL movieRequestUrl = NetWorkUtils.buildUrl(location);
            try{
                String jsonMovieResponse = NetWorkUtils.getResponseFromHttpUrl(movieRequestUrl);
                JSONObject[] simpleJsonMovieData = MovieJSONObject.getSimpleMovieStringsFromJson(MainActivity.this,jsonMovieResponse);
                for (JSONObject aSimpleJsonMovieData : simpleJsonMovieData) {
                    mMovie.add(GetAttributesFromJSONObject.addToListMovie(aSimpleJsonMovieData));
                }
                return mMovie;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Movie> movieData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if(movieData!=null){
                showMovieDataView();
                mMovieAdapter.setMovieImageUris(movieData);
            } else{
                showErrorMessage();
            }
        }
    }


    @Override
    public void MovieOnClick(Movie movieDetail) {
        Intent intent =  new Intent(MainActivity.this,MovieDetail.class);
        intent.putExtra("Title",movieDetail.getTitle());
        intent.putExtra("Movie Poster",movieDetail.getMoviePostUrl());
        intent.putExtra("Release Date",movieDetail.getReleaseDate());
        intent.putExtra("Overview",movieDetail.getPlotSynopsis());
        intent.putExtra("id",movieDetail.getId());
        intent.putExtra("vote average",movieDetail.getVoteAverage());
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
//            mMovieAdapter.setMovieImageUris(null);
            new FetchMovieTask().execute(MOST_POPULAR_MOVIE_URL);
        }else if(menuID == R.id.highest_rated){
//            mMovieAdapter.setMovieImageUris(null);
            new FetchMovieTask().execute(HIGHEST_RATED_MOVIE_URL);
        }
        return super.onOptionsItemSelected(item);
    }
}
