package com.example.seprojectfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.FirebaseDatabase;

public class View extends AppCompatActivity {

    BottomNavigationView bottomNavigationViewView;

    private SearchView searchViewView;
    private RecyclerView recyclerViewView;
    AdapterView adapterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

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
                        return true;

                    case R.id.signin:
                        startActivity(new Intent(getApplicationContext(), SignIn.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }

                return false;
            }
        });

        searchViewView = findViewById(R.id.searchViewView);
        searchViewView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        recyclerViewView = findViewById(R.id.recyclerViewView);
        recyclerViewView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<UserView> options =
                new FirebaseRecyclerOptions.Builder<UserView>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Visitors"), UserView.class)
                        .build();

        adapterView = new AdapterView(options);
        recyclerViewView.setAdapter(adapterView);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(View.this, Home.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapterView.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterView.stopListening();
    }

    private void textSearch(String txt){
        FirebaseRecyclerOptions<UserView> options =
                new FirebaseRecyclerOptions.Builder<UserView>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Visitors").orderByChild("name").startAt(txt).endAt(txt + "~"), UserView.class)
                        .build();

        adapterView = new AdapterView(options);
        adapterView.startListening();
        recyclerViewView.setAdapter(adapterView);
    }
}