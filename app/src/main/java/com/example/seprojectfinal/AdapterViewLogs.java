package com.example.seprojectfinal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class AdapterViewLogs extends FirebaseRecyclerAdapter <UserEmployeeStatusLogs, AdapterViewLogs.myDataHolderLog> {


    public AdapterViewLogs(@NonNull FirebaseRecyclerOptions<UserEmployeeStatusLogs> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AdapterViewLogs.myDataHolderLog holder, int position, @NonNull UserEmployeeStatusLogs model) {
        holder.email.setText(model.getEmail());
        holder.date.setText(model.getDate());
        holder.time.setText(model.getTime());
        holder.status.setText(model.getStatus());
    }

    @NonNull
    @Override
    public AdapterViewLogs.myDataHolderLog onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        android.view.View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_design_view_log, parent, false);
        return new AdapterViewLogs.myDataHolderLog(view);
    }

    class myDataHolderLog extends RecyclerView.ViewHolder{

        TextView email, date, time, status;

        public myDataHolderLog(@NonNull View itemView) {
            super(itemView);

            email = itemView.findViewById(R.id.textViewEmailLog);
            date = itemView.findViewById(R.id.textViewDateLog);
            time = itemView.findViewById(R.id.textViewTimeLog);
            status = itemView.findViewById(R.id.textViewStatusLog);
        }
    }

}
