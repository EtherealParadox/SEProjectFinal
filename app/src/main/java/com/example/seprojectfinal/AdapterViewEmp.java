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

public class AdapterViewEmp extends FirebaseRecyclerAdapter<User, AdapterViewEmp.myDataHolderEmp> {

    public AdapterViewEmp(@NonNull FirebaseRecyclerOptions<User> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myDataHolderEmp holder, int position, @NonNull User model) {
        holder.name.setText("Name: " + model.getName());
        holder.contact.setText("Contact: " + model.getContact());
        holder.email.setText("Email: " + model.getEmail());

        holder.btnEdit.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.name.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true, 1500)
                        .create();



                android.view.View view = dialogPlus.getHolderView();

                EditText name = view.findViewById(R.id.editTextNameGlideEmp);
                EditText email = view.findViewById(R.id.editTextEmailGlideEmp);
                EditText address = view.findViewById(R.id.editTextAddressGlideEmp);
                EditText age = view.findViewById(R.id.editTextAgeGlideEmp);
                EditText gender = view.findViewById(R.id.editTextGenderGlideEmp);
                EditText destination = view.findViewById(R.id.editTextDestinationGlideEmp);
                EditText contact = view.findViewById(R.id.editTextNumberGlideEmp);
                EditText temp = view.findViewById(R.id.editTextTemperatureGlideEmp);

                Button btnUpdate = view.findViewById(R.id.btnUpdateEmp);

                name.setText(model.getName());
                email.setText(model.getEmail());
                address.setText(model.getAddress());
                age.setText(model.getAge());
                gender.setText(model.getGender());
                destination.setText(model.getDestination());
                contact.setText(model.getContact());
                temp.setText(model.getTemperature());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("name", name.getText().toString());
                        map.put("email", email.getText().toString());
                        map.put("address", address.getText().toString());
                        map.put("age", age.getText().toString());
                        map.put("gender", gender.getText().toString());
                        map.put("destination", destination.getText().toString());
                        map.put("contact", contact.getText().toString());
                        map.put("temperature", temp.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Visitors")
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

                holder.btnDelete.setOnClickListener(new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(holder.name.getContext());
                        dialog.setTitle("Are you sure?");
                        dialog.setMessage("Once deleted you cant undo this action. Proceed still?");
                        dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase.getInstance().getReference().child("Visitors")
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
        });
    }

    @NonNull
    @Override
    public myDataHolderEmp onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        android.view.View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_design_view_emp, parent, false);
        return new myDataHolderEmp(view);
    }

    class myDataHolderEmp extends RecyclerView.ViewHolder{

        TextView name, contact, email;
        Button btnEdit, btnDelete;

        public myDataHolderEmp(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textViewNameEmp);
            contact = itemView.findViewById(R.id.textViewContactEmp);
            email = itemView.findViewById(R.id.textViewEmailEmp);

            btnEdit = itemView.findViewById(R.id.buttonEditEmp);
            btnDelete = itemView.findViewById(R.id.buttonDeleteEmp);
        }
    }
}
