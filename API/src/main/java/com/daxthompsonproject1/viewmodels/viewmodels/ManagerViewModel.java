package com.daxthompsonproject1.viewmodels.viewmodels;

import android.app.Activity;

import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.daxthompsonproject1.viewmodels.models.Manager;
import com.daxthompsonproject1.viewmodels.models.Reservation;
import com.daxthompsonproject1.viewmodels.models.WaitList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;


public class ManagerViewModel extends ParentViewModel{

    private MutableLiveData<Manager> managerData;

    public ManagerViewModel(){
        super();

        this.managerData = new MutableLiveData<>();
        this.waitlist.setValue(new WaitList());

        this.managerDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ss : snapshot.getChildren()){
                    if(user.getValue() != null && ss.getKey().equals(user.getValue().getUid())){
                        Manager manager = ss.getValue(Manager.class);
                        manager.setUid(user.getValue().getUid());
                        managerData.setValue(manager);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void updateWaitList(DataSnapshot snapshot) {
        this.waitlist.getValue().clear();
        for(DataSnapshot ss : snapshot.getChildren()){

            if(ss.child("/managerUid").getValue().equals(managerData.getValue().uid)){
                Reservation res = ss.getValue(Reservation.class);
                res.setId(ss.getKey());
                this.waitlist.getValue().addReservation(res);
            }

        }

        //In case the order gets messed up
        Collections.sort(waitlist.getValue());
        waitlist.setValue(waitlist.getValue());
    }

    public MutableLiveData<Manager> getManagerData(){return this.managerData;}

    public void signUp(String email, String password, String displayName, String company) {
        super.signUp(email, password).addOnCompleteListener((result) -> {
            String uid = user.getValue().getUid();
            Manager manager = new Manager(displayName, company, email);
            manager.setUid(uid);
            managerData.setValue(manager);
            managerDatabase.child(uid).setValue(manager);
        });

    }

}
