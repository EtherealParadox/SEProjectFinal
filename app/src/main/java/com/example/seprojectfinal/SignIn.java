package com.example.seprojectfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
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

public class SignIn extends AppCompatActivity {

    BottomNavigationView bottomNavigationViewView;
    private EditText editTextEmailAddressSignIn, editTextTextPasswordSignIn, mwe;
    private Button buttonSignIn;

    private FirebaseAuth mAuth;
    private FirebaseUser user1, user2;
    private DatabaseReference reference1 , reference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        bottomNavigationViewView = findViewById(R.id.bottomNavigationViewView);
        bottomNavigationViewView.setSelectedItemId(R.id.view);

        bottomNavigationViewView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.view:
                        startActivity(new Intent(getApplicationContext(), View.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.signin:
                        return true;
                }

                return false;
            }
        });

        editTextEmailAddressSignIn = findViewById(R.id.editTextEmailAddressSignIn);
        editTextTextPasswordSignIn = findViewById(R.id.editTextTextPasswordSignIn);

        buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonSignIn.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                signIn();
            }
        });
    }

    private void signIn() {
        String email = editTextEmailAddressSignIn.getText().toString();
        String password = editTextTextPasswordSignIn.getText().toString();

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
                if(task.isSuccessful()){
                    user1 = FirebaseAuth.getInstance().getCurrentUser();
                    reference1 = FirebaseDatabase.getInstance().getReference("AdminAndEmployee");
                    String userId1 = user1.getUid();
                    reference1.child(userId1).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            UserSignIn userSignIn = snapshot.getValue(UserSignIn.class);

                            String num = userSignIn.num;
                            int numm = Integer.parseInt(num);

                            if(numm == 1){
                                Intent intent = new Intent(getApplicationContext(), AdminProfile.class);

                                startActivity(intent);
                            }
                            else if(numm == 2){
                                Intent intent = new Intent(getApplicationContext(), EmployeeProfile.class);

                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Failed to Login, Please check your credentials", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getApplicationContext(), "Failed to Login, Please check your credentials", Toast.LENGTH_LONG).show();
                        }
                    });

                }
                else{
                    Toast.makeText(getApplicationContext(), "Failed to Login, Please check your credentials", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}