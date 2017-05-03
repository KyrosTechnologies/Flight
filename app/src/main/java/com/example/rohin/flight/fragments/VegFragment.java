package com.example.rohin.flight.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.rohin.flight.List.EventLocation;
import com.example.rohin.flight.List.Events;
import com.example.rohin.flight.List.LocationEvent;
import com.example.rohin.flight.List.UserList;
import com.example.rohin.flight.List.UserPurchasedItems;
import com.example.rohin.flight.List.VegList;
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

public class VegFragment extends Fragment {

    private View vg;
    private TextView vegtha_price,salad_price,tikka_price,vegsoup_price,vegsnack_price,vegnoodles_price;
    List<String>vegpricelist=new ArrayList<String>();
    List<String>vegquantitylist=new ArrayList<String>();
    ArrayList<VegList>veg;
    private PreferenceManager store;
    private TextView vegtha_quan,salad_quan,tikka_quan,vegsoup_quan,vegsnack_quan,vegnoodles_quan;
    private LinearLayout vegtha_sel,salad_sel,tikka_sel,vegsoup_sel,vegsnack_sel,vegnoodles_sel;
    private ImageView veg_thalli,fruit_salad,paneer_tikka,veg_soup,veg_snack,veg_noodles;
    private boolean clicked=false;
    private boolean clicked1=false;
    private boolean clicked2=false;
    private boolean clicked3=false;
    private boolean clicked4=false;
    private boolean clicked5=false;
    private String userPurchasedString=null;
    private String Location;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vg = inflater.inflate(R.layout.veg_selected, container, false);
        vegtha_price=(TextView)vg.findViewById(R.id.vegtha_price);
        salad_price=(TextView)vg.findViewById(R.id.salad_price);
        tikka_price=(TextView)vg.findViewById(R.id.tikka_price);
        vegsoup_price=(TextView)vg.findViewById(R.id.vegsoup_price);
        vegsnack_price=(TextView)vg.findViewById(R.id.vegsnack_price);
        vegnoodles_price=(TextView)vg.findViewById(R.id.vegnoodles_price);
        vegtha_quan=(TextView)vg.findViewById(R.id.vegtha_quan);
        salad_quan=(TextView)vg.findViewById(R.id.salad_quan);
        tikka_quan=(TextView)vg.findViewById(R.id.tikka_quan);
        vegsoup_quan=(TextView)vg.findViewById(R.id.vegsoup_quan);
        vegsnack_quan=(TextView)vg.findViewById(R.id.vegsnack_quan);
        vegnoodles_quan=(TextView)vg.findViewById(R.id.vegnoodles_quan);
        vegtha_sel=(LinearLayout)vg.findViewById(R.id.vegtha_sel);
        salad_sel=(LinearLayout)vg.findViewById(R.id.salad_sel);
        tikka_sel=(LinearLayout)vg.findViewById(R.id.tikka_sel);
        vegsoup_sel=(LinearLayout)vg.findViewById(R.id.vegsoup_sel);
        vegsnack_sel=(LinearLayout)vg.findViewById(R.id.vegsnack_sel);
        vegnoodles_sel=(LinearLayout)vg.findViewById(R.id.vegnoodles_sel);
        veg_thalli=(ImageView)vg.findViewById(R.id.veg_thalli);
        fruit_salad=(ImageView)vg.findViewById(R.id.fruit_salad);
        paneer_tikka=(ImageView)vg.findViewById(R.id.paneer_tikka);
        veg_soup=(ImageView)vg.findViewById(R.id.veg_soup);
        veg_snack=(ImageView)vg.findViewById(R.id.veg_snack);
        veg_noodles=(ImageView)vg.findViewById(R.id.veg_noodles);
        store=PreferenceManager.getInstance(getContext());


        valueveg();


        String vegliststring=store.getVeglist();
        Location =store.getLocation();
        Log.d("vegliststring", vegliststring);


        return vg;
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
        store.putSeatNumber(activityFragmentMessage.getMessage());

    }
