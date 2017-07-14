package com.mukul.panorbit.services;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.mukul.panorbit.MyReceiver;
import com.mukul.panorbit.R;
import com.mukul.panorbit.data.Addreminder;
import com.mukul.panorbit.data.DatabasePanorbit;

import java.util.ArrayList;

/**
 * Created by Mukul on 17-06-2017.
 */

public class MyAlarmService extends Service {

    private NotificationManager mManager;
    SimpleDateFormat datewithtime = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    String notifyName;
    int notifyDos;
    Long notifyTime;
    ArrayList<Addreminder> rem2s ;
    private PendingIntent pendingIntent;
    AlarmManager alarmManager;
    DatabasePanorbit dbpan;
    Intent myIntent;

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
    }

    @SuppressWarnings("static-access")
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        datewithtime.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

        Bundle bundle1 = intent.getExtras();
        bundle1.getString("med_time");
        Log.e("hey"," we are Fifth : " +bundle1.getString("med_name") +"");
        //String remind_time= datewithtime.format(Long.parseLong(bundle1.getString("med_time")));

        Notification.Builder builder = new Notification.Builder(MyAlarmService.this);
        Intent notificationIntent = new Intent(this, MyAlarmService.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSmallIcon(R.drawable.next)
                .setContentTitle("Take Medicine "+bundle1.getString("med_name")+" now")
                .setContentText("Allowed dosage : "+bundle1.getString("med_dos") + " mg" )
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setSound(uri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = builder.getNotification();
        notificationManager.notify(R.drawable.next, notification);
        myIntent = new Intent(this, MyReceiver.class);
        dbpan = new DatabasePanorbit(this);

        rem2s = dbpan.getlatestReminder();
        for (Addreminder cn : rem2s) {
            notifyDos= cn.getMed_dos();
            notifyName=  cn.getMed_name();
            notifyTime=  cn.getrem_time();
        }

        myIntent.putExtra("med_name",notifyName);
        myIntent.putExtra("med_dos",String.valueOf(notifyDos));
        pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 0, myIntent, 0);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC,notifyTime, pendingIntent);
    }


    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
}
