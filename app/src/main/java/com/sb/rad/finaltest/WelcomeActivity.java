//WelcomeActivity.java
package com.sb.rad.finaltest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.util.Map;

public class WelcomeActivity extends AppCompatActivity {
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // full screen====welcome screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_welcome);


        sp=getSharedPreferences("userNameInfo", Context.MODE_PRIVATE);

        Log.e("Welcome Activity: ","ok");


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String userName=sp.getString("USER_NAME",null);
                if(userName!=null) {
                    Intent intent=new Intent(WelcomeActivity.this, MainActivity.class);
                    intent.putExtra("USER_NAME",userName);
                    startActivity(intent);
                    finish();
                }else {
                    startActivity(new Intent(WelcomeActivity.this, UserName.class));
                    finish();
                }
            }
        },3000);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
