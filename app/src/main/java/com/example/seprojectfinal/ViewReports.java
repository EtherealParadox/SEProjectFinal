package com.example.seprojectfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ViewReports extends AppCompatActivity {

    private SearchView searchViewRep;

    RecyclerView recyclerViewRep;
    AdapterViewRep adapterViewRep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports);

        searchViewRep = findViewById(R.id.searchViewReport);
        searchViewRep.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        recyclerViewRep = findViewById(R.id.recyclerViewReport);
        recyclerViewRep.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<UserReport> options =
                new FirebaseRecyclerOptions.Builder<UserReport>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Reports"), UserReport.class)
                        .build();

        adapterViewRep = new AdapterViewRep(options);
        recyclerViewRep.setAdapter(adapterViewRep);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapterViewRep.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterViewRep.stopListening();
    }

    private void textSearch(String txt){
        FirebaseRecyclerOptions<UserReport> options =
                new FirebaseRecyclerOptions.Builder<UserReport>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Reports").orderByChild("email").startAt(txt).endAt(txt + "~"), UserReport.class)
                        .build();

        adapterViewRep = new AdapterViewRep(options);
        adapterViewRep.startListening();
        recyclerViewRep.setAdapter(adapterViewRep);
    }
}