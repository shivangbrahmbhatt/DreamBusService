package com.service.bus.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.service.dream.bus.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import butterknife.BindView;

public class PaymentSuccess extends AppCompatActivity {

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
    AppCompatTextView spinnerSourceIssue;
    @BindView(R.id.tv_destination)
    AppCompatTextView tvDestination;
    @BindView(R.id.spinner_destination_issue)
    AppCompatTextView spinnerDestinationIssue;
    @BindView(R.id.tv_period)
    AppCompatTextView tvPeriod;
    @BindView(R.id.spinner_period_issue)
    AppCompatTextView spinnerPeriodIssue;
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
    AppCompatTextView spinnerDepoIssue;
    @BindView(R.id.btn_renew_pass)
    AppCompatButton btnRenewPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);

        etUserIdIssue=(AppCompatEditText)findViewById(R.id.et_user_id_issue);
        etNameIssue=(AppCompatEditText)findViewById(R.id.et_name_issue);
        spinnerSourceIssue=(AppCompatTextView)findViewById(R.id.spinner_source_issue);
        spinnerDestinationIssue=(AppCompatTextView)findViewById(R.id.spinner_destination_issue);
        spinnerPeriodIssue=(AppCompatTextView)findViewById(R.id.spinner_period_issue);
        etDistanceIssue=(AppCompatEditText)findViewById(R.id.et_distance_issue);
        etAgeIssue=(AppCompatEditText)findViewById(R.id.et_age_issue);
        etOraganizationNameIssue=(AppCompatEditText)findViewById(R.id.et_oraganization_name_issue);
        spinnerDepoIssue=(AppCompatTextView)findViewById(R.id.spinner_depo_issue);

        takeScreenshot();

        SharedPreferences sharedPreferences1=this.getSharedPreferences("Paymentinfo", Context.MODE_PRIVATE);
       String username=sharedPreferences1.getString("Username", null);
        String UserId=sharedPreferences1.getString("User Id",null);
        String Source=sharedPreferences1.getString("Source",null);
        String Destination=sharedPreferences1.getString("Destination",null);
        String TimePeriod=sharedPreferences1.getString("Time Period",null);
        String Distance=sharedPreferences1.getString("Distance",null);
        String Age=sharedPreferences1.getString("Age",null);

String Depo=sharedPreferences1.getString("Depo",null);

        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("ORGname", MODE_PRIVATE);
        String Organization=sharedPreferences.getString("Name", null);

        etUserIdIssue.setText(UserId);
        etNameIssue.setText(username);
        spinnerSourceIssue.setText(Source);
        spinnerDestinationIssue.setText(Destination);
        spinnerPeriodIssue.setText(TimePeriod);
        etDistanceIssue.setText(Distance);
        etAgeIssue.setText(Age);
        etOraganizationNameIssue.setText(Organization);
        spinnerDepoIssue.setText(Depo);

    }


    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
        }
    }

    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }
}
