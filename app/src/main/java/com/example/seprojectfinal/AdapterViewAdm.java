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

public class AdapterViewAdm extends FirebaseRecyclerAdapter<UserSignUp, AdapterViewAdm.myDataHolderAdm> {

    public AdapterViewAdm(@NonNull FirebaseRecyclerOptions<UserSignUp> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AdapterViewAdm.myDataHolderAdm holder, int position, @NonNull UserSignUp model) {
        holder.name.setText(model.getFname() + " " + model.getLname());
        holder.uname.setText(model.getUname());
        holder.email.setText(model.getEmail());

        holder.btnEdit.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.name.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup_adm))
                        .setExpanded(true, 1300)
                        .create();

                android.view.View view = dialogPlus.getHolderView();

                EditText fname = view.findViewById(R.id.editTextFNameGlideAdm);
                EditText lname = view.findViewById(R.id.editTextLNameGlideAdm);
                EditText uname = view.findViewById(R.id.editTextUNameGlideAdm);

                Button btnUpdate = view.findViewById(R.id.btnUpdateAdm);

                fname.setText(model.getFname());
                lname.setText(model.getLname());
                uname.setText(model.getUname());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("fname", fname.getText().toString());
                        map.put("lname", lname.getText().toString());
                        map.put("uname", uname.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Employees")
                                .child(getRef(holder.getAdapterPosition()).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.name.getContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText(holder.name.getContext(), "Error While Updating", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
            }
        });

        holder.btnDelete.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(holder.name.getContext());
                dialog.setTitle("Are you sure?");
                dialog.setMessage("Once deleted you cant undo this action. Proceed still?");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Employees")
                                .child(getRef(holder.getAdapterPosition()).getKey())
                                .removeValue();
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.name.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
            }
        });
    }

    @NonNull
    @Override
    public AdapterViewAdm.myDataHolderAdm onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        android.view.View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_design_view_adm, parent, false);
        return new AdapterViewAdm.myDataHolderAdm(view);
    }

    class myDataHolderAdm extends RecyclerView.ViewHolder{

        TextView name, uname, email;
        Button btnEdit, btnDelete;

        public myDataHolderAdm(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textViewNameAdm);
            uname = itemView.findViewById(R.id.textViewUsernameAdm);
            email = itemView.findViewById(R.id.textViewEmailAdm);

            btnEdit = itemView.findViewById(R.id.buttonEditAdm);
            btnDelete = itemView.findViewById(R.id.buttonDeleteAdm);
        }
    }
}
