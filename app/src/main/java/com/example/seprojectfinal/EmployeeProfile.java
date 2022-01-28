package com.example.seprojectfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EmployeeProfile extends AppCompatActivity {

    private Button buttonModifyEmp, buttonLocateEmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);

        buttonLocateEmp = (Button) findViewById(R.id.buttonLocateEmp);
        buttonLocateEmp.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent = new Intent(EmployeeProfile.this, LocateVisitor.class);
                startActivity(intent);
            }
        });

        buttonModifyEmp = (Button) findViewById(R.id.buttonModifyEmp);
        buttonModifyEmp.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeProfile.this, ViewVisitor.class);
                startActivity(intent);
            }
        });
    }
}