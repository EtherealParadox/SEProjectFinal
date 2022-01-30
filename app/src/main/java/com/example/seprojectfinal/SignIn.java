package com.example.seprojectfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.drm.DrmStore;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.MotionEvent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SignIn extends AppCompatActivity implements android.view.View.OnClickListener {

    BottomNavigationView bottomNavigationViewView;

    private TextView textViewRegister, textViewForgotPassword;
    private EditText editTextEmailAddressSignIn, editTextTextPasswordSignIn;
    private Button buttonSignIn;

    private FirebaseAuth mAuth;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    boolean passwordVisible;

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
        editTextTextPasswordSignIn.setOnTouchListener(new android.view.View.OnTouchListener() {
            @Override
            public boolean onTouch(android.view.View v, MotionEvent event) {
                final int right = 2;
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(event.getRawX()>=editTextTextPasswordSignIn.getRight()-editTextTextPasswordSignIn.getCompoundDrawables()[right].getBounds().width()) {
                        int selection = editTextTextPasswordSignIn.getSelectionEnd();
                        if (passwordVisible) {
                            editTextTextPasswordSignIn.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_off, 0);
                            editTextTextPasswordSignIn.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;
                        }
                        else{
                                editTextTextPasswordSignIn.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_on, 0);
                                editTextTextPasswordSignIn.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                passwordVisible = true;
                            }

                            editTextTextPasswordSignIn.setSelection(selection);
                            return true;
                        }
                }
                return false;
            }
        });

        buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonSignIn.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
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

        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (task.isSuccessful()) {
                        if(user.isEmailVerified()) {
                            editTextEmailAddressSignIn.setText("");
                            editTextTextPasswordSignIn.setText("");
                            rootNode = FirebaseDatabase.getInstance();
                            reference = rootNode.getReference("EmployeesLogs");
                            String si = "Logged In";
                            UserEmployeeStatusLogs userEmployeeStatusLogs = new UserEmployeeStatusLogs(email, getDate(), getTime(), si);
                            String key = reference.push().getKey();
                            reference.child(key).setValue(userEmployeeStatusLogs);
                            Intent intent = new Intent(getApplicationContext(), EmployeeProfile.class);
                            startActivity(intent);
                        }
                        else{
                            user.sendEmailVerification();
                            Toast.makeText(getApplicationContext(), "Check your email to verify your account", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if (editTextEmailAddressSignIn.getText().toString().equals("admin@gmail.com") && editTextTextPasswordSignIn.getText().toString().equals("admin123")) {
                        editTextEmailAddressSignIn.setText("");
                        editTextTextPasswordSignIn.setText("");
                        Intent intent = new Intent(getApplicationContext(), AdminProfile.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Failed to Login, Please check your credentials", Toast.LENGTH_SHORT).show();
                    }
                }
        });
    }

    private String getTime() {
        return new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
    }

    private String getDate() {
        return new SimpleDateFormat("dd/LLL/yyyy", Locale.getDefault()).format(new Date());
    }

}

