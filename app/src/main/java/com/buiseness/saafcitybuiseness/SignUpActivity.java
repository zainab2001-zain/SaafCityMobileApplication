package com.buiseness.saafcitybuiseness;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.location.Address;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.telephony.SmsManager;
import com.buiseness.saafcitybuiseness.Model.Complainant;
import com.buiseness.saafcitybuiseness.Interface.API.ApiService;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.Callback;
public class SignUpActivity extends AppCompatActivity {

    private Geocoder geocoder;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private ApiService apiService;
    String c_Name,c_Email,c_PhoneNo,c_Password,c_ConfirmPassword,c_location,c_dateofbirth;
    EditText Email, Name;
    EditText Phone,Location,Dateofbirth;
    EditText Password, Confirm_Password;
    Button signup,getlocation;
    String regexStr = "^[0-9]$";
    String emailInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        geocoder = new Geocoder(this, Locale.getDefault());

        Name = findViewById(R.id.name);
        Email = findViewById(R.id.email);
        Phone = findViewById(R.id.phone);
        Password = findViewById(R.id.password_signup);
        Confirm_Password = findViewById(R.id.password_reenter);
        Location=findViewById(R.id.location);
        Dateofbirth=findViewById(R.id.dateofbirth);
        getlocation=findViewById(R.id.getlocationbtn);
        signup = (Button) findViewById(R.id.signup);
        getlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(SignUpActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted
                    getLocation();
                } else {
                    // Permission is not granted
                    ActivityCompat.requestPermissions(SignUpActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            1);
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               c_Name = Name.getText().toString();
                c_Email = Email.getText().toString();
              c_PhoneNo = Phone.getText().toString();
                c_Password = Password.getText().toString();
               c_ConfirmPassword = Confirm_Password.getText().toString();
               c_dateofbirth=Dateofbirth.getText().toString();
               c_location=Location.getText().toString();

                if (validateinfo(Name, Email, Phone, Password, Confirm_Password,Location,Dateofbirth)) {

                    // Create an instance of the Complainant class
                      Complainant complainant=new Complainant(c_Name,c_Email,c_PhoneNo,c_Password,c_location,c_dateofbirth);

                    // Make a signup API call using Retrofit
                    ApiService apiService = ApiClient.getApiClient().create(ApiService.class);
                    Call<Complainant> call = apiService.signup(complainant);
                    call.enqueue(new Callback<Complainant>() {
                        @Override
                        public void onResponse(Call<Complainant> call, Response<Complainant> response) {
                            // Handle the API response here
                            Log.d("error: ","successful");
                            //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                            // Store the logged-in email in Shared Preferences
                            SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("logged_in_email", c_Email);
                            editor.apply();

                            Intent intent = new Intent(getApplicationContext(), Verification_Number_Screen.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<Complainant> call, Throwable t) {
                            // Handle the API call failure here
                            Log.e("API Error", t.getMessage());
                            Toast.makeText(getApplicationContext(), "Failed to create account. Please try again later.", Toast.LENGTH_SHORT).show();
                        }
                    });

                    // Make an API call to create the user account
                    //Call<Complainant> call = apiService.createComplainant(complainant);

                   /* call.enqueue(new Callback<Complainant>() {

                        @Override
                        public void onResponse(Call<Complainant> call, Response<Complainant> response) {

                            if (response.isSuccessful()) {
                                // User account created successfully
                                // Navigate to the verification number screen
                                Complainant complainant = response.body();

                            }
                            else {
                                // Handle unsuccessful response
                                Log.e("API Error", "HTTP status code: " + response.code());
                                // Display error message to the user
                                Toast.makeText(getApplicationContext(), "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Complainant> call, Throwable t) {
                            // Handle failure

                        }
                    });*/
                }
            }
        });

    }

    private void getLocation() {
        // Get the device's location
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Handle location changes
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                // Handle status changes
            }

            @Override
            public void onProviderEnabled(String provider) {
                // Handle provider enabled
            }

            @Override
            public void onProviderDisabled(String provider) {
                // Handle provider disabled
            }
        };

        if (ContextCompat.checkSelfPermission(SignUpActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission is granted, make the method call
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (locationManager != null) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }

        } else {
            // Permission is not granted, request the permission
            ActivityCompat.requestPermissions(SignUpActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        // Set the location in the EditText view
        if (Location != null) {
            Location.setText(location1.getLatitude() + ", " + location1.getLongitude());
        } else {
            Location.setText("Unable to get location");
        }
    }





    private Boolean validateinfo (EditText name, EditText email, EditText phone, EditText password, EditText confirmpass,EditText location,EditText dateofbirth){
        if (name.length() == 0) {
            Name.requestFocus();
            Name.setError("Field cannot be empty");
            return false;
        }
        else if (email.length() == 0) {
            Email.requestFocus();
            Email.setError("Field cannot be empty");
            return false;
        }
        else if (location.length() == 0) {
            Location.requestFocus();
            Location.setError("Field cannot be empty");
            return false;
        }
        else if (dateofbirth.length() == 0) {
            Dateofbirth.requestFocus();
            Dateofbirth.setError("Field cannot be empty");
            return false;
        }

      /*  else if(!email.getText().toString().matches("[a-z0-9._-]+\\.+@[a-z]+")){
            Email.requestFocus();
            Email.setError("Invalid email address");
            return false;
        }*/
        else if (phone.length() == 0) {
            Phone.requestFocus();
            Phone.setError("Please enter phone no");
            return false;
        }
        else if (phone.length() > 13) {
            Phone.requestFocus();
            Phone.setError("Invalid phone number");
            return false;
        }
        else if (!Password.getText().toString().matches(Confirm_Password.getText().toString())) {
            Confirm_Password.requestFocus();
            Confirm_Password.setError("Password do not matches");
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                getLocation();
            } else {
                // Permission is not granted
                Toast.makeText(SignUpActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private class GetAddressTask extends AsyncTask<Double, Void, String> {
        @Override
        protected String doInBackground(Double... params) {
            double latitude = params[0];
            double longitude = params[1];
            String addressString = null;
            try {
                Geocoder geocoder = new Geocoder(SignUpActivity.this, Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                if (addresses != null && addresses.size() > 0) {
                    Address address = addresses.get(0);
                    addressString = address.getAddressLine(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return addressString;
        }

        @Override
        protected void onPostExecute(String addressString) {
            if (addressString != null) {
                Location.setText(addressString);
            } else {
                Toast.makeText(SignUpActivity.this, "unable to get address", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
