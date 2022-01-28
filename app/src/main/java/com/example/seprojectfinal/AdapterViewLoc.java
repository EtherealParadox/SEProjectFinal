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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

public class AdapterViewLoc extends FirebaseRecyclerAdapter<User, AdapterViewLoc.myViewHolderLocate> {

    public AdapterViewLoc(@NonNull FirebaseRecyclerOptions<User> options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolderLocate holder, int position, @NonNull User model) {
        holder.name.setText(model.getName());
        holder.contact.setText(model.getContact());
        holder.email.setText(model.getEmail());

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

                location.setText("Balanga City Bataan");
                destination.setText(model.getDestination());

                dialogPlus.show();

                btnloc.setOnClickListener(new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
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

        TextView name, contact, email;

        Button btnLoc;

        public myViewHolderLocate(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textViewNameLoc);
            contact = itemView.findViewById(R.id.textViewContactLoc);
            email = itemView.findViewById(R.id.textViewEmailLoc);

            btnLoc = itemView.findViewById(R.id.buttonLocLoc);
        }
    }
}
