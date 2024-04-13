package com.buiseness.saafcitybuiseness.Interface.API;
import com.buiseness.saafcitybuiseness.Model.Complainant;
import com.buiseness.saafcitybuiseness.Model.Complainant;
import com.buiseness.saafcitybuiseness.Model.Complaint;
import com.buiseness.saafcitybuiseness.Model.ComplaintResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {


    @POST("Complainant/getdetails")
    Call<Complainant> getDetails(@Body Complainant complainant);

    @POST("Complainant/login")
    Call<String> login(@Body Complainant complainant);

    @POST("Complainant/signup")
    Call<Complainant> signup(@Body Complainant complainant);

    @POST("Complainant/submitfeedback")
    Call<Complainant> submitfeedback(@Body Complaint complaint);
    @Multipart
    @POST("Complaint/upload")
    Call<ComplaintResponse> uploadImage(
            @Header("Complaint_ID") String email,
            @Part MultipartBody.Part file
    );
    @POST("Complaint/GetOpenComplaints")
    Call<List<Complaint>> getOpenComplaints(@Body Complaint complaint);

    @POST("Complaint/GetCompletedComplaints")
    Call<List<Complaint>> getCompletedComplaints(@Body Complaint complaint);

    @POST("Complaint/GetRejectedComplaints")
    Call<List<Complaint>> getRejectedComplaints(@Body Complaint complaint);

    @GET("Complaint/image/{email}")
    Call<ComplaintResponse> GetImage(
            @Path("email") String email
    );

    @Multipart
    @POST("Complaint/uploadverificationimage")
    Call<ComplaintResponse> uploadVerificationImage(
            @Header("Complaint_ID") String C_ID,
            @Part MultipartBody.Part file
    );
}