package com.daxthompsonproject1.managerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.daxthompsonproject1.viewmodels.viewmodels.ManagerViewModel;
import com.daxthompsonproject1.viewmodels.viewmodels.ParentViewModel;

public class SignInActivity extends AppCompatActivity {

    private ManagerViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        this.viewModel = new ViewModelProvider(this).get(ManagerViewModel.class);

        AppCompatEditText email = findViewById(R.id.email);
        AppCompatEditText password = findViewById(R.id.password);

        AppCompatButton signUp = findViewById(R.id.signUp);
        signUp.setOnClickListener((view) -> {
            Log.d("MANAGER-APP", "User created");
            viewModel.signUp(email.getText().toString(), password.getText().toString());
            finish();
        });

        AppCompatButton signIn = findViewById(R.id.signIn);
        signIn.setOnClickListener((view) -> {
            Log.d("MANAGER-APP", "User signed in");
            viewModel.signIn(email.getText().toString(), password.getText().toString());
            finish();
        });
    }
}