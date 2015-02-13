package com.myinc.keikha.simplechat;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by keikha on 2/5/15.
 */
public class ChatApplication extends Application {
    public static final String YOUR_APPLICATION_ID = "69WkXcHMph0iP1YFflqvaesvJRh6D9SiyOe2dHgw";
    public static final String YOUR_CLIENT_KEY = "JJXr5qIsTot9cH79BctzXEZaQTDwrw4IBoHfHJod";
    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Message.class);
        Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
    }
}