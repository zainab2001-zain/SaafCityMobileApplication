package com.buiseness.saafcitybuiseness.Model;

public class Complaint {
    private String Complaint_ID;
    private String Complaint_Time;
    private String Complaint_Location;

    public Complaint(String Complaint_ID,String Comments ) {
this.Complaint_ID=Complaint_ID;
this.Comments=Comments;
    }

    public Complaint() {

    }

    public String getComplaint_ID() {
        return Complaint_ID;
    }

    public void setComplaint_ID(String complaint_ID) {
        Complaint_ID = complaint_ID;
    }

    public String getComplaint_Time() {
        return Complaint_Time;
    }

    public void setComplaint_Time(String complaint_Time) {
        Complaint_Time = complaint_Time;
    }

    public String getComplaint_Location() {
        return Complaint_Location;
    }

    public void setComplaint_Location(String complaint_Location) {
        Complaint_Location = complaint_Location;
    }

    public String getComplaint_Status() {
        return Complaint_Status;
    }

    public void setComplaint_Status(String complaint_Status) {
        Complaint_Status = complaint_Status;
    }

    public String getDepart_ID() {
        return Depart_ID;
    }

    public void setDepart_ID(String depart_ID) {
        Depart_ID = depart_ID;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getComplaint_Image() {
        return Complaint_Image;
    }

    public void setComplaint_Image(String complaint_Image) {
        Complaint_Image = complaint_Image;
    }

    public String getComplainant_Email() {
        return Complainant_Email;
    }

    public void setComplainant_Email(String complainant_Email) {
        Complainant_Email = complainant_Email;
    }

    public String getVerification_Image() {
        return Verification_Image;
    }

    public void setVerification_Image(String verification_Image) {
        Verification_Image = verification_Image;
    }

    private String Complaint_Status;
    private String Depart_ID;
    private String Comments;
    private String Complaint_Image;
    private String Complainant_Email;
    private String Verification_Image;

}
