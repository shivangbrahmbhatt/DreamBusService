package com.service.bus.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.service.bus.activity.Login;
import com.service.bus.activity.Payment;
import com.service.bus.watcher.InitialSpaceWatcher;
import com.service.dream.bus.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RenewFragment extends Fragment {

    @BindView(R.id.tv_userid)
    AppCompatTextView tvUserid;
    @BindView(R.id.et_id_renew)
    AppCompatEditText etIdRenew;
    @BindView(R.id.tv_source)
    AppCompatTextView tvSource;
    @BindView(R.id.spinner_source_renew)
    AppCompatSpinner spinnerSourceRenew;
    @BindView(R.id.tv_destination)
    AppCompatTextView tvDestination;
    @BindView(R.id.spinner_destination_renew)
    AppCompatSpinner spinnerDestinationRenew;
    @BindView(R.id.tv_period)
    AppCompatTextView tvPeriod;
    @BindView(R.id.spinner_period_renew)
    AppCompatSpinner spinnerPeriodRenew;
    @BindView(R.id.tv_distance)
    AppCompatTextView tvDistance;
    @BindView(R.id.et_distance_renew)
    AppCompatEditText etDistanceRenew;
    @BindView(R.id.btn_renew_pass)
    AppCompatButton btnRenewPass;
    @BindView(R.id.tv_depo)
    AppCompatTextView tvDepo;
    @BindView(R.id.spinner_depo_renew)
    AppCompatSpinner spinnerDepoRenew;
    FirebaseDatabase database;
    DatabaseReference reff,refff;
    ProgressDialog progressDialog;
    String Org,Age,Proof,UID,UName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.renew_fragment, container, false);
        ButterKnife.bind(this, view);
        insertValueSpinner();
        SharedPreferences ss=getContext().getSharedPreferences("details", Context.MODE_PRIVATE);
        progressDialog = new ProgressDialog(getContext());
        database = FirebaseDatabase.getInstance();
         UID=ss.getString("UserID", null);
         UName=ss.getString("UName", null);
        String Cnum=ss.getString("Contact",null);
        etIdRenew.setText(UID);

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

                Spinner areaSpinner = (Spinner) view.findViewById(R.id.spinner_depo_renew);
                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                areaSpinner.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnRenewPass=(AppCompatButton)view.findViewById(R.id.btn_renew_pass);
        validation();




        btnRenewPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              isDataValid();
            }
        });

        return view;
    }

    public void insert(){
        progressDialog.setMessage("Saving Details");
        progressDialog.show();
        refff=database.getReference().child("New Pass Issue");
        refff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                Org = dataSnapshot.child("Organization").getValue(String.class);
                Age = dataSnapshot.child("Age").getValue(String.class);
                Proof=dataSnapshot.child("Proof").getValue(String.class);




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });


        String Source=spinnerSourceRenew.getSelectedItem().toString();
        String Destination=spinnerDestinationRenew.getSelectedItem().toString();
        String time=spinnerPeriodRenew.getSelectedItem().toString();
        String Distance=etDistanceRenew.getText().toString();

        String DepoIsuue=spinnerDepoRenew.getSelectedItem().toString();

        reff=database.getReference("ReNew Pass Issue").child(DepoIsuue).push();
        reff.child("User Name").setValue(UName);
        reff.child("User Id").setValue(UID);
        reff.child("Source").setValue(Source);
        reff.child("Destination").setValue(Destination);
        reff.child("Time Period").setValue(time);
        reff.child("Distance").setValue(Distance);
        reff.child("Organization").setValue(Org);
        reff.child("Age").setValue(Age);
        reff.child("Proof").setValue(Proof);

        SharedPreferences sharedPreferences1=getContext().getSharedPreferences("Paymentinfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences1.edit();
        editor.putString("Username",UName);
        editor.putString("User Id",UID);
        editor.putString("Source",Source);
        editor.putString("Destination",Destination);
        editor.putString("Time Period",time);
        editor.putString("Distance",Distance);
        editor.putString("Age",Age);
        editor.putString("Organization",Org);
        editor.putString("Depo",DepoIsuue);
        editor.commit();
        Toast.makeText(getContext(),"Information Saved",Toast.LENGTH_LONG).show();
        progressDialog.dismiss();
        Intent i=new Intent(getContext(), Payment.class);
        startActivity(i);
    }

    private void onNextClick() {
        if (isDataValid()) {
            Intent loginIntent = new Intent(getContext(), Login.class);
            startActivity(loginIntent);
        }
    }

    private void validation() {
        etIdRenew.addTextChangedListener(new InitialSpaceWatcher(etIdRenew));
        etDistanceRenew.addTextChangedListener(new InitialSpaceWatcher(etDistanceRenew));

    }

    private boolean isDataValid() {

        String UserIDRenew = etIdRenew.getText().toString().trim();
        String DistanceRenew = etDistanceRenew.getText().toString().trim();

        //Check ID is Empty or not
        if (TextUtils.isEmpty(UserIDRenew)) {
            Toast.makeText(getContext(), "Please Enter ID ", Toast.LENGTH_SHORT).show();
            return false;
        }

        //Check  ID min character
        if (UserIDRenew.length() <= 3) {
            Toast.makeText(getContext(), "Please Enter more than 3 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        //check  Distance Empty or not
        if (TextUtils.isEmpty(DistanceRenew)) {
            Toast.makeText(getContext(), "Please Enter Distance ", Toast.LENGTH_SHORT).show();
            return false;
        }

        //check  proof Empty or not
insert();
        return true;
    }

    private void insertValueSpinner() {

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
        spinnerDestinationRenew.setAdapter(placeAdapter);
        spinnerSourceRenew.setAdapter(placeAdapter);

        String[] values_period = {
                "3 Months",
                "6 Months",
                "9 Months"};
        ArrayAdapter<String> periodAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values_period);
        periodAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerPeriodRenew.setAdapter(periodAdapter);


    }

    @OnClick(R.id.btn_renew_pass)
    public void onViewClicked() {
        onNextClick();

    }

}