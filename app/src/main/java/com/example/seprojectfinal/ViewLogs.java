package com.example.seprojectfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ViewLogs extends AppCompatActivity {

    private SearchView searchViewLog;

    RecyclerView recyclerViewLog;
    AdapterViewLogs adapterViewLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_logs);

        searchViewLog = findViewById(R.id.searchViewLog);
        searchViewLog.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        recyclerViewLog = findViewById(R.id.recyclerViewLog);
        recyclerViewLog.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<UserEmployeeStatusLogs> options =
                new FirebaseRecyclerOptions.Builder<UserEmployeeStatusLogs>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("EmployeesLogs"), UserEmployeeStatusLogs.class)
                        .build();

        adapterViewLog = new AdapterViewLogs(options);
        recyclerViewLog.setAdapter(adapterViewLog);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapterViewLog.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterViewLog.stopListening();
    }

    private void textSearch(String txt){
        FirebaseRecyclerOptions<UserEmployeeStatusLogs> options =
                new FirebaseRecyclerOptions.Builder<UserEmployeeStatusLogs>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("EmployeesLogs").orderByChild("email").startAt(txt).endAt(txt + "~"), UserEmployeeStatusLogs.class)
                        .build();

        adapterViewLog = new AdapterViewLogs(options);
        adapterViewLog.startListening();
        recyclerViewLog.setAdapter(adapterViewLog);
    }
}