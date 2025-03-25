package com.example.licpolicyhelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class BirthdayDetailsClass {

    private String name = "";
    private String phoneNo = "";
    private String birthDate = "";
    private Long birthDateLong = -1L;

    public BirthdayDetailsClass() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public BirthdayDetailsClass(String name, String phoneNo, String birthDate) {
        this.name = name;
        this.phoneNo = phoneNo;
        this.birthDate = birthDate;
        this.birthDateLong = getLongBirthDate();
    }


    public Long getLongBirthDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC")); // Set timezone to UTC for accuracy
        try {
            Date date = sdf.parse(this.birthDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.YEAR, 2000); // Set year to 2000

            date = calendar.getTime();
            return date.getTime() / 1000 + 10; // Convert milliseconds to seconds
        } catch (ParseException e) {
            e.printStackTrace();
            return -1L; // Return -1 if parsing fails
        }
    }

    public BirthdayDetailsClass encryptBirthdayClass(String password){
        this.name = SecurityClass.encryptDecrypt(this.name, password, true);
        this.phoneNo = SecurityClass.encryptDecrypt(this.phoneNo, password, true);
        this.birthDate = SecurityClass.encryptDecrypt(this.birthDate, password, true);
        return this;
    }

    public BirthdayDetailsClass decryptBirthdayClass(String password){
        this.name = SecurityClass.encryptDecrypt(this.name, password, false);
        this.phoneNo = SecurityClass.encryptDecrypt(this.phoneNo, password, false);
        this.birthDate = SecurityClass.encryptDecrypt(this.birthDate, password, false);
        return this;
    }
}
