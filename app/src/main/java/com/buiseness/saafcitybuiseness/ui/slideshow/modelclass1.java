package com.buiseness.saafcitybuiseness.ui.slideshow;

public class modelclass1 {
    private int complintno;
    private String status;
    private String Date;
    private String Time;

    modelclass1(int c_no, String status){
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
