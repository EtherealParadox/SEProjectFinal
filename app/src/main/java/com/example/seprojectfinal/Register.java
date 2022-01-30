package com.example.seprojectfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextFirstNameReg, editTextLastNameReg, editTextEmailAddressReg, editTextUserNameReg, editTextTextPasswordReg;
    private Button buttonRegister;

    private FirebaseAuth mAuth;

    boolean passwordVisible;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        editTextFirstNameReg = findViewById(R.id.editTextFirstNameReg);
        editTextLastNameReg = findViewById(R.id.editTextLastNameReg);
        editTextEmailAddressReg = findViewById(R.id.editTextEmailAddressReg);
        editTextUserNameReg = findViewById(R.id.editTextUserNameReg);
        editTextTextPasswordReg = findViewById(R.id.editTextTextPasswordReg);
        editTextTextPasswordReg.setOnTouchListener(new android.view.View.OnTouchListener() {
            @Override
            public boolean onTouch(android.view.View v, MotionEvent event) {
                final int right = 2;
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(event.getRawX()>=editTextTextPasswordReg.getRight()-editTextTextPasswordReg.getCompoundDrawables()[right].getBounds().width()) {
                        int selection = editTextTextPasswordReg.getSelectionEnd();
                        if (passwordVisible) {
                            editTextTextPasswordReg.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_off, 0);
                            editTextTextPasswordReg.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;
                        }
                        else{
                            editTextTextPasswordReg.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_on, 0);
                            editTextTextPasswordReg.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible = true;
                        }

                        editTextTextPasswordReg.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Register.this, SignIn.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonRegister:
                    signUp();
                break;
        }
    }

    private void signUp() {
        String fname = editTextFirstNameReg.getText().toString().trim();
        String lname = editTextLastNameReg.getText().toString().trim();
        String email = editTextEmailAddressReg.getText().toString().trim();
        String uname = editTextUserNameReg.getText().toString().trim();
        String password = editTextTextPasswordReg.getText().toString().trim();

        if (fname.isEmpty()) {
            editTextFirstNameReg.setError("First name is required!");
            editTextFirstNameReg.requestFocus();
            return;
        }

        if (lname.isEmpty()) {
            editTextLastNameReg.setError("Last name is required!");
            editTextLastNameReg.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextEmailAddressReg.setError("Email is required!");
            editTextEmailAddressReg.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmailAddressReg.setError("Please provide valid email!");
            editTextEmailAddressReg.requestFocus();
            return;
        }

        if (uname.isEmpty()) {
            editTextUserNameReg.setError("Username is required!");
            editTextUserNameReg.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextTextPasswordReg.setError("Password is required!");
            editTextTextPasswordReg.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextTextPasswordReg.setError("Minimum password length is 6 characters!");
            editTextTextPasswordReg.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            UserSignUp userSignUp = new UserSignUp(fname, lname, email, uname, password);

                            FirebaseDatabase.getInstance().getReference("Employees")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(userSignUp).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(Register.this, "You have been registered successfully!", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(Register.this, SignIn.class);
                                        startActivity(intent);
                                        //finish();
                                    } else {
                                        Toast.makeText(Register.this, "Failed to register, Try again", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(Register.this, "Failed to , Try again", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}