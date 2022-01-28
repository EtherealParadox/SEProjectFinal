package com.example.seprojectfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ViewVisitor extends AppCompatActivity {

    private SearchView searchViewEmp;

    RecyclerView recyclerViewEmp;
    AdapterViewEmp adapterViewEmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_visitor);

        searchViewEmp = findViewById(R.id.searchViewEmp);
        searchViewEmp.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        recyclerViewEmp = findViewById(R.id.recyclerViewEmp);
        recyclerViewEmp.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Visitors"), User.class)
                        .build();

        adapterViewEmp = new AdapterViewEmp(options);
        recyclerViewEmp.setAdapter(adapterViewEmp);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapterViewEmp.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterViewEmp.stopListening();
    }

    private void textSearch(String txt){
        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Visitors").orderByChild("name").startAt(txt).endAt(txt + "~"), User.class)
                        .build();

        adapterViewEmp = new AdapterViewEmp(options);
        adapterViewEmp.startListening();
        recyclerViewEmp.setAdapter(adapterViewEmp);
    }
}