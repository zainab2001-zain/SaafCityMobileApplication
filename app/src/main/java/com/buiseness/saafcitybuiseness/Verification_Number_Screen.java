package com.buiseness.saafcitybuiseness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.buiseness.saafcitybuiseness.Model.Complainant;

public class Verification_Number_Screen extends AppCompatActivity {

    EditText no1;
    Button sendbtn;
    String verificationCode = "123456";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verification_number_screen);
        sendbtn=findViewById(R.id.sendbutton);
        no1=findViewById(R.id.number1);
        Complainant complainant=new Complainant();



        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check=validateinfo(no1);
                if(check==true){
                    Toast.makeText(Verification_Number_Screen.this, "Welcome!", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(getApplicationContext(),Dashboard_Screen.class);
                    startActivity(intent);

                }


            }
        });
    }
    private boolean validateinfo(EditText No1) {

        if(No1.length()==0){
            no1.requestFocus();
            no1.setError("Field cannot be empty");
            return false;
        }
        return true;




    }

}