//    @Subscribe
//    public void getLocationMessageEvent(EventLocation.LocationMessage activityFragmentMessage) {
//        store.putLocation(activityFragmentMessage.getLocation());
//        Location=activityFragmentMessage.getLocation();
//        Toast.makeText(getActivity(),
//               " Selected Location  is : " + activityFragmentMessage.getLocation(),
//                Toast.LENGTH_SHORT).show();
//    }

    @org.greenrobot.eventbus.Subscribe
    public void onMessage(EventLocation event){
        String value=event.getLocation();
        Location=value;

    }
    public void valueveg(){

        try{
            Gson gson=new Gson();
            Type type1=new TypeToken<List<VegList>>(){}.getType();
            String google=store.getVeglist();
            veg=gson.fromJson(google,type1);

        }catch (Exception e){
            e.printStackTrace();
        }

        try {

            for (int k=0;k<veg.size();k++){
                VegList p=veg.get(k);
                vegpricelist.add(p.getPrice());
                vegquantitylist.add(p.getQuantity());
                int s=k;
                switch (s) {
                    case 0:
                        vegtha_price.setText(p.getPrice());
                        vegtha_quan.setText(p.getQuantity());
                        break;
                    case 1:
                        salad_price.setText(p.getPrice());
                        salad_quan.setText(p.getQuantity());
                        break;
                    case 2:
                        tikka_price.setText(p.getPrice());
                        tikka_quan.setText(p.getQuantity());
                        break;
                    case 3:
                        vegsoup_price.setText(p.getPrice());
                        vegsoup_quan.setText(p.getQuantity());
                        break;
                    case 4:
                        vegsnack_price.setText(p.getPrice());
                        vegsnack_quan.setText(p.getQuantity());
                        break;
                    case 5:
                        vegnoodles_price.setText(p.getPrice());
                        vegnoodles_quan.setText(p.getQuantity());
                        break;

                }
            }


        }catch (Exception e){
            e.printStackTrace();
        }

        vegtha_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SelectedSeat=store.getSeatNumber();
                String drivinglocation=store.getLocation();

                if(SelectedSeat==null){
                    Toast.makeText(getContext(),"Please select seat number first!",Toast.LENGTH_SHORT).show();
                }else{



                    VegClicked(19);


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
                                userPurchasedItems.setPrice(vegtha_price.getText().toString());
                                userPurchasedItems.setQuantity(vegtha_quan.getText().toString());
                                userPurchasedItems.setName("Veg Thalli");
                                userPurchasedItems.setType("veg");
                                userPurchasedItems.setImage(R.drawable.thalli1);
                                iuserPurchasedItemses.add(userPurchasedItems);
                            }  else{
                                UserPurchasedItems userPurchasedItems=new UserPurchasedItems();
                                userPurchasedItems.setPrice(vegtha_price.getText().toString());
                                userPurchasedItems.setQuantity(vegtha_quan.getText().toString());
                                userPurchasedItems.setName("Veg Thalli");
                                userPurchasedItems.setType("veg");
                                userPurchasedItems.setImage(R.drawable.thalli1);

                                userPurchasedItemses.add(userPurchasedItems);



                                UserList userList=new UserList();
                                userList.setPaymentdone(false);
                                userList.setSeatnumber(SelectedSeat);
                                userList.setUserPurchaseList(userPurchasedItemses);
                                userList.setLocation(drivinglocation);
                                userListArrayList.add(userList);
                            }







                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }else {
                        UserPurchasedItems userPurchasedItems=new UserPurchasedItems();
                        userPurchasedItems.setPrice(vegtha_price.getText().toString());
                        userPurchasedItems.setQuantity(vegtha_quan.getText().toString());
                        userPurchasedItems.setName("Veg Thalli");
                        userPurchasedItems.setType("veg");
                        userPurchasedItems.setImage(R.drawable.thalli1);

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


                    if (!clicked){
                        clicked=true;
                        veg_thalli.setBackgroundDrawable(getResources().getDrawable(R.drawable.thalli3));
                    }else {
                        clicked=false;
                        veg_thalli.setBackgroundDrawable(getResources().getDrawable(R.drawable.thalli2));

                    }
                }


            }
        });

        salad_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SelectedSeat=store.getSeatNumber();
                String drivinglocation=store.getLocation();

                if(SelectedSeat==null){
                    Toast.makeText(getContext(),"Please select seat number first!",Toast.LENGTH_SHORT).show();
                }else{
                    VegClicked(20);

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

                            ArrayList<String > seatname=new ArrayList<String>();
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
                                userPurchasedItems.setPrice(salad_price.getText().toString());
                                userPurchasedItems.setQuantity(salad_quan.getText().toString());
                                userPurchasedItems.setName("Fruit Salad");
                                userPurchasedItems.setType("veg");
                                userPurchasedItems.setImage(R.drawable.fruit1);
                                iuserPurchasedItemses.add(userPurchasedItems);
                            }else{
                                UserPurchasedItems userPurchasedItems=new UserPurchasedItems();
                                userPurchasedItems.setPrice(salad_price.getText().toString());
                                userPurchasedItems.setQuantity(salad_quan.getText().toString());
                                userPurchasedItems.setName("Fruit Salad");
                                userPurchasedItems.setType("veg");
                                userPurchasedItems.setImage(R.drawable.fruit1);

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
                    }else{
                        UserPurchasedItems userPurchasedItems=new UserPurchasedItems();
                        userPurchasedItems.setPrice(salad_price.getText().toString());
                        userPurchasedItems.setQuantity(salad_quan.getText().toString());
                        userPurchasedItems.setName("Fruit Salad");
                        userPurchasedItems.setType("veg");
                        userPurchasedItems.setImage(R.drawable.fruit1);

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
//


                    if (!clicked1){
                        clicked1=true;
                        fruit_salad.setBackgroundDrawable(getResources().getDrawable(R.drawable.fruit3));
                    }else {
                        clicked1=false;
                        fruit_salad.setBackgroundDrawable(getResources().getDrawable(R.drawable.fruit2));

                    }
                }

            }
        });

        tikka_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SelectedSeat=store.getSeatNumber();
                String drivinglocation=store.getLocation();

                if(SelectedSeat==null){
                    Toast.makeText(getContext(),"Please select seat number first!",Toast.LENGTH_SHORT).show();
                }else{
                    VegClicked(21);

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

                            ArrayList<String > seatname=new ArrayList<String>();
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
                                userPurchasedItems.setPrice(tikka_price.getText().toString());
                                userPurchasedItems.setQuantity(tikka_quan.getText().toString());
                                userPurchasedItems.setName("Paneer Tikka");
                                userPurchasedItems.setType("veg");
                                userPurchasedItems.setImage(R.drawable.paneer1);
                                iuserPurchasedItemses.add(userPurchasedItems);
                            }else{
                                UserPurchasedItems userPurchasedItems=new UserPurchasedItems();
                                userPurchasedItems.setPrice(tikka_price.getText().toString());
                                userPurchasedItems.setQuantity(tikka_quan.getText().toString());
                                userPurchasedItems.setName("Paneer Tikka");
                                userPurchasedItems.setType("veg");
                                userPurchasedItems.setImage(R.drawable.paneer1);

                                userPurchasedItemses.add(userPurchasedItems);



                                UserList userList=new UserList();
                                userList.setSeatnumber(SelectedSeat);
                                userList.setPaymentdone(false);
                                userList.setLocation(drivinglocation);

                                userList.setUserPurchaseList(userPurchasedItemses);
                                userListArrayList.add(userList);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else{
                        UserPurchasedItems userPurchasedItems=new UserPurchasedItems();
                        userPurchasedItems.setPrice(tikka_price.getText().toString());
                        userPurchasedItems.setQuantity(tikka_quan.getText().toString());
                        userPurchasedItems.setName("Paneer Tikka");
                        userPurchasedItems.setType("veg");
                        userPurchasedItems.setImage(R.drawable.paneer1);

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


                    if (!clicked2){
                        clicked2=true;
                        paneer_tikka.setBackgroundDrawable(getResources().getDrawable(R.drawable.paneer3));
                    }else {
                        clicked2=false;
                        paneer_tikka.setBackgroundDrawable(getResources().getDrawable(R.drawable.paneer2));

                    }
                }

            }
        });

        vegsoup_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SelectedSeat=store.getSeatNumber();
                String drivinglocation=store.getLocation();

                if(SelectedSeat==null){
                    Toast.makeText(getContext(),"Please select seat number first!",Toast.LENGTH_SHORT).show();
                }else{
                    VegClicked(22);

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
                            ArrayList<String > seatname=new ArrayList<String>();
                            for(int i=0;i<arraytotalsize;i++){
                                String iseatnumber=userListArrayList.get(i).getSeatnumber();
                                seatname.add(iseatnumber);
                            }
                            String value= getmatched(SelectedSeat,seatname);
                            int position=0;
                            if(!value.equals("false")) {
                                position = Integer.parseInt(value);
                                UserList iuserlist = userListArrayList.get(position);
                                ArrayList<UserPurchasedItems> iuserPurchasedItemses = iuserlist.getUserPurchaseList();
                                UserPurchasedItems userPurchasedItems = new UserPurchasedItems();
                                userPurchasedItems.setPrice(vegsoup_price.getText().toString());
                                userPurchasedItems.setQuantity(vegsoup_quan.getText().toString());
                                userPurchasedItems.setName("Veggie Soup");
                                userPurchasedItems.setType("veg");
                                userPurchasedItems.setImage(R.drawable.soup1);
                                iuserPurchasedItemses.add(userPurchasedItems);
                            }else{
                                UserPurchasedItems userPurchasedItems=new UserPurchasedItems();
                                userPurchasedItems.setPrice(vegsoup_price.getText().toString());
                                userPurchasedItems.setQuantity(vegsoup_quan.getText().toString());
                                userPurchasedItems.setName("Veggie Soup");
                                userPurchasedItems.setType("veg");
                                userPurchasedItems.setImage(R.drawable.soup1);

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
                    }else{
                        UserPurchasedItems userPurchasedItems=new UserPurchasedItems();
                        userPurchasedItems.setPrice(vegsoup_price.getText().toString());
                        userPurchasedItems.setQuantity(vegsoup_quan.getText().toString());
                        userPurchasedItems.setName("Veggie Soup");
                        userPurchasedItems.setType("veg");
                        userPurchasedItems.setImage(R.drawable.soup1);

                        userPurchasedItemses.add(userPurchasedItems);



                        UserList userList=new UserList();
                        userList.setPaymentdone(false);
                        userList.setLocation(drivinglocation);

                        userList.setSeatnumber(SelectedSeat);
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
                    if (!clicked3){
                        clicked3=true;
                        veg_soup.setBackgroundDrawable(getResources().getDrawable(R.drawable.soup3));
                    }else {
                        clicked3=false;
                        veg_soup.setBackgroundDrawable(getResources().getDrawable(R.drawable.soup2));

                    }
                }


            }
        });

        vegsnack_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SelectedSeat=store.getSeatNumber();
                String drivinglocation=store.getLocation();
                if(SelectedSeat==null){
                    Toast.makeText(getContext(),"Please select seat number first!",Toast.LENGTH_SHORT).show();
                }else{
                    VegClicked(23);

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
                            if(!value.equals("false")) {
                                position = Integer.parseInt(value);
                                UserList iuserlist = userListArrayList.get(position);
                                ArrayList<UserPurchasedItems> iuserPurchasedItemses = iuserlist.getUserPurchaseList();
                                UserPurchasedItems userPurchasedItems = new UserPurchasedItems();
                                userPurchasedItems.setPrice(vegsnack_price.getText().toString());
                                userPurchasedItems.setQuantity(vegsnack_quan.getText().toString());
                                userPurchasedItems.setName("Veggie Snack");
                                userPurchasedItems.setType("veg");
                                userPurchasedItems.setImage(R.drawable.snack1);
                                iuserPurchasedItemses.add(userPurchasedItems);
                            }else{
                                UserPurchasedItems userPurchasedItems=new UserPurchasedItems();
                                userPurchasedItems.setPrice(vegsnack_price.getText().toString());
                                userPurchasedItems.setQuantity(vegsnack_quan.getText().toString());
                                userPurchasedItems.setName("Veggie Snack");
                                userPurchasedItems.setType("veg");
                                userPurchasedItems.setImage(R.drawable.snack1);

                                userPurchasedItemses.add(userPurchasedItems);


                                UserList userList=new UserList();
                                userList.setSeatnumber(SelectedSeat);
                                userList.setPaymentdone(false);
                                userList.setLocation(drivinglocation);

                                userList.setUserPurchaseList(userPurchasedItemses);
                                userListArrayList.add(userList);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else{
                        UserPurchasedItems userPurchasedItems=new UserPurchasedItems();
                        userPurchasedItems.setPrice(vegsnack_price.getText().toString());
                        userPurchasedItems.setQuantity(vegsnack_quan.getText().toString());
                        userPurchasedItems.setName("Veggie Snack");
                        userPurchasedItems.setType("veg");
                        userPurchasedItems.setImage(R.drawable.snack1);

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
                    if (!clicked4){
                        clicked4=true;
                        veg_snack.setBackgroundDrawable(getResources().getDrawable(R.drawable.snack3));
                    }else {
                        clicked4=false;
                        veg_snack.setBackgroundDrawable(getResources().getDrawable(R.drawable.snack2));

                    }
                }

            }
        });

        vegnoodles_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SelectedSeat=store.getSeatNumber();
                String drivinglocation=store.getLocation();

                if(SelectedSeat==null){
                    Toast.makeText(getContext(),"Please select seat number first!",Toast.LENGTH_SHORT).show();
                }else{
                    VegClicked(24);

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
//                            UserList iuserlist=userListArrayList.get(i);
                                String iseatnumber=userListArrayList.get(i).getSeatnumber();
                                seatname.add(iseatnumber);
                            }
                            String value= getmatched(SelectedSeat,seatname);
                            int position=0;
                            if(!value.equals("false")) {
                                position = Integer.parseInt(value);
                                UserList iuserlist = userListArrayList.get(position);
                                ArrayList<UserPurchasedItems> iuserPurchasedItemses = iuserlist.getUserPurchaseList();
                                UserPurchasedItems userPurchasedItems = new UserPurchasedItems();
                                userPurchasedItems.setPrice(vegnoodles_price.getText().toString());
                                userPurchasedItems.setQuantity(vegnoodles_quan.getText().toString());
                                userPurchasedItems.setName("Veg Noodles");
                                userPurchasedItems.setType("veg");
                                userPurchasedItems.setImage(R.drawable.vegn1);
                                iuserPurchasedItemses.add(userPurchasedItems);
                            }else{
                                UserPurchasedItems userPurchasedItems=new UserPurchasedItems();
                                userPurchasedItems.setPrice(vegnoodles_price.getText().toString());
                                userPurchasedItems.setQuantity(vegnoodles_quan.getText().toString());
                                userPurchasedItems.setName("Veg Noodles");
                                userPurchasedItems.setType("veg");
                                userPurchasedItems.setImage(R.drawable.vegn1);

                                userPurchasedItemses.add(userPurchasedItems);


                                UserList userList=new UserList();
                                userList.setSeatnumber(SelectedSeat);
                                userList.setPaymentdone(false);
                                userList.setLocation(drivinglocation);

                                userList.setUserPurchaseList(userPurchasedItemses);
                                userListArrayList.add(userList);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else{
                        UserPurchasedItems userPurchasedItems=new UserPurchasedItems();
                        userPurchasedItems.setPrice(vegnoodles_price.getText().toString());
                        userPurchasedItems.setQuantity(vegnoodles_quan.getText().toString());
                        userPurchasedItems.setName("Veg Noodles");
                        userPurchasedItems.setType("veg");
                        userPurchasedItems.setImage(R.drawable.vegn1);

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
                    if (!clicked5){
                        clicked5=true;
                        veg_noodles.setBackgroundDrawable(getResources().getDrawable(R.drawable.vegn3));
                    }else {
                        clicked5=false;
                        veg_noodles.setBackgroundDrawable(getResources().getDrawable(R.drawable.vegn2));

                    }
                }


            }
        });

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
        private void VegClicked(int id){
            for(int i=0;i<veg.size();i++){
                VegList veglist=veg.get(i);
                int ids=veglist.getId();
                int image=veglist.getImage();
                String name=veglist.getName();
                String price=veglist.getPrice();
                if(ids==id){
                    VegList vss=new VegList();
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
                        veg.set(i,vss);
                    }


                }


            }
            try{
                Gson gson=new Gson();
                String waggonapitransferlist12=gson.toJson(veg);
                store.putVeglist(waggonapitransferlist12);
                Log.d("veg_list",waggonapitransferlist12);

            }catch (Exception es){
                es.printStackTrace();

            }
valueveg();
        }
    @Override
    public void onResume() {
        super.onResume();
        valueveg();
    }
}