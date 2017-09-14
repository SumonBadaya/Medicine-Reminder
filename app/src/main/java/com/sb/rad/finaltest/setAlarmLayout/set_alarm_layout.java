
//set_alarm_layout.java
package com.sb.rad.finaltest.setAlarmLayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.Toast;

import com.sb.rad.finaltest.MainActivity;
import com.sb.rad.finaltest.R;

public class set_alarm_layout extends AppCompatActivity {


    static CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8, checkBox9, checkBox10, checkBox11, checkBox12;
    static CheckBox checkBox1pm, checkBox2pm, checkBox3pm, checkBox4pm, checkBox5pm, checkBox6pm, checkBox7pm, checkBox8pm, checkBox9pm, checkBox10pm, checkBox11pm, checkBox12pm;

    public static boolean isFromListItem=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm_layout);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        initializeAllCheckBox();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(isFromListItem){
            getSupportActionBar().setTitle(getIntent().getStringExtra("MEDICINE_NAME").toString().toUpperCase());
            setCheckBoxes(getIntent().getStringExtra("SCHEDULE").toString());
            disableToEdit();
            isFromListItem=false;
        }else {
            getMenuInflater().inflate(R.menu.menu_for_alarm_intent, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.saveButton) {
            if(isValidCheckSchedule()){
                MainActivity.setDataFromUser();
                String schedule=getSchedule();
                MainActivity.userData.setSchedule(schedule);
                MainActivity.userData.setDosePerDay(getDosePerDay(schedule));

                if(MainActivity.myDataBase.insertData(MainActivity.userData)){
                    String s=MainActivity.userData.getMedicineName()+'\n'+"Dose Per Day : "+MainActivity.userData.getDosePerDay()+'\n'+"Pills Per Dose : "+MainActivity.userData.getPillsPerDose()+'\n';
                    Toast.makeText(getApplicationContext(),s+" Are Added",Toast.LENGTH_LONG).show();
                    MainActivity.resetInputField();
                }else {
                    Toast.makeText(getApplicationContext(),"Insertion problem.",Toast.LENGTH_SHORT).show();
                }
                finish();
            }

        } else if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //check boxes initialize here
    private void initializeAllCheckBox(){
        checkBox6=(CheckBox)findViewById(R.id.checkbox6);
        checkBox7=(CheckBox)findViewById(R.id.checkbox7);
        checkBox8=(CheckBox)findViewById(R.id.checkbox8);
        checkBox9=(CheckBox)findViewById(R.id.checkbox9);
        checkBox10=(CheckBox)findViewById(R.id.checkbox10);
        checkBox11=(CheckBox)findViewById(R.id.checkbox11);
        checkBox12pm=(CheckBox)findViewById(R.id.checkbox12pm);
        checkBox1pm=(CheckBox)findViewById(R.id.checkbox1pm);
        checkBox2pm=(CheckBox)findViewById(R.id.checkbox2pm);
        checkBox3pm=(CheckBox)findViewById(R.id.checkbox3pm);
        checkBox4pm=(CheckBox)findViewById(R.id.checkbox4pm);
        checkBox5pm=(CheckBox)findViewById(R.id.checkbox5pm);
        checkBox6pm=(CheckBox)findViewById(R.id.checkbox6pm);
        checkBox7pm=(CheckBox)findViewById(R.id.checkbox7pm);
        checkBox8pm=(CheckBox)findViewById(R.id.checkbox8pm);
        checkBox9pm=(CheckBox)findViewById(R.id.checkbox9pm);
        checkBox10pm=(CheckBox)findViewById(R.id.checkbox10pm);
        checkBox11pm=(CheckBox)findViewById(R.id.checkbox11pm);
        checkBox12=(CheckBox)findViewById(R.id.checkbox12);
        checkBox1=(CheckBox)findViewById(R.id.checkbox1);
        checkBox2=(CheckBox)findViewById(R.id.checkbox2);
        checkBox3=(CheckBox)findViewById(R.id.checkbox3);
        checkBox4=(CheckBox)findViewById(R.id.checkbox4);
        checkBox5=(CheckBox)findViewById(R.id.checkbox5);
    }

    //get schedule string
    public String  getSchedule(){
        String schedule="@";
        schedule +=getCheckStatus(checkBox1);
        schedule +=getCheckStatus(checkBox2);
        schedule +=getCheckStatus(checkBox3);
        schedule +=getCheckStatus(checkBox4);
        schedule +=getCheckStatus(checkBox5);
        schedule +=getCheckStatus(checkBox6);
        schedule +=getCheckStatus(checkBox7);
        schedule +=getCheckStatus(checkBox8);
        schedule +=getCheckStatus(checkBox9);
        schedule +=getCheckStatus(checkBox10);
        schedule +=getCheckStatus(checkBox11);
        schedule +=getCheckStatus(checkBox12pm);
        schedule +=getCheckStatus(checkBox1pm);
        schedule +=getCheckStatus(checkBox2pm);
        schedule +=getCheckStatus(checkBox3pm);
        schedule +=getCheckStatus(checkBox4pm);
        schedule +=getCheckStatus(checkBox5pm);
        schedule +=getCheckStatus(checkBox6pm);
        schedule +=getCheckStatus(checkBox7pm);
        schedule +=getCheckStatus(checkBox8pm);
        schedule +=getCheckStatus(checkBox9pm);
        schedule +=getCheckStatus(checkBox10pm);
        schedule +=getCheckStatus(checkBox11pm);
        schedule +=getCheckStatus(checkBox12);
        return schedule;
    }
    private String getCheckStatus(CheckBox checkBox){
        if(checkBox.isChecked())
            return "1";
        return "0";
    }
    private boolean isValidCheckSchedule(){
        if(checkBox1.isChecked()||checkBox2.isChecked()||checkBox3.isChecked()||checkBox4.isChecked()||checkBox5.isChecked()||checkBox6.isChecked()||checkBox7.isChecked()||checkBox8.isChecked()||checkBox9.isChecked()||checkBox10.isChecked()||checkBox11.isChecked()||checkBox12pm.isChecked()||checkBox1pm.isChecked()||checkBox2pm.isChecked()||checkBox3pm.isChecked()||checkBox4pm.isChecked()||checkBox5pm.isChecked()||checkBox6pm.isChecked()||checkBox7pm.isChecked()||checkBox8pm.isChecked()||checkBox9pm.isChecked()||checkBox10pm.isChecked()||checkBox11pm.isChecked()||checkBox12.isChecked())
            return true;
        Toast.makeText(this,"Check Alarm Time",Toast.LENGTH_SHORT).show();
        return false;
    }
    String getDosePerDay(String s){
        int count=0;
        for(int i=1; i<s.length(); i++)
        {
            char c=s.charAt(i);
            if(c=='1')
                count++;
        }
        return String.valueOf(count);
    }
    public static void setCheckBoxes(String s){

        if(s.charAt(1)=='1')
            checkBox1.setChecked(true);
        if(s.charAt(2)=='1')
            checkBox2.setChecked(true);
        if(s.charAt(3)=='1')
            checkBox3.setChecked(true);
        if(s.charAt(4)=='1')
            checkBox4.setChecked(true);
        if(s.charAt(5)=='1')
            checkBox5.setChecked(true);
        if(s.charAt(6)=='1')
            checkBox6.setChecked(true);
        if(s.charAt(7)=='1')
            checkBox7.setChecked(true);
        if(s.charAt(8)=='1')
            checkBox8.setChecked(true);
        if(s.charAt(9)=='1')
            checkBox9.setChecked(true);
        if(s.charAt(10)=='1')
            checkBox10.setChecked(true);
        if(s.charAt(11)=='1')
            checkBox11.setChecked(true);
        if(s.charAt(12)=='1')
            checkBox12pm.setChecked(true);
        if(s.charAt(13)=='1')
            checkBox1pm.setChecked(true);
        if(s.charAt(14)=='1')
            checkBox2pm.setChecked(true);
        if(s.charAt(15)=='1')
            checkBox3pm.setChecked(true);
        if(s.charAt(16)=='1')
            checkBox4pm.setChecked(true);
        if(s.charAt(17)=='1')
            checkBox5pm.setChecked(true);
        if(s.charAt(18)=='1')
            checkBox6pm.setChecked(true);
        if(s.charAt(19)=='1')
            checkBox7pm.setChecked(true);
        if(s.charAt(20)=='1')
            checkBox8pm.setChecked(true);
        if(s.charAt(21)=='1')
            checkBox9pm.setChecked(true);
        if(s.charAt(22)=='1')
            checkBox10pm.setChecked(true);
        if(s.charAt(23)=='1')
            checkBox11pm.setChecked(true);
        if(s.charAt(24)=='1')
            checkBox12.setChecked(true);
    }

    void resetCheckBoxes(){
        checkBox1.setChecked(false);
        checkBox2.setChecked(false);
        checkBox3.setChecked(false);
        checkBox4.setChecked(false);
        checkBox5.setChecked(false);
        checkBox6.setChecked(false);
        checkBox7.setChecked(false);
        checkBox8.setChecked(false);
        checkBox9.setChecked(false);
        checkBox10.setChecked(false);
        checkBox11.setChecked(false);
        checkBox12pm.setChecked(false);
        checkBox1pm.setChecked(false);
        checkBox2pm.setChecked(false);
        checkBox3pm.setChecked(false);
        checkBox4pm.setChecked(false);
        checkBox5pm.setChecked(false);
        checkBox6pm.setChecked(false);
        checkBox7pm.setChecked(false);
        checkBox8pm.setChecked(false);
        checkBox9pm.setChecked(false);
        checkBox10pm.setChecked(false);
        checkBox11pm.setChecked(false);
        checkBox12.setChecked(false);
    }
    void disableToEdit(){
        checkBox1.setClickable(false);
        checkBox2.setClickable(false);
        checkBox3.setClickable(false);
        checkBox4.setClickable(false);
        checkBox5.setClickable(false);
        checkBox6.setClickable(false);
        checkBox7.setClickable(false);
        checkBox8.setClickable(false);
        checkBox9.setClickable(false);
        checkBox10.setClickable(false);
        checkBox11.setClickable(false);
        checkBox12pm.setClickable(false);
        checkBox1pm.setClickable(false);
        checkBox2pm.setClickable(false);
        checkBox3pm.setClickable(false);
        checkBox4pm.setClickable(false);
        checkBox5pm.setClickable(false);
        checkBox6pm.setClickable(false);
        checkBox7pm.setClickable(false);
        checkBox8pm.setClickable(false);
        checkBox9pm.setClickable(false);
        checkBox10pm.setClickable(false);
        checkBox11pm.setClickable(false);
        checkBox12.setClickable(false);
    }
}
