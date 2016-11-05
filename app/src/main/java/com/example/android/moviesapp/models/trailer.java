package com.example.android.moviesapp.models;

/**
 * Created by khaled on 10/22/2016.
 */
public class trailer {

    String key,name;
    String link = "https://www.youtube.com/watch?v=";

    public trailer(){

    }

    public void setKey(String key) {
        this.key =link+key ;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getKey() {
        return key;
    }
    public String getName() {
        return name;
    }


}
