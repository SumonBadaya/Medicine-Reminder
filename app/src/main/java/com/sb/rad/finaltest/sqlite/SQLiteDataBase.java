
//SQLiteDataBase.java
package com.sb.rad.finaltest.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.firebase.database.FirebaseDatabase;
import com.sb.rad.finaltest.obj.DataForFirebase;
import com.sb.rad.finaltest.obj.UserData;

import java.util.ArrayList;

/**
 * Created by RAD on 03-Apr-17.
 */

public class SQLiteDataBase extends SQLiteOpenHelper {

    private static final String DATA_BASE_NAME="USER_DATA_BASE.db";
    private static final String TABLE_NAME="USER_TABLE";

    private static final String MEDICINE_NAME="MEDICINE_NAME";
    private static final String STOCK="STOCK";
    private static final String DOSE_PER_DAY="DOSE_PER_DAY";
    private static final String PILL_PER_DOSE="PILL_PER_DOSE";
    private static final String SCHEDULE="SCHEDULE";

    private static final int VERSION=1;



    public SQLiteDataBase(Context context) {
        super(context, DATA_BASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE "+TABLE_NAME+"("+MEDICINE_NAME+" TEXT PRIMARY KEY,"+STOCK+" TEXT,"+DOSE_PER_DAY+" TEXT,"+PILL_PER_DOSE+" TEXT,"+SCHEDULE+" TEXT)";
        try {
            db.execSQL(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
            onCreate(db);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public boolean insertData(UserData userData){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(MEDICINE_NAME,userData.getMedicineName().toUpperCase());
        contentValues.put(STOCK,userData.getStock());
        contentValues.put(DOSE_PER_DAY,userData.getDosePerDay());
        contentValues.put(PILL_PER_DOSE,userData.getPillsPerDose());
        contentValues.put(SCHEDULE,userData.getSchedule());

        try{
            db.insert(TABLE_NAME,null,contentValues);
            sendToFireBase();//// update firebase data with Sqlite
            return  true;
        }catch (Exception e){
            return false;
        }
    }
    public boolean updateData(UserData userData){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(MEDICINE_NAME,userData.getMedicineName().toUpperCase());
        contentValues.put(STOCK,userData.getStock());
        contentValues.put(DOSE_PER_DAY,userData.getDosePerDay());
        contentValues.put(PILL_PER_DOSE,userData.getPillsPerDose());
        contentValues.put(SCHEDULE,userData.getSchedule());

        try{
            db.update(TABLE_NAME,contentValues,MEDICINE_NAME+" = ?",new String[]{userData.getMedicineName().toUpperCase()});
            sendToFireBase();//// update firebase data with Sqlite
            return  true;
        }catch (Exception e){
            return false;
        }
    }
    public boolean deleteData(UserData userData){
        SQLiteDatabase db=getWritableDatabase();
        try{
            db.delete(TABLE_NAME,MEDICINE_NAME+" = ?",new String[]{userData.getMedicineName().toUpperCase()});
            sendToFireBase();//// update firebase data with Sqlite
            return  true;
        }catch (Exception e){
            return false;
        }
    }

    public ArrayList<UserData> getDataToDisplay(){

        SQLiteDatabase db=getReadableDatabase();

        Cursor cursor=db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        if(cursor!=null && cursor.getCount()>0){
            ArrayList<UserData> userData =new ArrayList<>(cursor.getCount());

            cursor.moveToFirst();
            do{
                UserData userData1=new UserData();

                userData1.setMedicineName(cursor.getString(0));
                userData1.setStock(cursor.getString(1));
                userData1.setDosePerDay(cursor.getString(2));
                userData1.setPillsPerDose(cursor.getString(3));
                userData1.setSchedule(cursor.getString(4));

                userData.add(userData1);
            }while (cursor.moveToNext());

            return userData;
        }
        return null;
    }
    public void sendToFireBase(){
        FirebaseDatabase.getInstance().getReference("user").setValue(new DataForFirebase(getDataToDisplay()));
    }
}
