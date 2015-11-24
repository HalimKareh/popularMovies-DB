package com.example.halimkareh.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity {

    static TextView t1;

    public static Movie[] moviesArray=new Movie[9];
    private GridView movie_poster_grid;
    private ImageView imageView1;


    @Override
    protected void onCreate(Bundle
                                        savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        t1= (TextView)findViewById(R.id.test);
        String topRated="http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&&api_key=4d081fefd27c8fe3e0bbeb0ada19925e";
        String APIKey = "4d081fefd27c8fe3e0bbeb0ada19925e";


       // movie_poster_grid=(GridView)findViewById(R.id.movie_poster_grid);
        moviesAsyncTask hello = new moviesAsyncTask();
        hello.execute(topRated);
        ImageView i1 =(ImageView)findViewById(R.id.movie_poster_1);
        Picasso.with(this).load("http://image.tmdb.org/t/p/w185/D6e8RJf2qUstnfkTslTXNTUAlT.jpg").into(i1);



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
