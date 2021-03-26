package com.daxthompsonproject1.viewmodels.models;

public class Reservation {

    public String managerUid;
    public String clientUid;
    public int timestamp;

    public Reservation(){

    }

    public Reservation(String managerUid, String clientUid, int timestamp){
        this.managerUid = managerUid;
        this.clientUid = clientUid;
        this.timestamp = timestamp;
    }
}
