//UserName.java
package com.sb.rad.finaltest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sb.rad.finaltest.alarmF.AlarmReceiver;

public class UserName extends AppCompatActivity {

    EditText userName;
    Button okbtn;

    static SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_name);

        userName=(EditText)findViewById(R.id.userName);
        okbtn=(Button)findViewById(R.id.okBtn);

        sp=getSharedPreferences("userNameInfo", Context.MODE_PRIVATE);



        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkNameField()) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("USER_NAME", getUserName());
                    editor.putBoolean("SET_ALARM", true);
                    editor.apply();

                    /// set alarm services
                    MainActivity.alarmReceiver.setAlarm(getApplicationContext());
                    Toast.makeText(getApplicationContext(), "Alarm Start", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(UserName.this, MainActivity.class));
                    finish();
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public boolean checkNameField(){
        if(userName.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Patient Name is Empty",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    String getUserName(){
        return userName.getText().toString().toUpperCase().trim();
    }


}
