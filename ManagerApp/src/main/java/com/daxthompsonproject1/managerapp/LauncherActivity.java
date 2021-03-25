package com.daxthompsonproject1.managerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.daxthompsonproject1.viewmodels.viewmodels.ParentViewModel;

public class LauncherActivity extends AppCompatActivity {

    private ParentViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        //Creates view-model
        this.viewModel = new ViewModelProvider(this).get(ParentViewModel.class);
        AppCompatTextView currentUser = findViewById(R.id.currentUser);

        viewModel.getUser().observe(this, (user) -> {
            currentUser.setText(viewModel.getUserIdentity());
            Log.d("VIEWMODEL", String.format("Current user - %s", viewModel.getUserIdentity()));
        });

        AppCompatEditText email = findViewById(R.id.email);
        AppCompatEditText password = findViewById(R.id.password);


        AppCompatButton signUp = findViewById(R.id.signUp);
        signUp.setOnClickListener((view) -> {
            Log.d("MANAGER-APP", "User created");
            viewModel.signUp(email.getText().toString(), password.getText().toString());
        });

        AppCompatButton logOut = findViewById(R.id.logOut);
        logOut.setOnClickListener((view) -> {
            Log.d("MANAGER-APP", "User logged out");
            viewModel.signOut();
        });

        AppCompatButton signIn = findViewById(R.id.signIn);
        signIn.setOnClickListener((view) -> {
            Log.d("MANAGER-APP", "User signed in");
            viewModel.signIn(email.getText().toString(), password.getText().toString());
        });
    }
}