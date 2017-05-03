package com.example.rohin.flight.List;

import java.util.ArrayList;

/**
 * Created by Rohin on 25-04-2017.
 */

public class UserList {
    String seatnumber;
    int id;
    boolean paymentdone;
    String Location;
    private ArrayList<UserPurchasedItems> userPurchaseList;
    public UserList(){

    }

    public ArrayList<UserPurchasedItems> getUserPurchaseList() {
        return userPurchaseList;
    }

    public void setUserPurchaseList(ArrayList<UserPurchasedItems> userPurchaseList) {
        this.userPurchaseList = userPurchaseList;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public boolean isPaymentdone() {
        return paymentdone;
    }

    public void setPaymentdone(boolean paymentdone) {
        this.paymentdone = paymentdone;
    }

    public String getSeatnumber() {
        return seatnumber;
    }

    public void setSeatnumber(String seatnumber) {
        this.seatnumber = seatnumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
