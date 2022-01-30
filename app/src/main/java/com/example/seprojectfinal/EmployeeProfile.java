package com.example.seprojectfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EmployeeProfile extends AppCompatActivity implements View.OnClickListener {

    private Button buttonModifyEmp, buttonLocateEmp, buttonReportEmp, buttonProfileEmp;

    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);

        buttonModifyEmp = findViewById(R.id.buttonModifyEmp);
        buttonModifyEmp.setOnClickListener(this);

        buttonLocateEmp = findViewById(R.id.buttonLocateEmp);
        buttonLocateEmp.setOnClickListener(this);

        buttonReportEmp = findViewById(R.id.buttonReportEmp);
        buttonReportEmp.setOnClickListener(this);

        buttonProfileEmp = findViewById(R.id.buttonProfileEmp);
        buttonProfileEmp.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        LogOutLog();
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
            case R.id.buttonReportEmp:
                Intent intent2 = new Intent(EmployeeProfile.this, Report.class);
                startActivity(intent2);
                break;
            case R.id.buttonProfileEmp:
                Intent intent3 = new Intent(EmployeeProfile.this, EmployeeUserProfile.class);
                startActivity(intent3);
                break;
        }
    }

    private String getTime() {
        return new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
    }

    private String getDate() {
        return new SimpleDateFormat("dd/LLL/yyyy", Locale.getDefault()).format(new Date());
    }

    private void LogOutLog() {
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("EmployeesLogs");
        String si = "Logged Out";
        Log.d("CDA", "onBackPressed Called");
        UserEmployeeStatusLogs userEmployeeStatusLogs = new UserEmployeeStatusLogs(FirebaseAuth.getInstance().getCurrentUser().getEmail(), getDate(), getTime(), si);
        String key = reference.push().getKey();
        reference.child(key).setValue(userEmployeeStatusLogs);
    }
}