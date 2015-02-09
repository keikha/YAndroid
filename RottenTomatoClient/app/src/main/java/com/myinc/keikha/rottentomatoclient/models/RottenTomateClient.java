package com.myinc.keikha.rottentomatoclient.models;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.impl.io.HttpRequestParser;
import org.json.JSONObject;

/**
 * Created by keikha on 1/29/15.
 */
public class RottenTomateClient {

    // url : http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json?apikey=9htuhtcb4ymusd73d4z6jxcj

    private final String key = "9htuhtcb4ymusd73d4z6jxcj";
    private final String baseURL = "http://api.rottentomatoes.com/api/public/v1.0/";

    private AsyncHttpClient client;

    public RottenTomateClient()
    {
        this.client = new AsyncHttpClient();
    }


    private String getAPIURL(String relativeURL)
    {
        return this.baseURL+relativeURL;
    }
    public void getBoxOfficeMovies(JsonHttpResponseHandler handler)
    {
        String requestURL = this.getAPIURL("lists/movies/box_office.json");
        RequestParams param = new RequestParams();
        param.add("apikey", this.key);

        this.client.get(requestURL , param, handler);
    }

}
