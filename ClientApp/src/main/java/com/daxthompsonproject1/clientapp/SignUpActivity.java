package com.daxthompsonproject1.clientapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.daxthompsonproject1.viewmodels.viewmodels.ClientViewModel;

public class SignUpActivity extends AppCompatActivity {

    private ClientViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        this.viewModel = new ViewModelProvider(this).get(ClientViewModel.class);

//        AppCompatEditText email = findViewById(R.id.email);
//        AppCompatEditText password = findViewById(R.id.password);
//        AppCompatEditText displayName = findViewById(R.id.displayName);
//        AppCompatEditText company = findViewById(R.id.company);
//
//        AppCompatButton signUp = findViewById(R.id.signUp);
//        signUp.setOnClickListener((view) -> {
//            Log.d("MANAGER-APP", "User created");
//            viewModel.signUp(email.getText().toString(), password.getText().toString(), displayName.getText().toString(), company.getText().toString());
//            finish();
//        });
    }
}