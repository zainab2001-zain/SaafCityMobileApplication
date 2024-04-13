package com.buiseness.saafcitybuiseness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NoCache;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.buiseness.saafcitybuiseness.Model.Complainant;
import com.buiseness.saafcitybuiseness.Model.EnvVariables;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class SignInActivity extends AppCompatActivity {

    EditText Email_Phone, Password,URL;
    TextView Forget_Password;
    Button SignInbtn;
    TextView SignUpbtn;
    String Email, user_Password, Phone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_in);
        //hooks
        Email_Phone = findViewById(R.id.emailtextfield);
      //  Email_Phone = "zainfarq2001@gmail.com";
       // URL=findViewById(R.id.urltextfield);
        Password = findViewById(R.id.password_field);
        Forget_Password = findViewById(R.id.forget_pass);
        SignInbtn = findViewById(R.id.signinbtn);
        SignUpbtn = findViewById(R.id.signupbtn);

        //signin
        SignInbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean check = validateinfo(Email_Phone, Password);
                if (check == true) {
                    loginApi();

                }

            }

        });

        //forgetpassword
        Forget_Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, Forget_Password_Screen.class);
                startActivity(intent);

            }
        });

        //Signup
        SignUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);

            }
        });
    }

    private boolean validateinfo(EditText email_phone, EditText password) {
        if (email_phone.length() == 0) {
            Email_Phone.requestFocus();
            Email_Phone.setError("Field cannot be empty");
            return false;
        } else if (password.length() == 0) {
            Password.requestFocus();
            Password.setError("Field cannot be empty");
            return false;
        }
        return true;
    }

    private void Login(){

    }
    private void loginApi() {
        String ipAddress = EnvVariables.MY_IP_ADDRESS;
        String url = ipAddress + "Complainant/login"; // Replace with your API URL
        Email = Email_Phone.getText().toString();
        user_Password = Password.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("Complainant_Email", Email);
            jsonBody.put("Complainant_Password", user_Password);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("error", e.toString());
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("Response", response.toString());
                            boolean success = response.getBoolean("success");
                            if (success) {
                                Log.d("error", "success");
                                Toast.makeText(SignInActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                Complainant complainant = new Complainant(Email, user_Password);
                                Log.d("email", complainant.getComplainant_Email());

                                // Store the logged-in email in Shared Preferences
                                SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("logged_in_email", Email);
                                editor.apply();

                                Intent intent = new Intent(SignInActivity.this, Dashboard_Screen.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(SignInActivity.this, "Incorrect Password or Email", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(SignInActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                            Log.e("error", error.toString());
                        } else if (error instanceof ServerError) {
                            Toast.makeText(SignInActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                            Log.e("error", error.toString());
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(SignInActivity.this, "Authentication Error", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(SignInActivity.this, "Parse Error", Toast.LENGTH_SHORT).show();
                            Log.e("error", error.toString());
                        } else if (error instanceof TimeoutError) {
                            Toast.makeText(SignInActivity.this, "Timeout Error", Toast.LENGTH_SHORT).show();
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