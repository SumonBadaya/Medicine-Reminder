//MyLayout.java
package com.sb.rad.finaltest.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sb.rad.finaltest.R;

/**
 * Created by RAD on 29-Mar-17.
 */

public class MyLayout{

    TextView medicineName, stock,dosePerDay,pillsPerDose;
    String schedule="";

    public Button timeDetails;


    public MyLayout(View v) {

        //initialized all xml components
        medicineName = (TextView) v.findViewById(R.id.medicineName);
        stock = (TextView) v.findViewById(R.id.currentStock);
        dosePerDay = (TextView) v.findViewById(R.id.dosePerDay);
        pillsPerDose = (TextView) v.findViewById(R.id.pillPerDose);

        timeDetails=(Button)v.findViewById(R.id.timeDetails);

    }

    public void setMedicineName(String medicineName) {
        this.medicineName.setText(medicineName.toString());
    }

    public void setStock(String stock) {
        this.stock.setText(stock);
    }

    public void setDosePerDay(String dosePerDay) {
        this.dosePerDay.setText(dosePerDay);
    }

    public void setPillsPerDose(String pillsPerDose) {
        this.pillsPerDose.setText(pillsPerDose);
    }
    public void setSchedule(String schedule) {
        this.schedule=schedule;
    }
}
