//AlarmReceiver.java
package com.sb.rad.finaltest.alarmF;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by RAD on 09-Apr-17.
 */

public class AlarmReceiver extends WakefulBroadcastReceiver {

    AlarmManager alarmManager;
    PendingIntent pendingAlarmIntent;

    long INTERVAL=60*1000*3;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("AlarmReceiver: ","ok");
        Intent serviceIntent=new Intent(context,AlarmService.class);

        startWakefulService(context,serviceIntent);
    }
    public void setAlarm(Context context){
        alarmManager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(context,AlarmReceiver.class);//simple alarm receiver
        pendingAlarmIntent =PendingIntent.getBroadcast(context,0,intent,0);

        // TODO: 13-Apr-17 calender object/// set specific time, start times

        Calendar calendar=Calendar.getInstance();

        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int min=calendar.get(Calendar.MINUTE);

        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,(min+(60-min)));


        Log.e("Start Alarm: ","ok");


        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_HOUR, pendingAlarmIntent);


        ComponentName receiver=new ComponentName(context,BootReceiver.class);
        PackageManager pm=context.getPackageManager();
        pm.setComponentEnabledSetting(receiver,PackageManager.COMPONENT_ENABLED_STATE_ENABLED,PackageManager.DONT_KILL_APP);
    }
    public void cancelAlarm(Context context){

        Log.e("Cancel Alarm: ","ok");
        if(alarmManager!=null)
            alarmManager.cancel(pendingAlarmIntent);

        ComponentName receiver=new ComponentName(context,BootReceiver.class);
        PackageManager pm=context.getPackageManager();
        pm.setComponentEnabledSetting(receiver,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,PackageManager.DONT_KILL_APP);
    }

}
