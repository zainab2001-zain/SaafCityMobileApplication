package com.buiseness.saafcitybuiseness.ui.gallery;

public class modelclass {
    private int complintno;
    private String status;
    private String Date;
    private String Time;

    modelclass(int c_no,String status){
        this.complintno = c_no;
        this.status = status;
    }

    public int getComplintno() {
        return complintno;
    }
    public String getDate(){ return Date;}
    public String getTime(){return Time;}
    public String getStatus() {return status;}
}
