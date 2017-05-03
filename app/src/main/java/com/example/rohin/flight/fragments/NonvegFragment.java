package com.example.rohin.flight.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rohin.flight.List.EventLocation;
import com.example.rohin.flight.List.Events;
import com.example.rohin.flight.List.LocationEvent;
import com.example.rohin.flight.List.NonvegList;
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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rohin on 21-04-2017.
 */

public class NonvegFragment extends Fragment {

    private View nonvg;
    private TextView eggnv_price,fishnv_price,nvcom_price,chickennv_price,muttonnv_price,nvnoodles_price;
    List<String> nonvegpricelist=new ArrayList<String>();
    List<String>nonvegquantitylist=new ArrayList<String>();
    ArrayList<NonvegList>noveg;
    private PreferenceManager store;
    private TextView eggnv_quan,fishnv_quan,nvcom_quan,chickennv_quan,muttonnv_quan,nvnoodles_quan;
    private LinearLayout eggnv_sel,fishnv_sel,nvcom_sel,chickennv_sel,muttonnv_sel,nvnoodles_sel;
    private ImageView egg,fish,prawn,chicken,mutton,nonvegnoodles;
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

        nonvg = inflater.inflate(R.layout.nonveg_selected, container, false);
        eggnv_price=(TextView)nonvg.findViewById(R.id.eggnv_price);
        fishnv_price=(TextView)nonvg.findViewById(R.id.fishnv_price);
        nvcom_price=(TextView)nonvg.findViewById(R.id.nvcom_price);
        chickennv_price=(TextView)nonvg.findViewById(R.id.chickennv_price);
        muttonnv_price=(TextView)nonvg.findViewById(R.id.muttonnv_price);
        nvnoodles_price=(TextView)nonvg.findViewById(R.id.nvnoodles_price);
        eggnv_quan=(TextView)nonvg.findViewById(R.id.eggnv_quan);
        fishnv_quan=(TextView)nonvg.findViewById(R.id.fishnv_quan);
        nvcom_quan=(TextView)nonvg.findViewById(R.id.nvcom_quan);
        chickennv_quan=(TextView)nonvg.findViewById(R.id.chickennv_quan);
        muttonnv_quan=(TextView)nonvg.findViewById(R.id.muttonnv_quan);
        nvnoodles_quan=(TextView)nonvg.findViewById(R.id.nvnoodles_quan);
        eggnv_sel=(LinearLayout)nonvg.findViewById(R.id.eggnv_sel);
        fishnv_sel=(LinearLayout)nonvg.findViewById(R.id.fishnv_sel);
        nvcom_sel=(LinearLayout)nonvg.findViewById(R.id.nvcom_sel);
        chickennv_sel=(LinearLayout)nonvg.findViewById(R.id.chickennv_sel);
        muttonnv_sel=(LinearLayout)nonvg.findViewById(R.id.muttonnv_sel);
        nvnoodles_sel=(LinearLayout)nonvg.findViewById(R.id.nvnoodles_sel);
        egg=(ImageView)nonvg.findViewById(R.id.egg);
        fish=(ImageView)nonvg.findViewById(R.id.fish);
        prawn=(ImageView)nonvg.findViewById(R.id.prawn);
        chicken=(ImageView)nonvg.findViewById(R.id.chicken);
        mutton=(ImageView)nonvg.findViewById(R.id.mutton);
        nonvegnoodles=(ImageView)nonvg.findViewById(R.id.nonvegnoodles);
        store=PreferenceManager.getInstance(getContext());
        valuenonveg();
        String google=store.getNonVeglist();
        Location=store.getLocation();

        Log.d("NonVegList",google);


        return nonvg;
    }
