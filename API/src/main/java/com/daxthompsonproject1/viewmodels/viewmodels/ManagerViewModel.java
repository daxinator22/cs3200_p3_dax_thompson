package com.daxthompsonproject1.viewmodels.viewmodels;

import android.net.MacAddress;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.daxthompsonproject1.viewmodels.models.Manager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

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
                    if(user.getValue() != null && ss.getValue().equals(user.getValue().getUid())){
                        Log.d("VIEWMODEL", String.format("Manager changed"));
                        managerData.setValue(ss.getValue(Manager.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public MutableLiveData<Manager> getManagerData(){return this.managerData;}

    public void updateManager(String uid){
        if(managerData.getValue() == null){
            Log.d("VIEWMODEL", "User is null");
        }
        database.child(uid).setValue(managerData);
    }

    @Override
    public void signUp(String email, String password) {
        super.signUp(email, password);

//        String uid = this.getUser().getValue().getUid();
        String displayName = "test";
        String company = "test";
        Manager manager = new Manager(displayName, company, email);
        managerData.setValue(manager);
    }

    @Override
    public void updateData() {
        managerDatabase.child(user.getValue().getUid()).get().addOnCompleteListener(task -> {
            managerData.setValue(task.getResult().getValue(Manager.class));
        });
    }
}
