//Update_Layout.java
package com.sb.rad.finaltest.updateIntent;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.sb.rad.finaltest.MainActivity;
import com.sb.rad.finaltest.R;
import com.sb.rad.finaltest.obj.UserData;
import com.sb.rad.finaltest.sqlite.SQLiteDataBase;

import java.util.ArrayList;

public class Update_Layout extends AppCompatActivity {

    static CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8, checkBox9, checkBox10, checkBox11, checkBox12;
    static CheckBox checkBox1pm, checkBox2pm, checkBox3pm, checkBox4pm, checkBox5pm, checkBox6pm, checkBox7pm, checkBox8pm, checkBox9pm, checkBox10pm, checkBox11pm, checkBox12pm;

    static EditText stock,pillsPerDose;

    static ListView listView;

    static SQLiteDataBase db;
    static Update_Adapter update_adapter;

   static Context context;
    static ActionBar actionBar;

    public static boolean isReady=false;
    View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__layout);

        final Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set back button to home
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Select Any Item");

        stock=(EditText)findViewById(R.id.stock);
        pillsPerDose=(EditText)findViewById(R.id.pillsPerDose);

        initializeAllCheckBox();
        listView=(ListView)findViewById(R.id.listView);

        context=getApplicationContext();
        actionBar=getSupportActionBar();

        v=this.getCurrentFocus();//get view



        db=new SQLiteDataBase(getApplicationContext());
        setDataIntoListView();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_for_update, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if(id==android.R.id.home){
            finish();
        }else if(id==R.id.updateOk){

            InputMethodManager keyboard=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            keyboard.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

            //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


            if(checkValidInput()&&isReady){


                db.updateData(getAllUpdatedData());

                Toast.makeText(getApplicationContext(),getSupportActionBar().getTitle().toString()+'\n'+"Is Updated",Toast.LENGTH_SHORT).show();
                resetAllInputField();
                setDataIntoListView();

            }else {
                Toast.makeText(getApplicationContext(),"Invalid Input",Toast.LENGTH_SHORT).show();
            }
            isReady=false;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
    private void resetAllInputField() {
        actionBar.setTitle("Select Any Item");
        stock.setText("");
        pillsPerDose.setText("");
        resetCheckBoxes();
    }


    //get schedule string
    public static String  getSchedule(){
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
    private static String getCheckStatus(CheckBox checkBox){
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
    static String getDosePerDay(String s){
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

    public static void resetCheckBoxes(){
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

    public static void setStock(String stock){
        Update_Layout.stock.setText(stock);
    }
    public static void setPillsPerDose(String pillsPerDose){
        Update_Layout.pillsPerDose.setText(pillsPerDose);
    }

    public static void setDataIntoListView() {
        ArrayList<UserData> userDatas = db.getDataToDisplay();

        if (userDatas != null) {
            update_adapter = new Update_Adapter(context,userDatas );
            listView.setAdapter(update_adapter);
        }else {
            Toast.makeText(context, "Empty Medicine List", Toast.LENGTH_SHORT).show();
        }
    }

    boolean checkStock(){
        if(stock.getText().toString().trim().isEmpty()==false)
            return true;
        Toast.makeText(context,"Check Stock",Toast.LENGTH_SHORT).show();
        return false;
    }
    boolean checkPillsPerDay(){
        if(pillsPerDose.getText().toString().trim().isEmpty()==false)
            return true;
        Toast.makeText(context,"Check Pills",Toast.LENGTH_SHORT).show();
        return false;
    }

    boolean checkValidInput(){
        if(checkStock()&& checkPillsPerDay())
            return true;
        return false;
    }

    public static void setAllInput(UserData userData){

        actionBar.setTitle(userData.getMedicineName().toUpperCase());

        setStock(userData.getStock());
        setPillsPerDose(userData.getPillsPerDose());

        resetCheckBoxes();
        setCheckBoxes(userData.getSchedule());

    }
    public static UserData getAllUpdatedData(){
        UserData updatedData=new UserData();

        updatedData.setMedicineName(actionBar.getTitle().toString());
        updatedData.setStock(stock.getText().toString());
        updatedData.setDosePerDay(getDosePerDay(getSchedule()));
        updatedData.setPillsPerDose(pillsPerDose.getText().toString());
        updatedData.setSchedule(getSchedule());

        return updatedData;
    }

}
