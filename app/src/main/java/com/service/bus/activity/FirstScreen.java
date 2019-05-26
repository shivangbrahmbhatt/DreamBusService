package com.service.bus.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;


import com.service.dream.bus.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FirstScreen extends AppCompatActivity {


    @BindView(R.id.btn_user)
    AppCompatButton btnUser;
    @BindView(R.id.btn_organization)
    AppCompatButton btnOrganization;
    @BindView(R.id.btn_depo)
    AppCompatButton btnDepo;

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        ButterKnife.bind(this);
        if (checkAndRequestPermissions()) {
            // carry on the normal flow, as the case of  permissions  granted.
        }
    }


    private  boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS);

        int receiveSMS = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS);

        int readSMS = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (receiveSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_MMS);
        }
        if (readSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

        @OnClick({R.id.btn_user, R.id.btn_organization, R.id.btn_depo})
        public void onViewClicked (View view){
        switch (view.getId()) {

            case R.id.btn_user:
                Intent userIntent = new Intent(FirstScreen.this, Login.class);
                startActivity(userIntent);
                finish();
                break;

            case R.id.btn_organization:
                Intent organizationIntent = new Intent(FirstScreen.this, Login.class);
                startActivity(organizationIntent);
                finish();
                break;

            case R.id.btn_depo:
                Intent depoIntent = new Intent(FirstScreen.this, Login.class);
                startActivity(depoIntent);
                finish();
                break;
        }
    }

}
