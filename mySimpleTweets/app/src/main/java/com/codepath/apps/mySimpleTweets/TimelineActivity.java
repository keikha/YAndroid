package com.codepath.apps.mySimpleTweets;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.codepath.apps.mySimpleTweets.R;
import com.codepath.apps.mySimpleTweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TimelineActivity extends ActionBarActivity {

    private TwitterClient client;
    private TweetsArrayAdapter aTweets;
    private ArrayList<Tweet> tweets;
    private ListView lvTweets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        // find the listview
        lvTweets = (ListView) findViewById(R.id.lvTweets);
        // create the arraylist
        tweets = new ArrayList<Tweet>();
        // create the adapter
        aTweets = new TweetsArrayAdapter(this, tweets);
        // connect the adapter to the listview
        lvTweets.setAdapter(aTweets);


        client = TwitterApplication.getRestClient();
        populateTimelie();
    }


    // send an API request to get the json timeline
    // populate the listview with the data
    private void populateTimelie()
    {
        client.getHomeTimeline(new JsonHttpResponseHandler()
        {
                                   // success

                                   @Override
                                   public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                                    // root of the json is an array, it starts with [] as oppsed to json object which is {}
                                       Log.d("DEBUG", response.toString());
                                       // get JSON here
                                       // Deserialize json
                                       // create models and add them to the adapter
                                       // Load the model data into the list view
                                       ArrayList<Tweet> tweets = Tweet.fromJSONArray(response);
                                        aTweets.addAll(tweets);

                                   }

                                   // failure


                                   @Override
                                   public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                         Log.d("DEBUG" , errorResponse.toString());
                                   }
                               }
        );


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
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
