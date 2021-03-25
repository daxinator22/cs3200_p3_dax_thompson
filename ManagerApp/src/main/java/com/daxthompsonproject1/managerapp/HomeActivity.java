package com.daxthompsonproject1.managerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.daxthompsonproject1.viewmodels.viewmodels.ParentViewModel;

public class HomeActivity extends AppCompatActivity {

    private ParentViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.viewModel = new ViewModelProvider(this).get(ParentViewModel.class);

        AppCompatTextView currentUser = findViewById(R.id.currentUser);
        currentUser.setText(viewModel.getUserIdentity());



        AppCompatButton logOut = findViewById(R.id.logOut);
        logOut.setOnClickListener((view) -> {
            Log.d("MANAGER-APP", "User logged out");
            viewModel.signOut();
        });

    }

    @Override
    protected void onStart(){
        super.onStart();

        viewModel.getUser().observe(this, (user) -> {
            Log.d("VIEWMODEL", String.format("Current user - %s", viewModel.getUserIdentity()));
            if(user == null){
                Intent intent = new Intent(this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }
}