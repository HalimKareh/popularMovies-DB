package com.example.android.sunshine.app;

/**
 * Created by HALIM on 11/28/2015.
 */
public class Movie
{
    private String title;
    private String poster;
    private String overview;

    public String getOverview() {
        return overview;
    }
    public String getTitle() {
        return title;
    }
    public String getPoster() {
        return poster;
    }

    public Movie(String title, String poster, String overview) {
        this.title = title;
        this.poster = "http://image.tmdb.org/t/p/w500/"+poster;
        this.overview = overview;
    }
    public Movie(){

    }
}

