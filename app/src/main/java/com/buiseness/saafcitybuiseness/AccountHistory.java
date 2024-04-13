package com.buiseness.saafcitybuiseness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class AccountHistory extends AppCompatActivity {

    Button Verifybtn;
    TextView Complaint_Time,Complaint_Date,Complaint_Status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_account_history);

        Verifybtn=findViewById(R.id.Complaint_button);
        Complaint_Time=findViewById(R.id.Complaint_Time);
        Complaint_Date=findViewById(R.id.Complaint_Date);
        Complaint_Status=findViewById(R.id.Complaint_Status);

        Verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AccountHistory.this,CameraScreen2.class);
                startActivity(intent);
            }
        });
    }
}