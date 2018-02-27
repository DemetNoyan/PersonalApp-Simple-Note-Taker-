package com.example.demet.demetandroid;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by demet on 28.01.2018.
 */

public class AlarmAlarm extends BroadcastReceiver {
    @SuppressLint("NewApi")
    @Override
    public void onReceive(Context context, Intent intent) {
        int notificationId=intent.getIntExtra("notification",0);
        String message=intent.getStringExtra("todo");
        Intent mainIntent=new Intent(context,Calendar1.class);
        PendingIntent contentIntent=PendingIntent.getActivity(context,0,mainIntent,0);
        NotificationManager myNotificationManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder=new Notification.Builder(context);
        builder.setSmallIcon(R.drawable.notification)
                .setContentTitle("It's time!!")
                .setContentText(message)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_ALL);

        myNotificationManager.notify(notificationId,builder.build());

    }
}
