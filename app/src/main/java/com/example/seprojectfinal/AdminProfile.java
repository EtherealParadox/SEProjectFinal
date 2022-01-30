package com.example.seprojectfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class AdminProfile extends AppCompatActivity implements View.OnClickListener {

    private Button buttonModifyAdm, buttonLogsAdm, buttonReportsAdm, buttonLogoutAdm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        buttonModifyAdm = findViewById(R.id.buttonModifyAdm);
        buttonModifyAdm.setOnClickListener(this);

        buttonLogsAdm = findViewById(R.id.buttonLogsAdm);
        buttonLogsAdm.setOnClickListener(this);

        buttonReportsAdm = findViewById(R.id.buttonReportsAdm);
        buttonReportsAdm.setOnClickListener(this);

        buttonLogoutAdm = findViewById(R.id.buttonLogoutAdm);
        buttonLogoutAdm.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(AdminProfile.this, Home.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonModifyAdm:
                Intent intent = new Intent(AdminProfile.this, ViewEmployee.class);
                startActivity(intent);
                break;
            case R.id.buttonLogsAdm:
                Intent intent1 = new Intent(AdminProfile.this, ViewLogs.class);
                startActivity(intent1);
                break;
            case R.id.buttonReportsAdm:
                Intent intent2 = new Intent(AdminProfile.this, ViewReports.class);
                startActivity(intent2);
                break;
            case R.id.buttonLogoutAdm:
                Intent intent3 = new Intent(AdminProfile.this, Home.class);
                startActivity(intent3);
                break;
        }
    }
}