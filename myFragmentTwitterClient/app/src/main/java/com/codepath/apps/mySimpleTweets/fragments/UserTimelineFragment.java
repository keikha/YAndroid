package com.codepath.apps.mySimpleTweets.fragments;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.mySimpleTweets.TwitterApplication;
import com.codepath.apps.mySimpleTweets.TwitterClient;
import com.codepath.apps.mySimpleTweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by keikha on 2/13/15.
 */


public class UserTimelineFragment  extends  TweetsListFragmen{

    private TwitterClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = TwitterApplication.getRestClient();
        populateTimeline();

    }


    // Creates a new fragment given an int and title
    // DemoFragment.newInstance(5, "Hello");
    public static UserTimelineFragment newInstance(String screen_name) {
        UserTimelineFragment userfFragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screen_name", screen_name);
        userfFragment.setArguments(args);
        return userfFragment;
    }

    // send an API request to get the json timeline
    // populate the listview with the data
//    @Override
    protected void populateTimeline()
    {

        String screen_name = getArguments().getString("screen_name");

        client.getUserTimeline(screen_name , new JsonHttpResponseHandler()
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

                                       ArrayList<Tweet> tweets = Tweet.fromJSONArray(response);
                                       clearTweets();
                                       addAll(tweets);
//                                       swipContainer.setRefreshing(false);

                                   }

                                   // failure


                                   @Override
                                   public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                       Log.d("DEBUG", errorResponse.toString());
                                   }
                               }
        );


    }



}
