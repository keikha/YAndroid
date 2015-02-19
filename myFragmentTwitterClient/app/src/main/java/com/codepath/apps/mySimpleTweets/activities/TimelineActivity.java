package com.codepath.apps.mySimpleTweets.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mySimpleTweets.EndlessScrollListener;
import com.codepath.apps.mySimpleTweets.R;
import com.codepath.apps.mySimpleTweets.TweetsArrayAdapter;
import com.codepath.apps.mySimpleTweets.TwitterApplication;
import com.codepath.apps.mySimpleTweets.TwitterClient;
import com.codepath.apps.mySimpleTweets.fragments.HomeTimelineFragment;
import com.codepath.apps.mySimpleTweets.fragments.MentionsTimelineFragment;
import com.codepath.apps.mySimpleTweets.fragments.SmartFragmentStatePagerAdapter;
import com.codepath.apps.mySimpleTweets.fragments.TweetsListFragmen;
import com.codepath.apps.mySimpleTweets.models.Tweet;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimelineActivity extends ActionBarActivity {

    private static final int REQUEST_RESULT = 50;
    private TweetsListFragmen fragmentTweetsList;
    private  ViewPager vpPager;
    private TweetsPagerAdapter tweetsPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);


        // Get the viewPager
        vpPager = (ViewPager) findViewById(R.id.viewpager);

        // Set the viewPager Adapter for the view pager
        tweetsPager = new TweetsPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(tweetsPager);

        // Find the sliding tabstrip
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabStrip.setViewPager(vpPager);

        //attach the tabstrip to the viewpager





//        getUserScreenName();
//        getUSerInfo();

//        lvTweets.setOnScrollListener(new EndlessScrollListener() {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount) {
//                if(totalItemsCount<= tweets.size())
//                {
//                    populateMore(aTweets.getItem(totalItemsCount-1).getUid());
//                }
//            }
//        });
//
//
//        swipContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
//        swipContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                populateTimelie();
//            }
//        });
//
//        swipContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);

    }



//    public void getUSerInfo()
//    {
//        client.getUserInfo(new JsonHttpResponseHandler()
//        {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                try {
//                    userName = response.getString("name");
//                    profileImageURL = response.getString("profile_image_url");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                Log.d("DEBUG" , errorResponse.toString());
//            }
//
//
//        } , screenName);
//    }



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
//            i.putExtra("name", userName);
//            i.putExtra("screenname", screenName);
//            i.putExtra("profileImage", profileImageURL);
//
            startActivityForResult(i, REQUEST_RESULT);

        }
       return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {



        if(requestCode==REQUEST_RESULT)
        {
            if(resultCode==RESULT_OK) {
                String body = data.getStringExtra("body");
                ((HomeTimelineFragment) tweetsPager.getRegisteredFragment(0)).postTweet(body);
//                Toast.makeText(getApplicationContext(), "returned : " + body, Toast.LENGTH_SHORT).show();
//                ((HomeTimelineFragment) tweetsPager.getRegisteredFragment(0)).populateTimeline();
            }
        }
    }

    public void onProfileView(MenuItem item) {
        // launch the profile view
        Intent i = new Intent(this , ProfileActivity.class);
        startActivity(i);

    }



    // returns the order of the fragments in the vew pager
    public static class TweetsPagerAdapter extends SmartFragmentStatePagerAdapter
    {

        private static int NUM_ITEMS = 2;

        private String tabTitles[] = {"Home" , "Mentions"};


        // adapter gets the manager insert or remove fragments from activity
        public TweetsPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        //
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }


        // control the order and creation of fragment
        @Override
        public Fragment getItem(int position) {


            if(position==0)
            {
                return HomeTimelineFragment.newInstance();
            }
            else if (position==1)
            {
                return MentionsTimelineFragment.newInstance();
            }

            else
                return null;
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
    }


}
