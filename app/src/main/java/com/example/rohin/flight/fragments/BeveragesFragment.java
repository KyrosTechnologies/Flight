package com.example.rohin.flight.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rohin.flight.List.BeveragesList;
import com.example.rohin.flight.List.EventLocation;
import com.example.rohin.flight.List.Events;
import com.example.rohin.flight.List.LocationEvent;
import com.example.rohin.flight.List.UserList;
import com.example.rohin.flight.List.UserPurchasedItems;
import com.example.rohin.flight.List.VegList;
import com.example.rohin.flight.MainActivity;
import com.example.rohin.flight.R;
import com.example.rohin.flight.SharedPreference.PreferenceManager;
import com.example.rohin.flight.otto.OttoBus;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.otto.Subscribe;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rohin on 21-04-2017.
 */

public class BeveragesFragment extends Fragment {

    private View beverage;
    private TextView bottle_price,cola_price,applejuice_price,orangejuice_price,spriteid_price,maaza_price;
    private TextView bottle_quan,cola_quan,applejuice_quan,orangejuice_quan,spriteid_quan,maaza_quan;
    private PreferenceManager store;
    List<String> bevpricelist=new ArrayList<String>();
    List<String>bevquantitylist=new ArrayList<String>();
    ArrayList<BeveragesList>bev;
    private LinearLayout bottle_sel,cola_sel,applejuice_sel,orangejuice_sel,spriteid_sel,maaza_sel;
    private ImageView bottle,cola,apple,orange,sprite,maaza;
    private boolean clicked=false;
    private boolean clicked1=false;
    private boolean clicked2=false;
    private boolean clicked3=false;
    private boolean clicked4=false;
    private boolean clicked5=false;
    private String SelectedSeat="";
    private String userPurchasedString=null;
    private String Location;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        beverage = inflater.inflate(R.layout.beverages_selected, container, false);
        bottle_price=(TextView)beverage.findViewById(R.id.bottle_price);
        cola_price=(TextView)beverage.findViewById(R.id.cola_price);
        applejuice_price=(TextView)beverage.findViewById(R.id.applejuice_price);
        orangejuice_price=(TextView)beverage.findViewById(R.id.orangejuice_price);
        spriteid_price=(TextView)beverage.findViewById(R.id.spriteid_price);
        maaza_price=(TextView)beverage.findViewById(R.id.maaza_price);
        bottle_quan=(TextView)beverage.findViewById(R.id.bottle_quan);
        cola_quan=(TextView)beverage.findViewById(R.id.cola_quan);
        applejuice_quan=(TextView)beverage.findViewById(R.id.applejuice_quan);
        orangejuice_quan=(TextView)beverage.findViewById(R.id.orangejuice_quan);
        spriteid_quan=(TextView)beverage.findViewById(R.id.spriteid_quan);
        maaza_quan=(TextView)beverage.findViewById(R.id.maaza_quan);
        bottle_sel=(LinearLayout)beverage.findViewById(R.id.bottle_sel);
        cola_sel=(LinearLayout)beverage.findViewById(R.id.cola_sel);
        applejuice_sel=(LinearLayout)beverage.findViewById(R.id.applejuice_sel);
        orangejuice_sel=(LinearLayout)beverage.findViewById(R.id.orangejuice_sel);
        spriteid_sel=(LinearLayout)beverage.findViewById(R.id.spriteid_sel);
        maaza_sel=(LinearLayout)beverage.findViewById(R.id.maaza_sel);
        bottle=(ImageView)beverage.findViewById(R.id.bottle);
        cola=(ImageView)beverage.findViewById(R.id.cola);
        apple=(ImageView)beverage.findViewById(R.id.apple);
        orange=(ImageView)beverage.findViewById(R.id.orange);
        sprite=(ImageView)beverage.findViewById(R.id.sprite);
        maaza=(ImageView)beverage.findViewById(R.id.maaza);
        store= PreferenceManager.getInstance(getContext());
        valuebev();
        String google=store.getBevelist();
        Location=store.getLocation();
        Log.d("beveragelist",google);



