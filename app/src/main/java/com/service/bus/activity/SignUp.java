package com.service.bus.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.service.bus.watcher.InitialSpaceWatcher;
import com.service.bus.watcher.InitialTextWatcher;
import com.service.bus.watcher.PhoneNumberTextWatcher;
import com.service.dream.bus.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SignUp extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reff;
    ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    private final String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @BindView(R.id.et_firstname_signup)
    AppCompatEditText etFirstnameSignup;
    @BindView(R.id.et_Lastname_signup)
    AppCompatEditText etLastnameSignup;
    @BindView(R.id.et_password_signup)
    AppCompatEditText etPasswordSignup;
    @BindView(R.id.et_confirmpassword_signup)
    AppCompatEditText etConfirmpasswordSignup;
    @BindView(R.id.et_contactno_signup)
    AppCompatEditText etContactnoSignup;
    @BindView(R.id.et_email_signup)
    AppCompatEditText etEmailSignup;
    @BindView(R.id.rb_male)
    AppCompatRadioButton rbMale;
    @BindView(R.id.rb_female)
    AppCompatRadioButton rbFemale;
    @BindView(R.id.rb_other)
    AppCompatRadioButton rbOther;
    @BindView(R.id.rd_User)
    AppCompatRadioButton rdUser;
    @BindView(R.id.rb_organization)
    AppCompatRadioButton rbOrganization;
    @BindView(R.id.rb_Admin_signup)
    AppCompatRadioButton rbAdminSignup;
    @BindView(R.id.rb_Depo_signup)
    AppCompatRadioButton rbDepoSignup;
    @BindView(R.id.btn_signup)
    AppCompatButton btnSignup;
    @BindView(R.id.tv_back_to_login_signup)
    AppCompatTextView tvBackToLoginSignup;
    @BindView(R.id.tv_gendet)
    AppCompatTextView tvGendet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        validation();
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDataValid();

            }
        });

        tvBackToLoginSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backtologinintent = new Intent(SignUp.this, Login.class);
                startActivity(backtologinintent);
            }
        });


    }

    public void logged()
    {
        String FName = etFirstnameSignup.getText().toString();
        String LName=etLastnameSignup.getText().toString();
        String passwd = etPasswordSignup.getText().toString();
        String num = etContactnoSignup.getText().toString();
        String Email = etEmailSignup.getText().toString();
        String gender;
        String belong;
        if(rbMale.isChecked())
        {
             gender="Male" ;
        }else if(rbFemale.isChecked()){
             gender="Female" ;
        }else{
             gender="Other" ;
        }

        if(rdUser.isChecked())
        {
             belong="User";
        }else if(rbOrganization.isChecked()){
             belong="Organization";
        }else{
             belong="Depot Department";
        }
          Toast.makeText(getApplicationContext(),gender+" "+belong,Toast.LENGTH_LONG).show();
        insertintodb(FName, LName, passwd, num, Email, gender, belong);
        if(rdUser.isSelected())
        {
            startActivity(new Intent(SignUp.this, Login.class));
        }else if(rbOrganization.isSelected()){
            startActivity(new Intent(SignUp.this, Login.class));
        }else{
            startActivity(new Intent(SignUp.this, Login.class));
        }

    }

    public void next()
    {
        Authentication();
    }

    public void insertintodb(String Fname,String Lname,String passwd,String mob,String Email,String gender,String belong)
    {

        reff=database.getReference("Users").child(mob);
        reff.child("FName").setValue(Fname);
        reff.child("LName").setValue(Lname);
        reff.child("Mobile number").setValue(mob);
        reff.child("Email").setValue(Email);
        reff.child("Password").setValue(passwd);
        reff.child("gender").setValue(gender);
        reff.child("belong").setValue(belong);

        Intent i=new Intent(getApplicationContext(),Login.class);
        startActivity(i);
    }


    private void Authentication()
    {
        String Email=etEmailSignup.getText().toString().trim();
        String passwd=etPasswordSignup.getText().toString().trim();
        Toast.makeText(getApplicationContext(),Email+" "+passwd,Toast.LENGTH_LONG).show();
        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(Email, passwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {

                            Toast.makeText(SignUp.this, "Your account is created", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();

                            logged();


                        } else {
                            //display some message here
                            progressDialog.dismiss();
                            Toast.makeText(SignUp.this, "Email already registered or no Internet connection", Toast.LENGTH_LONG).show();

                        }

                    }
                });
    }



    private void validation() {
        etFirstnameSignup.addTextChangedListener(new InitialSpaceWatcher(etFirstnameSignup));
        etLastnameSignup.addTextChangedListener(new InitialSpaceWatcher(etLastnameSignup));
        etPasswordSignup.addTextChangedListener(new InitialSpaceWatcher(etPasswordSignup));
        etConfirmpasswordSignup.addTextChangedListener(new InitialSpaceWatcher(etConfirmpasswordSignup));
        etContactnoSignup.addTextChangedListener(new PhoneNumberTextWatcher(etContactnoSignup));
        etContactnoSignup.addTextChangedListener(new InitialTextWatcher(etContactnoSignup));
        etEmailSignup.addTextChangedListener(new InitialTextWatcher(etEmailSignup));
    }


    private boolean isDataValid() {
        String FirstName = etFirstnameSignup.getText().toString().trim();
        String LastName = etLastnameSignup.getText().toString().trim();
        String EmailSignup = etEmailSignup.getText().toString().toLowerCase().trim();
        String ConfirmPassword = etConfirmpasswordSignup.getText().toString().trim();
        String PasswordSignup = etPasswordSignup.getText().toString().trim();
        String ContactNoSignup = etContactnoSignup.getText().toString().trim();


        //Check First Name is Empty or not
        if (TextUtils.isEmpty(FirstName)) {
            Toast.makeText(this, "Please Enter First Name", Toast.LENGTH_SHORT).show();
            return false;
        }

        //Check  First name min character
        if (FirstName.length() <= 3) {
            Toast.makeText(this, "Please Enter more than 3 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        //check  LAst Name Empty or not
        if (TextUtils.isEmpty(LastName)) {
            Toast.makeText(this, "Please Enter Last Name", Toast.LENGTH_SHORT).show();
            return false;
        }

        //Check Last Name min character
        if (LastName.length() <= 2) {
            Toast.makeText(this, "Please Enter atleast 3 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        //check  Password Empty or not
        if (TextUtils.isEmpty(PasswordSignup)) {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        //Check Password min character
        if (PasswordSignup.length() <= 6) {
            Toast.makeText(this, "Please Enter atleast 3 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        //check  Confirm Password Empty or not
        if (TextUtils.isEmpty(ConfirmPassword)) {
            Toast.makeText(this, "Please Enter Confirm Password", Toast.LENGTH_SHORT).show();
            return false;
        }

        //Check Confirm Password min character
        if (ConfirmPassword.length() <= 6) {
            Toast.makeText(this, "Please Enter atleast 3 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        //Password matches or not
        if (!etPasswordSignup.getText().toString().equals(etConfirmpasswordSignup.getText().toString())) {
            Toast.makeText(this, "Password Does not match!", Toast.LENGTH_SHORT).show();
            return false;
        }
        //check  Contact Empty or not
        if (TextUtils.isEmpty(ContactNoSignup)) {
            Toast.makeText(this, "Please Enter ContactNo.", Toast.LENGTH_SHORT).show();
            return false;
        }
        // verification for mobile number length
        if (ContactNoSignup.length() != 12) {
            Toast.makeText(this, "Please Enter valid Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        //check  Email Empty or not
        if (TextUtils.isEmpty(EmailSignup)) {
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return false;
        }

        //Check Email min character
        if (EmailSignup.length() <= 2) {
            Toast.makeText(this, "Please Enter atleast 3 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        //Check Email is valid or not
        if (!EmailSignup.matches(EMAIL_PATTERN)) {
            Toast.makeText(this, "Please Enter valid EMAIL address", Toast.LENGTH_SHORT).show();
            return false;
        }

        //check  proof Empty or not
        next();
        return true;
    }



}
