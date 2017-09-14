//Update_Adapter.java
package com.sb.rad.finaltest.updateIntent;

import android.content.Context;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.sb.rad.finaltest.R;
import com.sb.rad.finaltest.obj.UserData;

import java.util.ArrayList;

/**
 * Created by RAD on 04-Apr-17.
 */

public class Update_Adapter extends BaseAdapter {

    Context context;
    static ArrayList<UserData> userDatas;
    LayoutInflater inflater;

    Vibrator vibrator;

    public Update_Adapter(Context context, ArrayList<UserData> userDatas){

        this.context=context;
        this.userDatas=userDatas;

        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        vibrator=(Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
    }
    @Override
    public int getCount() {
        return userDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return userDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View myUpdateView=inflater.inflate(R.layout.update_layout,null);

        final Update_Item updateLayout=new Update_Item(myUpdateView);

        updateLayout.setMedicineName(userDatas.get(position).getMedicineName());
        updateLayout.setStock(userDatas.get(position).getStock()+" Piece");
        updateLayout.setDosePerDayValues(userDatas.get(position).getDosePerDay()+" times");
        updateLayout.setPillsPerDoseValues(userDatas.get(position).getPillsPerDose()+" piece");
        updateLayout.setSchedule(userDatas.get(position).getSchedule());

        myUpdateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Update_Layout.setAllInput(userDatas.get(position));
                vibrator.vibrate(50);

                Update_Layout.isReady=true;
                Toast.makeText(context, userDatas.get(position).getMedicineName()+" is Selected", Toast.LENGTH_SHORT).show();

            }
        });

        return myUpdateView;
    }



}
