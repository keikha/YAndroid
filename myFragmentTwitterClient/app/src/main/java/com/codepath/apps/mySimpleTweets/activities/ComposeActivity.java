package com.codepath.apps.mySimpleTweets.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.mySimpleTweets.R;
import com.codepath.apps.mySimpleTweets.TwitterApplication;
import com.codepath.apps.mySimpleTweets.TwitterClient;
import com.codepath.apps.mySimpleTweets.models.User;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

public class ComposeActivity extends ActionBarActivity {


    private EditText etTweet;
    private ImageView ivProfile;
    private TextView username;
    private TextView screenName;

    private TwitterClient client;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        client = TwitterApplication.getRestClient();

        setContentView(R.layout.activity_compose);
        etTweet = (EditText) findViewById(R.id.etBody);
        username = (TextView) findViewById(R.id.tvUserName);
        screenName = (TextView) findViewById(R.id.tvUserScreenName);
        ivProfile = (ImageView) findViewById(R.id.ivProfileImage);


        client.getUserInfo(new JsonHttpResponseHandler() {
                               @Override
                               public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                   // my current user account
                                   user = User.fromJson(response);

                                   if(user!=null) {
                                       screenName.setText("@" + user.getScreenName());
                                       username.setText(user.getName());
                                //        username.setText(i.getStringExtra("name"));
                                       Picasso.with(getApplicationContext()).load(user.getProfileImageUrl()).into(ivProfile);
                                   }

//                                       getSupportActionBar().setTitle("@" + user.getScreenName());
                               }
                           }
        );


//        Intent i = getIntent();
//
//
//        Toast.makeText( getApplicationContext(), "got the user : " + user.getName(), Toast.LENGTH_SHORT).show();





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_tweet) {

                String body = etTweet.getText().toString();


            Intent i = new Intent();
            i.putExtra("body" , etTweet.getText().toString());
            setResult(RESULT_OK , i);
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
