package com.example.vikasprasad.moviedb;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by vikas prasad on 21-01-17.
 */
public class RvAdapter extends RecyclerView.Adapter<RvAdapter.RvHolder> {
  private Context context;
  private List<MovieDetail> list;

  public RvAdapter(Context context, List<MovieDetail> list) {
    this.context = context;
    this.list = list;
  }

  @Override
  public RvAdapter.RvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_cell, parent, false);
    return new RvHolder(view);
  }

  @Override
  public void onBindViewHolder(final RvAdapter.RvHolder holder, int position) {
    MovieDetail model = list.get(position);
    Picasso.with(context)
        .load(model.getPoster_path())
        .placeholder(R.drawable.user_placeholder)
        .error(R.drawable.user_placeholder)
        .into(holder.ivPoster);
    holder.ivPoster.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra("DataModel", list.get(holder.getAdapterPosition()));
        context.startActivity(intent);
      }
    });
  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  public class RvHolder extends RecyclerView.ViewHolder {
    public ImageView ivPoster;

    public RvHolder(View itemView) {
      super(itemView);
      ivPoster = (ImageView) itemView.findViewById(R.id.iv_poster);
    }
  }
}
