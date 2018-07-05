package com.example.android.popularmovies.data;

import android.os.Parcel;
import android.os.Parcelable;


public class Movie implements Parcelable{
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(releaseDate);
        parcel.writeString(moviePostUrl);
        parcel.writeDouble(voteAverage);
        parcel.writeString(plotSynopsis);
        parcel.writeString(movieID);

    }

    protected Movie(Parcel in) {
        title = in.readString();
        releaseDate = in.readString();
        moviePostUrl = in.readString();
        voteAverage = in.readDouble();
        plotSynopsis = in.readString();
        movieID = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }
        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
