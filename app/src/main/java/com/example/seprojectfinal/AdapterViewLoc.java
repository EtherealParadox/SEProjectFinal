package com.example.seprojectfinal;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
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
import java.util.Locale;

public class AdapterViewLoc extends FirebaseRecyclerAdapter<User, AdapterViewLoc.myViewHolderLocate> {

    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    private FirebaseUser user;
    private String userId;

    public AdapterViewLoc(@NonNull FirebaseRecyclerOptions<User> options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolderLocate holder, int position, @NonNull User model) {
        holder.name.setText("Name: " + model.getName());
        holder.contact.setText("Contact: " + model.getContact());
        holder.address.setText("Address: " + model.getAddress());
        holder.destination.setText("Destination: " + model.getDestination());

        holder.btnLoc.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.name.getContext())
                        .setContentHolder(new ViewHolder(R.layout.locate_popup))
                        .setExpanded(true, 1000)
                        .create();

                android.view.View view = dialogPlus.getHolderView();

                EditText location = view.findViewById(R.id.editTextLocationGlideLoc);
                EditText destination = view.findViewById(R.id.editTextDestinationGlideLoc);

                Button btnloc = view.findViewById(R.id.btnLocateLoc);

                location.setText("SM Bataan Terminal Complex");
                destination.setText(model.getDestination());

                dialogPlus.show();

                btnloc.setOnClickListener(new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        user = FirebaseAuth.getInstance().getCurrentUser();
                        reference = FirebaseDatabase.getInstance().getReference("Employees");
                        userId = user.getUid();
                        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                rootNode = FirebaseDatabase.getInstance();
                                reference = rootNode.getReference("EmployeesLogs");
                                UserSignUp userSignUp = snapshot.getValue(UserSignUp.class);
                                String si = "Located " + model.getEmail();
                                String em = userSignUp.email;
                                UserEmployeeStatusLogs userEmployeeStatusLogs = new UserEmployeeStatusLogs(em, getDate(), getTime(), si);
                                String key = reference.push().getKey();
                                reference.child(key).setValue(userEmployeeStatusLogs);

                                String loca = location.getText().toString();
                                String desti = model.getDestination().toString();
                                try{
                                    Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + loca + "/" + desti);
                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                    intent.setPackage("com.google.android.apps.maps");
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    v.getContext().startActivity(intent);
                                }catch (ActivityNotFoundException e){
                                    Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    v.getContext().startActivity(intent);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(holder.name.getContext(),"Something wrong happened", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
            }
        });
    }

    @NonNull
    @Override
    public myViewHolderLocate onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        android.view.View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_design_view_loc, parent, false);
        return new myViewHolderLocate(view);
    }

    class myViewHolderLocate extends RecyclerView.ViewHolder{

        TextView name, contact, address, destination;

        Button btnLoc;

        public myViewHolderLocate(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textViewNameLoc);
            contact = itemView.findViewById(R.id.textViewContactLoc);
            address = itemView.findViewById(R.id.textViewAddressLoc);
            destination = itemView.findViewById(R.id.textViewDestinationLoc);

            btnLoc = itemView.findViewById(R.id.buttonLocLoc);
        }

    }

    private String getTime() {
        return new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
    }

    private String getDate() {
        return new SimpleDateFormat("dd/LLL/yyyy", Locale.getDefault()).format(new Date());
    }
}
