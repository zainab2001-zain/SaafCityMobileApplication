    package com.buiseness.saafcitybuiseness;
    
    import android.Manifest;
    import android.app.AlertDialog;
    import android.content.Context;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.content.pm.PackageManager;
    import android.location.Location;
    import android.location.LocationListener;
    import android.location.LocationManager;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.RadioButton;
    import android.widget.RadioGroup;
    import android.widget.Toast;

    import java.nio.charset.StandardCharsets;
    import java.util.Calendar;
    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.app.ActivityCompat;
    import androidx.core.content.ContextCompat;
    
    import com.android.volley.AuthFailureError;
    import com.android.volley.DefaultRetryPolicy;
    import com.android.volley.NetworkError;
    import com.android.volley.NetworkResponse;
    import com.android.volley.ParseError;
    import com.android.volley.Request;
    import com.android.volley.RequestQueue;
    import com.android.volley.Response;
    import com.android.volley.ServerError;
    import com.android.volley.TimeoutError;
    import com.android.volley.VolleyError;
    import com.android.volley.toolbox.JsonObjectRequest;
    import com.android.volley.toolbox.Volley;
    import com.buiseness.saafcitybuiseness.Model.Complainant;
    import com.buiseness.saafcitybuiseness.Model.EnvVariables;

    import org.json.JSONException;
    import org.json.JSONObject;
    
    import retrofit2.http.Tag;
    
    public class LocationScreen extends AppCompatActivity {
    
        private RadioGroup radioGroup;
        private RadioButton deviceLocationRadioButton, manualLocationRadioButton;
        private EditText manualLocationEditText;
        private Button nextButton;
        private static final String TAG = "LocationScreen";
        private static final int REQUEST_CODE = 1001;
        private LocationManager locationManager;
        private LocationListener locationListener;
        private Location userLocation;
        String currentDateTime;
        String location;

        String currentLocation, devicelocation;
        // retrieve the encoded string from the intent
       // Intent intent = getIntent();
        String userEmail;
        String encodedImage;
    
        Complainant complainant = new Complainant();
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_location_screen);
    
         /*   if (intent != null) {
                encodedImage= intent.getStringExtra("encodedImage");
                        // rest of your code""
            }
            else{
                Toast.makeText(LocationScreen.this,"Unable to retrive image",Toast.LENGTH_SHORT);
            }*/
            // Find all the UI elements by their IDs
            radioGroup = findViewById(R.id.radiogroup);
            deviceLocationRadioButton = findViewById(R.id.deviceloction);
            manualLocationRadioButton = findViewById(R.id.manuallocation);
            manualLocationEditText = findViewById(R.id.Manuallocationedittext);
            nextButton = findViewById(R.id.nextbtn);
    
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);

          userEmail = sharedPreferences.getString("logged_in_email", "");

            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    userLocation = location;
                }
    
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {}
    
                @Override
                public void onProviderEnabled(String provider) {}
    
                @Override
                public void onProviderDisabled(String provider) {}
            };
            // Set a listener for the radio group to handle radio button changes
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.deviceloction) {
                        Context context = getApplicationContext();
                        // Device location radio button is selected
                        manualLocationEditText.setVisibility(View.GONE);
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                                == PackageManager.PERMISSION_GRANTED) {
                            // if permission is granted, get the location
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                            userLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        } else {
                            // if permission is not granted, request the permission from the user
                            ActivityCompat.requestPermissions(LocationScreen.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
                        }
    
                    } else {
                        // Manual location radio button is selected
                        manualLocationEditText.setVisibility(View.VISIBLE);
                    }
                }
            });
    
            // Set a listener for the Next button to handle button click
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
    
                    if (manualLocationRadioButton.isChecked()) {
                        // If manual location radio button is checked, get the entered location
                     location = manualLocationEditText.getText().toString().trim();
    
                        if (!location.isEmpty()) {
                            // Location is not empty, do something with it (e.g. send it to a web API)
                            Toast.makeText(LocationScreen.this, "Location: " + location, Toast.LENGTH_SHORT).show();
                        } else {
                            // Location is empty, show an error message
                            manualLocationEditText.setError("Please enter a location");
                            manualLocationEditText.requestFocus();
                        }
                        showConfirmationDialog();
                    } else {
                        location = userLocation.getLatitude() + ", " + userLocation.getLongitude();
    
    
                            showConfirmationDialog();
    
                    }
                }
            });
    
        }
    
    
    
    
        private void showConfirmationDialog() {
            // Create the alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirm Complaint");
            builder.setMessage("Your location is " + location + ".\nAre you sure you want to lodge a complaint?");
    
            // Add the buttons
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // Get the current date and time
                    Calendar calendar = Calendar.getInstance();
    
    // Get the year, month, day, hour, minute, and second from the calendar
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH) + 1; // Add 1 to month because it is zero-indexed
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int hour = calendar.get(Calendar.HOUR_OF_DAY); // Use 24-hour time
                    int minute = calendar.get(Calendar.MINUTE);
    
    
    // Store the date and time in a string variable
                    currentDateTime = year + "-" + month + "-" + day + " " + hour + ":" + minute;

                    /*Intent intent = new Intent(LocationScreen.this, CameraScreen.class);
                    intent.putExtra("Email",complainant_email);
                    intent.putExtra("Location",location);
                  intent.putExtra("Complaint_Time", currentDateTime);
                    intent.putExtra("Complaint_Status", "In-Progress");*/
                    //startActivity(intent);
                   //complainant_email = complainant.getComplainant_Email();
                    complaintapi();
                    // User clicked Yes button
    
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                    dialog.dismiss();
                }
            });
    
            // Create and show the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        private void complaintapi() {
            String ipAddress = EnvVariables.MY_IP_ADDRESS;
            String url = ipAddress+"Complaint/registercomplaint"; // Replace with your API URL
