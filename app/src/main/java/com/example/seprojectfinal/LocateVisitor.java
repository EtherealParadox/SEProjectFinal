package com.example.seprojectfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class LocateVisitor extends AppCompatActivity {

    private SearchView searchViewLoc;

    RecyclerView recyclerViewLocate;
    AdapterViewLoc adapterViewLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate_visitor);

        searchViewLoc = findViewById(R.id.searchViewLocate);
        searchViewLoc.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                textSearchLoc(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                textSearchLoc(query);
                return false;
            }
        });

        recyclerViewLocate = findViewById(R.id.recyclerViewLocate);
        recyclerViewLocate.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Visitors"), User.class)
                        .build();

        adapterViewLoc = new AdapterViewLoc(options);
        recyclerViewLocate.setAdapter(adapterViewLoc);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapterViewLoc.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterViewLoc.stopListening();
    }

    private void textSearchLoc(String txt){
        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Visitors").orderByChild("name").startAt(txt).endAt(txt + "~"), User.class)
                        .build();

        adapterViewLoc = new AdapterViewLoc(options);
        adapterViewLoc.startListening();
        recyclerViewLocate.setAdapter(adapterViewLoc);
    }

}