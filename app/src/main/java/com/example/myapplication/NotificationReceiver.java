package com.example.myapplication;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Date;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("///////...........Receivinf......");

        Intent repeatingIntent = new Intent(context, MainActivity.class);
        repeatingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pdNotificationIntent = PendingIntent.getActivity(context, 2, repeatingIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        Notification notification = new NotificationCompat.Builder(context, App.dailyNotification)
                .setContentTitle("五不遇时")
                .setContentText(MainActivity.calculateMagicTime())
                .setSmallIcon(R.drawable.ic_event_note_black_24dp)
                .setContentIntent(pdNotificationIntent)
                .build();
        manager.notify(2, notification);

    }
}
