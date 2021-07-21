package com.example.notificationapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.notificationapp.databinding.ActivityMainBinding;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {



    ActivityMainBinding binding;
    NotificationManager manager;
    public static  int notificationId;
    long timeInMs;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        manager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        Toast.makeText(this, ""+(Calendar.getInstance().get(Calendar.YEAR)), Toast.LENGTH_SHORT).show();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.MINUTE, 00);







//1*60*1000

        Intent notifyIntent = new Intent(this,MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (MainActivity.this, 222, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis(),
//                1*60*1000, pendingIntent);
////        alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMs, pendingIntent);

        GregorianCalendar gc = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DATE),4,2);

        long time = gc.getTimeInMillis();
        alarmManager.set(AlarmManager.RTC_WAKEUP,time,pendingIntent);









        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationCompat.Builder nd = new NotificationCompat.Builder(MainActivity.this);
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
                nd.setContentText("Test Notification");
                Intent in = new Intent(Intent.ACTION_DIAL);
                PendingIntent pin = PendingIntent.getActivity(MainActivity.this,222,in,PendingIntent.FLAG_UPDATE_CURRENT);
                nd.addAction(R.mipmap.ic_launcher,"CALL",pin);
                manager.notify(++notificationId,nd.build());

            }
        });





    }
}