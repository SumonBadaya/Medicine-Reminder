//SecondAlarmReceiver.java
package com.sb.rad.finaltest.alarmF;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by RAD on 18-Apr-17.
 */

public class SecondAlarmReceiver extends WakefulBroadcastReceiver {

    AlarmManager alarmManager;
    PendingIntent pendingAlarmIntent;

    long INTERVAL;//=60*1000*1;
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent=new Intent(context,SecondAlarmService.class);

        startWakefulService(context,serviceIntent);
    }
    public void setAlarm(Context context,long interval){
        alarmManager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(context,SecondAlarmReceiver.class);//simple alarm receiver
        pendingAlarmIntent =PendingIntent.getBroadcast(context,0,intent,0);

        Log.e("SecondAlarmRE: ","ok");

        INTERVAL=interval*60*1000;

        alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+INTERVAL, pendingAlarmIntent);


        ComponentName receiver=new ComponentName(context,SecondAlarmBootReceiver.class);
        PackageManager pm=context.getPackageManager();
        pm.setComponentEnabledSetting(receiver,PackageManager.COMPONENT_ENABLED_STATE_ENABLED,PackageManager.DONT_KILL_APP);
    }
    public void cancelAlarm(Context context){

        Log.e("Cancel-S-Alarm: ","ok");
        if(alarmManager!=null)
            alarmManager.cancel(pendingAlarmIntent);

        ComponentName receiver=new ComponentName(context,SecondAlarmBootReceiver.class);
        PackageManager pm=context.getPackageManager();
        pm.setComponentEnabledSetting(receiver,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,PackageManager.DONT_KILL_APP);
    }
}
