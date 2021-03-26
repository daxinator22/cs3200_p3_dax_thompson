package com.daxthompsonproject1.viewmodels.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.daxthompsonproject1.viewmodels.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public abstract class ParentViewModel extends ViewModel {
    protected FirebaseAuth auth;
    protected DatabaseReference database;
    protected MutableLiveData<User> user = new MutableLiveData<>();
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
            updateData();

        });

       ;
        this.database = FirebaseDatabase.getInstance().getReference("/userData");
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

    public void signUp(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password);
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

    public abstract void updateData();
}

