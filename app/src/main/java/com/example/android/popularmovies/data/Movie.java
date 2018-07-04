package com.example.android.popularmovies.data;

public class Movie {
    private final String title;
    private final String releaseDate;
    private final String moviePostUrl;
    private final double voteAverage;
    private final String plotSynopsis;
    private final String movieID;

    public Movie(String mTitle, String mReleaseData, String mMoviePostUrl, double mVoteAverage, String mPlotSynopsis,String mMovieID){
        title = mTitle;
        releaseDate = mReleaseData;
        moviePostUrl = mMoviePostUrl;
        voteAverage = mVoteAverage;
        plotSynopsis = mPlotSynopsis;
        movieID = mMovieID;
    }

    public String getTitle(){
        return  title;
    }

    public String getReleaseDate(){
        return releaseDate;
    }

    public String getMoviePostUrl() {
        return moviePostUrl;
    }

    public double getVoteAverage(){
        return voteAverage;
    }

    public String getPlotSynopsis(){
        return plotSynopsis;
    }

    public String getId(){
        return movieID;
    }
}
