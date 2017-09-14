//BootReceiver.java
package com.sb.rad.finaltest.alarmF;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by RAD on 09-Apr-17.
 */

public class BootReceiver extends BroadcastReceiver {

    AlarmReceiver alarm=new AlarmReceiver();


    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("BootReceiver Activ: ","ok");
        if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            alarm.setAlarm(context);
        }
    }
}
