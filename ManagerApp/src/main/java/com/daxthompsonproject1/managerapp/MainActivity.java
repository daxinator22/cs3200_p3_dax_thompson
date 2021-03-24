package com.daxthompsonproject1.managerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.daxthompsonproject1.viewmodels.Verify;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Verify.verifyPhoneApp();
    }
}