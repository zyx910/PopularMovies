package com.example.android.popularmovies.utilities;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.popularmovies.data.Movie;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.List;

public class FetchMovieAsyncTask extends AsyncTask<String, Void, List<Movie>> {

    private OnTaskCompleted taskCompleted;
    private List<Movie> mMovie;
    private WeakReference<ProgressBar> mProgressBar;

    public interface OnTaskCompleted {
        void onTaskCompleted(List<Movie> movies);
    }

    public FetchMovieAsyncTask(OnTaskCompleted context, List<Movie> movie, ProgressBar progressBar) {
        this.taskCompleted = context;
        mMovie = movie;
        mProgressBar = new WeakReference<>(progressBar);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressBar.get().setVisibility(View.VISIBLE);
    }

    @Override
    protected List<Movie> doInBackground(String... strings) {
        if (strings.length == 0) {
            return null;
        }
        mMovie.clear();
        String location = strings[0];
        URL movieRequestUrl = NetWorkUtils.buildUrl(location);
        try {
            String jsonMovieResponse = NetWorkUtils.getResponseFromHttpUrl(movieRequestUrl);
            JSONObject[] simpleJsonMovieData = MovieJSONObject.getSimpleMovieStringsFromJson(jsonMovieResponse);
            for (JSONObject aSimpleJsonMovieData : simpleJsonMovieData) {
                mMovie.add(GetAttributesFromJSONObject.addToListMovie(aSimpleJsonMovieData));
            }
            return mMovie;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Movie> movie) {
        taskCompleted.onTaskCompleted(movie);
    }
}
