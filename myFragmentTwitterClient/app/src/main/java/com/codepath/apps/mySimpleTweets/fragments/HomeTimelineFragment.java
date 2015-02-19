package com.codepath.apps.mySimpleTweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.codepath.apps.mySimpleTweets.EndlessScrollListener;
import com.codepath.apps.mySimpleTweets.R;
import com.codepath.apps.mySimpleTweets.TwitterApplication;
import com.codepath.apps.mySimpleTweets.TwitterClient;
import com.codepath.apps.mySimpleTweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by keikha on 2/12/15.
 */

public class HomeTimelineFragment extends TweetsListFragmen {

    private TwitterClient client;
    private SwipeRefreshLayout swipContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = TwitterApplication.getRestClient();
        populateTimeline();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {

        return super.onCreateView(inflater, parent, savedInstanceState);




    }

    // send an API request to get the json timeline
    // populate the listview with the data
    @Override
    protected void populateTimeline()
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

    @Override
    protected  void populateMore(Long id)
    {
        client.getHomeTimeline(new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                ArrayList<Tweet> tweets = Tweet.fromJSONArray(response);
                addAll(tweets);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG" , errorResponse.toString());
            }
        }
                , id);
    }

//    @Override
    protected  void populateRefresh(Long id)
    {
        client.refreshHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                ArrayList<Tweet> tweets_new = Tweet.fromJSONArray(response);

                addAll(0,tweets_new);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        }
                , id);
    }



}
