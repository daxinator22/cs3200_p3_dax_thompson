package com.daxthompsonproject1.viewmodels.models;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.IgnoreExtraProperties;

public class User {
    String uid;
    String email;

    public User(FirebaseUser user) {
        this.uid = user.getUid();
        this.email = user.getEmail();
    }

    public String getUid() {
        return uid;
    }
    public String getEmail() {
        return email;
    }
}
