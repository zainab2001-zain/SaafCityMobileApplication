package com.buiseness.saafcitybuiseness;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.buiseness.saafcitybuiseness.Interface.API.ApiService;
import com.buiseness.saafcitybuiseness.Model.ComplaintResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImageShowingActivity extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_showing);

        imageView=findViewById(R.id.imageviewcomplaint);

        // Create a Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.38/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

// Create an instance of the ApiService
        ApiService apiService = retrofit.create(ApiService.class);

// Call the getImage method with the email
        Call<ComplaintResponse> call = apiService.GetImage("zainfarq@gmail.com");

// Enqueue the call and implement the callback
        call.enqueue(new Callback<ComplaintResponse>() {
            @Override
            public void onResponse(Call<ComplaintResponse> call, Response<ComplaintResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        InputStream inputStream = response.body().byteStream();
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = inputStream.read(buffer)) != -1) {
                            byteArrayOutputStream.write(buffer, 0, len);
                        }
                        byte[] imageBytes = byteArrayOutputStream.toByteArray();

                        // Create a Bitmap from the byte array
                        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

                        // Display the Bitmap in an ImageView
                        imageView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        Log.d("Eror: ",e.toString());
                    }
                } else {
                    // Image retrieval failed
                    Log.d("Response: ",response.toString());
                }
            }

            @Override
            public void onFailure(Call<ComplaintResponse> call, Throwable t) {
                Log.e("Upload", "API call failed: " + t.getMessage());
            }




        });

    }
}