//Update_Item.java
package com.sb.rad.finaltest.updateIntent;

import android.view.View;
import android.widget.TextView;

import com.sb.rad.finaltest.R;

/**
 * Created by RAD on 05-Apr-17.
 */

public class Update_Item {

    TextView medicineName, stock;
    TextView dosePerDayValues,pillsPerDoseValues;
    String schedule="";

    public Update_Item(View v){
        medicineName=(TextView)v.findViewById(R.id.medicineName);
        stock=(TextView)v.findViewById(R.id.stock);
        dosePerDayValues=(TextView)v.findViewById(R.id.dosePerDayValues);
        pillsPerDoseValues=(TextView)v.findViewById(R.id.pillPerDoseValues);
    }


    public void setStock(String stock) {
        this.stock.setText(stock);
    }

    public void setMedicineName(String medicineName) {
        this.medicineName.setText(medicineName);
    }

    public void setDosePerDayValues(String dosePerDayValues) {
        this.dosePerDayValues.setText(dosePerDayValues);
    }

    public void setPillsPerDoseValues(String pillsPerDoseValues) {
        this.pillsPerDoseValues.setText(pillsPerDoseValues);
    }
    public  void setSchedule(String schedule){
        this.schedule=schedule;
    }
}
