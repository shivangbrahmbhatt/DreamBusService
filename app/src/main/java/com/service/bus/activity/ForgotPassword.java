package com.service.bus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.service.bus.watcher.InitialSpaceWatcher;
import com.service.dream.bus.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotPassword extends AppCompatActivity {


    private final String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @BindView(R.id.iv_forgot_back)
    AppCompatImageView ivForgotBack;
    @BindView(R.id.et_forgot_email)
    AppCompatEditText etForgotEmail;
    @BindView(R.id.btn_request_password)
    AppCompatButton btnRequestPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        validation();
    }

    private void validation() {
        etForgotEmail.addTextChangedListener(new InitialSpaceWatcher(etForgotEmail));
    }

    private boolean isDataValid() {
        String email = etForgotEmail.getText().toString().toLowerCase().trim();
        //Check Email is Empty or not
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please Enter Email ID", Toast.LENGTH_SHORT).show();
            return false;
        }

        //Check Email  min character
        if (email.length() <= 3) {
            Toast.makeText(this, "Please Enter more than 3 characters", Toast.LENGTH_SHORT).show();
            return false;
        }

        //Check Email is valid or not
        if (!email.matches(EMAIL_PATTERN)) {
            Toast.makeText(this, "Please Enter valid EMAIL address", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void onNextClick() {
        if (isDataValid()) {
            Intent intent = new Intent(ForgotPassword.this, FirstScreen.class);
            startActivity(intent);
            finish();
        }
    }


    @OnClick({R.id.iv_forgot_back, R.id.btn_request_password})

    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.iv_forgot_back:
                Intent intent = new Intent(ForgotPassword.this, FirstScreen.class);
                startActivity(intent);
                finish();
                break;

            case R.id.btn_request_password:
                onNextClick();
                break;
        }
    }
}
