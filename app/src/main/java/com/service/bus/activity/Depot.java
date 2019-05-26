package com.service.bus.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatRadioButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.service.bus.watcher.InitialSpaceWatcher;
import com.service.bus.watcher.PhoneNumberTextWatcher;
import com.service.dream.bus.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Depot extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference refff,ref3,ref;
    ProgressDialog progressDialog;
    private final String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @BindView(R.id.iv_back_Depo)
    AppCompatImageView ivBackDepo;
    @BindView(R.id.et_Depo_name)
    AppCompatEditText etDepoName;
    @BindView(R.id.et_Depo_address)
    AppCompatEditText etDepoAddress;
    @BindView(R.id.et_Depo_email)
    AppCompatEditText etDepoEmail;
    @BindView(R.id.et_Depo_contact)
    AppCompatEditText etDepoContact;
    @BindView(R.id.rb_Depo_proof)
    AppCompatRadioButton rbDepoProof;
    @BindView(R.id.rb_Depo_others_proof)
    AppCompatRadioButton rbDepoOthersProof;
    @BindView(R.id.btn_Depo_signup)
    AppCompatButton btnDepoSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depo);
        ButterKnife.bind(this);
        validation();
        progressDialog = new ProgressDialog(this);
        database = FirebaseDatabase.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("Depo For App");
        ref3 = FirebaseDatabase.getInstance().getReference("Depo Registered");
        btnDepoSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               isDataValid();

            }
        });
    }

    public void insert()
    {
        refff=database.getReference().child("Depo Registered");
        refff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(etDepoName.getText().toString()).exists()) {
                    Toast.makeText(getApplicationContext(), "Depo Already Registered", Toast.LENGTH_LONG).show();

                } else {
                    ref3 = database.getReference("Depo Registered").child(etDepoName.getText().toString());
                    ref3.child("Depo Address").setValue(etDepoAddress.getText().toString());
                    ref3.child("Depo Email").setValue(etDepoEmail.getText().toString());
                    ref3.child("Depo Contact").setValue(etDepoContact.getText().toString());

                    final DatabaseReference ref2 = ref.push();

                    ref2.child("DepoName").setValue(etDepoName.getText().toString());
                    Toast.makeText(Depot.this, "Depo Registered Successfully", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });



    }

    private void onNextClick() {
        if (isDataValid()) {
            Intent loginIntent = new Intent(Depot.this, MainActivity.class);
            startActivity(loginIntent);
            finish();
        }    }

    private void validation() {
        etDepoName.addTextChangedListener(new InitialSpaceWatcher(etDepoName));
        etDepoAddress.addTextChangedListener(new InitialSpaceWatcher(etDepoAddress));
        etDepoEmail.addTextChangedListener(new InitialSpaceWatcher(etDepoEmail));
        etDepoContact.addTextChangedListener(new PhoneNumberTextWatcher(etDepoContact));
    }

    private boolean isDataValid() {
        String DepoName = etDepoName.getText().toString().trim();
        String DepoAddress = etDepoAddress.getText().toString().trim();
        String DepoEmail = etDepoEmail.getText().toString().toLowerCase().trim();
        String DepoContact = etDepoContact.getText().toString().trim();

        //Check Name is Empty or not
        if (TextUtils.isEmpty(DepoName)) {
            Toast.makeText(this, "Please Enter Depot. Name", Toast.LENGTH_SHORT).show();
            return false;
        }

        //Check  name min character
        if (DepoName.length() <= 3) {
            Toast.makeText(this, "Please Enter more than 3 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        //check  Address Empty or not
        if (TextUtils.isEmpty(DepoAddress)) {
            Toast.makeText(this, "Please Enter Depot. Address", Toast.LENGTH_SHORT).show();
            return false;
        }

        //Check Address min character
        if (DepoAddress.length() <= 2) {
            Toast.makeText(this, "Please Enter atleast 3 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        //check  Email Empty or not
        if (TextUtils.isEmpty(DepoEmail)) {
            Toast.makeText(this, "Please Enter Depot. Email", Toast.LENGTH_SHORT).show();
            return false;
        }

        //Check Email min character
        if (DepoEmail.length() <= 2) {
            Toast.makeText(this, "Please Enter atleast 3 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        //Check Email is valid or not
        if (!DepoEmail.matches(EMAIL_PATTERN)) {
            Toast.makeText(this, "Please Enter valid EMAIL address", Toast.LENGTH_SHORT).show();
            return false;
        }
        //check  contact Empty or not
        if (TextUtils.isEmpty(DepoContact)) {
            Toast.makeText(this, "Please Enter Organization Contact", Toast.LENGTH_SHORT).show();
            return false;
        }

        // verification for mobile number length
        if (DepoContact.length() != 12) {
            Toast.makeText(this, "Please Enter valid Number", Toast.LENGTH_SHORT).show();
            return false;
        }
insert();
        //check  proof Empty or not
        return true;
    }

    @OnClick({R.id.iv_back_Depo, R.id.btn_Depo_signup})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.iv_back_Depo:
                Intent DepoBackIntent = new Intent(Depot.this,Login.class);
                startActivity(DepoBackIntent);
                break;

            case R.id.btn_Depo_signup:
                onNextClick();
                break;
        }
    }
}
