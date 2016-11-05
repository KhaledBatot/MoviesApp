package com.example.android.moviesapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by khaled on 7/12/2016.
 */

public class movie implements Parcelable {

    private  String id;
    private String poster_path;
    private String backdrop_path;
    private String overview;
    private String release_date;
    private String original_title;
    private String original_language;
    private String title;
    private String vote_average;
    private String popularity;
    private String vote_count;
    private String video;
    public movie(){
    }
    protected movie(Parcel in) {
        id = in.readString();
        poster_path = in.readString();
        backdrop_path = in.readString();
        overview = in.readString();
        release_date = in.readString();
        original_title = in.readString();
        original_language = in.readString();
        title = in.readString();
        vote_average = in.readString();
    }

    public static final Parcelable.Creator<movie> CREATOR = new Parcelable.Creator<movie>() {
        @Override
        public movie createFromParcel(Parcel in) {
            return new movie(in);
        }

        @Override
        public movie[] newArray(int size) {
            return new movie[size];
        }
    };

    public String getBackdrop_path() {
        return "http://image.tmdb.org/t/p/w500/"+backdrop_path;
    }
    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }
    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVote_average() {
        return  vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }


    public String getImageUrl() {
        return "http://image.tmdb.org/t/p/w185/"+poster_path;
    }

    public void setImageUrl(String imageUrl) {
        this.poster_path = imageUrl;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String get_back_poster_db(){
        return backdrop_path;
    }
    public String get_image_url_db(){
        return poster_path;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(poster_path);
        parcel.writeString(backdrop_path);
        parcel.writeString(overview);
        parcel.writeString(release_date);
        parcel.writeString(original_title);
        parcel.writeString(original_language);
        parcel.writeString(title);
        parcel.writeString(vote_average);
    }
}