package com.example.notificationapp;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MyNewIntentService  extends IntentService
{
    public static  int notificationId;
    NotificationManager manager;

    public MyNewIntentService() {
        super("MyNewIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        System.out.println("Background Service Called");
        manager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder nd = new NotificationCompat.Builder(getApplicationContext());
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            String channelId = "TESTID";
            String channelName = "TESTCHANNEL";
            NotificationChannel channel = new NotificationChannel(channelId,channelName,
                    NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
            nd.setChannelId(channelId);

        }
        nd.setSmallIcon(R.mipmap.ic_launcher);

        nd.setContentTitle("Test");
        nd.setContentText("Test Service Notification");
        Intent in = new Intent(Intent.ACTION_DIAL);
        PendingIntent pin = PendingIntent.getActivity(getApplicationContext(),222,in,PendingIntent.FLAG_UPDATE_CURRENT);
        nd.addAction(R.mipmap.ic_launcher,"CALL",pin);
        manager.notify(++notificationId,nd.build());




        // Alaram Service
        Intent notifyIntent = new Intent(this,MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (getApplicationContext(), 222, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis(),
//                1*60*1000, pendingIntent);
////        alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMs, pendingIntent);

        GregorianCalendar gc = new GregorianCalendar((Calendar.getInstance().get(Calendar.YEAR)),(Calendar.getInstance().get(Calendar.MONTH)), (Calendar.getInstance().get(Calendar.DATE)+1),4,8);
        System.out.println("Notification from service ");
        long time = gc.getTimeInMillis();
        alarmManager.set(AlarmManager.RTC_WAKEUP,time,pendingIntent);















//        Toast.makeText(this, ">>>>>>>>>>", Toast.LENGTH_LONG).show();
    }
}
