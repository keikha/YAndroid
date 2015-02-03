package com.myinc.keikha.gridimagesearch.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.myinc.keikha.gridimagesearch.R;
import com.myinc.keikha.gridimagesearch.adapters.EndlessScrollListener;
import com.myinc.keikha.gridimagesearch.adapters.ImageResultsAdapter;
import com.myinc.keikha.gridimagesearch.models.ImageResult;
import com.myinc.keikha.gridimagesearch.models.SettingsOptions;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchActivity extends ActionBarActivity {


    private EditText etQuery;
    private GridView gvResults;
    private ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter aImageResults;
    private static final int REQUEST_RESULT = 50;
    private SettingsOptions settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();

        imageResults = new ArrayList<ImageResult>();
        gvResults = (GridView) findViewById(R.id.gvResults);
        aImageResults = new ImageResultsAdapter(this , imageResults);
        settings = new SettingsOptions();



        // linke the adapter to the grid view
        gvResults.setAdapter(aImageResults);


        gvResults.setOnScrollListener( new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                customLoadMoreDataFromApi(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
            }
        });
    }


    // Append more data into the adapter
    public void customLoadMoreDataFromApi(int offset) {
        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter

        String query = etQuery.getText().toString();
        String searchURL = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="+query+"&rsz=8";
        RequestParams params = new RequestParams();
        params.add("q", query);
        params.add("rsz","8");
        params.add("start", String.valueOf(offset));

        if(settings.site!="")
            params.add("as_sitesearch", settings.site);


        if(settings.type!="")
            params.add("imgtype", settings.type);

        if(settings.size !="")
            params.add("imgsz", settings.size);

        if(settings.color !="")
            params.add("imgcolor", settings.color);


        AsyncHttpClient client = new AsyncHttpClient();

        client.get(searchURL , params , new JsonHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        JSONArray imageResultsJson;
                        try {
                            imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
//                            imageResults.clear(); // if it is a new query, otherwise don't clear

                            // changes in the adapter, modifies the underlying data
                            aImageResults.addAll( ImageResult.fromJsonArray(imageResultsJson));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.i("INFO", imageResults.toString());
                    }
                }
        );


    }


    private void setupViews()
    {

        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResults);

        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // launch the image display activity
                // create the intent
                Intent i = new Intent( SearchActivity.this, ImageDisplayActivity.class);
                // get the image result to display
                 ImageResult result = imageResults.get(position);

                // pass the data
                 i.putExtra("result", result);
                // pass image result into intent
                startActivity(i);

                // launch the new activity
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.miSetting) {
            Intent i = new Intent( this, SettingActivity.class );
            i.putExtra("settings", this.settings);
            startActivityForResult(i, REQUEST_RESULT);
//            startActivity(i);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // hand the form data
        String message = "";

        if (requestCode == REQUEST_RESULT) {
            if (resultCode == RESULT_OK) {
                //get the age out of the form data
//                age = data.getIntExtra("age", -1);
                settings = (SettingsOptions) data.getSerializableExtra("settings");
                Log.i("INFO", settings.toString());
                this.runTheSearch();
            }
        }
//        onImageSearch(gvResults);
    }



    public void onImageSearch(View view) {

        this.runTheSearch();
    }

    public void runTheSearch()
    {
        // URL : https://ajax.googleapis.com/ajax/services/search/images
        // rsz = number of results, max 8
        // start = index of the first results
        // v=1.0
        // query = text

        // as_sitesearch=photobucket.com
        // imgcolor=black
        //imgsz=icon
        //imgtype=face


        // json path:
        // responseData ==> results ==> [x] ==> tbURL

        String query = etQuery.getText().toString();
        String searchURL = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="+query+"&rsz=8";
        RequestParams params = new RequestParams();
        params.add("q", query);
        params.add("rsz","8");

        if(settings.site!="")
            params.add("as_sitesearch", settings.site);


        if(settings.type!="")
            params.add("imgtype", settings.type);

        if(settings.size !="")
            params.add("imgsz", settings.size);

        if(settings.color !="")
            params.add("imgcolor", settings.color);


        AsyncHttpClient client = new AsyncHttpClient();

        client.get(searchURL , params , new JsonHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        JSONArray imageResultsJson;
                        try {
                            imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
                            imageResults.clear(); // if it is a new query, otherwise don't clear

                            // changes in the adapter, modifies the underlying data
                            aImageResults.addAll( ImageResult.fromJsonArray(imageResultsJson));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.i("INFO", imageResults.toString());
                    }
                }
        );
    }
}

