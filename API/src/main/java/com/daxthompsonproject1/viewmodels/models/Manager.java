package com.daxthompsonproject1.viewmodels.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Manager {

    public String displayName;
    public String company;
    public String email;

    @Exclude
    public String uid;

    public Manager(){

    }

    public Manager(String displayName, String company, String email){
        this.displayName = displayName;
        this.company = company;
        this.email = email;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String toString(){
        StringBuilder manager = new StringBuilder();

        appendToStringAttribute("ID", uid, manager);
        appendToStringAttribute("Display Name", displayName, manager);
        appendToStringAttribute("Company", company, manager);
        appendToStringAttribute("Email", email, manager);

        return manager.toString();
    }

    private void appendToStringAttribute(String name, String value, StringBuilder manager){
        manager.append(String.format("%8s: %s\n", name, value));
    }

}
