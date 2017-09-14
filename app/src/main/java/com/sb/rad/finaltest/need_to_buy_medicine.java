//need_to_buy_medicine.java
package com.sb.rad.finaltest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.sb.rad.finaltest.MainActivity;
import com.sb.rad.finaltest.R;

public class need_to_buy_medicine extends AppCompatActivity {

    ListView listView;

    MediaPlayer mediaPlayer;
    NotificationManager mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_to_buy_medicine);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SharedPreferences sp=getSharedPreferences("userNameInfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();

        listView=(ListView)findViewById(R.id.listView);

        mNotificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        sendNotification("Some Medicines are need to buy.");

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,sp.getString("NEED_TO_BUY",null).split("@"));

        listView.setAdapter(arrayAdapter);

        mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.butmedi);

        if(sp.getBoolean("ONE_TIME",false)) {
            mediaPlayer.start();
            editor.putBoolean("ONE_TIME",false);
            editor.apply();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(mediaPlayer.isPlaying())
            mediaPlayer.stop();
        mediaPlayer.release();
        startActivity(new Intent(need_to_buy_medicine.this,MainActivity.class));
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            startActivity(new Intent(need_to_buy_medicine.this,MainActivity.class));
            finish();
        }
        return  true;
    }
    public void action(View view){
        startActivity(new Intent(need_to_buy_medicine.this,MainActivity.class));
        finish();
    }
    void sendNotification(String msg){
        mNotificationManager=(NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent=PendingIntent.getActivity(this,0,new Intent(this,need_to_buy_medicine.class),0);

        NotificationCompat.Builder mBuilder= (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setContentTitle("Medicine Reminder")
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setDefaults(Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE)
                .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(007,mBuilder.build());
    }

}
