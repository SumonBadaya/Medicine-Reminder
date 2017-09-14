//low_medicines.java
package com.sb.rad.finaltest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.Toast;

import com.sb.rad.finaltest.adapter.MyAdapter;
import com.sb.rad.finaltest.obj.UserData;
import com.sb.rad.finaltest.sqlite.SQLiteDataBase;

import java.util.ArrayList;

public class low_medicines extends AppCompatActivity {

    ListView listView;
    public static MyAdapter myAdapter;
    public  SQLiteDataBase myDataBase;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_low_medicines);

        this.context=low_medicines.this;


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listView=(ListView)findViewById(R.id.listView) ;
        myDataBase=new SQLiteDataBase(this);

        setDataFromDataBaseToListView();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if(id==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public boolean setDataFromDataBaseToListView(){

        ArrayList<UserData> userDatas = myDataBase.getDataToDisplay();
        if (userDatas != null) {
            ArrayList<UserData> u=new ArrayList<>();

            for (int i = 0; i < userDatas.size(); i++) {
                int currentStock = Integer.valueOf(userDatas.get(i).getStock());
                int pillsPerDose = Integer.valueOf(userDatas.get(i).getPillsPerDose());
                if (currentStock <= (pillsPerDose * 2)) {
                    u.add(userDatas.get(i));
                    //this.warning.add(userDatas.get(i).getMedicineName());
                }
            }
            if(u!=null && u.isEmpty()==false) {

                myAdapter = new MyAdapter(context, u,"low_medicines",false);
                listView.setAdapter(myAdapter);
            }
            return true;
        }
        return false;
    }
}
