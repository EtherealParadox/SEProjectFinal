package com.example.seprojectfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity implements android.view.View.OnClickListener {

    BottomNavigationView bottomNavigationViewView;

    private TextView textViewRegister, textViewForgotPassword;
    private EditText editTextEmailAddressSignIn, editTextTextPasswordSignIn;
    private Button buttonSignIn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        bottomNavigationViewView = findViewById(R.id.bottomNavigationViewView);
        bottomNavigationViewView.setSelectedItemId(R.id.signin);

        bottomNavigationViewView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                    case R.id.view:
                        startActivity(new Intent(getApplicationContext(), View.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                    case R.id.signin:
                        return true;
                }

                return false;
            }
        });

        textViewForgotPassword = findViewById(R.id.textViewForgotPass);
        textViewForgotPassword.setOnClickListener(this);
        textViewRegister = findViewById(R.id.textViewRegister);
        textViewRegister.setOnClickListener(this);

        editTextEmailAddressSignIn = findViewById(R.id.editTextEmailAddressSignIn);
        editTextTextPasswordSignIn = findViewById(R.id.editTextTextPasswordSignIn);

        buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonSignIn.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SignIn.this, Home.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(android.view.View v) {
        switch (v.getId()){
            case R.id.textViewRegister:
                Intent intent = new Intent(SignIn.this, Register.class);
                startActivity(intent);
                finish();
                break;
            case R.id.textViewForgotPass:
                Intent intent1 = new Intent(SignIn.this, ForgotPassword.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.buttonSignIn:
                signIn();
                break;
        }
    }

    private void signIn() {
        String email = editTextEmailAddressSignIn.getText().toString().trim();
        String password = editTextTextPasswordSignIn.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmailAddressSignIn.setError("Email is required!");
            editTextEmailAddressSignIn.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextTextPasswordSignIn.setError("Password is required!");
            editTextTextPasswordSignIn.requestFocus();
            return;
        }

        if (editTextEmailAddressSignIn.getText().toString().equals("admin@gmail.com") && editTextTextPasswordSignIn.getText().toString().equals("admin123")) {
            Intent intent = new Intent(getApplicationContext(), AdminProfile.class);
            startActivity(intent);
        }
        else if (!editTextEmailAddressSignIn.getText().toString().equals("admin@gmail.com") && !editTextTextPasswordSignIn.getText().toString().equals("admin123")) {
            mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(getApplicationContext(), EmployeeProfile.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Failed to Login, Please check your credentials", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        else{
                Toast.makeText(getApplicationContext(), "Failed to Login, Please check your credentials", Toast.LENGTH_LONG).show();
            }
        }

}

