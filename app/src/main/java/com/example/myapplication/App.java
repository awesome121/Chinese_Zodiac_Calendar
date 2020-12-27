package com.example.myapplication;

import android.app.AlarmManager;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;

public class App extends Application {
    public static final String dailyNotification = "每日五不遇时提醒";

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    @Override
    public void onCreate() {
        super.onCreate();




        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel dailyChannel = new NotificationChannel(dailyNotification, "dailyChannel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(dailyChannel);
        }

        setDailyNotification();
    }

    private void setDailyNotification() {
        System.out.println("on set.....");

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);

        alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent dailyNotificationIntent = new Intent(this, NotificationReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, dailyNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);

    }

}
