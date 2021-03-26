package com.daxthompsonproject1.clientapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

public class SignInActivity extends AppCompatActivity {

    private ClientViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        this.viewModel = new ViewModelProvider(this).get(ClietViewModel.class);
    }
}