//all_medicine_list.java
package com.sb.rad.finaltest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.sb.rad.finaltest.adapter.MyAdapter;
import com.sb.rad.finaltest.obj.UserData;
import com.sb.rad.finaltest.sqlite.SQLiteDataBase;

import java.util.ArrayList;

public class all_medicine_list extends AppCompatActivity {

    ListView listView;
    public static   MyAdapter myAdapter;
    public  SQLiteDataBase myDataBase;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_medicine_list);
        this.context=all_medicine_list.this;



        listView=(ListView)findViewById(R.id.listView) ;
        myDataBase=new SQLiteDataBase(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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
            myAdapter = new MyAdapter(context, userDatas,"all_medicine_list",true);
            listView.setAdapter(myAdapter);
            return true;
        }
//        else {
//            Toast.makeText(context, "Empty Data Base", Toast.LENGTH_SHORT).show();
//            return false;
//        }
        return false;
    }
}