//    @Subscribe
//    public void getLocationMessageEvent(EventLocation.LocationMessage activityFragmentMessage) {
//        store.putLocation(activityFragmentMessage.getLocation());
//        Location=activityFragmentMessage.getLocation();
//        Toast.makeText(getActivity(),
//                " Selected Location is : " + activityFragmentMessage.getLocation(),
//                Toast.LENGTH_SHORT).show();
//    }
@org.greenrobot.eventbus.Subscribe
public void onMessage(EventLocation event){
    String value=event.getLocation();
    Location=value;

}
    public void valuenonveg(){

        try{
            Gson gson=new Gson();
            Type type1=new TypeToken<List<NonvegList>>(){}.getType();
            String google=store.getNonVeglist();
            noveg=gson.fromJson(google,type1);

        }catch (Exception e){
            e.printStackTrace();
        }

        try {

            for (int l=0;l<noveg.size();l++){
                NonvegList q=noveg.get(l);
                nonvegpricelist.add(q.getPrice());
                nonvegquantitylist.add(q.getQuantity());
                int s=l;
                switch (s) {
                    case 0:
                        eggnv_price.setText(q.getPrice());
                        eggnv_quan.setText(q.getQuantity());
                        break;
                    case 1:
                        fishnv_price.setText(q.getPrice());
                        fishnv_quan.setText(q.getQuantity());
                        break;
                    case 2:
                        nvcom_price.setText(q.getPrice());
                        nvcom_quan.setText(q.getQuantity());
                        break;
                    case 3:
                        chickennv_price.setText(q.getPrice());
                        chickennv_quan.setText(q.getQuantity());
                        break;
                    case 4:
                        muttonnv_price.setText(q.getPrice());
                        muttonnv_quan.setText(q.getQuantity());
                        break;
                    case 5:
                        nvnoodles_price.setText(q.getPrice());
                        nvnoodles_quan.setText(q.getQuantity());
                        break;

                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        eggnv_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SelectedSeat=store.getSeatNumber();
                String drivinglocation=store.getLocation();

                if(SelectedSeat==null){
                    Toast.makeText(getContext(),"Please select seat number first!",Toast.LENGTH_SHORT).show();
                }else{
                    NonVegClicked(25);
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
                                userPurchasedItems.setPrice(eggnv_price.getText().toString());
                                userPurchasedItems.setQuantity(eggnv_quan.getText().toString());
                                userPurchasedItems.setName("Egg");
                                userPurchasedItems.setType("nonveg");
                                userPurchasedItems.setImage(R.drawable.egg1);
                                iuserPurchasedItemses.add(userPurchasedItems);
                            }  else{
                                UserPurchasedItems userPurchasedItems=new UserPurchasedItems();
                                userPurchasedItems.setPrice(eggnv_price.getText().toString());
                                userPurchasedItems.setQuantity(eggnv_quan.getText().toString());
                                userPurchasedItems.setName("Egg");
                                userPurchasedItems.setType("nonveg");
                                userPurchasedItems.setImage(R.drawable.egg1);

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
                        userPurchasedItems.setPrice(eggnv_price.getText().toString());
                        userPurchasedItems.setQuantity(eggnv_quan.getText().toString());
                        userPurchasedItems.setName("Egg");
                        userPurchasedItems.setType("nonveg");
                        userPurchasedItems.setImage(R.drawable.egg1);

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
//                        egg.setBackgroundDrawable(getResources().getDrawable(R.drawable.egg3));
//                    }else {
//                        clicked=false;
//                        egg.setBackgroundDrawable(getResources().getDrawable(R.drawable.egg2));
//
//                    }
                }


            }
        });

        fishnv_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SelectedSeat=store.getSeatNumber();
                String drivinglocation=store.getLocation();
                if(SelectedSeat==null){
                    Toast.makeText(getContext(),"Please select seat number first!",Toast.LENGTH_SHORT).show();

                }else {
                    NonVegClicked(26);

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
                                userPurchasedItems.setPrice(fishnv_price.getText().toString());
                                userPurchasedItems.setQuantity(fishnv_quan.getText().toString());
                                userPurchasedItems.setName("Fish");
                                userPurchasedItems.setType("nonveg");
                                userPurchasedItems.setImage(R.drawable.fish1);

                                iuserPurchasedItemses.add(userPurchasedItems);
                            }  else{
                                UserPurchasedItems userPurchasedItems=new UserPurchasedItems();
                                userPurchasedItems.setPrice(fishnv_price.getText().toString());
                                userPurchasedItems.setQuantity(fishnv_quan.getText().toString());
                                userPurchasedItems.setName("Fish");
                                userPurchasedItems.setType("nonveg");
                                userPurchasedItems.setImage(R.drawable.fish1);

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
                        userPurchasedItems.setPrice(fishnv_price.getText().toString());
                        userPurchasedItems.setQuantity(fishnv_quan.getText().toString());
                        userPurchasedItems.setName("Fish");
                        userPurchasedItems.setType("nonveg");
                        userPurchasedItems.setImage(R.drawable.fish1);

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
//                        fish.setBackgroundDrawable(getResources().getDrawable(R.drawable.fish3));
//                    }else {
//                        clicked1=false;
//                        fish.setBackgroundDrawable(getResources().getDrawable(R.drawable.fish2));
//
//                    }
                }



            }
        });

        nvcom_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SelectedSeat=store.getSeatNumber();
                String drivinglocation=store.getLocation();
                if(SelectedSeat==null){
                    Toast.makeText(getContext(),"Please select seat number first!",Toast.LENGTH_SHORT).show();

                }else{
                    NonVegClicked(27);

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
                                userPurchasedItems.setPrice(nvcom_price.getText().toString());
                                userPurchasedItems.setQuantity(nvcom_quan.getText().toString());
                                userPurchasedItems.setName("Prawn");
                                userPurchasedItems.setType("nonveg");
                                userPurchasedItems.setImage(R.drawable.prawn1);

                                iuserPurchasedItemses.add(userPurchasedItems);
                            }  else{
                                UserPurchasedItems userPurchasedItems=new UserPurchasedItems();
                                userPurchasedItems.setPrice(nvcom_price.getText().toString());
                                userPurchasedItems.setQuantity(nvcom_quan.getText().toString());
                                userPurchasedItems.setName("Prawn");
                                userPurchasedItems.setType("nonveg");
                                userPurchasedItems.setImage(R.drawable.prawn1);

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
                        userPurchasedItems.setPrice(nvcom_price.getText().toString());
                        userPurchasedItems.setQuantity(nvcom_quan.getText().toString());
                        userPurchasedItems.setName("Prawn");
                        userPurchasedItems.setType("nonveg");
                        userPurchasedItems.setImage(R.drawable.prawn1);

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
//                        prawn.setBackgroundDrawable(getResources().getDrawable(R.drawable.prawn3));
//                    }else {
//                        clicked2=false;
//                        prawn.setBackgroundDrawable(getResources().getDrawable(R.drawable.prawn2));
//
//                    }
                }


            }
        });

        chickennv_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SelectedSeat=store.getSeatNumber();
                String drivinglocation=store.getLocation();
                if(SelectedSeat==null){
                    Toast.makeText(getContext(),"Please select seat number first!",Toast.LENGTH_SHORT).show();

                }else{
                    NonVegClicked(28);

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
                                userPurchasedItems.setPrice(chickennv_price.getText().toString());
                                userPurchasedItems.setQuantity(chickennv_quan.getText().toString());
                                userPurchasedItems.setName("Chicken");
                                userPurchasedItems.setType("nonveg");
                                userPurchasedItems.setImage(R.drawable.chicken1);

                                iuserPurchasedItemses.add(userPurchasedItems);
                            }  else{
                                UserPurchasedItems userPurchasedItems=new UserPurchasedItems();
                                userPurchasedItems.setPrice(chickennv_price.getText().toString());
                                userPurchasedItems.setQuantity(chickennv_quan.getText().toString());
                                userPurchasedItems.setName("Chicken");
                                userPurchasedItems.setType("nonveg");
                                userPurchasedItems.setImage(R.drawable.chicken1);

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
                        userPurchasedItems.setPrice(chickennv_price.getText().toString());
                        userPurchasedItems.setQuantity(chickennv_quan.getText().toString());
                        userPurchasedItems.setName("Chicken");
                        userPurchasedItems.setType("nonveg");
                        userPurchasedItems.setImage(R.drawable.chicken1);

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
//                        chicken.setBackgroundDrawable(getResources().getDrawable(R.drawable.chicken3));
//                    }else {
//                        clicked3=false;
//                        chicken.setBackgroundDrawable(getResources().getDrawable(R.drawable.chicken2));
//
//                    }
                }


            }
        });

        muttonnv_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SelectedSeat=store.getSeatNumber();
                String drivinglocation=store.getLocation();
                if(SelectedSeat==null){
                    Toast.makeText(getContext(),"Please select seat number first!",Toast.LENGTH_SHORT).show();

                }else{
                    NonVegClicked(29);

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
                                userPurchasedItems.setPrice(muttonnv_price.getText().toString());
                                userPurchasedItems.setQuantity(muttonnv_quan.getText().toString());
                                userPurchasedItems.setName("Mutton");
                                userPurchasedItems.setType("nonveg");
                                userPurchasedItems.setImage(R.drawable.mutton1);

                                iuserPurchasedItemses.add(userPurchasedItems);
                            }  else{
                                UserPurchasedItems userPurchasedItems=new UserPurchasedItems();
                                userPurchasedItems.setPrice(muttonnv_price.getText().toString());
                                userPurchasedItems.setQuantity(muttonnv_quan.getText().toString());
                                userPurchasedItems.setName("Mutton");
                                userPurchasedItems.setType("nonveg");
                                userPurchasedItems.setImage(R.drawable.mutton1);

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
                        userPurchasedItems.setPrice(muttonnv_price.getText().toString());
                        userPurchasedItems.setQuantity(muttonnv_quan.getText().toString());
                        userPurchasedItems.setName("Mutton");
                        userPurchasedItems.setType("nonveg");
                        userPurchasedItems.setImage(R.drawable.mutton1);

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
//                        mutton.setBackgroundDrawable(getResources().getDrawable(R.drawable.mutton3));
//                    }else {
//                        clicked4=false;
//                        mutton.setBackgroundDrawable(getResources().getDrawable(R.drawable.mutton2));
//
//                    }

                }


            }
        });

        nvnoodles_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SelectedSeat=store.getSeatNumber();
                String drivinglocation=store.getLocation();
                if(SelectedSeat==null){
                    Toast.makeText(getContext(),"Please select seat number first!",Toast.LENGTH_SHORT).show();

                }else{
                    NonVegClicked(30);

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
                                userPurchasedItems.setPrice(nvnoodles_price.getText().toString());
                                userPurchasedItems.setQuantity(nvnoodles_quan.getText().toString());
                                userPurchasedItems.setName("Non Veg Noodles");
                                userPurchasedItems.setType("nonveg");
                                userPurchasedItems.setImage(R.drawable.nv1);

                                iuserPurchasedItemses.add(userPurchasedItems);
                            }  else{
                                UserPurchasedItems userPurchasedItems=new UserPurchasedItems();
                                userPurchasedItems.setPrice(nvnoodles_price.getText().toString());
                                userPurchasedItems.setQuantity(nvnoodles_quan.getText().toString());
                                userPurchasedItems.setName("Non Veg Noodles");
                                userPurchasedItems.setType("nonveg");
                                userPurchasedItems.setImage(R.drawable.nv1);

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
                        userPurchasedItems.setPrice(nvnoodles_price.getText().toString());
                        userPurchasedItems.setQuantity(nvnoodles_quan.getText().toString());
                        userPurchasedItems.setName("Non Veg Noodles");
                        userPurchasedItems.setType("nonveg");
                        userPurchasedItems.setImage(R.drawable.nv1);

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
//                        nonvegnoodles.setBackgroundDrawable(getResources().getDrawable(R.drawable.nv3));
//                    }else {
//                        clicked5=false;
//                        nonvegnoodles.setBackgroundDrawable(getResources().getDrawable(R.drawable.nv2));
//
//                    }
                }


            }
        });

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
        EventBus.getDefault().unregister(this);

        OttoBus.getBus().unregister(this);
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
    @Override
    public void onResume() {
        super.onResume();
        store=PreferenceManager.getInstance(getContext());
        valuenonveg();
    }
    private void NonVegClicked(int id){
        for(int i=0;i<noveg.size();i++){
            NonvegList veglist=noveg.get(i);
            int ids=veglist.getId();
            int image=veglist.getImage();
            String name=veglist.getName();
            String price=veglist.getPrice();
            if(ids==id){
                NonvegList vss=new NonvegList();
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
                    noveg.set(i,vss);
                }


            }


        }
        try{
            Gson gson=new Gson();
            String waggonapitransferlist12=gson.toJson(noveg);
            store.putNonVeglist(waggonapitransferlist12);
            Log.d("veg_list",waggonapitransferlist12);

        }catch (Exception es){
            es.printStackTrace();

        }
        valuenonveg();
    }

}