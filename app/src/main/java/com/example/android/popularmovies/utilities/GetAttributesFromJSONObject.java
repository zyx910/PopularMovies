package com.example.android.popularmovies.utilities;

import com.example.android.popularmovies.data.Movie;

import org.json.JSONObject;

public final class GetAttributesFromJSONObject {


    public static Movie addToListMovie(JSONObject movieDetail){
        return new Movie(getTitleFromJSONObject(movieDetail),getReleaseDateFromJSONObject(movieDetail),getImageFromJSONObject(movieDetail),getVoteAverageFromJSONObject(movieDetail),getPlotSynopsisFromJSONObject(movieDetail),getMovieIDFromJSONObject(movieDetail));

    }

    private   static String getImageFromJSONObject(JSONObject movieDetail){
        String imageUrl;
        try {
            imageUrl = "http://image.tmdb.org/t/p/w500/" + movieDetail.getString("poster_path");
            return imageUrl;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private static String getTitleFromJSONObject(JSONObject movieDetail) {
        String movieTitle;
        try {
            movieTitle = movieDetail.getString("title");
            return movieTitle;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    private static String getReleaseDateFromJSONObject(JSONObject movieDetail) {
        String releaseDate;
        try {
            releaseDate = movieDetail.getString("release_date");
            return releaseDate;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private static double getVoteAverageFromJSONObject(JSONObject movieDetail) {
        double voteAverage;
        try {
            String voteAverageString = movieDetail.getString("vote_average");
            voteAverage = Double.parseDouble(voteAverageString);
            return voteAverage;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    private static String getPlotSynopsisFromJSONObject(JSONObject movieDetail) {
        String movieOverview;
        try {
            movieOverview = movieDetail.getString("overview");
            return movieOverview;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    private static String getMovieIDFromJSONObject(JSONObject movieDetail) {
        String movieID;
        try {
            movieID = movieDetail.getString("id");
            return movieID;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static String getMovieReviewFromJSONObject(JSONObject movieDetail) {
        String movieReview;
        try {
            movieReview = movieDetail.getString("content");
            return movieReview;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
