package com.example.android.moviesapp.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.android.moviesapp.Activites.myreviews;
import com.example.android.moviesapp.BuildConfig;
import com.example.android.moviesapp.Controller.AppController;
import com.example.android.moviesapp.Database.MovieDbHelper;
import com.example.android.moviesapp.R;
import com.example.android.moviesapp.models.movie;
import com.example.android.moviesapp.models.trailer;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by khaled on 10/27/2016.
 */
public class DetailFragment extends android.support.v4.app.Fragment {

    movie data;
    ListView List_view_trailers;
    ArrayList<trailer> List_trailers;
    ArrayAdapter<String> adapter_trailers;
    String video_review_id;
    TextView overview;
    TextView original_title;
    TextView Rate;
    boolean favor_button_check = false;
    ImageView favorit;
    ImageView backdrop;
    Button review;
    MovieDbHelper movieDbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_detail, container, false);
        init(root);
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),myreviews.class);
                i.putExtra("ID",video_review_id);
                startActivity(i);
            }
        });
        favorit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(favor_button_check == false) {
                    favorit.setImageResource(R.drawable.ic_favorite_full);
                    favor_button_check =true;
                    movieDbHelper.insertMovie(data);
                    Toast.makeText(getContext(),"saved",Toast.LENGTH_SHORT).show();
                }
                else {
                    favorit.setImageResource(R.drawable.ic_favorite_border);
                    favor_button_check =false;
                    movieDbHelper.deletemovie(video_review_id);
                    Toast.makeText(getContext(),"unsaved",Toast.LENGTH_SHORT).show();
                }
            }
        });
        List_view_trailers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(List_trailers.get(position).getKey())));
            }
        });
        return root;
    }
    public movie getMovieObject(){
        if(getArguments().getParcelable("moviedetail")!=null) {
            data = getArguments().getParcelable("moviedetail");
        }
        return data;
    }
    public void UpdateData(movie movie){
        data = movie;
        if(data!=null) {
            Picasso.with(getContext()).load(Uri.parse(data.getBackdrop_path())).into(backdrop);
            overview.setText(data.getOriginal_title());
            original_title.setText(data.getOverview());
            Rate.setText((data.getVote_average()));
            video_review_id=data.getId();
            Uri link=Uri.parse("https://api.themoviedb.org/3/movie").buildUpon().appendPath(video_review_id).appendPath("videos").appendQueryParameter("api_key", BuildConfig.OPEN_Movies_MAP_API_KEY).build();
            sendJsonRequest(link.toString());
        }
    }
    public void sendJsonRequest(String url) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                List_trailers = new ArrayList<trailer>();
                ArrayList<String> tmp = new ArrayList<String>();
                try {
                    JSONArray array = jsonObject.getJSONArray("results");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        trailer TRI = new trailer();
                        TRI.setKey(object.getString("key"));
                        TRI.setName("Trailer : " + String.valueOf(i+1));
                        tmp.add(TRI.getName());
                        List_trailers.add(TRI);
                    }
                    if(tmp.size()>0) {
                        adapter_trailers = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, tmp);
                        List_view_trailers.setAdapter(adapter_trailers);
                    }
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
    private void init(View root){
        backdrop=(ImageView)root.findViewById(R.id.cover);
        overview=(TextView)root.findViewById(R.id.film_title);
        original_title=(TextView)root.findViewById(R.id.description);
        favorit=(ImageView)root.findViewById(R.id.favor);
        review=(Button)root.findViewById(R.id.rev);
        Rate=(TextView)root.findViewById(R.id.rate_value);
        List_view_trailers=(ListView)root.findViewById(R.id.trailer_list);
        List_trailers = new ArrayList<trailer>();
        movieDbHelper = new MovieDbHelper(getContext());
        movie temp = getMovieObject();
        UpdateData(temp);
        if(getArguments().getParcelable("moviedetail")!=null)
        {
            UpdateData((movie) getArguments().getParcelable("moviedetail"));
        }
        handleFavoriteDetailFilm();
    }
    private void handleFavoriteDetailFilm(){
        if(movieDbHelper.ifexist(video_review_id))
        {
            favorit.setImageResource(R.drawable.ic_favorite_full);
            favor_button_check= true;
        }
    }
}
