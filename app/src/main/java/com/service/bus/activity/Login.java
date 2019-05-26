package com.service.bus.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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

public class Login extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reff;
    ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @BindView(R.id.iv_login_back)
    AppCompatImageView ivLoginBack;
    @BindView(R.id.et_login_username)
    AppCompatEditText etLoginUsername;
    @BindView(R.id.et_login_password)
    AppCompatEditText etLoginPassword;
    @BindView(R.id.tv_forgot_password)
    TextView tvForgotPassword;
    @BindView(R.id.btn_login)
    AppCompatButton btnLogin;
    @BindView(R.id.et_contactno_signup)
    AppCompatEditText etContactnoSignup;
    @BindView(R.id.tv_sign_up)
    AppCompatTextView tvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        validation();
        progressDialog = new ProgressDialog(this);
        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               isDataValid();
            }
        });

    }

    public void insert(){
        SharedPreferences s=getSharedPreferences("Pref",MODE_PRIVATE);
        SharedPreferences.Editor editor=s.edit();
        editor.putString("Number",etContactnoSignup.getText().toString());
        editor.commit();

        reff=database.getReference().child("Users");
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(etContactnoSignup.getText().toString()).exists()) {
                    String email = dataSnapshot.child(etContactnoSignup.getText().toString()).child("Email").getValue(String.class);
                    String belong = dataSnapshot.child(etContactnoSignup.getText().toString()).child("belong").getValue(String.class);
                    if (etLoginUsername.getText().toString().equals(email)) {
                        Toast.makeText(getApplicationContext(),belong,Toast.LENGTH_LONG).show();
                        next(belong);
                    } else {
                        Toast.makeText(getApplicationContext(), "Email not registered", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "This number is not Registered", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

    }

    public void next(String belong)
    {
        authenticate(belong);
    }

    public void authenticate(final String belong)
    {
        progressDialog.setMessage("Checking Credentials");
        progressDialog.show();
        final String Email=etLoginUsername.getText().toString().trim();
        String Pass=etLoginPassword.getText().toString().trim();
        //logging in the user

        firebaseAuth.signInWithEmailAndPassword(Email, Pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()) {

                            Login.this.finish();
                            nextactivity(belong);



                        } else
                            Toast.makeText(getApplicationContext(), "Please Register First", Toast.LENGTH_SHORT).show();
                    }
                });


    }


    public void nextactivity(String belong)
    {
        if (belong.equals("User") ) {
            Intent i = new Intent(Login.this, Verification.class);
            Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
            startActivity(i);
        } else if (belong.equals("Organization") ) {
            Intent i = new Intent(Login.this, Organization.class);
            Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
            startActivity(i);
        } else {
            Intent i = new Intent(Login.this, Depot.class);
            Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
            startActivity(i);
        }
    }



    @OnClick({R.id.iv_login_back, R.id.tv_forgot_password, R.id.btn_login, R.id.tv_sign_up})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.iv_login_back:
                Intent backIntent = new Intent(Login.this, FirstScreen.class);
                startActivity(backIntent);
                finish();
                break;

            case R.id.tv_forgot_password:
                Intent forgotIntent = new Intent(Login.this, ForgotPassword.class);
                startActivity(forgotIntent);
                finish();
                break;

            case R.id.btn_login:
                onNextClick();
                break;

            case R.id.tv_sign_up:
                Intent signupintent = new Intent(Login.this, SignUp.class);
                startActivity(signupintent);

                break;
        }

    }

    private void onNextClick() {
        if (isDataValid()) {
            Intent loginIntent = new Intent(Login.this, MainActivity.class);
            startActivity(loginIntent);
            finish();
        }
    }

    private void validation() {
        etLoginUsername.addTextChangedListener(new InitialSpaceWatcher(etLoginUsername));
        etLoginPassword.addTextChangedListener(new InitialSpaceWatcher(etLoginPassword));
        etContactnoSignup.addTextChangedListener(new PhoneNumberTextWatcher(etContactnoSignup));
        etContactnoSignup.addTextChangedListener(new InitialTextWatcher(etContactnoSignup));
    }

    private boolean isDataValid() {
        String username = etLoginUsername.getText().toString().trim();
        String password = etLoginPassword.getText().toString().trim();

        //Check First Name is Empty or not
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please Enter User Name", Toast.LENGTH_SHORT).show();
            return false;
        }

        //Check First name min character
        if (username.length() <= 3) {
            Toast.makeText(this, "Please Enter more than 3 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        //check Last Name Empty or not
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return false;
        }

        //Check Last name min character
        if (password.length() <= 2) {
            Toast.makeText(this, "Please Enter atleast 3 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
insert();
        return true;
    }

}
