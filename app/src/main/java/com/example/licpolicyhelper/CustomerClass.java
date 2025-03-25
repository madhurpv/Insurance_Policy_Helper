package com.example.licpolicyhelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CustomerClass {
    private int policyNo = -1;
    private String name = "";
    private String dateOfCommencement = "";
    private String premium = "";
    private String dateOfBirth = "";
    private String planTerm = "";
    private String modeOfPayment = "";
    private String nextDueDate = "";
    private Long nextDueDateUnix = -1L;


    public int getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(int policyNo) {
        this.policyNo = policyNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfCommencement() {
        return dateOfCommencement;
    }

    public void setDateOfCommencement(String dateOfCommencement) {
        this.dateOfCommencement = dateOfCommencement;
    }

    public String getPremium() {
        return premium;
    }

    public void setPremium(String premium) {
        this.premium = premium;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPlanTerm() {
        return planTerm;
    }

    public void setPlanTerm(String planTerm) {
        this.planTerm = planTerm;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public String getNextDueDate() {
        return nextDueDate;
    }

    public void setNextDueDate(String nextDueDate) {
        this.nextDueDate = nextDueDate;
        this.nextDueDateUnix = convertDateToLong(nextDueDate);
    }

    public Long getNextDueDateUnix() {
        return nextDueDateUnix;
    }

    public void setNextDueDateUnix(Long nextDueDateUnix) {
        this.nextDueDateUnix = nextDueDateUnix;
    }

    public CustomerClass() {
    }

    public CustomerClass(int policyNo, String name, String dateOfCommencement, String premium, String dateOfBirth,  String planTerm, String modeOfPayment, String nextDueDate) {
        this.policyNo = policyNo;
        this.name = name;
        this.dateOfCommencement = dateOfCommencement;
        this.premium = premium;
        this.dateOfBirth = dateOfBirth;
        this.modeOfPayment = modeOfPayment;
        this.nextDueDate = nextDueDate;
        this.planTerm = planTerm;
        this.nextDueDateUnix = convertDateToLong(nextDueDate);
    }


    public Long convertDateToLong(String nextDueDate){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC")); // Set timezone to UTC for accuracy
        try {
            Date date = sdf.parse(nextDueDate);
            return date.getTime() / 1000 + 10; // Convert milliseconds to seconds
        } catch (ParseException e) {
            e.printStackTrace();
            return -1L; // Return -1 if parsing fails
        }
    }



}
