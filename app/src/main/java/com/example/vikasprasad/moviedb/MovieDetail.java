package com.example.vikasprasad.moviedb;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vikas prasad on 21-01-17.
 */
public class MovieDetail implements Parcelable{
  public static final String BASE_IMG_URL = "http://image.tmdb.org/t/p/";
  public static final String w342 = "w342/";
  public static final String w780 = "w780/";
  public static final String RESULTS = "results";
  public static final String BACKDROP_PATH = "backdrop_path";
  public static final String ID = "id";
  public static final String ORIGINAL_TITLE = "title";
  public static final String OVERVIEW = "overview";
  public static final String POSTER_PATH = "poster_path";
  public static final String RELEASE_DATE = "release_date";
  public static final String VOTE_AVERAGE = "vote_average";
  private String backdrop_path;
  private String id;
  private String original_title;
  private String overview;
  private String poster_path;
  private String release_date;
  private String vote_average;

  protected MovieDetail(Parcel in) {
    backdrop_path = in.readString();
    id = in.readString();
    original_title = in.readString();
    overview = in.readString();
    poster_path = in.readString();
    release_date = in.readString();
    vote_average = in.readString();
  }

  public static final Creator<MovieDetail> CREATOR = new Creator<MovieDetail>() {
    @Override
    public MovieDetail createFromParcel(Parcel in) {
      return new MovieDetail(in);
    }

    @Override
    public MovieDetail[] newArray(int size) {
      return new MovieDetail[size];
    }
  };

  public MovieDetail() {

  }

  public String getBackdrop_path() {
    return backdrop_path;
  }

  public void setBackdrop_path(String backdrop_path) {
    this.backdrop_path = backdrop_path;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getOriginal_title() {
    return original_title;
  }

  public void setOriginal_title(String original_title) {
    this.original_title = original_title;
  }

  public String getOverview() {
    return overview;
  }

  public void setOverview(String overview) {
    this.overview = overview;
  }

  public String getPoster_path() {
    return poster_path;
  }

  public void setPoster_path(String poster_path) {
    this.poster_path = poster_path;
  }

  public String getRelease_date() {
    return release_date;
  }

  public void setRelease_date(String release_date) {
    this.release_date = release_date;
  }

  public String /**/getVote_average() {
    return vote_average;
  }

  public void setVote_average(String vote_average) {
    this.vote_average = vote_average;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(backdrop_path);
    dest.writeString(id);
    dest.writeString(original_title);
    dest.writeString(overview);
    dest.writeString(poster_path);
    dest.writeString(release_date);
    dest.writeString(vote_average);
  }
}
