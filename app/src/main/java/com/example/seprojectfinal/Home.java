package com.example.seprojectfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.seprojectfinal.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Home extends AppCompatActivity {

    BottomNavigationView bottomNavigationViewHome;
    private EditText editTextTemperatureHome, editTextNameHome, editTextEmailHome, editTextAddressHome, editTextAgeHome,  editTextGenderHome,  editTextSymptomsHome, editTextContactHome, editTextDestinationHome;
    private Button buttonConfirm;

    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationViewHome = findViewById(R.id.bottomNavigationViewHome);
        bottomNavigationViewHome.setSelectedItemId(R.id.home);

        bottomNavigationViewHome.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:
                        return true;

                    case R.id.view:
                        startActivity(new Intent(getApplicationContext(), View.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.signin:
                        startActivity(new Intent(getApplicationContext(), SignIn.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }

        });

        editTextTemperatureHome = findViewById(R.id.editTextTemperatureHome);
        editTextNameHome = findViewById(R.id.editTextNameHome);
        editTextEmailHome = findViewById(R.id.editTextEmailHome);
        editTextAddressHome = findViewById(R.id.editTextAddressHome);
        editTextAgeHome = findViewById(R.id.editTextAgeHome);
        editTextGenderHome = findViewById(R.id.editTextGenderHome);
        editTextSymptomsHome = findViewById(R.id.editTextSymptomsHome);
        editTextContactHome = findViewById(R.id.editTextContactHome);
        editTextDestinationHome = findViewById(R.id.editTextDestinationHome);

        buttonConfirm = findViewById(R.id.buttonAddHome);
        buttonConfirm.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                enterData();
            }
        });

    }

    private void enterData() {
        String temperature = editTextTemperatureHome.getText().toString().trim();
        String name = editTextNameHome.getText().toString().trim();
        String email = editTextEmailHome.getText().toString().trim();
        String address = editTextAddressHome.getText().toString().trim();
        String age = editTextAgeHome.getText().toString().trim();
        String gender = editTextGenderHome.getText().toString().trim();
        String symptoms = editTextSymptomsHome.getText().toString().trim();
        String contact = editTextContactHome.getText().toString().trim();
        String destination = editTextDestinationHome.getText().toString().trim();

        if (temperature.isEmpty()) {
            editTextTemperatureHome.setError("Temperature is required!");
            editTextTemperatureHome.requestFocus();
            return;
        }

        if (name.isEmpty()) {
            editTextNameHome.setError("Name is required");
            editTextNameHome.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextEmailHome.setError("Email is required");
            editTextEmailHome.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmailHome.setError("Please provide valid email");
            editTextEmailHome.requestFocus();
            return;
        }

        if (address.isEmpty()) {
            editTextAddressHome.setError("Address is required");
            editTextAddressHome.requestFocus();
            return;
        }

        if (age.isEmpty()) {
            editTextAgeHome.setError("Age is required");
            editTextAgeHome.requestFocus();
            return;
        }

        if (gender.isEmpty()) {
            editTextGenderHome.setError("Gender is required");
            editTextGenderHome.requestFocus();
            return;
        }

        if (symptoms.isEmpty()) {
            editTextSymptomsHome.setError("An answer is required");
            editTextSymptomsHome.requestFocus();
            return;
        }

        if (contact.isEmpty()) {
            editTextContactHome.setError("Contact is required");
            editTextContactHome.requestFocus();
            return;
        }

        if (contact.length() > 11) {
            editTextContactHome.setError("Contact length is only 11 numbers!");
            editTextContactHome.requestFocus();
            return;
        }

        if (destination.isEmpty()) {
            editTextDestinationHome.setError("Destination is required");
            editTextDestinationHome.requestFocus();
            return;
        }

        AlertDialog.Builder dialog = new AlertDialog.Builder(Home.this);
        dialog.setMessage("Are you sure that there is nothing wrong on the information that you provided?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Entering your data to the database", Toast.LENGTH_LONG).show();
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Visitors");
                User user = new User(temperature, name, email, address, age, gender, symptoms, contact, destination, getDate(),getTime());
                String key = reference.push().getKey();
                reference.child(key).setValue(user);
                editTextTemperatureHome.setText("");
                editTextNameHome.setText("");
                editTextEmailHome.setText("");
                editTextAddressHome.setText("");
                editTextAgeHome.setText("");
                editTextGenderHome.setText("");
                editTextSymptomsHome.setText("");
                editTextContactHome.setText("");
                editTextDestinationHome.setText("");
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