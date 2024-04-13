package com.buiseness.saafcitybuiseness.Model;

import android.util.Log;

public class Complainant {
    private String Complainant_Name;
    private String Complainant_Email;
    private String Complainant_PhoneNo;
    private String Complainant_Password;
    private String Address;
    private String Date_Of_Birth;

    public Complainant(String name, String email, String phoneNo, String password, String address, String dob) {
        Complainant_Name = name;
        Complainant_Email = email;
        Complainant_PhoneNo = phoneNo;
        Complainant_Password = password;
        Address = address;
        Date_Of_Birth = dob;
    }
    public Complainant() {

    }
    public Complainant(String email,String password) {
        Complainant_Email = email;

        Log.d("email",Complainant_Email);
        Complainant_Password = password;
        }



    public String getComplainant_Name() {
        return Complainant_Name;
    }

    public void setComplainant_Name(String complainant_Name) {
        Complainant_Name = complainant_Name;
    }

    public String getComplainant_Email() {
        return Complainant_Email;
    }

    public void setComplainant_Email(String complainant_Email) {
        Complainant_Email = complainant_Email;
    }

    public String getComplainant_PhoneNo() {
        return Complainant_PhoneNo;
    }

    public void setComplainant_PhoneNo(String complainant_PhoneNo) {
        Complainant_PhoneNo = complainant_PhoneNo;
    }

    public String getComplainant_Password() {
        return Complainant_Password;
    }

    public void setComplainant_Password(String complainant_Password) {
        Complainant_Password = complainant_Password;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDate_Of_Birth() {
        return Date_Of_Birth;
    }

    public void setDate_Of_Birth(String date_Of_Birth) {
        Date_Of_Birth = date_Of_Birth;
    }
}
