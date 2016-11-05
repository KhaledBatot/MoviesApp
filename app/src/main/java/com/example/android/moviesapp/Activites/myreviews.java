package com.example.android.moviesapp.Activites;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.moviesapp.BuildConfig;
import com.example.android.moviesapp.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class myreviews extends AppCompatActivity {

    private TextView mytxt;
    RequestQueue requestQueue;
    ArrayList<String>revi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myreviews);
        mytxt = (TextView) findViewById(R.id.reviews_text);
        requestQueue = Volley.newRequestQueue(myreviews.this);
        setTitle("Reviews");
        String id= getIntent().getExtras().getString("ID");
        Uri build=Uri.parse("https://api.themoviedb.org/3/movie").buildUpon()
                .appendPath(id).appendPath("reviews").appendQueryParameter("api_key", BuildConfig.OPEN_Movies_MAP_API_KEY).build();
        sendJsonRequest(build.toString());
    }
    public void sendJsonRequest(String url) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                revi = new ArrayList<>();
                try {
                    JSONArray array = jsonObject.getJSONArray("results");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        revi.add("written by: " + object.getString("author")+ " -> "+ "\n" +object.getString("content"));
                    }
                    StringBuilder mybuilder =  new StringBuilder();
                    int counter = revi.size();
                    for(int i = 0 ; i < revi.size() ;i++){
                        if(i != (counter-1)) {
                            mybuilder.append(revi.get(i) + "\n\n*************************************\n\n");
                        }
                        else {
                            mybuilder.append(revi.get(i) + "\n");
                        }
                    }
                    String res=mybuilder.toString();
                    mytxt.setText(res);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        requestQueue.add(request);
    }
}