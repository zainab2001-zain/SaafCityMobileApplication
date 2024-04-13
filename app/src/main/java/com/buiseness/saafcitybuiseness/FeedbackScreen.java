package com.buiseness.saafcitybuiseness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.buiseness.saafcitybuiseness.Model.EnvVariables;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FeedbackScreen extends AppCompatActivity {

    RatingBar ratingBar;
    EditText feedbackText;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ratingBar = findViewById(R.id.ratingbar);
        feedbackText = findViewById(R.id.textbox);
        submitButton = findViewById(R.id.submitbtn);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the rating value
                float rating = ratingBar.getRating();

                // Get the feedback text
                String feedback = feedbackText.getText().toString();

                if (rating == 0) {
                    // Display a toast message if no rating is provided
                    Toast.makeText(FeedbackScreen.this, "Please provide ratings for the cleaning!", Toast.LENGTH_SHORT).show();
                } else {
                    // Call the API to submit feedback
                    submitFeedback(rating, feedback);
                }

                // Display a toast message with the submitted values
                Toast.makeText(FeedbackScreen.this, "Rating: " + rating, Toast.LENGTH_SHORT).show();

                // Start the Dashboard_Screen activity
                Intent intent = new Intent(FeedbackScreen.this, Dashboard_Screen.class);
                startActivity(intent);
            }
        });
    }

    private void submitFeedback(float rating, String feedback) {
        int complaintId = getComplaintIdFromSharedPreferences(); // Replace with the actual complaint ID
String Complaint_Feedback=rating+", "+feedback;
        // Create a JSON string with the complaint ID and feedback
        String jsonData = "{\"Complaint_ID\":" + complaintId + ", \"Comments\":\"" + Complaint_Feedback + "\"}";


        // Execute the API call asynchronously
        new SubmitFeedbackTask().execute(jsonData);
    }

    private class SubmitFeedbackTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            String jsonData = strings[0];
            String ipAddress = EnvVariables.MY_IP_ADDRESS;
            try {
                // Create the connection to the API endpoint
                URL url = new URL(ipAddress+"Complaint/submitfeedback/"); // Add trailing slash
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                // Write the JSON data to the request body
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(jsonData.getBytes());
                outputStream.flush();
                outputStream.close();

                // Check the response code
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Feedback submitted successfully
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Toast.makeText(FeedbackScreen.this, "Feedback submitted successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(FeedbackScreen.this, "Unable to submit feedback", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private int getComplaintIdFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("ComplaintData", MODE_PRIVATE);
        int complaintId = sharedPreferences.getInt("complaintId", 0); // Replace 0 with the default value if complaint ID is not found
        return complaintId;
    }

}
