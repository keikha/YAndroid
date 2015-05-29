package com.myinc.keikha.servicesdemo;

import android.app.Activity;
import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by keikha on 3/24/15.
 */
public class MySimpleToastService extends IntentService {

    public  static String ACTION = "com.myinc.keikha.servicesdemo.MySimpleToastService";

    public MySimpleToastService() {
        super("my-simple-toastÂ");
    }


    // equivalent of doInBackground
    // Some processing tasks
    //
    @Override
    protected void onHandleIntent(Intent intent) {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Shout/Send the broadcast

        // Construct an Intent tying it to the ACTION (arbitrary event namespace)
        Intent in = new Intent(ACTION);
        // Put extras into the intent as usual
        in.putExtra("foo" , "baz");
        // Fire the broadcast with intent packaged
        LocalBroadcastManager.getInstance(this).sendBroadcast(in);
        // or sendBroadcast(in) for a normal broadcast;
        // GLOBAL broadcast
//        sendBroadcast(in);


        // create a dashboard notification
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("My notification" + System.currentTimeMillis())
                .setContentText("Hello World!");


        // Grab the notification service
        NotificationManager mNotificationManager =  (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(56 , mBuilder.build());
    }
}
