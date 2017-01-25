package com.example.vikasprasad.moviedb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by vikas prasad on 21-01-17.
 */
public class MovieDetailActivity extends AppCompatActivity {

  private MovieDetail detail;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie_detail);
    detail = getIntent().getParcelableExtra("DataModel");
    init();
  }

  private void init() {
    TextView tvTittle = (TextView) findViewById(R.id.tv_movie_tittle);
    TextView tvReleaseDate = (TextView) findViewById(R.id.tv_released_date);
    TextView tvRating = (TextView) findViewById(R.id.tv_rating);
    TextView tvDetail = (TextView) findViewById(R.id.tv_detail);
    ImageView ivPoster = (ImageView) findViewById(R.id.iv_poster_detail);
    if (detail != null) {
      try {
        tvTittle.setText(detail.getOriginal_title());
        tvDetail.setText(detail.getOverview());
        Picasso.with(this)
            .load(detail.getBackdrop_path())
            .placeholder(R.drawable.user_placeholder)
            .error(R.drawable.user_placeholder)
            .into(ivPoster);
        tvReleaseDate.setText("Released on: " + detail.getRelease_date());
        tvRating.setText("Rating: " + detail.getVote_average());
      } catch (NullPointerException e) {
        Log.e("Id Not found", e.getMessage());
      }
    }
  }
}
