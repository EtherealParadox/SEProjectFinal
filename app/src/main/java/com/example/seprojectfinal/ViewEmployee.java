package com.example.seprojectfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ViewEmployee extends AppCompatActivity {

    private SearchView searchViewAdm;

    RecyclerView recyclerViewAdm;
    AdapterViewAdm adapterViewAdm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_employee);

        searchViewAdm = findViewById(R.id.searchViewAdm);
        searchViewAdm.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                textSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                textSearch(query);
                return false;
            }
        });

        recyclerViewAdm = findViewById(R.id.recyclerViewAdm);
        recyclerViewAdm.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<UserSignUp> options =
                new FirebaseRecyclerOptions.Builder<UserSignUp>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Employees"), UserSignUp.class)
                        .build();

        adapterViewAdm = new AdapterViewAdm(options);
        recyclerViewAdm.setAdapter(adapterViewAdm);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapterViewAdm.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterViewAdm.stopListening();
    }

    private void textSearch(String txt){
        FirebaseRecyclerOptions<UserSignUp> options =
                new FirebaseRecyclerOptions.Builder<UserSignUp>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Employees").orderByChild("fname").startAt(txt).endAt(txt + "~"), UserSignUp.class)
                        .build();

        adapterViewAdm = new AdapterViewAdm(options);
        adapterViewAdm.startListening();
        recyclerViewAdm.setAdapter(adapterViewAdm);
    }
}