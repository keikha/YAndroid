package com.myinc.keikha.servicesdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by keikha on 3/24/15.
 */
public class MyAlarmReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;
    public static final String ACTION = "com.myinc.keikha.servicesdemo.alarm";

    // Triggered by the Alarm periodically (starts the service to run task)
    // whenever alaram fires
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, MySimpleToastService.class);
        i.putExtra("foo", "bar");
        context.startService(i);
    }
}
