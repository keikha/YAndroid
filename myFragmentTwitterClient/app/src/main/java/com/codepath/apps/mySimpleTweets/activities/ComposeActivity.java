package com.codepath.apps.mySimpleTweets.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mySimpleTweets.R;
import com.squareup.picasso.Picasso;

public class ComposeActivity extends ActionBarActivity {


    private EditText etTweet;
    private ImageView ivProfile;
    private TextView username;
    private TextView screenName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();

        setContentView(R.layout.activity_compose);
        etTweet = (EditText) findViewById(R.id.etBody);
        username = (TextView) findViewById(R.id.tvUserName);
        screenName = (TextView) findViewById(R.id.tvUserScreenName);
        ivProfile = (ImageView) findViewById(R.id.ivProfileImage);

        screenName.setText("@"+i.getStringExtra("screenname"));
        username.setText("Mostaf Keikha");
//        username.setText(i.getStringExtra("name"));
//        Picasso.with(this).load(i.getStringExtra("profileImage")).into(ivProfile);

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
            Intent i = new Intent();
            i.putExtra("body" , etTweet.getText().toString());
//
            setResult(RESULT_OK , i);
            // step 2 : dismiss this screen and go back

            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
