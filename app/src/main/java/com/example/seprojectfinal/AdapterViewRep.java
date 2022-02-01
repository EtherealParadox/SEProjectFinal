package com.example.seprojectfinal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

public class AdapterViewRep extends FirebaseRecyclerAdapter <UserReport, AdapterViewRep.myDataHolderRep> {

    public AdapterViewRep(@NonNull FirebaseRecyclerOptions<UserReport> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AdapterViewRep.myDataHolderRep holder, int position, @NonNull UserReport model) {
        holder.email.setText("Name: " + model.getEmail());
        holder.date.setText("Date: " + model.getDate());
        holder.time.setText("Time: " + model.getTime());

        holder.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.email.getContext())
                        .setContentHolder(new ViewHolder(R.layout.report_popup))
                        .setExpanded(true, 1200)
                        .create();

                android.view.View view = dialogPlus.getHolderView();

                TextView email = view.findViewById(R.id.textViewReportByRep);
                TextView report = view.findViewById(R.id.textViewReportViewRep);

                email.setText("Reported By \n" + model.getEmail());
                report.setText(model.getReport());

                dialogPlus.show();
            }
        });

        holder.btnDelete.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(holder.email.getContext());
                dialog.setTitle("Are you sure?");
                dialog.setMessage("Once deleted you cant undo this action. Proceed still?");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Reports")
                                .child(getRef(holder.getAdapterPosition()).getKey())
                                .removeValue();
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.email.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
            }
        });
    }

    @NonNull
    @Override
    public AdapterViewRep.myDataHolderRep onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        android.view.View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_design_view_rep, parent, false);
        return new AdapterViewRep.myDataHolderRep(view);
    }

    class myDataHolderRep extends RecyclerView.ViewHolder{

        TextView email, date, time;
        Button btnView, btnDelete;

        public myDataHolderRep(@NonNull View itemView) {
            super(itemView);

            email = itemView.findViewById(R.id.textViewEmailRep);
            date = itemView.findViewById(R.id.textViewDateRep);
            time = itemView.findViewById(R.id.textViewTimeRep);

            btnView = itemView.findViewById(R.id.buttonViewRep);
            btnDelete = itemView.findViewById(R.id.buttonDeleteRep);
        }
    }

}
