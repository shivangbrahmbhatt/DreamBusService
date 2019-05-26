package com.service.bus.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.service.dream.bus.R;

public class RegisteredOrganizations extends AppCompatActivity {
    FirebaseDatabase f;
    DatabaseReference ref,ref2;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_organizations);
        f=FirebaseDatabase.getInstance();

        recyclerView=(RecyclerView)findViewById(R.id.listView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        f= FirebaseDatabase.getInstance();
        ref=f.getReference().child("Organizations For App");


        final FirebaseRecyclerAdapter<pojodrop,foodviewholder> FBRA=new FirebaseRecyclerAdapter<pojodrop, foodviewholder>(
                pojodrop.class,
                R.layout.pickupview,
                foodviewholder.class,
                ref
        ){
            @Override
            public foodviewholder onCreateViewHolder(ViewGroup parent, int viewType) {


                return super.onCreateViewHolder(parent, viewType);
            }

            @Override
            protected void populateViewHolder(final foodviewholder viewHolder, final pojodrop model, final int position) {

                viewHolder.r.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = model.getOrganizationName();

                        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("ORGname",MODE_PRIVATE);
                        SharedPreferences.Editor editor= sharedPreferences.edit();
                        editor.putString("Name",name);
                        editor.commit();

                        SharedPreferences ss=getApplicationContext().getSharedPreferences("details", MODE_PRIVATE);
                        String UName=ss.getString("UName", null);
                        String UID=ss.getString("UserID",null);
                        String Cnum=ss.getString("Contact",null);

                        ref2=f.getReference("OrganizationsVerification").child(name).push();
                        ref2.child("Username").setValue(UName);
                        ref2.child("User ID").setValue(UID);
                        ref2.child("Number").setValue(Cnum);
                        Toast.makeText(getApplicationContext(),"Details send for verification",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(RegisteredOrganizations.this, MainActivity.class);
                        startActivity(i);
                    }
                });
                viewHolder.setOrganizationName(model.getOrganizationName());
                //  viewHolder.setdate(model.getdate());

            }
        };
        recyclerView.setAdapter(FBRA);


    }


    public static class foodviewholder extends RecyclerView.ViewHolder
    {
        View mview;
        RadioButton r;

        public foodviewholder(View itemView) {
            super(itemView);
            mview=itemView;
            r=(RadioButton)mview.findViewById(R.id.r1);
        }
        public void setOrganizationName(String name)
        {

            r.setText(name);
        }


    }
}
