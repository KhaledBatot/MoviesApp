package com.example.android.moviesapp.Adapter;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.moviesapp.R;
import com.example.android.moviesapp.models.movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by khaled on 10/27/2016.
 */
public class movieAdapter extends RecyclerView.Adapter<movieAdapter.Movieholder> {
    List<movie> moviesList;
    Context context ;
    LayoutInflater layoutInflater;
    Activity activity;
    private static RecyclerViewClickListener itemListener;

    public movieAdapter(Context context,List<movie> moviesList,Activity activity,RecyclerViewClickListener listener) {
        this.moviesList = moviesList;
        this.context=context;
        layoutInflater=layoutInflater.from(context);
        this.activity=activity;
        this.itemListener=listener;
    }

    @Override
    public Movieholder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View row= LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item,parent,false);
        Movieholder holder = new Movieholder(row);
        return holder;
    }
    @Override
    public void onBindViewHolder(Movieholder holder, final int position) {
        movie movieobj = moviesList.get(position);
        Picasso.with(context).load(Uri.parse(movieobj.getImageUrl())).into(holder.moviepic);
    }
    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    class Movieholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView moviepic;

        public Movieholder(View itemView) {
            super(itemView);
            moviepic= (ImageView)itemView.findViewById(R.id.img);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemListener.recyclerViewListClicked(view,this.getLayoutPosition());
        }
    }
    public interface RecyclerViewClickListener
    {
        void recyclerViewListClicked(View v, int position);
    }
}