// Retrieve the email from Shared Preferences
            SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
            String userEmail = sharedPreferences.getString("logged_in_email", "");

            RequestQueue queue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("Complaint_Time", currentDateTime);
                jsonBody.put("Complaint_Loction", location);
                jsonBody.put("Complaint_Status", "In-Progress");
                jsonBody.put("Complainant_Email",userEmail);

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("eror", e.toString());
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.d("Response", response.toString());
                                boolean success = response.getBoolean("success");
                                if (success) {
                                    int complaintId = response.getInt("complaintId");
                                    Log.d("Complaint ID", String.valueOf(complaintId));

                                    // Save the complaint ID in SharedPreferences
                                    SharedPreferences sharedPreferences = getSharedPreferences("ComplaintData", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putInt("complaintId", complaintId);
                                    editor.apply();

                                    Log.d("eror", "success");
                                    //Toast.makeText(LocationScreen.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                    //Complainant complainant=new Complainant(Email,user_Password);
                                    Intent intent = new Intent(LocationScreen.this, CameraScreen.class);
                                    intent.putExtra("Email", userEmail);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(LocationScreen.this, "An error occurred", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error instanceof NetworkError) {
                                Toast.makeText(LocationScreen.this, "Network Error", Toast.LENGTH_SHORT).show();
                                Log.e("eror", error.toString());
                            } else if (error instanceof ServerError) {
                                NetworkResponse networkResponse = error.networkResponse;
                                if (networkResponse != null) {
                                    String errorMessage = new String(networkResponse.data, StandardCharsets.UTF_8);
                                    Toast.makeText(LocationScreen.this, "Server Error", Toast.LENGTH_SHORT).show();
                                    Log.e("ServerError", errorMessage);
                                }
                            } else if (error instanceof AuthFailureError) {
                                Toast.makeText(LocationScreen.this, "Authentication Error", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof ParseError) {
                                Toast.makeText(LocationScreen.this, "Parse Error", Toast.LENGTH_SHORT).show();
                                Log.e("eror", error.toString());
                            } else if (error instanceof TimeoutError) {
                                Toast.makeText(LocationScreen.this, "Timeout Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(jsonObjectRequest);
        }
    }