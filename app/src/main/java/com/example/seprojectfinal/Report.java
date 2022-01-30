package com.example.seprojectfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;

public class Report extends AppCompatActivity {

    private EditText editTextReport;
    private Button buttonReport;

    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    private FirebaseUser user;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        editTextReport = findViewById(R.id.editTextReport);

        buttonReport = findViewById(R.id.buttonReport);
        buttonReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportToAdmin();
            }
        });
    }

    private void reportToAdmin() {
        String report = editTextReport.getText().toString().trim();

        if (report.isEmpty()) {
            editTextReport.setError("You cant leave the text area empty");
            editTextReport.requestFocus();
            return;
        }

        AlertDialog.Builder dialog = new AlertDialog.Builder(Report.this);
        dialog.setMessage("Are you sure that you entered all your concern/report you want to send to the admin?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                reference = FirebaseDatabase.getInstance().getReference("Employees");
                userId = user.getUid();
                reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        rootNode = FirebaseDatabase.getInstance();
                        reference = rootNode.getReference("Reports");
                        UserSignUp userSignUp = snapshot.getValue(UserSignUp.class);
                        String em = userSignUp.getEmail().toString();
                        UserReport userReport = new UserReport(em, report, getDate(),getTime());
                        String key = reference.push().getKey();
                        reference.child(key).setValue(userReport);
                        editTextReport.setText("");
                        Toast.makeText(Report.this, "Report Sent Successfully", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Report.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        dialog.setNegativeButton("No", null);
        AlertDialog alert = dialog.create();
        alert.show();
    }

    private String getTime() {
        return new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
    }

    private String getDate() {
        return new SimpleDateFormat("dd/LLL/yyyy", Locale.getDefault()).format(new Date());
    }
}