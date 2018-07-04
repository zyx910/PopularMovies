package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetail extends AppCompatActivity {

    private String movieId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String movieIntentMessage;
        TextView voteAverage;
        TextView movieDetail;
        ImageView moviePosterImage;
        TextView movieReleaseTime;
        TextView movieOverview;
        TextView testTextView;


        movieDetail = findViewById(R.id.tv_display_weather);
        moviePosterImage = findViewById(R.id.movie_poster);
        movieReleaseTime = findViewById(R.id.release_time);
        movieOverview = findViewById(R.id.movie_overview);
        voteAverage = findViewById(R.id.vote_average);

        testTextView = findViewById(R.id.movie_review);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra("Title")) {
                movieIntentMessage = intentThatStartedThisActivity.getStringExtra("Title");
                movieDetail.setText(movieIntentMessage);

                String movieReleaseDate = intentThatStartedThisActivity.getStringExtra("Release Date");
                movieReleaseTime.setText(movieReleaseDate);

                double voteScore = intentThatStartedThisActivity.getDoubleExtra("vote average",0);
                String voteString = voteScore + getString(R.string.vote_score);
                voteAverage.setText(voteString);


                String moviePosterUrl = intentThatStartedThisActivity.getStringExtra("Movie Poster");
                Picasso.get().load(moviePosterUrl).into(moviePosterImage);

                String movieOverviewText = intentThatStartedThisActivity.getStringExtra("Overview");
                movieOverview.setText(movieOverviewText);
                movieId = intentThatStartedThisActivity.getStringExtra("id");
            }

        }

        testTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovieDetail.this, ReviewActivity.class);

                intent.putExtra("id", movieId);
                startActivity(intent);
            }
        });


    }


}

