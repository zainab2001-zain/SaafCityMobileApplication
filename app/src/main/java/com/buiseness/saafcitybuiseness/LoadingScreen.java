package com.buiseness.saafcitybuiseness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.buiseness.saafcitybuiseness.Model.EnvVariables;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoadingScreen extends AppCompatActivity {

    private ProgressBar progressBar;
    Button verificationbtn;
    private TextView textView;
    private TextView detailsTextView;
    private RelativeLayout loadingLayout;
    private static int SPLASH_SCREEN = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loading_screen);

        progressBar = findViewById(R.id.progressbar);
        textView = findViewById(R.id.textview);
        detailsTextView = findViewById(R.id.Details_TextView);
        loadingLayout = findViewById(R.id.contentloadingscreen);
        verificationbtn=findViewById(R.id.verificationbtn);
        verificationbtn.setVisibility(View.INVISIBLE);

        verificationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                            Intent intent = new Intent(LoadingScreen.this, CameraScreen2.class);
                            startActivity(intent);

                    }
                });


        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Call your API
                    new APICallTask().execute();
                }
            }, SPLASH_SCREEN);
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private class APICallTask extends AsyncTask<Void, Void, String> {
        String complaintid;
        @Override
        protected void onPreExecute() {
            // Show the progress bar and hide the response text views
            progressBar.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
            detailsTextView.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(Void... voids) {
            // Perform the API call and retrieve the response
            String response = "";
           complaintid =  getComplaintIdFromSharedPreferences();
            String ipAddress = EnvVariables.MY_IP_ADDRESS;
            try {
                URL url = new URL(ipAddress+"/Complaint/GetComplaintStatus/"+complaintid); // Replace with your API URL
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // Read the response
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                response = stringBuilder.toString();

                // Close connections
                bufferedReader.close();
                inputStream.close();
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            // Hide the progress bar
            progressBar.setVisibility(View.GONE);

            // Show the response text views
            textView.setVisibility(View.VISIBLE);
            detailsTextView.setVisibility(View.VISIBLE);

            // Display the response in the text views
            try {
                JSONObject jsonResponse = new JSONObject(response);
                String complaintStatus = jsonResponse.optString("ComplaintStatus");
                String departId = jsonResponse.optString("DepartId");

                textView.setText("We have Register your complaint. Your Complaint ID is "+ complaintid);
                detailsTextView.setText("Complaint Status: "+complaintStatus+"\n"+"Department: "+departId);
                verificationbtn.setVisibility(View.VISIBLE);
                // Display a Toast message with the response
                //Toast.makeText(LoadingScreen.this, complaintStatus, Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        private String getComplaintIdFromSharedPreferences() {
            SharedPreferences sharedPreferences = getSharedPreferences("ComplaintData", MODE_PRIVATE);
            int complaintId = sharedPreferences.getInt("complaintId", 0); // Replace 0 with the default value if complaint ID is not found
            return String.valueOf(complaintId);
        }

    }
}
