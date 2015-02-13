package com.codepath.apps.mySimpleTweets.models;

import android.text.format.DateUtils;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by keikha on 2/7/15.
 */


/**
 *
 * {
 "coordinates": null,
 "truncated": false,
 "created_at": "Tue Aug 28 21:16:23 +0000 2012",
 "favorited": false,
 "id_str": "240558470661799936",
 "in_reply_to_user_id_str": null,
 "entities": {
 "urls": [

 ],
 "hashtags": [

 ],
 "user_mentions": [

 ]
 },
 "text": "just another test",
 "contributors": null,
 "id": 240558470661799936,
 "retweet_count": 0,
 "in_reply_to_status_id_str": null,
 "geo": null,
 "retweeted": false,
 "in_reply_to_user_id": null,
 "place": null,
 "source": "<a href="//realitytechnicians.com\"" rel="\"nofollow\"">OAuth Dancer Reborn</a>",
 "user": {
 "name": "OAuth Dancer",
 "profile_sidebar_fill_color": "DDEEF6",
 "profile_background_tile": true,
 "profile_sidebar_border_color": "C0DEED",
 "profile_image_url": "http://a0.twimg.com/profile_images/730275945/oauth-dancer_normal.jpg",
 "created_at": "Wed Mar 03 19:37:35 +0000 2010",
 "location": "San Francisco, CA",
 "follow_request_sent": false,
 "id_str": "119476949",
 "is_translator": false,
 "profile_link_color": "0084B4",
 "entities": {.....

 *
 */

@Table(name = "Tweets")
public class Tweet extends Model{


    @Column(name = "body")
    private String body;

    @Column(name = "uid", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private long uid; //unique id

    @Column(name = "createdAt")
    private String createdAt;

    @Column(name = "user", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    private User user;

    // deserialize the json and build Tweet object
    //
    public static Tweet fromJSON( JSONObject jsonObject)
    {

        Tweet tweet = new Tweet();
        // extract values from json and store\

        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = getRelativeTimeAgo(jsonObject.getString("created_at"));
            tweet.user = User.fromJson(jsonObject.getJSONObject("user"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tweet;
    }


    public static ArrayList<Tweet> fromJSONArray(JSONArray items)
    {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();
        for(int i =0 ; i<items.length();i++)
        {
            try {
                JSONObject tweetJSON = items.getJSONObject(i);
                Tweet t = Tweet.fromJSON(tweetJSON);
                if( t != null) {
                    tweets.add(t);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }

        return tweets;
    }
    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

}
