//MainActivity.java
package com.sb.rad.finaltest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sb.rad.finaltest.adapter.MyAdapter;
import com.sb.rad.finaltest.alarmF.AlarmReceiver;
import com.sb.rad.finaltest.obj.UserData;
import com.sb.rad.finaltest.setAlarmLayout.set_alarm_layout;
import com.sb.rad.finaltest.sqlite.SQLiteDataBase;
import com.sb.rad.finaltest.updateIntent.Update_Layout;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    public static boolean isComplete=false;

    static EditText medicineName, stock,perDose;
    public static UserData userData;


    ListView suggestMedicineListView;
    Button timeButton;

    String storedMedicineName[]={"Ace","Napa","Ace Plus","Adovas","Alatrol","Ambrox","Anadol","Amocid","Anima","Anril SR","Antazol","Bactrocin","Beclomin","Benzapen","Beovit","Barif","Bonizol","Brofex","Butefin","Burna","Bromolac","Calbo 500","Calbo-D","Comet","Colicon","Clobam","Ciprocin","Cinaron","Cef-3","Carva","Candex","Defiron","De-rash","Depram","Dormitol","Durol","Dyvon","Duberal","Dermasol","Deprex","DIbenol","Elzer","Emcil","Entacyd","Entacyd Plus","Evit","Ezex","Eyebil","Fexo","Fexo Plus","Flacol","Flexi","Flugal","Fona","Fona Plus","Forse","Fortunate","Fusid","Fusid Plus","Flabex","Gabastar","Garlin","Gayvet","Gelora","Genisia","Geston","Giloba","GOL","Grastim","Gintex","Holabet","Hemorif","Hepavir","Imotil","Impel","Inacea","Inflagic","Iracet","Jorvan","Jort","K-One","Kop","Kop Gel","Laciten","Lanso","Lido","Loracef","Lumast","Maganta Plus","Maxbon","Maxcef","Maxpime","Maxrin","Menoral","Mevin","Mexlo","Monera","Myonil","Nalid","Navit","Neuro-B","Nomi","Ofran","Olistat","Oni","Ostel-D","Oxat","Penvik","Panodin SR","Paridol","Promtil","Rabeca","Rex","Saga","Sedil","Solo","Tazid","Terminex","Tusca","Tulip","Ucol","Utal","Viodin","Virux","Vigorex","Verde","Xcid","Xfin","Xten","Zif","Zox"};


   ListAdapter adapter;
    //public static MyAdapter myAdapter;
    public static SQLiteDataBase myDataBase;

    public static Context context;


    InputMethodManager keyboard;
    SharedPreferences sp;




    public static AlarmReceiver alarmReceiver=new AlarmReceiver();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        //initialized all xml components
        medicineName=(EditText)findViewById(R.id.medicineName);
        stock =(EditText)findViewById(R.id.stock);
        perDose =(EditText)findViewById(R.id.unitPerDose);
        suggestMedicineListView=(ListView)findViewById(R.id.suggestMedicineListView);
        timeButton=(Button)findViewById(R.id.timeButton);
        context=this;

        sp=getSharedPreferences("userNameInfo", Context.MODE_PRIVATE);


        //defaultSuggestList();/// show all save medicine list

       // myAdapter = new MyAdapter();



        myDataBase=new SQLiteDataBase(this);
        myDataBase.sendToFireBase();

        keyboard=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        //set patient name into title bar
        //getSupportActionBar().setTitle(sp.getString("USER_NAME",null));
        getSupportActionBar().setTitle(null);
        Toast.makeText(this,"Welcome "+sp.getString("USER_NAME",null),Toast.LENGTH_SHORT).show();


        //not working

