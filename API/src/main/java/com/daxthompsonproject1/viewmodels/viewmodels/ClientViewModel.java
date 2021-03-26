package com.daxthompsonproject1.viewmodels.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.daxthompsonproject1.viewmodels.models.Client;
import com.daxthompsonproject1.viewmodels.models.Manager;
import com.daxthompsonproject1.viewmodels.models.Reservation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ClientViewModel extends ParentViewModel{

    private DatabaseReference clientDatabase;
    private MutableLiveData<Client> clientData;
    private MutableLiveData<ArrayList<Manager>> managers;

    public ClientViewModel(){
        super();

        this.clientDatabase = database.child("/clients");
        this.clientDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ss : snapshot.getChildren()){
                    if(user.getValue() != null && ss.getKey().equals(user.getValue().getUid())){
                        Client client = ss.getValue(Client.class);
                        client.setUid(user.getValue().getUid());
                        clientData.setValue(client);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        this.clientData = new MutableLiveData<>();

        this.managers = new MutableLiveData<>();
        this.managers.setValue(new ArrayList<>());
        this.managerDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ss : snapshot.getChildren()){
                    Manager manager = ss.getValue(Manager.class);
                    manager.setUid(ss.getKey());

                    managers.getValue().add(manager);
                }

                managers.setValue(managers.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public MutableLiveData<Client> getClientData(){return this.clientData;}
    public MutableLiveData<ArrayList<Manager>> getManagers(){return this.managers;}

    public void makeReservation(){
        long curTime = System.currentTimeMillis();
        this.reservations.push().setValue(new Reservation("test", clientData.getValue().uid, curTime));
    }

    public void signUp(String email, String password, String displayName){
        super.signUp(email, password).addOnCompleteListener(task -> {
            String uid = this.getUser().getValue().getUid();

            Client client = new Client(email, displayName);
            client.setUid(uid);

            this.clientData.setValue(client);
            this.clientDatabase.child(uid).setValue(client);
        });

    }

    @Override
    public void updateWaitList(DataSnapshot snapshot) {
        this.waitlist.getValue().clear();
        for(DataSnapshot ss : snapshot.getChildren()){

            if(ss.child("/clientUid").getValue().equals(clientData.getValue().uid)){
                Reservation res = ss.getValue(Reservation.class);
                res.setId(ss.getKey());
                this.waitlist.getValue().addReservation(res);
            }

        }

        //In case the order gets messed up
        Collections.sort(waitlist.getValue());
        waitlist.setValue(waitlist.getValue());
    }
}
