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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AdapterViewEmp extends FirebaseRecyclerAdapter<User, AdapterViewEmp.myDataHolderEmp> {

    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    private FirebaseUser user;
    private String userId;

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

                EditText temp = view.findViewById(R.id.editTextTemperatureGlideEmp);
                EditText name = view.findViewById(R.id.editTextNameGlideEmp);
                EditText email = view.findViewById(R.id.editTextEmailGlideEmp);
                EditText address = view.findViewById(R.id.editTextAddressGlideEmp);
                EditText age = view.findViewById(R.id.editTextAgeGlideEmp);
                EditText gender = view.findViewById(R.id.editTextGenderGlideEmp);
                EditText symp = view.findViewById(R.id.editTextSymptomsGlideEmp);
                EditText contact = view.findViewById(R.id.editTextNumberGlideEmp);
                EditText destination = view.findViewById(R.id.editTextDestinationGlideEmp);


                Button btnUpdate = view.findViewById(R.id.btnUpdateEmp);

                temp.setText(model.getTemperature());
                name.setText(model.getName());
                email.setText(model.getEmail());
                address.setText(model.getAddress());
                age.setText(model.getAge());
                gender.setText(model.getGender());
                symp.setText(model.getSymptoms());
                contact.setText(model.getContact());
                destination.setText(model.getDestination());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("temperature", temp.getText().toString());
                        map.put("name", name.getText().toString());
                        map.put("email", email.getText().toString());
                        map.put("address", address.getText().toString());
                        map.put("age", age.getText().toString());
                        map.put("gender", gender.getText().toString());
                        map.put("symptoms", symp.getText().toString());
                        map.put("contact", contact.getText().toString());
                        map.put("destination", destination.getText().toString());


                        FirebaseDatabase.getInstance().getReference().child("Visitors")
                                .child(getRef(holder.getAdapterPosition()).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        user = FirebaseAuth.getInstance().getCurrentUser();
                                        reference = FirebaseDatabase.getInstance().getReference("Employees");
                                        userId = user.getUid();
                                        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                rootNode = FirebaseDatabase.getInstance();
                                                reference = rootNode.getReference("EmployeesLogs");
                                                UserSignUp userSignUp = snapshot.getValue(UserSignUp.class);
                                                String si = "Edited " + model.getEmail();
                                                String em = userSignUp.email;
                                                UserEmployeeStatusLogs userEmployeeStatusLogs = new UserEmployeeStatusLogs(em, getDate(), getTime(), si);
                                                String key = reference.push().getKey();
                                                reference.child(key).setValue(userEmployeeStatusLogs);

                                                Toast.makeText(holder.name.getContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                                                dialogPlus.dismiss();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                Toast.makeText(holder.name.getContext(),"Something wrong happened", Toast.LENGTH_SHORT).show();
                                                dialogPlus.dismiss();
                                            }
                                        });


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
                        user = FirebaseAuth.getInstance().getCurrentUser();
                        reference = FirebaseDatabase.getInstance().getReference("Employees");
                        userId = user.getUid();
                        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                rootNode = FirebaseDatabase.getInstance();
                                reference = rootNode.getReference("EmployeesLogs");
                                UserSignUp userSignUp = snapshot.getValue(UserSignUp.class);
                                String si = "Deleted " + model.getEmail();
                                String em = userSignUp.email;
                                UserEmployeeStatusLogs userEmployeeStatusLogs = new UserEmployeeStatusLogs(em, getDate(), getTime(), si);
                                String key = reference.push().getKey();
                                reference.child(key).setValue(userEmployeeStatusLogs);

                                FirebaseDatabase.getInstance().getReference().child("Visitors")
                                        .child(getRef(holder.getAdapterPosition()).getKey())
                                        .removeValue();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(holder.name.getContext(),"Something wrong happened", Toast.LENGTH_SHORT).show();
                            }
                        });

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

    private String getTime() {
        return new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
    }

    private String getDate() {
        return new SimpleDateFormat("dd/LLL/yyyy", Locale.getDefault()).format(new Date());
    }
}
