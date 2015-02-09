package com.myinc.keikha.rottentomatoclient.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.myinc.keikha.rottentomatoclient.R;
import com.myinc.keikha.rottentomatoclient.adapters.BoxOfficeMoviesAdapter;
import com.myinc.keikha.rottentomatoclient.models.BoxOfficeMovie;
import com.myinc.keikha.rottentomatoclient.models.RottenTomateClient;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class BoxOfficeActivity extends ActionBarActivity {


    private ListView lvMovies;
    private BoxOfficeMoviesAdapter adapterMovies;
    private RottenTomateClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_office);

        lvMovies=(ListView) findViewById(R.id.lvMovies);
        ArrayList<BoxOfficeMovie> aMovies = new ArrayList<BoxOfficeMovie>();
        adapterMovies = new BoxOfficeMoviesAdapter(this , aMovies);
        lvMovies.setAdapter(adapterMovies);


        fetchBoxOfficeMovies();
    }

    private void fetchBoxOfficeMovies() {
        client = new RottenTomateClient();
        client.getBoxOfficeMovies(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                JSONArray items = null;
                try {

                    // Get the movies json array
                    items = response.getJSONArray("movies");

                    // Parse json array into array of model objects
                    ArrayList<BoxOfficeMovie> movies = BoxOfficeMovie.extractMoviesFromJSON(items);

                    // Load model objects into the adapter
                    for (BoxOfficeMovie movie : movies) {
                        adapterMovies.add(movie);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_box_office, menu);
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
