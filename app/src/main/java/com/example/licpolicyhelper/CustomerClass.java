package com.example.licpolicyhelper;

public class CustomerClass {
    private String policyNo;
    private String name;
    private String dateOfCommencement;
    private String premium;
    private String dateOfBirth;
    private String planTerm;
    private String modeOfPayment;
    private String nextDueDate;




    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
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
    }

    public CustomerClass(String policyNo, String name, String dateOfCommencement, String premium, String dateOfBirth,  String planTerm, String modeOfPayment, String nextDueDate) {
        this.policyNo = policyNo;
        this.name = name;
        this.dateOfCommencement = dateOfCommencement;
        this.premium = premium;
        this.dateOfBirth = dateOfBirth;
        this.modeOfPayment = modeOfPayment;
        this.nextDueDate = nextDueDate;
        this.planTerm = planTerm;
    }



}
