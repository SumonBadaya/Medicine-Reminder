//AlarmService.java
package com.sb.rad.finaltest.alarmF;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.sb.rad.finaltest.MyAlarm;
import com.sb.rad.finaltest.obj.Alarm;
import com.sb.rad.finaltest.obj.DataForFirebase;
import com.sb.rad.finaltest.obj.UserData;
import com.sb.rad.finaltest.sqlite.SQLiteDataBase;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by RAD on 09-Apr-17.
 */

public class AlarmService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param //name Used to name the worker thread, important only for debugging.
     */
    public AlarmService() {
        super("AlarmService");
    }

    static  final String TAG="Scheduling Demo";
    static Context context;
    static DataForFirebase dataForFirebase;
    public static ArrayList<UserData> userDatas;

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onHandleIntent(Intent intent) {
        this.context=getApplicationContext();
        initialize();

        sp=getSharedPreferences("userNameInfo",Context.MODE_PRIVATE);
        editor=sp.edit();

//        int hour= Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
//        Log.e("hour of service: ",""+hour);

        Calendar calendar=Calendar.getInstance();
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        Log.e("Hour: ",""+hour);


        switch (hour){
            case 1:
                startAlarmIntent(dataForFirebase.alarm1,1);
                break;
            case 2:
                startAlarmIntent(dataForFirebase.alarm2,2);
                break;
            case 3:
                startAlarmIntent(dataForFirebase.alarm3,3);
                break;
            case 4:
                startAlarmIntent(dataForFirebase.alarm4,4);
                break;
            case 5:
                startAlarmIntent(dataForFirebase.alarm5,5);
                break;
            case 6:
                startAlarmIntent(dataForFirebase.alarm6,6);
                break;
            case 7:
                startAlarmIntent(dataForFirebase.alarm7,7);
                break;
            case 8:
                startAlarmIntent(dataForFirebase.alarm8,8);
                break;
            case 9:
                startAlarmIntent(dataForFirebase.alarm9,9);
                break;
            case 10:
                startAlarmIntent(dataForFirebase.alarm10,10);
                break;
            case 11:
                startAlarmIntent(dataForFirebase.alarm11,11);
                break;
            case 12:
                startAlarmIntent(dataForFirebase.alarm12,12);
                break;
            case 13:
                startAlarmIntent(dataForFirebase.alarm13,13);
                break;
            case 14:
                startAlarmIntent(dataForFirebase.alarm14,14);
                break;
            case 15:
                startAlarmIntent(dataForFirebase.alarm15,15);
                break;
            case 16:
                startAlarmIntent(dataForFirebase.alarm16,16);
                break;
            case 17:
                startAlarmIntent(dataForFirebase.alarm17,17);
                break;
            case 18:
                startAlarmIntent(dataForFirebase.alarm18,18);
                break;
            case 19:
                startAlarmIntent(dataForFirebase.alarm19,19);
                break;
            case 20:
                startAlarmIntent(dataForFirebase.alarm20,20);
                break;
            case 21:
                startAlarmIntent(dataForFirebase.alarm21,21);
                break;
            case 22:
                startAlarmIntent(dataForFirebase.alarm22,22);
                break;
            case 23:
                startAlarmIntent(dataForFirebase.alarm23,23);
                break;
            case 0:
                startAlarmIntent(dataForFirebase.alarm0,0);
                break;

        }

        AlarmReceiver.completeWakefulIntent(intent);
    }
    public static DataForFirebase initialize(){
        userDatas=new SQLiteDataBase(context).getDataToDisplay();
        return dataForFirebase=new DataForFirebase(userDatas);
    }

    void startNewIntent(int id){
        Intent intent = new Intent(AlarmService.this, MyAlarm.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        editor.putInt("ALARM_ID",id);
        editor.apply();
        editor.putBoolean("ONE_TIME",true);
        editor.apply();
        startActivity(intent);
    }

    void startAlarmIntent(Alarm alarm,int id){
        if(alarm.medicineNames.isEmpty()==false) {
            startNewIntent(id);
        }
    }




}
