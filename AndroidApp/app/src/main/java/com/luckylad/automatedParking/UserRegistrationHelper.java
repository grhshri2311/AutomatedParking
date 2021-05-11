package com.luckylad.automatedParking;


public class UserRegistrationHelper {

    String email,fname,pass,phone;
    double balance;


    public UserRegistrationHelper(String fname, String email, String phone, String pass, double balance) {
        this.email = email;
        this.fname = fname;
        this.pass = pass;
        this.phone = phone;
        this.balance = balance;
    }

    public UserRegistrationHelper() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
