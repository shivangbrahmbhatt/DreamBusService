package com.service.bus.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.service.dream.bus.R;

import butterknife.BindView;

public class Verification extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference reff;
    ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @BindView(R.id.et_userid)
    AppCompatEditText etuserid;
    @BindView(R.id.et_login_username)
    AppCompatTextView etusername;
    @BindView(R.id.et_contactno_signup)
    AppCompatTextView etcontactno;
    @BindView(R.id.btn_login)
    AppCompatButton tvSignUp;
    String Fname,Lname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        etcontactno=(AppCompatTextView)findViewById(R.id.et_contactno_signup);
        etusername=(AppCompatTextView)findViewById(R.id.et_login_username);
        etuserid=(AppCompatEditText)findViewById(R.id.et_userid);
        tvSignUp=(AppCompatButton)findViewById(R.id.btn_login);
        progressDialog = new ProgressDialog(this);
        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        SharedPreferences s=getSharedPreferences("Pref",MODE_PRIVATE);
        final String Number=s.getString("Number", null);
        etcontactno.setText(Number);
        reff=database.getReference().child("Users");
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                Fname = dataSnapshot.child(Number).child("FName").getValue(String.class);
                Lname = dataSnapshot.child(Number).child("LName").getValue(String.class);

                etusername.setText(Fname + " " + Lname);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences s=getApplicationContext().getSharedPreferences("details",MODE_PRIVATE);
                SharedPreferences.Editor editor=s.edit();
                editor.putString("UName",Fname+" "+Lname);
                editor.putString("Contact",Number);
                editor.putString("UserID",etuserid.getText().toString());
                editor.commit();
Intent i=new Intent(Verification.this,RegisteredOrganizations.class);
                startActivity(i);
            }
        });
    }
}
