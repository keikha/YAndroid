package com.myinc.keikha.simpleinstgclient;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {


    public static final String clientID = "b7128ec8d0f84e73b8e11f1e712433a2";
    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotoAdapter aPhotos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fetchPopularPhotos();
    }

    private void fetchPopularPhotos() {

        photos = new ArrayList<InstagramPhoto>();

        //bind data
        aPhotos = new InstagramPhotoAdapter( this , photos );

        // populate data into listView
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);

        //attach adapter
        lvPhotos.setAdapter(aPhotos);

        // token : b7128ec8d0f84e73b8e11f1e712433a2
        // url : https://api.instagram.com/v1/media/popular?client_id =

        //set up popular endpoint
        String popularURL = "https://api.instagram.com/v1/media/popular?client_id=" +clientID;

        // create the network client
        AsyncHttpClient client = new AsyncHttpClient();

        // trigger the network request
        client.get(popularURL , new JsonHttpResponseHandler()
        {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        // fired once the successfull response is back
                        // response is popular photos json

                        // url, hight, username, caption
                        JSONArray photosJson = null;
                        try {

                            photos.clear();
                            photosJson = response.getJSONArray("data");
                            for (int i =0; i<photosJson.length() ; i++)
                            {

                                JSONObject photoJSON = photosJson.getJSONObject(i);
                                InstagramPhoto photo = new InstagramPhoto();
                                photo.username = photoJSON.getJSONObject("user").getString("username");

                                try {
                                    photo.profilePictureURL = photoJSON.getJSONObject("user").getString("profile_picture");
                                }
                                catch(JSONException e)
                                {
                                    photo.profilePictureURL="";
                                }

                                try{
                                    photo.caption = photoJSON.getJSONObject("caption").getString("text");
                                }
                                catch(JSONException e)
                                {
                                    photo.caption="";
                                }


                                photo.imageURL = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                                photo.hight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");

                                try{
                                    photo.likes_count = photoJSON.getJSONObject("likes").getInt("count");
                                }
                                catch(JSONException e)
                                {
                                    photo.likes_count = 0;
                                }

                                photos.add(photo);
                            }

                            // notify adapter to refresh the data
                            aPhotos.notifyDataSetChanged();
                        }
                        catch(JSONException e)
                        {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                    }
                }
        );


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
