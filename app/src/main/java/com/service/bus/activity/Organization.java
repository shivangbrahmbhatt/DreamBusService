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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.service.bus.watcher.InitialSpaceWatcher;
import com.service.bus.watcher.InitialTextWatcher;
import com.service.bus.watcher.PhoneNumberTextWatcher;
import com.service.dream.bus.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Organization extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference ref,reff,ref3,refff;
    ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private final String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @BindView(R.id.iv_back_organization)
    AppCompatImageView ivBackOrganization;
    @BindView(R.id.et_organisation_name)
    AppCompatEditText etOrganisationName;
    @BindView(R.id.et_organisation_address)
    AppCompatEditText etOrganisationAddress;
    @BindView(R.id.et_organisation_email)
    AppCompatEditText etOrganisationEmail;
    @BindView(R.id.et_organisation_contact)
    AppCompatEditText etOrganisationContact;
    @BindView(R.id.rb_organisation_proof)
    AppCompatRadioButton rbOrganisationProof;
    @BindView(R.id.rb_organisation_others_proof)
    AppCompatRadioButton rbOrganisationOthersProof;
    @BindView(R.id.btn_organisation_signup)
    AppCompatButton btnOrganisationSignup;
String Name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization);
        ButterKnife.bind(this);
        validation();
        database = FirebaseDatabase.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("Organizations For App");
        ref3 = FirebaseDatabase.getInstance().getReference("Organizations Registered");



        btnOrganisationSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name=etOrganisationName.getText().toString();
             isDataValid();



            }
        });

    }
public void insert() {

    refff=database.getReference().child("Organizations Registered");
    refff.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            if (dataSnapshot.child(etOrganisationName.getText().toString()).exists()) {
                Toast.makeText(getApplicationContext(), "Organization Already Registered", Toast.LENGTH_LONG).show();

            } else {
                final DatabaseReference ref2 = ref.push();

                ref2.child("OrganizationName").setValue(etOrganisationName.getText().toString());
                ref2.child("OrganizationAddress").setValue(etOrganisationAddress.getText().toString());
                ref2.child("OrganizationEmail").setValue(etOrganisationEmail.getText().toString());
                ref2.child("OrganizationContact").setValue(etOrganisationContact.getText().toString());

                ref3 = database.getReference("Organizations Registered").child(Name);
                ref3.child("OrganizationAddress").setValue(etOrganisationAddress.getText().toString());
                ref3.child("OrganizationEmail").setValue(etOrganisationEmail.getText().toString());
                ref3.child("OrganizationContact").setValue(etOrganisationContact.getText().toString());

                Toast.makeText(Organization.this, "Organization Registered Successfully", Toast.LENGTH_SHORT).show();
            }


        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }


    });




}
    private void onNextClick() {
        if (isDataValid()) {
            Intent loginIntent = new Intent(Organization.this, MainActivity.class);
            startActivity(loginIntent);
            finish();
        }
    }

    private void validation() {
        etOrganisationName.addTextChangedListener(new InitialSpaceWatcher(etOrganisationName));
        etOrganisationAddress.addTextChangedListener(new InitialSpaceWatcher(etOrganisationAddress));
        etOrganisationEmail.addTextChangedListener(new InitialSpaceWatcher(etOrganisationEmail));
        etOrganisationContact.addTextChangedListener(new PhoneNumberTextWatcher(etOrganisationContact));
        etOrganisationContact.addTextChangedListener(new InitialTextWatcher(etOrganisationContact));
    }

    private boolean isDataValid() {
        String OrganizationName = etOrganisationName.getText().toString().trim();
        String OrganizationAddress = etOrganisationAddress.getText().toString().trim();
        String OrganizationEmail = etOrganisationEmail.getText().toString().toLowerCase().trim();
        String OrganizationContact = etOrganisationContact.getText().toString().trim();

        //Check Name is Empty or not
        if (TextUtils.isEmpty(OrganizationName)) {
            Toast.makeText(this, "Please Enter Organization Name", Toast.LENGTH_SHORT).show();
            return false;
        }

        //Check  name min character
        if (OrganizationName.length() <= 3) {
            Toast.makeText(this, "Please Enter more than 3 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        //check  Address Empty or not
        if (TextUtils.isEmpty(OrganizationAddress)) {
            Toast.makeText(this, "Please Enter Organization Address", Toast.LENGTH_SHORT).show();
            return false;
        }

        //Check Address min character
        if (OrganizationAddress.length() <= 2) {
            Toast.makeText(this, "Please Enter atleast 3 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        //check  Email Empty or not
        if (TextUtils.isEmpty(OrganizationEmail)) {
            Toast.makeText(this, "Please Enter Organization Email", Toast.LENGTH_SHORT).show();
            return false;
        }

        //Check Email min character
        if (OrganizationEmail.length() <= 2) {
            Toast.makeText(this, "Please Enter atleast 3 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        //Check Email is valid or not
        if (!OrganizationEmail.matches(EMAIL_PATTERN)) {
            Toast.makeText(this, "Please Enter valid EMAIL address", Toast.LENGTH_SHORT).show();
            return false;
        }
        //check  contact Empty or not
        if (TextUtils.isEmpty(OrganizationContact)) {
            Toast.makeText(this, "Please Enter Organization Contact", Toast.LENGTH_SHORT).show();
            return false;
        }

        // verification for mobile number length
        if (OrganizationContact.length() != 12) {
            Toast.makeText(this, "Please Enter valid Number", Toast.LENGTH_SHORT).show();
            return false;
        }

        //check  proof Empty or not
insert();
        return true;
    }

    @OnClick({R.id.iv_back_organization, R.id.btn_organisation_signup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back_organization:
                Intent backIntent = new Intent(Organization.this, FirstScreen.class);
                startActivity(backIntent);
                finish();
                break;
            case R.id.btn_organisation_signup:
                onNextClick();
                break;
        }
    }
}
