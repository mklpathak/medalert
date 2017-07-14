package com.mukul.panorbit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.mukul.panorbit.services.MyAlarmService;

/**
 * Created by Mukul on 17-06-2017.
 */

public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            Bundle bundle1 = intent.getExtras();
            Log.e("hey"," we are Fourth"+bundle1.getString("med_name") +"");
            Intent service1 = new Intent(context, MyAlarmService.class);
            service1.putExtra("med_name",bundle1.getString("med_name"));
            service1.putExtra("med_dos",bundle1.getString("med_dos"));
            service1.putExtra("med_time",bundle1.getString("med_time"));
            context.startService(service1);

        }
    }

