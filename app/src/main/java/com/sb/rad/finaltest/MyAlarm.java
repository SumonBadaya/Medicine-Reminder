//MyAlarm.java
package com.sb.rad.finaltest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sb.rad.finaltest.alarmF.AlarmService;
import com.sb.rad.finaltest.alarmF.SecondAlarmReceiver;
import com.sb.rad.finaltest.obj.Alarm;
import com.sb.rad.finaltest.obj.DataForFirebase;
import com.sb.rad.finaltest.obj.UserData;
import com.sb.rad.finaltest.obj.WarningMessage;
import com.sb.rad.finaltest.sqlite.SQLiteDataBase;

import java.util.ArrayList;

public class MyAlarm extends AppCompatActivity {

    Button cancelButton;
    TextView textView;


    static  final int NOTIFICATION_ID=1;

    public static MediaPlayer mediaPlayer;
    NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    SQLiteDataBase db;
    ArrayList<UserData> userData;
    DataForFirebase dataForFirebase;

    String  suggestMedicine="";
    String  warningMedicineList="";

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    WarningMessage warningMessage;
    int ALARM_ID;


    boolean secondAlarmIsTrue=false;
    SecondAlarmReceiver secondAlarmReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // secreen wake up and light on or screen on
        PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");
        wakeLock.acquire();

        // full screen====Alarm screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_my_alarm);

        textView=(TextView)findViewById(R.id.textView);
        cancelButton=(Button)findViewById(R.id.cancelButton);

        db= new SQLiteDataBase(this);
        dataForFirebase= AlarmService.initialize();
        userData=AlarmService.userDatas;
        secondAlarmReceiver=new SecondAlarmReceiver();

        sp=getSharedPreferences("userNameInfo", Context.MODE_PRIVATE);
        editor=sp.edit();


        warningMessage=new WarningMessage(this,"Query","Have you taken your Medicine?");

        secondAlarmIsTrue=sp.getBoolean("SET_SECOND_ALARM",false);

        //////////////////////////////////////////////////////////////////////


        //////////////////////////////////////////////////////////////////////////////////

        Log.e("MyAlarm Activity","ok");

        ALARM_ID=sp.getInt("ALARM_ID",0);


        textView.setText(collectMedicineName(ALARM_ID));
        collectWarningMedicine(dataForFirebase.warning);



        ///////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////

        sendNotification("Its Medicine Time, Take medicine Fast.");


        mediaPlayer=MediaPlayer.create(getApplicationContext(), R.raw.first_reminder);

        mediaPlayer.setLooping(true);
        if(sp.getBoolean("ONE_TIME",false)) {
            mediaPlayer.start();
            editor.putBoolean("ONE_TIME",false);
            editor.apply();
        }

        cancelButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:

                        v.setAlpha((float).8);
                        return true;

                    case MotionEvent.ACTION_UP:
                        v.setAlpha((float)1);
                        showDialogBox();

                        return true;
                }
                return false;
            }
        });
        registerForContextMenu(cancelButton);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                finish();