        return beverage;
    }
    @org.greenrobot.eventbus.Subscribe
    public void onMessage(EventLocation event){
        String value=event.getLocation();
        Location=value;

    }
    @Override
    public void onStart() {
        super.onStart();
        OttoBus.getBus().register(this);
        EventBus.getDefault().register(this);

    }
    @Override
    public void onStop() {
        super.onStop();
        OttoBus.getBus().unregister(this);
        EventBus.getDefault().unregister(this);

    }
    private void sendMessageToActivity() {

        Events.FragmentActivityMessage fragmentActivityMessageEvent =
                new Events.FragmentActivityMessage("value to activity");

        OttoBus.getBus().post(fragmentActivityMessageEvent);
    }

    @Subscribe
    public void getMessage(Events.ActivityFragmentMessage activityFragmentMessage) {
        SelectedSeat=activityFragmentMessage.getMessage();

    }
//    @Subscribe
//    public void getLocationMessageEvent(EventLocation.LocationMessage activityFragmentMessage) {
//        store.putLocation(activityFragmentMessage.getLocation());
//        Location=activityFragmentMessage.getLocation();
//        Toast.makeText(getActivity(),
//                " Selected Location  is : " + activityFragmentMessage.getLocation(),
//                Toast.LENGTH_SHORT).show();
//    }


    public void valuebev(){

        try{
            Gson gson=new Gson();
            Type type1=new TypeToken<List<BeveragesList>>(){}.getType();
            String google=store.getBevelist();
            bev=gson.fromJson(google,type1);

        }catch (Exception e){
            e.printStackTrace();
        }

        try {

            for (int m=0;m<bev.size();m++){
                BeveragesList r=bev.get(m);
                bevpricelist.add(r.getPrice());
                bevquantitylist.add(r.getQuantity());

                int s=m;
                switch (s) {
                    case 0:
                        bottle_price.setText(r.getPrice());
                        bottle_quan.setText(r.getQuantity());
                        break;
                    case 1:
                        cola_price.setText(r.getPrice());
                        cola_quan.setText(r.getQuantity());
                        break;
                    case 2:
                        applejuice_price.setText(r.getPrice());
                        applejuice_quan.setText(r.getQuantity());
                        break;
                    case 3:
                        orangejuice_price.setText(r.getPrice());
                        orangejuice_quan.setText(r.getQuantity());
                        break;
                    case 4:
                        spriteid_price.setText(r.getPrice());
                        spriteid_quan.setText(r.getQuantity());
                        break;
                    case 5:
                        maaza_price.setText(r.getPrice());
                        maaza_quan.setText(r.getQuantity());
                        break;

                }

            }


        }catch (Exception e){
            e.printStackTrace();
        }

        bottle_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SelectedSeat=store.getSeatNumber();
                String drivinglocation=store.getLocation();
                if(SelectedSeat==null){
                    Toast.makeText(getContext(),"Please select seat number first!",Toast.LENGTH_SHORT).show();

                }else{
                    BevClicked(31);
                    userPurchasedString=store.getUserPurchasedList();

                    ArrayList<UserPurchasedItems>userPurchasedItemses=new ArrayList<UserPurchasedItems>();
                    ArrayList<UserList>userListArrayList=new ArrayList<UserList>();
                    if(userPurchasedString!=null){
                        try{
                            Gson gson=new Gson();
                            Type type=new TypeToken<ArrayList<UserList>>(){}.getType();
                            String google=store.getUserPurchasedList();
                            userListArrayList=gson.fromJson(google,type);

                            final int arraytotalsize=userListArrayList.size();
                            ArrayList<String> seatname=new ArrayList<String>();
                            for(int i=0;i<arraytotalsize;i++){

                                String iseatnumber=userListArrayList.get(i).getSeatnumber();
                                seatname.add(iseatnumber);
                            }
                            String value= getmatched(SelectedSeat,seatname);
                            int position=0;
                            if(!value.equals("false")){
                                position=Integer.parseInt(value);
                                UserList iuserlist=userListArrayList.get(position);
                                ArrayList<UserPurchasedItems>iuserPurchasedItemses=iuserlist.getUserPurchaseList();
                                UserPurchasedItems userPurchasedItems=new UserPurchasedItems();
                                userPurchasedItems.setPrice(bottle_price.getText().toString());
                                userPurchasedItems.setQuantity(bottle_quan.getText().toString());
                                userPurchasedItems.setName("Water Bottle");
                                userPurchasedItems.setType("bev");
                                userPurchasedItems.setImage(R.drawable.water1);

                                iuserPurchasedItemses.add(userPurchasedItems);
                            }  else{
                                UserPurchasedItems userPurchasedItems=new UserPurchasedItems();
                                userPurchasedItems.setPrice(bottle_price.getText().toString());
                                userPurchasedItems.setQuantity(bottle_quan.getText().toString());
                                userPurchasedItems.setName("Water Bottle");
                                userPurchasedItems.setType("bev");
                                userPurchasedItems.setImage(R.drawable.water1);

                                userPurchasedItemses.add(userPurchasedItems);



                                UserList userList=new UserList();
                                userList.setPaymentdone(false);
                                userList.setLocation(drivinglocation);

                                userList.setSeatnumber(SelectedSeat);
                                userList.setUserPurchaseList(userPurchasedItemses);
                                userListArrayList.add(userList);
                            }







                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }else {
                        UserPurchasedItems userPurchasedItems=new UserPurchasedItems();
                        userPurchasedItems.setPrice(bottle_price.getText().toString());
                        userPurchasedItems.setQuantity(bottle_quan.getText().toString());
                        userPurchasedItems.setName("Water Bottle");
                        userPurchasedItems.setType("bev");
                        userPurchasedItems.setImage(R.drawable.water1);

                        userPurchasedItemses.add(userPurchasedItems);



                        UserList userList=new UserList();
                        userList.setSeatnumber(SelectedSeat);
                        userList.setPaymentdone(false);
                        userList.setLocation(drivinglocation);

                        userList.setUserPurchaseList(userPurchasedItemses);
                        userListArrayList.add(userList);
                    }



                    try{
                        Gson gson=new Gson();
                        String waggonapitransferlist12=gson.toJson(userListArrayList);
                        store.putUserPurchasedList(waggonapitransferlist12);
                        Log.d("values veg",waggonapitransferlist12);

                    }catch (Exception es){
                        es.printStackTrace();

                    }
//                    if (!clicked){
//                        clicked=true;
//                        bottle.setBackgroundDrawable(getResources().getDrawable(R.drawable.water3));
//                    }else {
//                        clicked=false;
//                        bottle.setBackgroundDrawable(getResources().getDrawable(R.drawable.water2));
//
//                    }
                }


            }
        });

        cola_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String SelectedSeat=store.getSeatNumber();
                String drivinglocation=store.getLocation();
                if(SelectedSeat==null){
                    Toast.makeText(getContext(),"Please select seat number first!",Toast.LENGTH_SHORT).show();

                }else{
                    BevClicked(32);

                    userPurchasedString=store.getUserPurchasedList();

                    ArrayList<UserPurchasedItems>userPurchasedItemses=new ArrayList<UserPurchasedItems>();
                    ArrayList<UserList>userListArrayList=new ArrayList<UserList>();
                    if(userPurchasedString!=null){
                        try{
                            Gson gson=new Gson();
                            Type type=new TypeToken<ArrayList<UserList>>(){}.getType();
                            String google=store.getUserPurchasedList();
                            userListArrayList=gson.fromJson(google,type);

                            final int arraytotalsize=userListArrayList.size();
                            ArrayList<String> seatname=new ArrayList<String>();
                            for(int i=0;i<arraytotalsize;i++){

                                String iseatnumber=userListArrayList.get(i).getSeatnumber();
                                seatname.add(iseatnumber);
                            }
                            String value= getmatched(SelectedSeat,seatname);
                            int position=0;
                            if(!value.equals("false")){
                                position=Integer.parseInt(value);
                                UserList iuserlist=userListArrayList.get(position);
                                ArrayList<UserPurchasedItems>iuserPurchasedItemses=iuserlist.getUserPurchaseList();
                                UserPurchasedItems userPurchasedItems=new UserPurchasedItems();
                                userPurchasedItems.setPrice(bottle_price.getText().toString());
                                userPurchasedItems.setQuantity(cola_price.getText().toString());
                                userPurchasedItems.setName("Coca cola");
                                userPurchasedItems.setType("bev");
                                userPurchasedItems.setImage(R.drawable.cola1);

                                iuserPurchasedItemses.add(userPurchasedItems);
                            }  else{
                                UserPurchasedItems userPurchasedItems=new UserPurchasedItems();
                                userPurchasedItems.setPrice(bottle_price.getText().toString());
                                userPurchasedItems.setQuantity(cola_price.getText().toString());
                                userPurchasedItems.setName("Coca cola");
                                userPurchasedItems.setType("bev");
                                userPurchasedItems.setImage(R.drawable.cola1);

                                userPurchasedItemses.add(userPurchasedItems);



                                UserList userList=new UserList();
                                userList.setPaymentdone(false);
                                userList.setSeatnumber(SelectedSeat);
                                userList.setLocation(drivinglocation);

                                userList.setUserPurchaseList(userPurchasedItemses);
                                userListArrayList.add(userList);
                            }







                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }else {
                        UserPurchasedItems userPurchasedItems=new UserPurchasedItems();
                        userPurchasedItems.setPrice(bottle_price.getText().toString());
                        userPurchasedItems.setQuantity(cola_price.getText().toString());
                        userPurchasedItems.setName("Coca cola");
                        userPurchasedItems.setType("bev");
                        userPurchasedItems.setImage(R.drawable.cola1);

                        userPurchasedItemses.add(userPurchasedItems);



                        UserList userList=new UserList();
                        userList.setSeatnumber(SelectedSeat);
                        userList.setPaymentdone(false);
                        userList.setLocation(drivinglocation);

                        userList.setUserPurchaseList(userPurchasedItemses);
                        userListArrayList.add(userList);
                    }



                    try{
                        Gson gson=new Gson();
                        String waggonapitransferlist12=gson.toJson(userListArrayList);
                        store.putUserPurchasedList(waggonapitransferlist12);
                        Log.d("values veg",waggonapitransferlist12);

                    }catch (Exception es){
                        es.printStackTrace();

                    }

//                    if (!clicked1){
//                        clicked1=true;
//                        cola.setBackgroundDrawable(getResources().getDrawable(R.drawable.cola3));
//                    }else {
//                        clicked1=false;
//                        cola.setBackgroundDrawable(getResources().getDrawable(R.drawable.cola2));
//
//                    }

                }

            }
        });

        applejuice_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
