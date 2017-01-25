package com.example.vikasprasad.moviedb;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by vikas prasad on 21-01-17.
 */
public class MainActivity extends AppCompatActivity {

  public static final String POPULAR_MOVIES = "https://api.themoviedb.org/3/movie/popular?api_key=988e50c99192670dcd4d7537867100d8";
  public static final String TOP_RATED_MOVIES = "https://api.themoviedb.org/3/movie/top_rated?api_key=988e50c99192670dcd4d7537867100d8";
  private RecyclerView rvMovie;
  private ProgressDialog dialog;
  private List<MovieDetail> list;
  private RvAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    init();
    if (isNetworkAvailable()) {
      getServerMovie(POPULAR_MOVIES);
    }
  }

  private void getServerMovie(final String URL) {
    showPD();
    RequestQueue queue = Volley.newRequestQueue(this);
    StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
      @Override
      public void onResponse(String response) {
        dismissPD();
        Log.d("Response", response);
        if (!TextUtils.isEmpty(response)) {
          setAdapter(parseResponse(response));
        } else {
          Toast.makeText(MainActivity.this, "Movie list is empty. Try again later", Toast.LENGTH_SHORT).show();
        }
      }
    }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        Toast.makeText(MainActivity.this, "We are getting some technical error to fetch data. Please try again later!", Toast.LENGTH_SHORT).show();
        dismissPD();
      }
    });
    request.setShouldCache(false);
    queue.add(request);
  }

  private void init() {
    rvMovie = (RecyclerView) findViewById(R.id.rv_movie);
  }

  private boolean isNetworkAvailable() {
    ConnectivityManager connectivityManager
        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
      return true;
    } else {
      Toast.makeText(MainActivity.this, "Please check your internet connection or try again later", Toast.LENGTH_SHORT).show();
      return false;
    }
  }

  private List<MovieDetail> parseResponse(String response) {
    if (list == null)
      list = new ArrayList<>();
    else
      list.clear();
    try {
      JSONObject object = new JSONObject(response);
      JSONArray array = object.optJSONArray(MovieDetail.RESULTS);
      if (array != null) {
        for (int i = 0; i < array.length(); i++) {
          JSONObject detail = array.optJSONObject(i);
          MovieDetail model = new MovieDetail();
          model.setPoster_path(MovieDetail.BASE_IMG_URL + MovieDetail.w342 + detail.optString(MovieDetail.POSTER_PATH));
          model.setBackdrop_path(MovieDetail.BASE_IMG_URL + MovieDetail.w780 + detail.optString(MovieDetail.BACKDROP_PATH));
          model.setId(detail.optString(MovieDetail.ID));
          model.setOriginal_title(detail.optString(MovieDetail.ORIGINAL_TITLE));
          model.setOverview(detail.optString(MovieDetail.OVERVIEW));
          model.setRelease_date(detail.optString(MovieDetail.RELEASE_DATE));
          model.setVote_average(detail.optString(MovieDetail.VOTE_AVERAGE));
          list.add(model);
        }
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return list;
  }

  private void setAdapter(List<MovieDetail> list) {
    if (list == null || list.size() == 0) {
      return;
    }
    if (adapter == null) {
      RecyclerView.LayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
      rvMovie.setLayoutManager(manager);
      adapter = new RvAdapter(this, list);
      rvMovie.setAdapter(adapter);
    } else {
      adapter.notifyDataSetChanged();
    }
  }

  private void showPD() {
    if (dialog == null) {
      dialog = new ProgressDialog(this);
      dialog.setMessage("Loading ..");
      dialog.setCanceledOnTouchOutside(false);
      dialog.setCancelable(false);
      dialog.show();
    }
  }

  private void dismissPD() {
    if (dialog != null) {
      dialog.dismiss();
      dialog = null;
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    switch (id) {
      case R.id.high_rate:
        if (isNetworkAvailable()) {
          getServerMovie(TOP_RATED_MOVIES);
        }
        break;
      case R.id.popular_movies:
        if (isNetworkAvailable()) {
          getServerMovie(POPULAR_MOVIES);
        }
        break;
    }
    return super.onOptionsItemSelected(item);
  }
}