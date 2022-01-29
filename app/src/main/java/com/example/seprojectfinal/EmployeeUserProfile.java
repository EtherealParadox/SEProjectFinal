package com.example.seprojectfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EmployeeUserProfile extends AppCompatActivity {

    private Button Logout;

    private FirebaseUser user;
    private DatabaseReference reference;
    private FirebaseDatabase rootNode;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_user_profile);

        Logout = findViewById(R.id.buttonLogoutEup);

        Logout.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("EmployeesLogs");
                String si = "Logged Out";
                UserEmployeeStatusLogs userEmployeeStatusLogs = new UserEmployeeStatusLogs(FirebaseAuth.getInstance().getCurrentUser().getEmail(), getDate(), getTime(), si);
                String key = reference.push().getKey();
                reference.child(key).setValue(userEmployeeStatusLogs);
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(EmployeeUserProfile.this, Home.class);
                startActivity(intent);
                finish();
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Employees");
        userId = user.getUid();

        final TextView textViewFullNameEup = (TextView) findViewById(R.id.textViewFullNameEup);
        final TextView textViewUserNameEup = (TextView) findViewById(R.id.textViewUserNameEup);
        final TextView textViewEmailEup = (TextView) findViewById(R.id.textViewEmailEup);

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserSignUp userSignUp = snapshot.getValue(UserSignUp.class);

                if(userSignUp != null) {
                    String email = userSignUp.email;
                    String uname = userSignUp.uname;
                    String fname = userSignUp.fname;
                    String lname = userSignUp.lname;

                    textViewFullNameEup.setText("Name: \n"  + fname + " " + lname);
                    textViewUserNameEup.setText("Username: \n" + uname);
                    textViewEmailEup.setText("Email: \n" + email);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EmployeeUserProfile.this, "Something wrong happened", Toast.LENGTH_LONG).show();
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