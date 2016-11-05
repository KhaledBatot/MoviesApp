package com.example.android.moviesapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.android.moviesapp.Adapter.movieAdapter;
import com.example.android.moviesapp.BuildConfig;
import com.example.android.moviesapp.Controller.AppController;
import com.example.android.moviesapp.R;
import com.example.android.moviesapp.models.movie;
import com.example.android.moviesapp.changing_interface;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khaled on 10/27/2016.
 */
public class MainFragment extends Fragment {
    public static RecyclerView recyclerView;
    List<movie> movies;
    public movieAdapter adapter;
    changing_interface change;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycle);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        setHasOptionsMenu(true);
        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();
        sendJsonRequest("https://api.themoviedb.org/3/discover/movie?api_key="+ BuildConfig.OPEN_Movies_MAP_API_KEY);
    }
    public  void sendJsonRequest(String url) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                movies = new ArrayList<>();
                try {
                    JSONArray array = jsonObject.getJSONArray("results");
                    for (int i = 0; i < array.length(); i++) {
                        Gson gson = new Gson();
                        movie movie = gson.fromJson(array.getJSONObject(i).toString(), movie.class);
                        movies.add(movie);
                    }
                    adapter = new movieAdapter(getContext(),movies,getActivity(),new movieAdapter.RecyclerViewClickListener() {
                        @Override
                        public void recyclerViewListClicked(View v, int position) {
                            change.change(movies.get(position));
                        }
                    });
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }
    public void setChange(changing_interface change) {
        this.change = change;
    }
}