//        if(setDataFromDataBaseToListView()==false) {
//            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
//           // keyboard.showSoftInput(medicineNames, InputMethodManager.SHOW_IMPLICIT);
//        }else{
//            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//
//        }
        suggestMedicineListView.setVisibility(View.INVISIBLE);

        suggestMedicineListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
                medicineName.setText(parent.getItemAtPosition(position).toString());
                clearListView();
                hideKeyBoard();

            }
        });

        medicineName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> list=new ArrayList<String>();

                String s1=medicineName.getText().toString().trim();
                if(s.length()>0) {
                    for (String ss:storedMedicineName) {
                        if (ss.toLowerCase().startsWith(s1.toLowerCase())) {
                            list.add(ss);
                        }
                    }
                    suggestMedicineListView.setVisibility(View.VISIBLE);
                    setDataIntoListView(list);
                }else {
                    if(! medicineName.getText().toString().isEmpty()) {
                        defaultSuggestList();
                    }else{
                        clearListView();
                        suggestMedicineListView.setVisibility(View.INVISIBLE);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isValidMedicineNameFiled()&& isValidStockField()&&isValidDoseField()) {
                    Intent intent = new Intent(MainActivity.this, set_alarm_layout.class);
                    startActivity(intent);
                }
            }
        });

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//
//                    // this method ensure the connectivity with the firebase
//                    isConnectedWithFirebase();
//
//                    try {
//                        Thread.sleep(30000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    Log.e("Connected: ","ok");
//                }
//            }
//        }).start();


         //this method ensure the connectivity with the firebase
        isConnectedWithFirebase();



//        sp=getSharedPreferences("userNameInfo", Context. MODE_PRIVATE);
//        editor=sp.edit();

        //alarmReceiver.setAlarm(this);



    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        Log.e("Main Activity: ","ok");
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

       if(id==R.id.updateButton){
           startActivity(new Intent(MainActivity.this, Update_Layout.class));
       }else if(id==R.id.about){
           startActivity(new Intent(MainActivity.this,About.class));
       }else if(id==R.id.viewLList){
           startActivity(new Intent(MainActivity.this,all_medicine_list.class));
       }else if(id==R.id.viewLowMedicine){
           startActivity(new Intent(MainActivity.this,low_medicines.class));
       }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    void isConnectedWithFirebase(){
        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    //getSupportActionBar().setTitle("connected");
                    getSupportActionBar().setBackgroundDrawable(null);
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3F51B5")));
                } else {
                    //getSupportActionBar().setTitle("not connected");
                    getSupportActionBar().setBackgroundDrawable(null);
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#EF5350")));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Listener was cancelled");
            }
        });
    }

    public void setDataIntoListView(List<String> list){

        String[] selectedList=new String [list.size()];
        selectedList=list.toArray(selectedList);
        adapter=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,selectedList);
        suggestMedicineListView.setAdapter(adapter);
    }
    public  void clearListView(){
        String[] ss={""};
        adapter=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,ss);
        suggestMedicineListView.setAdapter(adapter);
    }
    public void defaultSuggestList(){
        adapter=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,storedMedicineName);
        suggestMedicineListView.setAdapter(adapter);
    }

    private boolean isValidMedicineNameFiled()
    {
        if(medicineName.getText().toString().trim().isEmpty()) {
            Toast.makeText(this,"Check Medicine Name",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean isValidStockField()
    {
        if(stock.getText().toString().trim().isEmpty()){
            Toast.makeText(this,"Check Stock",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean isValidDoseField()
    {
        if(perDose.getText().toString().trim().isEmpty()){
            Toast.makeText(this,"Give Pills Per Dose",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    void hideKeyBoard(){
        keyboard.hideSoftInputFromWindow(medicineName.getWindowToken(),0);
    }

    public static void setDataFromUser(){
        userData=new UserData();
        userData.setMedicineName(medicineName.getText().toString());
        userData.setStock(stock.getText().toString());
        userData.setPillsPerDose(perDose.getText().toString());

    }
    public static void resetInputField(){
        medicineName.setText("");
        stock.setText("");
        perDose.setText("");
    }


}
