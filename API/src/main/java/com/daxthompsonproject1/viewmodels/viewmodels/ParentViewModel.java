package com.daxthompsonproject1.viewmodels.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.daxthompsonproject1.viewmodels.models.User;
import com.daxthompsonproject1.viewmodels.models.WaitList;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public abstract class ParentViewModel extends ViewModel {
    protected FirebaseAuth auth;
    protected DatabaseReference database;
    protected MutableLiveData<User> user = new MutableLiveData<>();
    protected  MutableLiveData<WaitList> waitlist = new MutableLiveData<>();
    //    MutableLiveData<RuntimeException> loginError = new MutableLiveData<>();
    public ParentViewModel() {
        this.auth = FirebaseAuth.getInstance();
        this.auth.addAuthStateListener((listener) -> {
            FirebaseUser fbUser = auth.getCurrentUser();
//                loginError.setValue(null);
            if (fbUser == null) {
                user.setValue(null);
            } else {
                user.setValue(new User(fbUser));
            }

        });

       ;
        this.database = FirebaseDatabase.getInstance().getReference("/userData");

        this.database.child("/reservations").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                updateWaitList(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        })
    }

    public String getUserIdentity(){
        if(user.getValue() == null){
            return "There is no current user";
        }
        return user.getValue().getEmail();
    }

    public MutableLiveData<User> getUser() {
        return user;
    }

    public Task<AuthResult> signUp(String email, String password) {
        return auth.createUserWithEmailAndPassword(email, password);
//        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                AuthResult result = task.getResult();
//                if (result.getUser() == null) {
//                    loginError.setValue(new RuntimeException("Signup failed"));
//                }
//            }
//        });
    }

    public void signIn(String email, String password) {
        auth.signInWithEmailAndPassword(email, password);
    }

    public void signOut() {
        auth.signOut();
    }

    public abstract void updateWaitList(DataSnapshot snapshot);
}

