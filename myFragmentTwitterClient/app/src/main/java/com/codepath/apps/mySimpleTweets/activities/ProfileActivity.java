package com.codepath.apps.mySimpleTweets.activities;

import android.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.mySimpleTweets.R;
import com.codepath.apps.mySimpleTweets.TwitterApplication;
import com.codepath.apps.mySimpleTweets.TwitterClient;
import com.codepath.apps.mySimpleTweets.fragments.UserTimelineFragment;
import com.codepath.apps.mySimpleTweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

public class ProfileActivity extends ActionBarActivity {


    TwitterClient client;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // get the screen name, comes from the activity that launches this
        String screenName = getIntent().getStringExtra("screen_name");
//        Toast.makeText( this , "image clicked " + screenName, Toast.LENGTH_SHORT).show();

        client = TwitterApplication.getRestClient();


        if(screenName==null) {
            client.getUserInfo(new JsonHttpResponseHandler() {
                                   @Override
                                   public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                       // my current user account
                                       user = User.fromJson(response);
                                       getSupportActionBar().setTitle("@" + user.getScreenName());
                                       populateProfileHeader(user);
                                   }
                               }
            );
        }

        else
        {
            client.getUserInfo(screenName , new JsonHttpResponseHandler() {
                                   @Override
                                   public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                       // my current user account
                                       user = User.fromJson(response);
                                       getSupportActionBar().setTitle("@" + user.getScreenName());
                                       populateProfileHeader(user);
                                   }
                               }
            );

        }



        if(savedInstanceState==null) {
            // create the user timeline fragment
            UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(screenName);

            // display the userFragment in this activity
            // dynamicly

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, fragmentUserTimeline);
            ft.commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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


    public void populateProfileHeader(User user)
    {
        TextView tvName = (TextView) findViewById(R.id.tvFullName);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagLine);
        TextView tvFollower = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        ImageView ivProfile = (ImageView) findViewById(R.id.ivProfileImage);

        tvName.setText(user.getName());
        tvTagline.setText(user.getTagLine());
        tvFollower.setText(user.getFollowersCount() + " Followers");
        tvFollowing.setText(user.getFollowingCount() + "Following");

        Picasso.with(this).load(user.getProfileImageUrl()).into(ivProfile);
    }
}
