package com.service.bus.watcher;

import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;

public class InitialSpaceWatcher implements TextWatcher {
    private AppCompatEditText mEditText;
    private final String WATCHER = " ";

    public InitialSpaceWatcher(AppCompatEditText mEditText) {
        this.mEditText = mEditText;

    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
        String enteredString = s.toString();
        if (enteredString.startsWith(WATCHER)) {
            if (enteredString.length() > 0) {
                mEditText.setText(enteredString.substring(1));
            } else {
                mEditText.setText("");
            }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
