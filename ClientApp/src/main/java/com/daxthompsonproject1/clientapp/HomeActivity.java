package com.daxthompsonproject1.clientapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.daxthompsonproject1.viewmodels.models.Manager;
import com.daxthompsonproject1.viewmodels.models.Reservation;
import com.daxthompsonproject1.viewmodels.viewmodels.ClientViewModel;

public class HomeActivity extends AppCompatActivity{

    private ClientViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.viewModel = new ViewModelProvider(this).get(ClientViewModel.class);

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

        AppCompatTextView clientData = findViewById(R.id.clientData);
        this.viewModel.getClientData().observe(this, (data) -> {
            if(this.viewModel.getClientData().getValue() == null){
                clientData.setText("Loading...");
            }
            else {
                clientData.setText(this.viewModel.getClientData().getValue().toString());
            }
        });

        ScrollView managerContainer = findViewById(R.id.managerContainer);
        this.viewModel.getManagers().observe(this, data -> {
            managerContainer.removeAllViews();

            for(Manager manager : this.viewModel.getManagers().getValue()){
                LinearLayout container = new LinearLayout(this);
                TextView managerName = new TextView(this);

                managerName.setText(manager.company);
                container.addView(managerName);
            }
        });

        findViewById(R.id.makeReservation).setOnClickListener(view -> {
            this.viewModel.makeReservation();
        });
//
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
}