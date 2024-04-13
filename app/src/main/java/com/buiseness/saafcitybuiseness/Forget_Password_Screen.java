package com.buiseness.saafcitybuiseness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.buiseness.saafcitybuiseness.Model.Complainant;
import com.buiseness.saafcitybuiseness.Model.EnvVariables;

import org.json.JSONException;
import org.json.JSONObject;

public class Forget_Password_Screen extends AppCompatActivity {

    EditText OldPssword,NewPassword,Re_enterPassword,Email;
    Button sendbtn;
    String OldPass,NewPass ,ReNewPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forget_password_screen);

        Email=findViewById(R.id.emailfield);
        OldPssword=findViewById(R.id.oldpasswordfield);
        NewPassword=findViewById(R.id.newpassword);
        Re_enterPassword=findViewById(R.id.reenterpassword);
        sendbtn=findViewById(R.id.sendbtn);



        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isEmpty(Email)){
                    Toast.makeText(Forget_Password_Screen.this, "Please write your old password", Toast.LENGTH_SHORT).show();
                }
                if(isEmpty(OldPssword)){
                    Toast.makeText(Forget_Password_Screen.this, "Please write your old password", Toast.LENGTH_SHORT).show();
                }
                else if(validatePassword(NewPassword,Re_enterPassword)){
                    UpdatePasswordApi();

                }
            }});
    }

    private void UpdatePasswordApi() {

        String ipAddress = EnvVariables.MY_IP_ADDRESS;
        String url = ipAddress+"Complainant/updatePassword"; // Replace with your API URL
        String Emailtext=Email.getText().toString();
        String OldPass=OldPssword.getText().toString();
        String NewPass =NewPassword.getText().toString();
        String ReNewPass =Re_enterPassword.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("Complainant_Email", Emailtext);
            jsonBody.put("Complainant_Password", OldPass);
            jsonBody.put("New_Password", NewPass);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("eror",e.toString());
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("Response", response.toString());
                            boolean success = response.getBoolean("success");
                            if (success) {
                                Log.d("eror","success");

                                Complainant complainant=new Complainant(Emailtext,OldPass);
                                Log.d("email",complainant.getComplainant_Email());
                                Toast.makeText(Forget_Password_Screen.this, "Your Password has been reset", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Forget_Password_Screen.this, SignInActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Forget_Password_Screen.this, "Incorrect Password or Email", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(Forget_Password_Screen.this, error.toString(), Toast.LENGTH_SHORT).show();
                            Log.e("eror",error.toString());
                        } else if (error instanceof ServerError) {
                            Toast.makeText(Forget_Password_Screen.this, "Server Error", Toast.LENGTH_SHORT).show();
                            Log.e("eror",error.toString());
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(Forget_Password_Screen.this, "Authentication Error", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(Forget_Password_Screen.this, "Parse Error", Toast.LENGTH_SHORT).show();
                            Log.e("eror",error.toString());
                        } else if (error instanceof TimeoutError) {
                            Toast.makeText(Forget_Password_Screen.this, "Timeout Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjectRequest);
    }
    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
    private boolean validatePassword(EditText newPassword, EditText re_enterPassword) {
        String newPass = newPassword.getText().toString();
        String reNewPass = re_enterPassword.getText().toString();

        if (isEmpty(newPassword)) {
            Toast.makeText(Forget_Password_Screen.this, "Please provide a new password", Toast.LENGTH_SHORT).show();
            return false;
        } else if (isEmpty(re_enterPassword)) {
            Toast.makeText(Forget_Password_Screen.this, "Please enter your password again.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!newPass.equals(reNewPass)) {
            re_enterPassword.requestFocus();
            re_enterPassword.setError("Password do not match");
            return false;
        } else {
            return true;
        }
    }

}