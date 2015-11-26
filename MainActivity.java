package com.example.halimkareh.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;


public class MainActivity extends Activity {



/*
    public static TextView t1;
    public static ImageView i1;

    public static TextView t2;
    public static ImageView i2;

    public static TextView t3;
    public static ImageView i3;
*/
    public static CustomGridViewAdapter customGridAdapter;
    public static GridView movie_grid;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        movie_grid = (GridView)findViewById(R.id.movies_grid);

        t1= (TextView)findViewById(R.id.text1);
        i1 =(ImageView)findViewById(R.id.movie_poster_1);

        t2= (TextView)findViewById(R.id.text2);
        i2 =(ImageView)findViewById(R.id.movie_poster_2);

        t3= (TextView)findViewById(R.id.text3);
        i3 =(ImageView)findViewById(R.id.movie_poster_3);
        */
        String topRated="http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&&api_key=4d081fefd27c8fe3e0bbeb0ada19925e";
        String APIKey = "4d081fefd27c8fe3e0bbeb0ada19925e";

        movie_grid=(GridView)findViewById(R.id.movies_grid);
        context=this;
        // movie_poster_grid=(GridView)findViewById(R.id.movie_poster_grid);
        moviesAsyncTask hello = new moviesAsyncTask();
        hello.execute(topRated);



     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
