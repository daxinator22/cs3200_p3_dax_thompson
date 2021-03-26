package com.daxthompsonproject1.managerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.daxthompsonproject1.viewmodels.models.Reservation;
import com.daxthompsonproject1.viewmodels.viewmodels.ManagerViewModel;

public class HomeActivity extends AppCompatActivity {

    private ManagerViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.viewModel = new ViewModelProvider(this).get(ManagerViewModel.class);

        AppCompatTextView currentUser = findViewById(R.id.currentUser);

        viewModel.getUser().observe(this, (user) -> {
            if(user == null){
                currentUser.setText("Loading...");
                Intent intent = new Intent(this, SignInActivity.class);
                startActivity(intent);
            }
            else{
                currentUser.setText(viewModel.getUserIdentity());
            }
        });

        AppCompatButton logOut = findViewById(R.id.logOut);
        logOut.setOnClickListener((view) -> {
            viewModel.signOut();
        });

        AppCompatTextView managerData = findViewById(R.id.managerData);
        this.viewModel.getManagerData().observe(this, (data) -> {
            if(this.viewModel.getManagerData().getValue() == null){
                managerData.setText("Loading...");
            }
            else {
                managerData.setText(this.viewModel.getManagerData().getValue().toString());
            }
        });

        LinearLayout reservationContainer = findViewById(R.id.reservationContiner);
        this.viewModel.getWaitList().observe(this, (view) -> {
            reservationContainer.removeAllViews();

            Log.d("HOMEACTIVITY-UPDATEWAITLIST", "Waitlist was changed");
            StringBuilder reservations = new StringBuilder();
            for(Reservation reservation : this.viewModel.getWaitList().getValue()){
                reservationContainer.addView(this.viewModel.renderReservation(reservation, this));
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
    }
}