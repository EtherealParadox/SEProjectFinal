package com.example.seprojectfinal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AdapterView  extends FirebaseRecyclerAdapter <UserView, AdapterView.myDataHolderView>{

    public AdapterView(@NonNull FirebaseRecyclerOptions<UserView> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myDataHolderView holder, int position, @NonNull UserView model) {
        holder.name.setText("Name: " + model.getName());
        holder.date.setText("Date: " + model.getDate());
        holder.time.setText("Time: " + model.getTime());
        holder.temp.setText("Temperature: " + model.getTemperature() + "°C");
    }

    @NonNull
    @Override
    public myDataHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_design_view, parent, false);
        return new myDataHolderView(view);
    }

    public class myDataHolderView extends RecyclerView.ViewHolder{

        TextView name, date, time, temp;

        public myDataHolderView(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textViewName);
            date = itemView.findViewById(R.id.textViewDate);
            time = itemView.findViewById(R.id.textViewTime);
            temp = itemView.findViewById(R.id.textViewTemperature);

        }
    }

}
