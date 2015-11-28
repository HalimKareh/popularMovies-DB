package com.example.android.sunshine.app;


import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

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
 * Created by HALIM on 11/28/2015.
 */
public class MovieFragment extends Fragment {

    private MovieAdapter mAdapter;
    private GridView gridViewMovies;
    private ArrayList<Movie> moviesData;

    public MovieFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.moviefragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
            return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        gridViewMovies = (GridView) rootView.findViewById(R.id.gridView);

        moviesData = new ArrayList<>();
        mAdapter = new MovieAdapter(getActivity(),R.id.grid_item_movie_imageview,moviesData);
        gridViewMovies.setAdapter(mAdapter);

        FetchWeatherTask weatherTask = new FetchWeatherTask();
        weatherTask.execute();


        return rootView;
    }

    public class FetchWeatherTask extends AsyncTask<String,Void, Integer> {


        @Override
        protected Integer doInBackground(String... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String moviesJsonStr = null;
           // String format="json";
           // String units="metric";
           // int numDays=7;
            String api="4d081fefd27c8fe3e0bbeb0ada19925e";
            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are available at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                final String MOVIE_BASE_URL=
                        "http://api.themoviedb.org/3/discover/movie?";
                //final String QUERY_PARAM="q";
                //final String FORMAT_PARAM="mode";
                //final String UNITS_PARAM="units";
                //final String DAYS_PARAM="cnt";
                final String API_KEY="api_key";

                Uri builtURI = Uri.parse(MOVIE_BASE_URL).buildUpon()
                        .appendQueryParameter(API_KEY, api)
                        .build();
                URL url = new URL(builtURI.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    moviesJsonStr = null;
                }
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
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attempting
                // to parse it.
                moviesJsonStr = null;
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

            try {
                return getWeatherDataFromJson(moviesJsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if(moviesData!=null){
                mAdapter.clear();
                mAdapter.setGridData(moviesData);
                }
            else
                Toast.makeText(getActivity(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }

        }

        /**
         * Take the String representing the complete forecast in JSON Format and
         * pull out the data we need to construct the Strings needed for the wireframes.
         *
         * Fortunately parsing is easy:  constructor takes the JSON string and converts it
         * into an Object hierarchy for us.
         */
        private Integer getWeatherDataFromJson(String moviesJsonStr)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String TMDB_RESULTS = "results";

            JSONObject movieJson = new JSONObject(moviesJsonStr);
            JSONArray moviesArray = movieJson.getJSONArray(TMDB_RESULTS);


            for(int i = 0; i < moviesArray.length(); i++) {
                // For now, using the format "Day, description, hi/low"
                String title;
                String poster_path;
                String overview;

                // Get the JSON object representing a movie
                JSONObject movie = moviesArray.getJSONObject(i);
                title = movie.getString("original_title");
                poster_path = movie.getString("poster_path");
                overview = movie.getString("overview");

                moviesData.add(new Movie(title, poster_path, overview));
            }

            return 1;
        }
}

