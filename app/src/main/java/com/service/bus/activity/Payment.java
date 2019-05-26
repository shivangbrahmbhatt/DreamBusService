package com.service.bus.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.service.dream.bus.R;

import java.util.Calendar;

public class Payment extends AppCompatActivity {

    EditText creditno,cvv,exp;
    Button b;
    private Calendar calendar;

    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        creditno=(EditText)findViewById(R.id.creditno);
        cvv=(EditText)findViewById(R.id.cvv);
        exp=(EditText)findViewById(R.id.exp);
        b=(Button)findViewById(R.id.pay);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (creditno == null || cvv == null || exp == null) {
                    Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(Payment.this, "Payment Done successfully", Toast.LENGTH_LONG).show();
                Intent i = new Intent(Payment.this, PaymentSuccess.class);
                startActivity(i);
            }
        });


    }
    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        exp.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }


}
