package com.daxthompsonproject1.viewmodels.models;

import android.app.Activity;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatTextView;

import com.google.firebase.database.Exclude;

public class Reservation implements Comparable<Reservation>{

    public String managerUid;
    public String clientUid;
    public long timestamp;

    @Exclude
    private String id;

    public Reservation(){

    }

    public Reservation(String managerUid, String clientUid, long timestamp){
        this.managerUid = managerUid;
        this.clientUid = clientUid;
        this.timestamp = timestamp;
    }

    public LinearLayout render(Activity activity){
        LinearLayout container = new LinearLayout(activity);

        AppCompatTextView reservation = new AppCompatTextView(activity);
        reservation.setText(this.toString());

        container.addView(reservation);
        return container;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){return this.id;}

    @Override
    public String toString(){
        StringBuilder reservation = new StringBuilder();

        appendToStringAttribute("Manager ID", managerUid, reservation);
        appendToStringAttribute("Client ID", clientUid, reservation);
        appendToStringAttribute("Timestamp", timestamp + "", reservation);

        return reservation.toString();
    }

    private void appendToStringAttribute(String name, String value, StringBuilder manager){
        manager.append(String.format("%8s: %s\n", name, value));
    }


    @Override
    public int compareTo(Reservation reservation) {
        return (int)(this.timestamp - reservation.timestamp);
    }
}
