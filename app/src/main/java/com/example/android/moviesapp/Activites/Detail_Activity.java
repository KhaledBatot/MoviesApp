package com.example.android.moviesapp.Activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.android.moviesapp.Fragments.DetailFragment;
import com.example.android.moviesapp.R;

public class Detail_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("Movie Detail");
        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable("moviedetail", getIntent().getParcelableExtra("moviedetail"));
            DetailFragment detailFragment = new DetailFragment();
            detailFragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().add(R.id.container_to_detail_fragement, detailFragment).commit();
        }
    }
}