package com.daxthompsonproject1.viewmodels.viewmodels;

import android.net.MacAddress;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.daxthompsonproject1.viewmodels.models.Manager;
import com.daxthompsonproject1.viewmodels.models.Reservation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.function.LongFunction;

public class ManagerViewModel extends ParentViewModel{

    private DatabaseReference managerDatabase;
    private MutableLiveData<Manager> managerData;

    public ManagerViewModel(){
        super();

        this.managerDatabase = database.child("/managers");
        this.managerData = new MutableLiveData<>();

        this.managerDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ss : snapshot.getChildren()){
                    Log.d("VIEWMODEL", ss.toString());
                    if(user.getValue() != null && ss.getKey().equals(user.getValue().getUid())){
                        Log.d("VIEWMODEL", String.format("Manager changed"));
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
                this.waitlist.getValue().addReservation(ss.getValue(Reservation.class));
            }
        }
        Log.d("VIEWMODEL", waitlist.getValue().toString());
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
