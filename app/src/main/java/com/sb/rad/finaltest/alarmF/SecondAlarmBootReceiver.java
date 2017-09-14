//SecondAlarmBootReceiver.java
package com.sb.rad.finaltest.alarmF;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by RAD on 18-Apr-17.
 */

public class SecondAlarmBootReceiver extends BroadcastReceiver {
    SecondAlarmReceiver secondAlarm=new SecondAlarmReceiver();
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("S-B-Receiver: ","ok");
        if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            secondAlarm.setAlarm(context,0);
        }
    }
}
