//MyAdapter.java
package com.sb.rad.finaltest.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.sb.rad.finaltest.MainActivity;
import com.sb.rad.finaltest.R;
import com.sb.rad.finaltest.all_medicine_list;
import com.sb.rad.finaltest.low_medicines;
import com.sb.rad.finaltest.obj.UserData;
import com.sb.rad.finaltest.obj.WarningMessage;
import com.sb.rad.finaltest.setAlarmLayout.set_alarm_layout;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by RAD on 30-Mar-17.
 */

public class MyAdapter extends BaseAdapter {

    ArrayList<UserData> userDatas;
    Context context;
    LayoutInflater inflater;

    Vibrator vibrator;

    String selectNotifyContext;

    public boolean flag=true;
public MyAdapter(){};

    public MyAdapter(Context context,ArrayList<UserData> userDatas,String selectNotifyContext,boolean flag){
        this.userDatas=userDatas;
        this.context=context;
        this.selectNotifyContext=selectNotifyContext;
        inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        vibrator=(Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        this.flag=flag;

    }
    @Override
    public int getCount() {
        return userDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        final View myView=inflater.inflate(R.layout.my_layout,null);
        MyLayout myLayout=new MyLayout(myView);


        /// tp do-----set user data to layout

        myLayout.setMedicineName(userDatas.get(position).getMedicineName());
        myLayout.setStock(userDatas.get(position).getStock()+" Piece");
        myLayout.setDosePerDay("Dose Per Day : "+userDatas.get(position).getDosePerDay());
        myLayout.setPillsPerDose("Pills Per Dose : "+userDatas.get(position).getPillsPerDose());

        myLayout.setSchedule(userDatas.get(position).getSchedule());

        if(this.flag) {
            myLayout.timeDetails.setBackgroundResource(R.drawable.button_color_gradiant);
        }

        myLayout.timeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,set_alarm_layout.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("MEDICINE_NAME",userDatas.get(position).getMedicineName());
                intent.putExtra("SCHEDULE",userDatas.get(position).getSchedule());
                set_alarm_layout.isFromListItem=true;
                myView.getContext().startActivity(intent);
            }
        });


        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(50);
                Toast.makeText(context,"Long Touch to Delete.",Toast.LENGTH_SHORT).show();

            }
        });
        myView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                vibrator.vibrate(50);

                final WarningMessage warningMessage=new WarningMessage(context,"Delete!","Are you sure?",R.drawable.warning3);
                warningMessage.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            String s = userDatas.get(position).getMedicineName();

                            MainActivity.myDataBase.deleteData(userDatas.get(position));
                            userDatas.remove(position);

                            if(selectNotifyContext.compareTo("all_medicine_list")==0){
                                all_medicine_list.myAdapter.notifyDataSetChanged();
                            }else if(selectNotifyContext.compareTo("low_medicines")==0){
                                low_medicines.myAdapter.notifyDataSetChanged();
                            }

                            Toast.makeText(context, s + " is Deleted", Toast.LENGTH_SHORT).show();

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                });
                warningMessage.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                warningMessage.display();



                return false;
            }
        });

        return myView;
    }
}
