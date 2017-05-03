package com.example.rohin.flight.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Thirunavukkarasu on 28-07-2016.
 */
public class PreferenceManager {
    private static PreferenceManager store;
    private final SharedPreferences sp;

    private PreferenceManager(Context context){
        String filename = "login_credentials";
        sp=context.getApplicationContext().getSharedPreferences(filename,0);
    }
    public static PreferenceManager getInstance(Context context){
        if(store==null){
            Log.v("PreferenceManager","NEW STORE");
            store=new PreferenceManager(context);
        }
        return store;
    }
    public String getQuantity_enter(){
        return sp.getString("QuantityEnter",null);
    }
    public void putquantity_enter(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("QuantityEnter", value);
        editor.commit();
    }

    public String getPrice(){
        return sp.getString("Price",null);
    }
    public void putPrice(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("Price", value);
        editor.commit();
    }

    public int getListitemvalue(){
        return sp.getInt("ListItemValue",0);
    }
    public void putListitemvalue(int value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putInt("ListItemValue", value);
        editor.commit();
    }

    public String getVeglist(){return sp.getString("VegList",null);
    }
    public void putVeglist(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("VegList", value);
        editor.commit();
    }

    public String getNonVeglist(){
        return sp.getString("NonVegList",null);
    }
    public void putNonVeglist(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("NonVegList", value);
        editor.commit();
    }

    public String getBevelist(){
        return sp.getString("BeveList",null);
    }
    public void putBevelist(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("BeveList", value);
        editor.commit();
    }
    public String getUserPurchasedList(){
        return sp.getString("UserPurchasedList",null);
    }
    public void putUserPurchasedList(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("UserPurchasedList", value);
        editor.commit();
    }
    public String getSeatNumber(){
        return sp.getString("SeatNumber",null);
    }
    public void putSeatNumber(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("SeatNumber", value);
        editor.commit();
    }
    public String getLocation(){
        return sp.getString("Location",null);
    }
    public void putLocation(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("Location", value);
        editor.commit();
    }


}