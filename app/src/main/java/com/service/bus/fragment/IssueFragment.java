package com.service.bus.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.service.bus.activity.MainActivity;
import com.service.bus.activity.Payment;
import com.service.bus.watcher.InitialSpaceWatcher;
import com.service.bus.watcher.InitialTextWatcher;
import com.service.bus.watcher.PhoneNumberTextWatcher;
import com.service.dream.bus.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class IssueFragment extends Fragment {

    @BindView(R.id.tv_userid)
    AppCompatTextView tvUserid;
    @BindView(R.id.et_user_id_issue)
    AppCompatEditText etUserIdIssue;
    @BindView(R.id.tv_username)
    AppCompatTextView tvUsername;
    @BindView(R.id.et_name_issue)
    AppCompatEditText etNameIssue;
    @BindView(R.id.tv_source)
    AppCompatTextView tvSource;
    @BindView(R.id.spinner_source_issue)
    AppCompatSpinner spinnerSourceIssue;
    @BindView(R.id.tv_destination)
    AppCompatTextView tvDestination;
    @BindView(R.id.spinner_destination_issue)
    AppCompatSpinner spinnerDestinationIssue;
    @BindView(R.id.tv_period)
    AppCompatTextView tvPeriod;
    @BindView(R.id.spinner_period_issue)
    AppCompatSpinner spinnerPeriodIssue;
    @BindView(R.id.tv_distance)
    AppCompatTextView tvDistance;
    @BindView(R.id.et_distance_issue)
    AppCompatEditText etDistanceIssue;
    @BindView(R.id.tv_age)
    AppCompatTextView tvAge;
    @BindView(R.id.et_age_issue)
    AppCompatEditText etAgeIssue;
    @BindView(R.id.tv_organization_name)
    AppCompatTextView tvOrganizationName;
    @BindView(R.id.et_oraganization_name_issue)
    AppCompatEditText etOraganizationNameIssue;
    @BindView(R.id.tv_depo)
    AppCompatTextView tvDepo;
    @BindView(R.id.spinner_depo_issue)
    AppCompatSpinner spinnerDepoIssue;
    @BindView(R.id.btn_renew_pass)
    AppCompatButton btnRenewPass;
    ImageButton b;
    private static final int GALLREQ=1;
    private static Uri uri;
    public static final int RESULT_OK = -1;
    private static StorageReference storageReference = null;
    FirebaseDatabase database;
    DatabaseReference reff;
    ProgressDialog progressDialog;
    String Org;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.issue_fragment, container, false);
        ButterKnife.bind(this, view);
        insertValuesSpinner();
        progressDialog = new ProgressDialog(getContext());
        database = FirebaseDatabase.getInstance();
        b=(ImageButton)view.findViewById(R.id.imageButton);
        etOraganizationNameIssue=(AppCompatEditText)view.findViewById(R.id.et_oraganization_name_issue);
        btnRenewPass=(AppCompatButton)view.findViewById(R.id.btn_renew_pass);
        validation();
        storageReference= FirebaseStorage.getInstance().getReference();
        SharedPreferences ss=getContext().getSharedPreferences("details", Context.MODE_PRIVATE);
        String UName=ss.getString("UName", null);
        String UID=ss.getString("UserID",null);
        String Cnum = ss.getString("Contact",null);
        etUserIdIssue.setText(UID);
        etNameIssue.setText(UName);

        database.getReference().child("Depo For App").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array
                final List<String> areas = new ArrayList<String>();

                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    String areaName = areaSnapshot.child("DepoName").getValue(String.class);
                    areas.add(areaName);
                }

                Spinner areaSpinner = (Spinner)view.findViewById(R.id.spinner_depo_issue);
                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                areaSpinner.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final SharedPreferences sharedPreferences=getContext().getSharedPreferences("ORGname", Context.MODE_PRIVATE);
         Org=sharedPreferences.getString("Name",null);
        etOraganizationNameIssue.setText(Org);
        btnRenewPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            isDataValid();
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryintent=new Intent(Intent.ACTION_GET_CONTENT);
                galleryintent.setType("Image/*");
                startActivityForResult(galleryintent, GALLREQ);
            }
        });
        return view;

    }

    public void insert()
    {
        progressDialog.setMessage("Saving Details");
        progressDialog.show();
        StorageReference filepath = storageReference.child(uri.getLastPathSegment());
        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                final Uri dwnlduri = taskSnapshot.getDownloadUrl();
                Toast.makeText(getContext(), "Added successfully", Toast.LENGTH_LONG).show();

                String Source=spinnerSourceIssue.getSelectedItem().toString();
                String Destination=spinnerDestinationIssue.getSelectedItem().toString();
                String time=spinnerPeriodIssue.getSelectedItem().toString();
                String Distance=etDistanceIssue.getText().toString();
                String Age=etAgeIssue.getText().toString();
                String Organization=etOraganizationNameIssue.getText().toString();
                String DepoIsuue=spinnerDepoIssue.getSelectedItem().toString();

                reff=database.getReference("New Pass Issue").child(DepoIsuue).push();
                reff.child("User Name").setValue(etNameIssue.getText().toString());
                reff.child("User Id").setValue(etUserIdIssue.getText().toString());
                reff.child("Source").setValue(Source);
                reff.child("Destination").setValue(Destination);
                reff.child("Time Period").setValue(time);
                reff.child("Distance").setValue(Distance);
                reff.child("Age").setValue(Age);
                reff.child("Organization").setValue(Organization);
                assert dwnlduri != null;
                reff.child("Proof").setValue(dwnlduri.toString());

                SharedPreferences sharedPreferences1=getContext().getSharedPreferences("Paymentinfo",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences1.edit();
                editor.putString("Username",etNameIssue.getText().toString());
                editor.putString("User Id",etUserIdIssue.getText().toString());
                editor.putString("Source",Source);
                editor.putString("Destination",Destination);
                editor.putString("Time Period",time);
                editor.putString("Distance",Distance);
                editor.putString("Age",Age);
                editor.putString("Organization",Organization);
                editor.putString("Depo",DepoIsuue);
                editor.commit();

                progressDialog.dismiss();
                Toast.makeText(getContext(),"Information Saved",Toast.LENGTH_LONG).show();
                Intent i=new Intent(getContext(), Payment.class);
                startActivity(i);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GALLREQ && resultCode==RESULT_OK && null != data)
        {

            uri=data.getData();
            b.setImageURI(uri);



        }
    }

    private void onNextClick() {
        if (isDataValid()) {
            Intent loginIntent = new Intent(getContext(), MainActivity.class);
            startActivity(loginIntent);
        }
    }



    private void validation() {
        etUserIdIssue.addTextChangedListener(new InitialSpaceWatcher(etUserIdIssue));
        etNameIssue.addTextChangedListener(new InitialSpaceWatcher(etNameIssue));
        etDistanceIssue.addTextChangedListener(new InitialSpaceWatcher(etDistanceIssue));
        etAgeIssue.addTextChangedListener(new PhoneNumberTextWatcher(etAgeIssue));
        etOraganizationNameIssue.addTextChangedListener(new InitialTextWatcher(etOraganizationNameIssue));
    }

    private void insertValuesSpinner() {
        String[] values_place = {
                "Place 1",
                "Place 2",
                "Place 3",
                "Place 4",
                "Place 5",
                "Place 6",
                "Place 7",
                "Place 8",
                "Place 9",
                "Place 10"};
        ArrayAdapter<String> placeAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values_place);
        placeAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerDestinationIssue.setAdapter(placeAdapter);
        spinnerSourceIssue.setAdapter(placeAdapter);

        String[] values_period = {
                "3 Months",
                "6 Months",
                "9 Months"};
        ArrayAdapter<String> periodAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values_period);
        periodAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerPeriodIssue.setAdapter(periodAdapter);


    }

    private boolean isDataValid() {

        String UserIDIssue = etUserIdIssue.getText().toString().trim();
        String UserNameIssue = etNameIssue.getText().toString().trim();
        String DistanceIssue = etDistanceIssue.getText().toString().toLowerCase().trim();
        String AgeIssue = etAgeIssue.getText().toString().trim();
        String OrganizationNameIssue = etOraganizationNameIssue.getText().toString().trim();

        //Check ID is Empty or not
        if (TextUtils.isEmpty(UserIDIssue)) {
            Toast.makeText(getContext(), "Please Enter ID ", Toast.LENGTH_SHORT).show();
            return false;
        }

        //Check  ID min character
        if (UserIDIssue.length() <= 3) {
            Toast.makeText(getContext(), "Please Enter more than 3 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        //check  Name Empty or not
        if (TextUtils.isEmpty(UserNameIssue)) {
            Toast.makeText(getContext(), "Please Enter Name ", Toast.LENGTH_SHORT).show();
            return false;
        }

        //Check Name min character
        if (UserNameIssue.length() <= 2) {
            Toast.makeText(getContext(), "Please Enter atleast 3 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        //check  Distance Empty or not
        if (TextUtils.isEmpty(DistanceIssue)) {
            Toast.makeText(getContext(), "Please Enter Distance", Toast.LENGTH_SHORT).show();
            return false;
        }

        //Check Age is valid or not
        if (TextUtils.isEmpty(AgeIssue)) {
            Toast.makeText(getContext(), "Please Enter Age", Toast.LENGTH_SHORT).show();
            return false;
        }

        //Check Organization Name is Empty or not
        if (TextUtils.isEmpty(OrganizationNameIssue)) {
            Toast.makeText(getContext(), "Please Enter Organization Name", Toast.LENGTH_SHORT).show();
            return false;
        }

        //Check  Organization Name min character
        if (OrganizationNameIssue.length() <= 3) {
            Toast.makeText(getContext(), "Please Enter more than 3 characters", Toast.LENGTH_SHORT).show();
            return false;
        }

        //check  proof Empty or not
insert();
        return true;
    }




    }
