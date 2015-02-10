package com.codepath.apps.mySimpleTweets.activities;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.mySimpleTweets.EndlessScrollListener;
import com.codepath.apps.mySimpleTweets.R;
import com.codepath.apps.mySimpleTweets.TweetsArrayAdapter;
import com.codepath.apps.mySimpleTweets.TwitterApplication;
import com.codepath.apps.mySimpleTweets.TwitterClient;
import com.codepath.apps.mySimpleTweets.models.Tweet;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimelineActivity extends ActionBarActivity {

    private TwitterClient client;
    private TweetsArrayAdapter aTweets;
    private ArrayList<Tweet> tweets;
    private ListView lvTweets;
    private SwipeRefreshLayout swipContainer;
    private static final int REQUEST_RESULT = 50;
    private String screenName="";
    private String userName="";
    private String profileImageURL="";



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
        getUserScreenName();
        getUSerInfo();

        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if(totalItemsCount<= tweets.size())
                {
                    populateMore(aTweets.getItem(totalItemsCount-1).getUid());
                }
            }
        });


        swipContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateTimelie();
            }
        });

        swipContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }



    public void getUSerInfo()
    {
        client.getUserInfo(new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    userName = response.getString("name");
                    profileImageURL = response.getString("profile_image_url");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG" , errorResponse.toString());
            }


        } , screenName);
    }
    public void getUserScreenName()
    {

        client.getAccountSetting(new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    screenName = response.getString("screen_name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG" , errorResponse.toString());
            }
        }
                );
    }


    public void populateMore(Long id)
   {
        client.getHomeTimeline(new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                ArrayList<Tweet> tweets = Tweet.fromJSONArray(response);
                aTweets.addAll(tweets);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG" , errorResponse.toString());
            }
        }
         , id);
   }

    public void populateRefresh(Long id)
    {
        client.refreshHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                ArrayList<Tweet> tweets_new = Tweet.fromJSONArray(response);

                tweets.addAll(0,tweets_new);
                Toast.makeText( getApplicationContext(), "no of new tweets: " + tweets_new.size(), Toast.LENGTH_SHORT).show();
                aTweets.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        }
                , id);
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
//                                       Log.d("DEBUG", response.toString());
                                       // get JSON here
                                       // Deserialize json
                                       // create models and add them to the adapter
                                       // Load the model data into the list view
                                       aTweets.clear();
                                       ArrayList<Tweet> tweets = Tweet.fromJSONArray(response);
                                       aTweets.addAll(tweets);
                                       swipContainer.setRefreshing(false);

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
        if(id== R.id.miCompose)
        {


            Intent i = new Intent( this , ComposeActivity.class);
            i.putExtra("name", userName);
            i.putExtra("screenname", screenName);
            i.putExtra("profileImage", profileImageURL);

            startActivityForResult(i  , REQUEST_RESULT);

        }
       return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==REQUEST_RESULT)
        {
            if(resultCode==RESULT_OK) {

                String body = data.getStringExtra("body");
                client.postStatus(new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                        Long id = tweets.get(0).getUid();
//                        populateRefresh(id);
                        populateTimelie();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                }, body);

            }
        }
    }
}
