package com.example.android.moviesapp.Activites;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.android.moviesapp.Adapter.movieAdapter;
import com.example.android.moviesapp.BuildConfig;
import com.example.android.moviesapp.Database.MovieDbHelper;
import com.example.android.moviesapp.Fragments.DetailFragment;
import com.example.android.moviesapp.Fragments.MainFragment;
import com.example.android.moviesapp.R;
import com.example.android.moviesapp.models.movie;
import com.example.android.moviesapp.changing_interface;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements changing_interface {

    public static MainFragment mainFragment;
    private boolean tabletOrMobileDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("PoPular movie");
        if (findViewById(R.id.fragment_detail) != null) {
            tabletOrMobileDevice = true;
        }
        else {
            tabletOrMobileDevice = false;
        }
        mainFragment=(MainFragment)getSupportFragmentManager().findFragmentById(R.id.main_fragment);
        mainFragment.setChange(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.popular) {
            mainFragment.sendJsonRequest("http://api.themoviedb.org/3/movie/popular?api_key="+ BuildConfig.OPEN_Movies_MAP_API_KEY);
            setTitle("PoP Movie");
            return true;
        }
        else if(id == R.id.fav){
            setTitle("Favorites");
            MovieDbHelper db = new MovieDbHelper(MainActivity.this);
            final ArrayList<movie> allfavoritemovies = db.getAllfavoritemovies();
            mainFragment.adapter = new movieAdapter(this,allfavoritemovies,MainActivity.this, new movieAdapter.RecyclerViewClickListener() {
                @Override
                public void recyclerViewListClicked(View v, int position) {
                    change(allfavoritemovies.get(position));
                }
            });
            mainFragment.recyclerView.setAdapter(mainFragment.adapter);
            return true;
        }
        else if (id == R.id.high) {
            mainFragment.sendJsonRequest(" http://api.themoviedb.org/3/movie/top_rated?api_key="+BuildConfig.OPEN_Movies_MAP_API_KEY);
            setTitle("Top Rated");
            return true;
        }
        return onOptionsItemSelected(item);
    }
    @Override
    public void change(movie mymovie) {
        if(tabletOrMobileDevice){
            Bundle busket=new Bundle();
            busket.putParcelable("moviedetail",mymovie);
            DetailFragment detailsFragment = new  DetailFragment();
            detailsFragment.setArguments(busket);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_detail,detailsFragment).commit();
        }
        else
        {
            Intent i=new Intent(MainActivity.this,Detail_Activity.class);
            i.putExtra("moviedetail",mymovie);
            startActivity(i);
        }
    }
}