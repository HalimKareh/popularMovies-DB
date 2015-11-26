package com.example.halimkareh.popularmovies;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by HALIM on 11/22/2015.
 */

    public class moviesAsyncTask extends AsyncTask<String,String,ArrayList<Movie>> {


    protected ArrayList<Movie> doInBackground(String... params) {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String moviesJsonStr;

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are available at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            URL url = new URL(params[0]);

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                moviesJsonStr = null;
            }

            moviesJsonStr = buffer.toString();
            try {
                return getMovieDataFromJson(moviesJsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            // If the code didn't successfully get the data, there's no point in attempting
            // to parse it.
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        super.onPostExecute(movies);
        MainActivity.customGridAdapter = new CustomGridViewAdapter(MainActivity.context, R.layout.single_movie_item,movies);
        MainActivity.movie_grid.setAdapter(MainActivity.customGridAdapter);

/*
        Picasso.with(MainActivity.context).load("http://image.tmdb.org/t/p/w500"+MainActivity.mMoviesAdapter.getItem(0).getPoster()).into(MainActivity.i1);
        MainActivity.t1.setText((CharSequence) MainActivity.mMoviesAdapter.getItem(0).getTitle());

        Picasso.with(MainActivity.context).load("http://image.tmdb.org/t/p/w500"+MainActivity.mMoviesAdapter.getItem(1).getPoster()).into(MainActivity.i2);
        MainActivity.t3.setText((CharSequence)MainActivity.mMoviesAdapter.getItem(1).getTitle());

        Picasso.with(MainActivity.context).load("http://image.tmdb.org/t/p/w500"+MainActivity.mMoviesAdapter.getItem(2).getPoster()).into(MainActivity.i3);
        MainActivity.t3.setText((CharSequence)MainActivity.mMoviesAdapter.getItem(2).getTitle());
*/
    }


    private ArrayList<Movie> getMovieDataFromJson(String moviesJsonStr)
            throws JSONException {

        ArrayList<Movie> moviesArray=new ArrayList<Movie>();
        try {
            JSONObject jsonStr = new JSONObject(moviesJsonStr);
            JSONArray ArrayMoviesJSON = jsonStr.getJSONArray("results");


            for (int i = 0; i < ArrayMoviesJSON.length(); i++) {
                moviesArray.add(new Movie(ArrayMoviesJSON.getJSONObject(i).getString("original_title"),
                        ArrayMoviesJSON.getJSONObject(i).getString("poster_path"),
                        ArrayMoviesJSON.getJSONObject(i).getString("overview")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return moviesArray;


    }
}
