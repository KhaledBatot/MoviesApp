package com.example.android.moviesapp.Database;

import android.provider.BaseColumns;

/**
 * Created by khaled on 10/27/2016.
 */
public class MovieContract  {
    public static final class MovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "favoritMovies";
        public static final String  MOVIE_TITLE = "title";
        public static final String  MOVIE_RELEASE ="year";
        public static final String  MOVIE_POSTER = "back_poster";
        public static final String  MOVIE_OVERVIEW ="overview";
        public static final String  MOVIE_VOTE ="vote";
        public static final String  IMAGE_URL ="Image";
    }
}
