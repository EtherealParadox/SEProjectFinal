package com.example.seprojectfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class EmployeeProfile extends AppCompatActivity implements View.OnClickListener {

    private Button buttonModifyEmp, buttonLocateEmp, buttonProfileEmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);

        buttonModifyEmp = findViewById(R.id.buttonModifyEmp);
        buttonModifyEmp.setOnClickListener(this);

        buttonLocateEmp = findViewById(R.id.buttonLocateEmp);
        buttonLocateEmp.setOnClickListener(this);

        buttonProfileEmp = findViewById(R.id.buttonProfileEmp);
        buttonProfileEmp.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(EmployeeProfile.this, Home.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonModifyEmp:
                Intent intent = new Intent(EmployeeProfile.this, ViewVisitor.class);
                startActivity(intent);
                break;
            case R.id.buttonLocateEmp:
                Intent intent1 = new Intent(EmployeeProfile.this, LocateVisitor.class);
                startActivity(intent1);
                break;
            case R.id.buttonProfileEmp:
                Intent intent2 = new Intent(EmployeeProfile.this, EmployeeUserProfile.class);
                startActivity(intent2);
                break;
        }
    }
}