package com.daxthompsonproject1.viewmodels.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

public class WaitList extends ArrayList<Reservation> {

    public void addReservation(Reservation reservation){
        this.add(reservation);
    }

    public void fillReservation(){
        this.remove(0);
    }

}
