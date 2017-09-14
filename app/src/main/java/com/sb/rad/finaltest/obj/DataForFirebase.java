//DataForFirebase.java
package com.sb.rad.finaltest.obj;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by RAD on 12-Apr-17.
 */

public class DataForFirebase {
    public Alarm alarm1=new Alarm();
    public Alarm alarm2=new Alarm();
    public Alarm alarm3=new Alarm();
    public Alarm alarm4=new Alarm();
    public Alarm alarm5=new Alarm();
    public Alarm alarm6=new Alarm();
    public Alarm alarm7=new Alarm();
    public Alarm alarm8=new Alarm();
    public Alarm alarm9=new Alarm();
    public Alarm alarm10=new Alarm();
    public Alarm alarm11=new Alarm();
    public Alarm alarm12=new Alarm();
    public Alarm alarm13=new Alarm();
    public Alarm alarm14=new Alarm();
    public Alarm alarm15=new Alarm();
    public Alarm alarm16=new Alarm();
    public Alarm alarm17=new Alarm();
    public Alarm alarm18=new Alarm();
    public Alarm alarm19=new Alarm();
    public Alarm alarm20=new Alarm();
    public Alarm alarm21=new Alarm();
    public Alarm alarm22=new Alarm();
    public Alarm alarm23=new Alarm();
    public Alarm alarm0 =new Alarm();

    public ArrayList<String> warning=new ArrayList<>();
    public DataForFirebase(){}

    public DataForFirebase(ArrayList<UserData> userData) {

        if (userData != null) {
            for (int i = 0; i < userData.size(); i++) {
                String schedule = userData.get(i).getSchedule();
                String medicineName = userData.get(i).getMedicineName();


                if (schedule.charAt(1) == '1')
                    alarm1.medicineNames.add(medicineName);
                if (schedule.charAt(2) == '1')
                    alarm2.medicineNames.add(medicineName);
                if (schedule.charAt(3) == '1')
                    alarm3.medicineNames.add(medicineName);
                if (schedule.charAt(4) == '1')
                    alarm4.medicineNames.add(medicineName);
                if (schedule.charAt(5) == '1')
                    alarm5.medicineNames.add(medicineName);
                if (schedule.charAt(6) == '1')
                    alarm6.medicineNames.add(medicineName);
                if (schedule.charAt(7) == '1')
                    alarm7.medicineNames.add(medicineName);
                if (schedule.charAt(8) == '1')
                    alarm8.medicineNames.add(medicineName);
                if (schedule.charAt(9) == '1')
                    alarm9.medicineNames.add(medicineName);
                if (schedule.charAt(10) == '1')
                    alarm10.medicineNames.add(medicineName);
                if (schedule.charAt(11) == '1')
                    alarm11.medicineNames.add(medicineName);
                if (schedule.charAt(12) == '1')
                    alarm12.medicineNames.add(medicineName);
                if (schedule.charAt(13) == '1')
                    alarm13.medicineNames.add(medicineName);
                if (schedule.charAt(14) == '1')
                    alarm14.medicineNames.add(medicineName);
                if (schedule.charAt(15) == '1')
                    alarm15.medicineNames.add(medicineName);
                if (schedule.charAt(16) == '1')
                    alarm16.medicineNames.add(medicineName);
                if (schedule.charAt(17) == '1')
                    alarm17.medicineNames.add(medicineName);
                if (schedule.charAt(18) == '1')
                    alarm18.medicineNames.add(medicineName);
                if (schedule.charAt(19) == '1')
                    alarm19.medicineNames.add(medicineName);
                if (schedule.charAt(20) == '1')
                    alarm20.medicineNames.add(medicineName);
                if (schedule.charAt(21) == '1')
                    alarm21.medicineNames.add(medicineName);
                if (schedule.charAt(22) == '1')
                    alarm22.medicineNames.add(medicineName);
                if (schedule.charAt(23) == '1')
                    alarm23.medicineNames.add(medicineName);
                if (schedule.charAt(24) == '1')
                    alarm0.medicineNames.add(medicineName);
            }
            for (int i = 0; i < userData.size(); i++) {
                int currentStock = Integer.valueOf(userData.get(i).getStock());
                int pillsPerDose = Integer.valueOf(userData.get(i).getPillsPerDose());
                if (currentStock <= (pillsPerDose * 2)) {
                    this.warning.add(userData.get(i).getMedicineName());
                }
            }
        }else
            Log.e("errorrrrr: ","true");
    }
//    public HashMap<String,Integer> hhh(ArrayList<UserData> userData) {
//
//        if (userData != null) {
//            for (int i = 0; i < userData.size(); i++) {
//                String schedule = userData.get(i).getSchedule();
//                String medicineName = userData.get(i).getMedicineName();
//
//
//                if (schedule.charAt(1) == '1')
//                    alarm1.medicineNames.add(medicineName);
//                if (schedule.charAt(2) == '1')
//                    alarm2.medicineNames.add(medicineName);
//                if (schedule.charAt(3) == '1')
//                    alarm3.medicineNames.add(medicineName);
//                if (schedule.charAt(4) == '1')
//                    alarm4.medicineNames.add(medicineName);
//                if (schedule.charAt(5) == '1')
//                    alarm5.medicineNames.add(medicineName);
//                if (schedule.charAt(6) == '1')
//                    alarm6.medicineNames.add(medicineName);
//                if (schedule.charAt(7) == '1')
//                    alarm7.medicineNames.add(medicineName);
//                if (schedule.charAt(8) == '1')
//                    alarm8.medicineNames.add(medicineName);
//                if (schedule.charAt(9) == '1')
//                    alarm9.medicineNames.add(medicineName);
//                if (schedule.charAt(10) == '1')
//                    alarm10.medicineNames.add(medicineName);
//                if (schedule.charAt(11) == '1')
//                    alarm11.medicineNames.add(medicineName);
//                if (schedule.charAt(12) == '1')
//                    alarm12.medicineNames.add(medicineName);
//                if (schedule.charAt(13) == '1')
//                    alarm13.medicineNames.add(medicineName);
//                if (schedule.charAt(14) == '1')
//                    alarm14.medicineNames.add(medicineName);
//                if (schedule.charAt(15) == '1')
//                    alarm15.medicineNames.add(medicineName);
//                if (schedule.charAt(16) == '1')
//                    alarm16.medicineNames.add(medicineName);
//                if (schedule.charAt(17) == '1')
//                    alarm17.medicineNames.add(medicineName);
//                if (schedule.charAt(18) == '1')
//                    alarm18.medicineNames.add(medicineName);
//                if (schedule.charAt(19) == '1')
//                    alarm19.medicineNames.add(medicineName);
//                if (schedule.charAt(20) == '1')
//                    alarm20.medicineNames.add(medicineName);
//                if (schedule.charAt(21) == '1')
//                    alarm21.medicineNames.add(medicineName);
//                if (schedule.charAt(22) == '1')
//                    alarm22.medicineNames.add(medicineName);
//                if (schedule.charAt(23) == '1')
//                    alarm23.medicineNames.add(medicineName);
//                if (schedule.charAt(24) == '1')
//                    alarm0.medicineNames.add(medicineName);
//            }
//            for (int i = 0; i < userData.size(); i++) {
//                int currentStock = Integer.valueOf(userData.get(i).getStock());
//                int pillsPerDose = Integer.valueOf(userData.get(i).getPillsPerDose());
//                if (currentStock <= (pillsPerDose * 2)) {
//                    this.warning.add(userData.get(i).getMedicineName());
//                }
//            }
//        }else
//            Log.e("errorrrrr: ","true");
//    }
//
}
