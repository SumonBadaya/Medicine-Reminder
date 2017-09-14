//SecondAlarmService.java
package com.sb.rad.finaltest.alarmF;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.sb.rad.finaltest.MyAlarm;

/**
 * Created by RAD on 18-Apr-17.
 */

public class SecondAlarmService extends IntentService {


    public SecondAlarmService() {
        super("SecondAlarmService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.e("S-A-Service: ","ok");
        SharedPreferences sp=getSharedPreferences("userNameInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();

        if(sp.getBoolean("SET_SECOND_ALARM",false)) {
            Intent intent2 = new Intent(SecondAlarmService.this, MyAlarm.class);
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            editor.putBoolean("ONE_TIME",true);
            editor.apply();
            startActivity(intent2);
        }

        SecondAlarmReceiver.completeWakefulIntent(intent);
    }

}
