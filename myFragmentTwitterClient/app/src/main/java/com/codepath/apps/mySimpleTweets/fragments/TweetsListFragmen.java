package com.codepath.apps.mySimpleTweets.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.mySimpleTweets.EndlessScrollListener;
import com.codepath.apps.mySimpleTweets.R;
import com.codepath.apps.mySimpleTweets.TweetsArrayAdapter;
import com.codepath.apps.mySimpleTweets.activities.ProfileActivity;
import com.codepath.apps.mySimpleTweets.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by keikha on 2/12/15.
 */
public class TweetsListFragmen extends Fragment {

    protected TweetsArrayAdapter aTweets;
    protected ArrayList<Tweet> tweets;
    protected ListView lvTweets;

    private SwipeRefreshLayout swipContainer;
    private static final int REQUEST_RESULT = 50;
    private String screenName="";
    private String userName="";
    private String profileImageURL="";

//    private OnItemSelectedListener listener;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // create the arraylist
        tweets = new ArrayList<Tweet>();
        // create the adapter
        aTweets = new TweetsArrayAdapter( getActivity() , tweets);
        // connect the adapter to the listview

    }



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list , parent , false);

        // find the listview
        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(aTweets);


        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {

                if(totalItemsCount<= tweets.size())
                {
                    populateMore(aTweets.getItem(totalItemsCount-1).getUid());
                }
            }
        });


//        swipContainer = (SwipeRefreshLayout) parent.findViewById(R.id.swipeContainer);
//        swipContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                populateRefresh();
//            }
//        });
//
//        swipContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);



        return v;
    }



    public void clearTweets()
    {
        aTweets.clear();
    }
    public void addAll(List<Tweet> tweets)
    {
        aTweets.addAll(tweets);
    }

    public void addAll(int index , List<Tweet> tweets )
    {
        tweets.addAll( index , tweets);
        aTweets.notifyDataSetChanged();
        Toast.makeText(getActivity().getApplicationContext(), "no of new tweets: " + tweets.size(), Toast.LENGTH_SHORT).show();
    }

    public void populateTimeline()
    {}

    public void populateMore(Long id)
    {}

    public void populateRefresh()
    {}



    // Define the events that the fragment will use to communicate
//    public interface OnItemSelectedListener {
//        public void onRssItemSelected(String link);
//    }

//    // Store the listener (activity) that will have events fired once the fragment is attached
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        if (activity instanceof OnItemSelectedListener) {
//            listener = (OnItemSelectedListener) activity;
//        } else {
//            throw new ClassCastException(activity.toString()
//                    + " must implement MyListFragment.OnItemSelectedListener");
//        }
//    }
//
//    // Now we can fire the event when the user selects something in the fragment
//    public void onSomeClick(View v) {
//        listener.onRssItemSelected("some link");
//    }



}
