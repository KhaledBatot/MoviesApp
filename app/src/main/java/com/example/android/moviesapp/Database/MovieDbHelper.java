package com.example.android.moviesapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.moviesapp.models.movie;

import java.util.ArrayList;

/**
 * Created by khaled on 11/1/2016.
 */
public class MovieDbHelper extends SQLiteOpenHelper {
    SQLiteDatabase sqLiteDatabase;

    static final String DATABASE_NAME = "favfilm.db";
    static final int DATABASE_VERSION = 1;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE "+MovieContract.MovieEntry.TABLE_NAME+" ("+
                MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY, "+
                MovieContract.MovieEntry.MOVIE_TITLE+" TEXT NOT NULL, "+
                MovieContract.MovieEntry.MOVIE_RELEASE+" TEXT NOT NULL, "+
                MovieContract.MovieEntry.MOVIE_VOTE+" TEXT NOT NULL, "+
                MovieContract.MovieEntry.MOVIE_OVERVIEW+" TEXT NOT NULL, "+
                MovieContract.MovieEntry.MOVIE_POSTER+" TEXT NOT NULL, "+
                MovieContract.MovieEntry.IMAGE_URL+" TEXT NOT NULL, "+
                "UNIQUE ("+MovieContract.MovieEntry._ID+") ON CONFLICT IGNORE);";
        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        onCreate(db);
    }
    public  void insertMovie(movie movie){
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues c=new ContentValues();
        c.put(MovieContract.MovieEntry._ID,movie.getId());
        c.put(MovieContract.MovieEntry.MOVIE_TITLE,movie.getOriginal_title());
        c.put(MovieContract.MovieEntry.MOVIE_RELEASE,movie.getRelease_date());
        c.put(MovieContract.MovieEntry.MOVIE_VOTE,movie.getVote_average());
        c.put(MovieContract.MovieEntry.MOVIE_OVERVIEW,movie.getOverview());
        c.put(MovieContract.MovieEntry.MOVIE_POSTER,movie.get_back_poster_db());
        c.put(MovieContract.MovieEntry.IMAGE_URL,movie.get_image_url_db());
        sqLiteDatabase.insert(MovieContract.MovieEntry.TABLE_NAME, null, c);
        sqLiteDatabase.close();
    }

    public void deletemovie(String ID){
        sqLiteDatabase= this.getWritableDatabase();
        sqLiteDatabase.delete(MovieContract.MovieEntry.TABLE_NAME, MovieContract.MovieEntry._ID+" =?",new String[]{ID});
        sqLiteDatabase.close();
    }
    public boolean ifexist(String ID)
    {
        sqLiteDatabase= this.getWritableDatabase();
        String Query = "Select * from " + MovieContract.MovieEntry.TABLE_NAME + " where " +  MovieContract.MovieEntry._ID + " = " + ID;
        Cursor cursor = sqLiteDatabase.rawQuery(Query, null);
        if(cursor.getCount() > 0){
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }
    public Cursor getData() {
        sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM " + MovieContract.MovieEntry.TABLE_NAME, null);
    }

    public ArrayList<movie> getAllfavoritemovies(){
        ArrayList<movie> movies= new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+ MovieContract.MovieEntry.TABLE_NAME,null);
        if(cursor.moveToFirst()) {
            do {
                movie movie= new movie();
                movie.setId(cursor.getString(0));
                movie.setOriginal_title(cursor.getString(1));
                movie.setRelease_date(cursor.getString(2));
                movie.setVote_average(cursor.getString(3));
                movie.setOverview(cursor.getString(4));
                movie.setBackdrop_path(cursor.getString(5));
                movie.setImageUrl(cursor.getString(6));
                movies.add(movie);
            } while(cursor.moveToNext());
        }
        db.close();
        return movies;
    }
}