//UserData.java
package com.sb.rad.finaltest.obj;

public class UserData {

    String medicineName="";
    String stock="";
    String dosePerDay="";
    String pillsPerDose="";
    String schedule="";

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getPillsPerDose() {
        return pillsPerDose;
    }

    public void setPillsPerDose(String pillsPerDose) {
        this.pillsPerDose = pillsPerDose;
    }

    public String getDosePerDay() {
        return dosePerDay;
    }

    public void setDosePerDay(String dosePerDay) {
        this.dosePerDay = dosePerDay;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
}