String drivinglocation=store.getLocation();

                String SelectedSeat=store.getSeatNumber();
                if(SelectedSeat==null){
                    Toast.makeText(getContext(),"Please select seat number first!",Toast.LENGTH_SHORT).show();

                }else{
                    BevClicked(33);

                    userPurchasedString=store.getUserPurchasedList();

                    ArrayList<UserPurchasedItems>userPurchasedItemses=new ArrayList<UserPurchasedItems>();
                    ArrayList<UserList>userListArrayList=new ArrayList<UserList>();
                    if(userPurchasedString!=null){
                        try{
                            Gson gson=new Gson();
                            Type type=new TypeToken<ArrayList<UserList>>(){}.getType();
                            String google=store.getUserPurchasedList();
                            userListArrayList=gson.fromJson(google,type);

                            final int arraytotalsize=userListArrayList.size();
                            ArrayList<String> seatname=new ArrayList<String>();
                            for(int i=0;i<arraytotalsize;i++){

                                String iseatnumber=userListArrayList.get(i).getSeatnumber();
                                seatname.add(iseatnumber);
                            }
                            String value= getmatched(SelectedSeat,seatname);
                            int position=0;
                            if(!value.equals("false")){
                                position=Integer.parseInt(value);
                                UserList iuserlist=userListArrayList.get(position);
                                ArrayList<UserPurchasedItems>iuserPurchasedItemses=iuserlist.getUserPurchaseList();
                                UserPurchasedItems userPurchasedItems=new UserPurchasedItems();
                                userPurchasedItems.setPrice(applejuice_price.getText().toString());
                                userPurchasedItems.setQuantity(applejuice_quan.getText().toString());
                                userPurchasedItems.setName("Apple Juice");
                                userPurchasedItems.setType("bev");
                                userPurchasedItems.setImage(R.drawable.apple1);

                                iuserPurchasedItemses.add(userPurchasedItems);
                            }  else{
                                UserPurchasedItems userPurchasedItems=new UserPurchasedItems();
                                userPurchasedItems.setPrice(applejuice_price.getText().toString());
                                userPurchasedItems.setQuantity(applejuice_quan.getText().toString());
                                userPurchasedItems.setName("Apple Juice");
                                userPurchasedItems.setType("bev");
                                userPurchasedItems.setImage(R.drawable.apple1);

                                userPurchasedItemses.add(userPurchasedItems);



                                UserList userList=new UserList();
                                userList.setPaymentdone(false);
                                userList.setLocation(drivinglocation);

                                userList.setSeatnumber(SelectedSeat);
                                userList.setUserPurchaseList(userPurchasedItemses);
                                userListArrayList.add(userList);
                            }







                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }else {
                        UserPurchasedItems userPurchasedItems=new UserPurchasedItems();
                        userPurchasedItems.setPrice(applejuice_price.getText().toString());
                        userPurchasedItems.setQuantity(applejuice_quan.getText().toString());
                        userPurchasedItems.setName("Apple Juice");
                        userPurchasedItems.setType("bev");
                        userPurchasedItems.setImage(R.drawable.apple1);

                        userPurchasedItemses.add(userPurchasedItems);



                        UserList userList=new UserList();
                        userList.setSeatnumber(SelectedSeat);
                        userList.setPaymentdone(false);
                        userList.setLocation(drivinglocation);

                        userList.setUserPurchaseList(userPurchasedItemses);
                        userListArrayList.add(userList);
                    }



                    try{
                        Gson gson=new Gson();
                        String waggonapitransferlist12=gson.toJson(userListArrayList);
                        store.putUserPurchasedList(waggonapitransferlist12);
                        Log.d("values veg",waggonapitransferlist12);

                    }catch (Exception es){
                        es.printStackTrace();

                    }


//                    if (!clicked2){
//                        clicked2=true;
//                        apple.setBackgroundDrawable(getResources().getDrawable(R.drawable.apple3));
//                    }else {
//                        clicked2=false;
//                        apple.setBackgroundDrawable(getResources().getDrawable(R.drawable.apple2));
//
//                    }
                }


            }
        });

        orangejuice_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String drivinglocation=store.getLocation();

                String SelectedSeat=store.getSeatNumber();
                if(SelectedSeat==null){
                    Toast.makeText(getContext(),"Please select seat number first!",Toast.LENGTH_SHORT).show();

                }else {
                    BevClicked(34);

                    userPurchasedString=store.getUserPurchasedList();

                    ArrayList<UserPurchasedItems>userPurchasedItemses=new ArrayList<UserPurchasedItems>();
                    ArrayList<UserList>userListArrayList=new ArrayList<UserList>();
                    if(userPurchasedString!=null){
                        try{
                            Gson gson=new Gson();
                            Type type=new TypeToken<ArrayList<UserList>>(){}.getType();
                            String google=store.getUserPurchasedList();
                            userListArrayList=gson.fromJson(google,type);

                            final int arraytotalsize=userListArrayList.size();
                            ArrayList<String> seatname=new ArrayList<String>();
                            for(int i=0;i<arraytotalsize;i++){

                                String iseatnumber=userListArrayList.get(i).getSeatnumber();
                                seatname.add(iseatnumber);
                            }
                            String value= getmatched(SelectedSeat,seatname);
                            int position=0;
                            if(!value.equals("false")){
                                position=Integer.parseInt(value);
                                UserList iuserlist=userListArrayList.get(position);
                                ArrayList<UserPurchasedItems>iuserPurchasedItemses=iuserlist.getUserPurchaseList();
                                UserPurchasedItems userPurchasedItems=new UserPurchasedItems();
                                userPurchasedItems.setPrice(orangejuice_price.getText().toString());
                                userPurchasedItems.setQuantity(orangejuice_quan.getText().toString());
                                userPurchasedItems.setName("Orange Juice");
                                userPurchasedItems.setType("bev");
                                userPurchasedItems.setImage(R.drawable.orange1);

                                iuserPurchasedItemses.add(userPurchasedItems);
                            }  else{
                                UserPurchasedItems userPurchasedItems=new UserPurchasedItems();
                                userPurchasedItems.setPrice(orangejuice_price.getText().toString());
                                userPurchasedItems.setQuantity(orangejuice_quan.getText().toString());
                                userPurchasedItems.setName("Orange Juice");
                                userPurchasedItems.setType("bev");
                                userPurchasedItems.setImage(R.drawable.orange1);

                                userPurchasedItemses.add(userPurchasedItems);



                                UserList userList=new UserList();
                                userList.setPaymentdone(false);
                                userList.setLocation(drivinglocation);

                                userList.setSeatnumber(SelectedSeat);
                                userList.setUserPurchaseList(userPurchasedItemses);
                                userListArrayList.add(userList);
                            }







                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }else {
                        UserPurchasedItems userPurchasedItems=new UserPurchasedItems();
                        userPurchasedItems.setPrice(orangejuice_price.getText().toString());
                        userPurchasedItems.setQuantity(orangejuice_quan.getText().toString());
                        userPurchasedItems.setName("Orange Juice");
                        userPurchasedItems.setType("bev");
                        userPurchasedItems.setImage(R.drawable.orange1);

                        userPurchasedItemses.add(userPurchasedItems);



                        UserList userList=new UserList();
                        userList.setSeatnumber(SelectedSeat);
                        userList.setPaymentdone(false);
                        userList.setLocation(drivinglocation);

                        userList.setUserPurchaseList(userPurchasedItemses);
                        userListArrayList.add(userList);
                    }



                    try{
                        Gson gson=new Gson();
                        String waggonapitransferlist12=gson.toJson(userListArrayList);
                        store.putUserPurchasedList(waggonapitransferlist12);
                        Log.d("values veg",waggonapitransferlist12);

                    }catch (Exception es){
                        es.printStackTrace();

                    }

//                    if (!clicked3){
//                        clicked3=true;
//                        orange.setBackgroundDrawable(getResources().getDrawable(R.drawable.orange3));
//                    }else {
//                        clicked3=false;
//                        orange.setBackgroundDrawable(getResources().getDrawable(R.drawable.orange2));
//
//                    }
                }


            }
        });

        spriteid_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String SelectedSeat=store.getSeatNumber();
                String drivinglocation=store.getLocation();
                if(SelectedSeat==null){
                    Toast.makeText(getContext(),"Please select seat number first!",Toast.LENGTH_SHORT).show();

                }else{
                    BevClicked(35);

                    userPurchasedString=store.getUserPurchasedList();

                    ArrayList<UserPurchasedItems>userPurchasedItemses=new ArrayList<UserPurchasedItems>();
                    ArrayList<UserList>userListArrayList=new ArrayList<UserList>();
                    if(userPurchasedString!=null){
                        try{
                            Gson gson=new Gson();
                            Type type=new TypeToken<ArrayList<UserList>>(){}.getType();
                            String google=store.getUserPurchasedList();
                            userListArrayList=gson.fromJson(google,type);

                            final int arraytotalsize=userListArrayList.size();
                            ArrayList<String> seatname=new ArrayList<String>();
                            for(int i=0;i<arraytotalsize;i++){

                                String iseatnumber=userListArrayList.get(i).getSeatnumber();
                                seatname.add(iseatnumber);
                            }
                            String value= getmatched(SelectedSeat,seatname);
                            int position=0;
                            if(!value.equals("false")){
                                position=Integer.parseInt(value);
                                UserList iuserlist=userListArrayList.get(position);
                                ArrayList<UserPurchasedItems>iuserPurchasedItemses=iuserlist.getUserPurchaseList();
                                UserPurchasedItems userPurchasedItems=new UserPurchasedItems();
                                userPurchasedItems.setPrice(spriteid_price.getText().toString());
                                userPurchasedItems.setQuantity(spriteid_quan.getText().toString());
                                userPurchasedItems.setName("Sprite");
                                userPurchasedItems.setType("bev");
                                userPurchasedItems.setImage(R.drawable.sprite1);

                                iuserPurchasedItemses.add(userPurchasedItems);
                            }  else{
                                UserPurchasedItems userPurchasedItems=new UserPurchasedItems();
                                userPurchasedItems.setPrice(spriteid_price.getText().toString());
                                userPurchasedItems.setQuantity(spriteid_quan.getText().toString());
                                userPurchasedItems.setName("Sprite");
                                userPurchasedItems.setType("bev");
                                userPurchasedItems.setImage(R.drawable.sprite1);

                                userPurchasedItemses.add(userPurchasedItems);



                                UserList userList=new UserList();
                                userList.setPaymentdone(false);
                                userList.setLocation(drivinglocation);

                                userList.setSeatnumber(SelectedSeat);
                                userList.setUserPurchaseList(userPurchasedItemses);
                                userListArrayList.add(userList);
                            }







                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }else {
                        UserPurchasedItems userPurchasedItems=new UserPurchasedItems();
                        userPurchasedItems.setPrice(spriteid_price.getText().toString());
                        userPurchasedItems.setQuantity(spriteid_quan.getText().toString());
                        userPurchasedItems.setName("Sprite");
                        userPurchasedItems.setType("bev");
                        userPurchasedItems.setImage(R.drawable.sprite1);

                        userPurchasedItemses.add(userPurchasedItems);



                        UserList userList=new UserList();
                        userList.setSeatnumber(SelectedSeat);
                        userList.setPaymentdone(false);
                        userList.setLocation(drivinglocation);

                        userList.setUserPurchaseList(userPurchasedItemses);
                        userListArrayList.add(userList);
                    }



                    try{
                        Gson gson=new Gson();
                        String waggonapitransferlist12=gson.toJson(userListArrayList);
                        store.putUserPurchasedList(waggonapitransferlist12);
                        Log.d("values veg",waggonapitransferlist12);

                    }catch (Exception es){
                        es.printStackTrace();

                    }


//                    if (!clicked4){
//                        clicked4=true;
//                        sprite.setBackgroundDrawable(getResources().getDrawable(R.drawable.sprite3));
//                    }else {
//                        clicked4=false;
//                        sprite.setBackgroundDrawable(getResources().getDrawable(R.drawable.sprite2));
//
//                    }
                }


            }
        });

        maaza_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String SelectedSeat=store.getSeatNumber();
                String drivinglocation=store.getLocation();
                if(SelectedSeat==null){
                    Toast.makeText(getContext(),"Please select seat number first!",Toast.LENGTH_SHORT).show();

                }else{
                    BevClicked(36);

                    userPurchasedString=store.getUserPurchasedList();

                    ArrayList<UserPurchasedItems>userPurchasedItemses=new ArrayList<UserPurchasedItems>();
                    ArrayList<UserList>userListArrayList=new ArrayList<UserList>();
                    if(userPurchasedString!=null){
                        try{
                            Gson gson=new Gson();
                            Type type=new TypeToken<ArrayList<UserList>>(){}.getType();
                            String google=store.getUserPurchasedList();
                            userListArrayList=gson.fromJson(google,type);

                            final int arraytotalsize=userListArrayList.size();
                            ArrayList<String> seatname=new ArrayList<String>();
                            for(int i=0;i<arraytotalsize;i++){

                                String iseatnumber=userListArrayList.get(i).getSeatnumber();
                                seatname.add(iseatnumber);
                            }
                            String value= getmatched(SelectedSeat,seatname);
                            int position=0;
                            if(!value.equals("false")){
                                position=Integer.parseInt(value);
                                UserList iuserlist=userListArrayList.get(position);
                                ArrayList<UserPurchasedItems>iuserPurchasedItemses=iuserlist.getUserPurchaseList();
                                UserPurchasedItems userPurchasedItems=new UserPurchasedItems();
                                userPurchasedItems.setPrice(maaza_price.getText().toString());
                                userPurchasedItems.setQuantity(maaza_quan.getText().toString());
                                userPurchasedItems.setName("Maaza");
                                userPurchasedItems.setType("bev");
                                userPurchasedItems.setImage(R.drawable.maaza1);

                                iuserPurchasedItemses.add(userPurchasedItems);
                            }  else{
                                UserPurchasedItems userPurchasedItems=new UserPurchasedItems();
                                userPurchasedItems.setPrice(maaza_price.getText().toString());
                                userPurchasedItems.setQuantity(maaza_quan.getText().toString());
                                userPurchasedItems.setName("Maaza");
                                userPurchasedItems.setType("bev");
                                userPurchasedItems.setImage(R.drawable.maaza1);

                                userPurchasedItemses.add(userPurchasedItems);



                                UserList userList=new UserList();
                                userList.setPaymentdone(false);
                                userList.setLocation(drivinglocation);

                                userList.setSeatnumber(SelectedSeat);
                                userList.setUserPurchaseList(userPurchasedItemses);
                                userListArrayList.add(userList);
                            }







                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }else {
                        UserPurchasedItems userPurchasedItems=new UserPurchasedItems();
                        userPurchasedItems.setPrice(maaza_price.getText().toString());
                        userPurchasedItems.setQuantity(maaza_quan.getText().toString());
                        userPurchasedItems.setName("Maaza");
                        userPurchasedItems.setType("bev");
                        userPurchasedItems.setImage(R.drawable.maaza1);

                        userPurchasedItemses.add(userPurchasedItems);



                        UserList userList=new UserList();
                        userList.setSeatnumber(SelectedSeat);
                        userList.setPaymentdone(false);
                        userList.setLocation(drivinglocation);

                        userList.setUserPurchaseList(userPurchasedItemses);
                        userListArrayList.add(userList);
                    }



                    try{
                        Gson gson=new Gson();
                        String waggonapitransferlist12=gson.toJson(userListArrayList);
                        store.putUserPurchasedList(waggonapitransferlist12);
                        Log.d("values veg",waggonapitransferlist12);

                    }catch (Exception es){
                        es.printStackTrace();

                    }

//                    if (!clicked5){
//                        clicked5=true;
//                        maaza.setBackgroundDrawable(getResources().getDrawable(R.drawable.maaza3));
//                    }else {
//                        clicked5=false;
//                        maaza.setBackgroundDrawable(getResources().getDrawable(R.drawable.maaza2));
//
//                    }
                }


            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        store=PreferenceManager.getInstance(getContext());
        valuebev();
    }
    private String getmatched(String seatnumber, ArrayList<String>  listseatnumber){
        for(int i=0; i<listseatnumber.size();i++){
            String kvalue=listseatnumber.get(i);
            if(seatnumber.equals(kvalue)){
                String value=String.valueOf(i);
                return value;
            }

        }

        return "false";
    }
    private void BevClicked(int id){
        for(int i=0;i<bev.size();i++){
            BeveragesList veglist=bev.get(i);
            int ids=veglist.getId();
            int image=veglist.getImage();
            String name=veglist.getName();
            String price=veglist.getPrice();
            if(ids==id){
                BeveragesList vss=new BeveragesList();
                int quantity=Integer.parseInt(veglist.getQuantity());
                if(quantity==0){
                    Toast.makeText(getContext(),"could not add the dish, cause is sold out",Toast.LENGTH_SHORT).show();
                }else{
                    quantity=quantity-1;
                    String fival=String.valueOf(quantity);
                    vss.setQuantity(fival);
                    vss.setId(ids);
                    vss.setImage(image);
                    vss.setName(name);
                    vss.setPrice(price);
                    bev.set(i,vss);
                }


            }


        }
        try{
            Gson gson=new Gson();
            String waggonapitransferlist12=gson.toJson(bev);
            store.putBevelist(waggonapitransferlist12);
            Log.d("veg_list",waggonapitransferlist12);

        }catch (Exception es){
            es.printStackTrace();

        }
        valuebev();

    }

}