//            }
//        },mediaPlayer.getDuration());
    }

    @Override
    public void onBackPressed() {
        if(mediaPlayer.isPlaying()) {
            showDialogBox();
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add("Choose Your Time");
        menu.add(0,v.getId(),0,"5 minutes");
        menu.add(0,v.getId(),0,"10 minutes");
        menu.add(0,v.getId(),0,"15 minutes");
        menu.add(0,v.getId(),0,"20 minutes");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if(item.getTitle()=="5 minutes"){
            stopRelease();
            new SecondAlarmReceiver().setAlarm(getApplicationContext(),5);
            editor.putBoolean("SET_SECOND_ALARM",true);
            editor.apply();
            finish();
        }else if(item.getTitle()=="10 minutes"){
            stopRelease();
            new SecondAlarmReceiver().setAlarm(getApplicationContext(),10);
            editor.putBoolean("SET_SECOND_ALARM",true);
            editor.apply();
            finish();
        }else if(item.getTitle()=="15 minutes"){
            stopRelease();
            new SecondAlarmReceiver().setAlarm(getApplicationContext(),15);
            editor.putBoolean("SET_SECOND_ALARM",true);
            editor.apply();
            finish();
        }else if(item.getTitle()=="20 minutes"){
            stopRelease();
            new SecondAlarmReceiver().setAlarm(getApplicationContext(),20);
            editor.putBoolean("SET_SECOND_ALARM",true);
            editor.apply();
            finish();
        }

        return  true;
    }


    void showDialogBox(){
        warningMessage.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                selectAlarmMedicineList(ALARM_ID);
                stopRelease();///// stop medial player
                Toast.makeText(getApplicationContext(),"Stop Alarm",Toast.LENGTH_SHORT).show();
                if(secondAlarmIsTrue){
                    editor.putBoolean("SET_SECOND_ALARM",false);
                    editor.apply();
                    secondAlarmReceiver.cancelAlarm(getApplicationContext());
                }

                boolean selesctIntent=true;
                if(warningMedicineList.isEmpty()==false){
                    editor.putBoolean("ONE_TIME",true);
                    editor.apply();

                    selesctIntent=false;

                    editor.putString("NEED_TO_BUY",warningMedicineList);
                    editor.apply();

                    startActivity(new Intent(MyAlarm.this,need_to_buy_medicine.class));
                }
                if(selesctIntent)
                    startActivity(new Intent(MyAlarm.this,MainActivity.class));
                finish();

            }
        });
        warningMessage.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openContextMenu(cancelButton);
            }
        });
        warningMessage.display();

    }
    void sendNotification(String msg){
        mNotificationManager=(NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent=PendingIntent.getActivity(this,0,new Intent(this,MainActivity.class),0);

        NotificationCompat.Builder mBuilder= (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setContentTitle("Medicine Reminder")
                .setSmallIcon(R.drawable.medicine_reminder_icon)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setDefaults(Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE)
                .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID,mBuilder.build());
    }

    public  static void stopRelease(){
        if(mediaPlayer!=null) {
            if (mediaPlayer.isPlaying())
                mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
    void selectAlarmMedicineList (int id){
        switch (id){
            case 1:
                checkAlarmMedicineList(dataForFirebase.alarm1);
                break;
            case 2:
                checkAlarmMedicineList(dataForFirebase.alarm2);
                break;
            case 3:
                checkAlarmMedicineList(dataForFirebase.alarm3);
                break;
            case 4:
                checkAlarmMedicineList(dataForFirebase.alarm4);
                break;
            case 5:
                checkAlarmMedicineList(dataForFirebase.alarm5);
                break;
            case 6:
                checkAlarmMedicineList(dataForFirebase.alarm6);
                break;
            case 7:
                checkAlarmMedicineList(dataForFirebase.alarm7);
                break;
            case 8:
                checkAlarmMedicineList(dataForFirebase.alarm8);
                break;
            case 9:
                checkAlarmMedicineList(dataForFirebase.alarm9);
                break;
            case 10:
                checkAlarmMedicineList(dataForFirebase.alarm10);
                break;
            case 11:
                checkAlarmMedicineList(dataForFirebase.alarm11);
                break;
            case 12:
                checkAlarmMedicineList(dataForFirebase.alarm12);
                break;
            case 13:
                checkAlarmMedicineList(dataForFirebase.alarm13);
                break;
            case 14:
                checkAlarmMedicineList(dataForFirebase.alarm14);
                break;
            case 15:
                checkAlarmMedicineList(dataForFirebase.alarm15);
                break;
            case 16:
                checkAlarmMedicineList(dataForFirebase.alarm16);
                break;
            case 17:
                checkAlarmMedicineList(dataForFirebase.alarm17);
                break;
            case 18:
                checkAlarmMedicineList(dataForFirebase.alarm18);
                break;
            case 19:
                checkAlarmMedicineList(dataForFirebase.alarm19);
                break;
            case 20:
                checkAlarmMedicineList(dataForFirebase.alarm20);
                break;
            case 21:
                checkAlarmMedicineList(dataForFirebase.alarm21);
                break;
            case 22:
                checkAlarmMedicineList(dataForFirebase.alarm22);
                break;
            case 23:
                checkAlarmMedicineList(dataForFirebase.alarm23);
                break;
            case 0:
                checkAlarmMedicineList(dataForFirebase.alarm0);
                break;
        }
    }


    void checkAlarmMedicineList(Alarm alarm){
        for (String s : alarm.medicineNames) {
            for (UserData u: userData) {
                if(s.compareTo(u.getMedicineName())==0){
                    int currentStrock=Integer.valueOf(u.getStock());
                    int pillPerDose=Integer.valueOf(u.getPillsPerDose());
                    if(0 < (currentStrock-pillPerDose)) {
                        u.setStock("" + (currentStrock - pillPerDose));
                        db.updateData(u);
                    }else {
                        u.setStock("0");
                        db.updateData(u);
                    }
                }

            }
        }
    }

    public String  collectMedicineName(int id){
        switch (id){
            case 1:
                collect(dataForFirebase.alarm1);
                break;
            case 2:
                collect(dataForFirebase.alarm2);
                break;
            case 3:
                collect(dataForFirebase.alarm3);
                break;
            case 4:
                collect(dataForFirebase.alarm4);
                break;
            case 5:
                collect(dataForFirebase.alarm5);
                break;
            case 6:
                collect(dataForFirebase.alarm6);
                break;
            case 7:
                collect(dataForFirebase.alarm7);
                break;
            case 8:
                collect(dataForFirebase.alarm8);
                break;
            case 9:
                collect(dataForFirebase.alarm9);
                break;
            case 10:
                collect(dataForFirebase.alarm10);
                break;
            case 11:
                collect(dataForFirebase.alarm11);
                break;
            case 12:
                collect(dataForFirebase.alarm12);
                break;
            case 13:
                collect(dataForFirebase.alarm13);
                break;
            case 14:
                collect(dataForFirebase.alarm14);
                break;
            case 15:
                collect(dataForFirebase.alarm15);
                break;
            case 16:
                collect(dataForFirebase.alarm16);
                break;
            case 17:
                collect(dataForFirebase.alarm17);
                break;
            case 18:
                collect(dataForFirebase.alarm18);
                break;
            case 19:
                collect(dataForFirebase.alarm19);
                break;
            case 20:
                collect(dataForFirebase.alarm20);
                break;
            case 21:
                collect(dataForFirebase.alarm21);
                break;
            case 22:
                collect(dataForFirebase.alarm22);
                break;
            case 23:
                collect(dataForFirebase.alarm23);
                break;
            case 0:
                collect(dataForFirebase.alarm0);
                break;
        }
        return suggestMedicine;
    }
    public void collect(Alarm alarm){
        for (String s : alarm.medicineNames) {
            for (UserData u: userData) {
                if(s.compareTo(u.getMedicineName())==0){
                    suggestMedicine += s+" - "+u.getPillsPerDose()+" pc"+'\n';
                }

            }
        }
    }
    public String collectWarningMedicine(ArrayList<String> warningList){
        for (String s : warningList) {
            warningMedicineList += s+"@";
        }
        return warningMedicineList;
    }

}

