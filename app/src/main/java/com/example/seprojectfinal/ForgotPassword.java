package com.example.seprojectfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgotPassword extends AppCompatActivity {

    private EditText editTextEmailAddressForgot;
    private Button buttonForgot;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        editTextEmailAddressForgot = findViewById(R.id.editTextEmailAddressForgot);

        buttonForgot = findViewById(R.id.buttonForgot);
        buttonForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ForgotPassword.this, SignIn.class);
        startActivity(intent);
        finish();
    }

    private void resetPassword() {
        String email = editTextEmailAddressForgot.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmailAddressForgot.setError("Email is required");
            editTextEmailAddressForgot.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmailAddressForgot.setError("Please provide valid email");
            editTextEmailAddressForgot.requestFocus();
            return;
        }

        mAuth = FirebaseAuth.getInstance();
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this, "Check your email to reset your password", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(ForgotPassword.this, "The email you entered is not in our database", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}