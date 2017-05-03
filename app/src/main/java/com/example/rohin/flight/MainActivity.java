package com.example.rohin.flight;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rohin.flight.List.BeveragesList;
import com.example.rohin.flight.List.EventLocation;
import com.example.rohin.flight.List.Events;
import com.example.rohin.flight.List.LocationEvent;
import com.example.rohin.flight.List.NonvegList;
import com.example.rohin.flight.List.UserList;
import com.example.rohin.flight.List.UserPurchasedItems;
import com.example.rohin.flight.List.VegList;
import com.example.rohin.flight.SharedPreference.PreferenceManager;
import com.example.rohin.flight.adapter.AdapterExchange;
import com.example.rohin.flight.adapter.AddCartAdapter;
import com.example.rohin.flight.fragments.BeveragesFragment;
import com.example.rohin.flight.fragments.NonvegFragment;
import com.example.rohin.flight.fragments.VegFragment;
import com.example.rohin.flight.otto.LocationOtto;
import com.example.rohin.flight.otto.OttoBus;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import com.squareup.otto.ThreadEnforcer;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,NavigationView.OnNavigationItemSelectedListener {

    BottomSheetDialog dialog;
    private Button save;
    private LinearLayout vegsel,nonvegsel,bevsel;
    private TextView vg,nv,bevr;
    private ArrayList<VegList>vegListArrayList=new ArrayList<VegList>();
    private ArrayList<NonvegList>nonvegListArrayList=new ArrayList<NonvegList>();
    private ArrayList<BeveragesList>beveragesListArrayList=new ArrayList<BeveragesList>();
    private PreferenceManager store;
    private int []id=new int[]{19,20,21,22,23,24};
    private int[]id1=new int[]{25,26,27,28,29,30};
    private int[]id2=new int[]{31,32,33,34,35,36};
    private String[]vegnames=new String[]{"Veg Thalli","Fruit Salad","Paneer Tikka","Veggie Soup","Veggie Snack","Veg Noodles"};
    private String[]nonvegnames=new String[]{"Egg","Fish","Prawn","Chicken","Mutton","Non Veg Noodles"};
    private String[]beveragesnames=new String[]{"Water Bottle","Coca cola","Apple Juice","Orange Juice","Sprite","Maaza"};
    private String[]vegprice=new String[]{"60","50","80","60","60","100"};
    private String[]nonvegprice=new String[]{"30","120","200","100","180","100"};
    private String[]beveprice=new String[]{"50","80","120","100","80","80"};
    private String[]vegquantity=new String[]{"20","30","40","20","30","20"};
    private String[]nonvegquantity=new String[]{"20","30","30","40","30","30"};
    private String[]bevequantity=new String[]{"40","40","40","40","40","40"};
    private RelativeLayout seat_1,seat_2,seat_3,seat_4,seat_5,seat_6,seat_7,seat_8,seat_9,seat_10,seat_11,seat_12,seat_13,seat_14,seat_15;
    ArrayList<UserList>totaluserlist=new ArrayList<UserList>();
    private LinearLayout home,dashboard,stock;
    private Button exchanges;
    private boolean clicked=false;
    private String MainseatSelected="";
    public static Bus bus;
    private RadioButton chennai,bangalore,mumbai;
    private ImageView menu;
    private Button cart_activity;
    private RadioGroup radio_group_main;
    private AdapterExchange adapterexchange;
    DrawerLayout drawer;
    private EventBus eventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vegsel=(LinearLayout)findViewById(R.id.vegsel);
        nonvegsel=(LinearLayout)findViewById(R.id.nonvegsel);
        bevsel=(LinearLayout)findViewById(R.id.bevsel);
        bevr=(TextView)findViewById(R.id.bevr);
        nv=(TextView)findViewById(R.id.nv);
        vg=(TextView)findViewById(R.id.vg);
        cart_activity=(Button) findViewById(R.id.cart_activity);
        store=PreferenceManager.getInstance(MainActivity.this);
        store.putSeatNumber(null);

        seat_1=(RelativeLayout) findViewById(R.id.seat_1);
        seat_2=(RelativeLayout)findViewById(R.id.seat_2);
        seat_3=(RelativeLayout)findViewById(R.id.seat_3);
        seat_4=(RelativeLayout)findViewById(R.id.seat_4);
        seat_5=(RelativeLayout)findViewById(R.id.seat_5);
        seat_6=(RelativeLayout)findViewById(R.id.seat_6);
        seat_7=(RelativeLayout)findViewById(R.id.seat_7);
        seat_8=(RelativeLayout)findViewById(R.id.seat_8);
        seat_9=(RelativeLayout)findViewById(R.id.seat_9);
        seat_10=(RelativeLayout)findViewById(R.id.seat_10);
        seat_11=(RelativeLayout)findViewById(R.id.seat_11);
        seat_12=(RelativeLayout)findViewById(R.id.seat_12);
        seat_13=(RelativeLayout)findViewById(R.id.seat_13);
        seat_14=(RelativeLayout)findViewById(R.id.seat_14);
        seat_15=(RelativeLayout)findViewById(R.id.seat_15);
        home=(LinearLayout) findViewById(R.id.home);
        dashboard=(LinearLayout)findViewById(R.id.dashboard);
        stock=(LinearLayout)findViewById(R.id.stock);
        chennai=(RadioButton)findViewById(R.id.chennai);
        bangalore=(RadioButton)findViewById(R.id.bangalore) ;
        mumbai=(RadioButton)findViewById(R.id.mumbai);
        chennai.setChecked(true);
        eventBus=new EventBus();
        eventBus.register(this);
        if(chennai.isChecked()){
            Toast.makeText(getApplicationContext(),"chennai selected",Toast.LENGTH_SHORT).show();
            sendLocationMessage("chennai");
            store.putLocation("chennai");
            EventBus.getDefault().post(new EventLocation("chennai"));

        }
        bus=new Bus(ThreadEnforcer.MAIN);
        bus.register(this);

        radio_group_main=(RadioGroup)findViewById(R.id.radio_group_main);
//
        radio_group_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
               switch (checkedId){
                   case R.id.chennai:
                       sendLocationMessage("chennai");
                       store.putLocation("chennai");
                       EventBus.getDefault().post(new EventLocation("chennai"));


                       break;
                   case R.id.bangalore:
                       sendLocationMessage("bangalore");
                       store.putLocation("bangalore");
                       EventBus.getDefault().post(new EventLocation("bangalore"));



                       break;
                   case R.id.mumbai:
                       sendLocationMessage("mumbai");
                       store.putLocation("mumbai");
                       EventBus.getDefault().post(new EventLocation("mumbai"));


                       break;
               }
            }
        });
        for (int i=0;i<6;i++){
            VegList vegList=new VegList();
            vegList.setId(id[i]);
//            vegList.setImage();
            vegList.setName(vegnames[i]);
            vegList.setPrice(vegprice[i]);
            vegList.setQuantity(vegquantity[i]);
            vegListArrayList.add(vegList);
        }


        try{
            Gson gson=new Gson();
            String waggonapitransferlist12=gson.toJson(vegListArrayList);
            store.putVeglist(waggonapitransferlist12);
            Log.d("values veg",waggonapitransferlist12);

        }catch (Exception es){
            es.printStackTrace();

        }

        for (int i=0;i<6;i++){
            NonvegList nonvegList=new NonvegList();
            nonvegList.setId(id1[i]);
//            vegList.setImage();
            nonvegList.setName(nonvegnames[i]);
            nonvegList.setPrice(nonvegprice[i]);
            nonvegList.setQuantity(nonvegquantity[i]);
            nonvegListArrayList.add(nonvegList);
        }


        try{
            Gson gson=new Gson();
            String waggonapitransferlist12=gson.toJson(nonvegListArrayList);
            store.putNonVeglist(waggonapitransferlist12);
            Log.d("values nonveg",waggonapitransferlist12);


        }catch (Exception es){
            es.printStackTrace();

        }

        for (int i=0;i<6;i++){
            BeveragesList beveragesList=new BeveragesList();
            beveragesList.setId(id2[i]);
//            vegList.setImage();
            beveragesList.setName(beveragesnames[i]);
            beveragesList.setPrice(beveprice[i]);
            beveragesList.setQuantity(bevequantity[i]);
            beveragesListArrayList.add(beveragesList);
        }
        cart_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String seatnumber=store.getSeatNumber();
                if(seatnumber!=null){
                    Intent intent=new Intent(MainActivity.this,AddCartActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Please select the seat first!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        try{
            Gson gson=new Gson();
            String waggonapitransferlist12=gson.toJson(beveragesListArrayList);
            store.putBevelist(waggonapitransferlist12);
            Log.d("values bev",waggonapitransferlist12);


        }catch (Exception es){
            es.printStackTrace();
        }

        VegFragment frag=new VegFragment();
        android.support.v4.app.FragmentTransaction fragmentTrans=
                getSupportFragmentManager().beginTransaction();
        fragmentTrans.replace(R.id.items,frag);
        fragmentTrans.addToBackStack(null);
        fragmentTrans.commit();
        vegsel.setBackgroundColor(getResources().getColor(R.color.filled));
        vg.setTextColor(getResources().getColor(R.color.white));
        nonvegsel.setBackground(null);
        bevsel.setBackground(null);

        vegsel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VegFragment frag=new VegFragment();
                sendMessageToFragment(store.getSeatNumber());
                android.support.v4.app.FragmentTransaction fragmentTrans=
                        getSupportFragmentManager().beginTransaction();
                fragmentTrans.replace(R.id.items,frag);
                fragmentTrans.addToBackStack(null);
                fragmentTrans.commit();
                vegsel.setBackgroundColor(getResources().getColor(R.color.filled));
                vg.setTextColor(getResources().getColor(R.color.white));
                nonvegsel.setBackground(null);
                bevsel.setBackground(null);


            }
        });

        nonvegsel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NonvegFragment frag=new NonvegFragment();
                sendMessageToFragment(store.getSeatNumber());
                android.support.v4.app.FragmentTransaction fragmentTrans=
                        getSupportFragmentManager().beginTransaction();
                fragmentTrans.replace(R.id.items,frag);
                fragmentTrans.addToBackStack(null);
                fragmentTrans.commit();
                sendMessageToFragment(store.getSeatNumber());
                nonvegsel.setBackgroundColor(getResources().getColor(R.color.filled));
                nv.setTextColor(getResources().getColor(R.color.white));
                bevsel.setBackground(null);
                vegsel.setBackground(null);



            }
        });

        bevsel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BeveragesFragment frag=new BeveragesFragment();
                sendMessageToFragment(store.getSeatNumber());
                android.support.v4.app.FragmentTransaction fragmentTrans=
                        getSupportFragmentManager().beginTransaction();
                fragmentTrans.replace(R.id.items,frag);
                fragmentTrans.addToBackStack(null);
                fragmentTrans.commit();
                sendMessageToFragment(store.getSeatNumber());
                bevsel.setBackgroundColor(getResources().getColor(R.color.filled));
                bevr.setTextColor(getResources().getColor(R.color.white));
                nonvegsel.setBackground(null);
                vegsel.setBackground(null);

            }
        });

        drawer = (DrawerLayout)findViewById(R.id.drawer_layout_dealer);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_dealer);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);


        menu = (ImageView) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);

            }
        });



        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!clicked){
                    clicked=true;
                    home.setBackgroundDrawable(getResources().getDrawable(R.drawable.home2));
                }else {
                    clicked=false;
                    home.setBackgroundDrawable(getResources().getDrawable(R.drawable.home1));

                }            }
        });

        stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,StockFull.class);
                startActivity(i);
            }
        });

        seat_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seat_1.setBackgroundColor(getResources().getColor(R.color.filled));
                seat_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_3.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_4.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_5.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_6.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_7.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_8.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_9.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_10.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_11.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_12.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_13.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_14.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_15.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                int size=totaluserlist.size();

                store.putSeatNumber("A1");
                sendMessageToFragment("A1");


            }
        });

        seat_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seat_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_2.setBackgroundColor(getResources().getColor(R.color.filled));
                seat_3.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_4.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_5.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_6.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_7.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_8.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_9.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_10.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_11.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_12.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_13.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_14.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_15.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                sendMessageToFragment("A2");
                store.putSeatNumber("A2");



            }
        });

        seat_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seat_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_3.setBackgroundColor(getResources().getColor(R.color.filled));
                seat_4.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_5.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_6.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_7.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_8.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_9.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_10.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_11.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_12.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_13.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_14.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_15.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                sendMessageToFragment("A3");
                store.putSeatNumber("A3");



            }
        });

        seat_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seat_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_3.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_4.setBackgroundColor(getResources().getColor(R.color.filled));
                seat_5.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_6.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_7.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_8.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_9.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_10.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_11.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_12.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_13.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_14.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_15.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                store.putSeatNumber("A4");
                sendMessageToFragment("A4");

            }
        });

        seat_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seat_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_3.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_4.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_5.setBackgroundColor(getResources().getColor(R.color.filled));
                seat_6.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_7.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_8.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_9.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_10.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_11.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_12.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_13.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_14.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_15.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                store.putSeatNumber("A5");
                sendMessageToFragment("A5");


            }
        });

        seat_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seat_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_3.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_4.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_5.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_6.setBackgroundColor(getResources().getColor(R.color.filled));
                seat_7.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_8.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_9.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_10.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_11.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_12.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_13.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_14.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_15.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                store.putSeatNumber("A6");
                sendMessageToFragment("A6");


//
            }
        });

        seat_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seat_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_3.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_4.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_5.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_6.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_7.setBackgroundColor(getResources().getColor(R.color.filled));
                seat_8.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_9.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_10.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_11.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_12.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_13.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_14.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_15.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                sendMessageToFragment("A7");
                store.putSeatNumber("A7");


//

            }
        });

        seat_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seat_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_3.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_4.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_5.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_6.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_7.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_8.setBackgroundColor(getResources().getColor(R.color.filled));
                seat_9.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_10.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_12.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_13.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_14.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_15.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                sendMessageToFragment("A8");
                store.putSeatNumber("A8");
//


            }
        });

        seat_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seat_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_3.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_4.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_5.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_6.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_7.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_8.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_9.setBackgroundColor(getResources().getColor(R.color.filled));
                seat_10.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_11.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_12.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_13.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_14.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_15.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                sendMessageToFragment("A9");
                store.putSeatNumber("A9");


            }
        });

        seat_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seat_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_3.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_4.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_5.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_6.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_7.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_8.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_9.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_10.setBackgroundColor(getResources().getColor(R.color.filled));
                seat_11.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_12.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_13.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_14.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_15.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                sendMessageToFragment("A10");
                store.putSeatNumber("A10");

            }
        });

        seat_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seat_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_3.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_4.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_5.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_6.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_7.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_8.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_9.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_10.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_11.setBackgroundColor(getResources().getColor(R.color.filled));
                seat_12.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_13.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_14.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_15.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                sendMessageToFragment("A11");
                store.putSeatNumber("A11");


            }
        });

        seat_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seat_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_3.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_4.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_5.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_6.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_7.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_8.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_9.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_10.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_11.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_12.setBackgroundColor(getResources().getColor(R.color.filled));
                seat_13.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_14.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_15.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                sendMessageToFragment("A12");
                store.putSeatNumber("A12");





            }
        });

        seat_13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seat_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_3.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_4.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_5.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_6.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_7.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_8.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_9.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_10.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_11.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_12.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_13.setBackgroundColor(getResources().getColor(R.color.filled));
                seat_14.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_15.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                sendMessageToFragment("A13");
                store.putSeatNumber("A13");




            }
        });

        seat_14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seat_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_3.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_4.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_5.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_6.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_7.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_8.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_9.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_10.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_11.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_12.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_13.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_14.setBackgroundColor(getResources().getColor(R.color.filled));
                seat_15.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                sendMessageToFragment("A14");
                store.putSeatNumber("A14");


//


            }
        });

        seat_15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seat_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_3.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_4.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_5.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_6.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_7.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_8.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_9.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_10.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_11.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_12.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_13.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_14.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_unfilled));
                seat_15.setBackgroundColor(getResources().getColor(R.color.filled));
                sendMessageToFragment("A15");
                store.putSeatNumber("A15");




            }
        });

        exchanges=(Button) findViewById(R.id.exchanges);
//        exchanges.setOnClickListener(this);
        exchanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String seatnumber= store.getSeatNumber();
                if(seatnumber!=null){
                    init_modal_bottomsheet();
                    dialog.show();

                }else{
                    Toast.makeText(getApplicationContext(),"Please select the seat number!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Persistent BottomSheet

        // Modal BottomSheet

    }
  @org.greenrobot.eventbus.Subscribe
    public void onMessage(EventLocation event){
        String value=event.getLocation();

    }

    @Override
    protected void onStart() {
        super.onStart();
        OttoBus.getBus().register(this);
        EventBus.getDefault().register(this);
    }
    @Override
    protected void onStop() {
        super.onStop();
        OttoBus.getBus().unregister(this);
        EventBus.getDefault().unregister(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        OttoBus.getBus().unregister(this);
    }
    public void sendLocationMessage(String location){
        LocationEvent locationevent=new LocationEvent();
        locationevent.setLocation(location);
        OttoBus.getBus().post(locationevent);

    }
    public void sendMessageToFragment(String seatno) {
        Events.ActivityFragmentMessage activityFragmentMessageEvent =
                new Events.ActivityFragmentMessage(seatno);
        OttoBus.getBus().post(activityFragmentMessageEvent);
    }
//    public void sendLocationEventMessage(String location){
//        EventLocation.LocationMessage locationmessage=new1 EventLocation.LocationMessage(location);
//        LocationOtto.getBus().post(locationmessage);
//    }
    @Subscribe
    public void getMessage(Events.FragmentActivityMessage fragmentActivityMessage) {
        Toast.makeText(getApplicationContext(),
               "fragment message is : " + fragmentActivityMessage.getMessage(),
                Toast.LENGTH_SHORT).show();
    }


    public void onClick(View view) {
        VegFragment frag=new VegFragment();
        android.support.v4.app.FragmentTransaction fragmentTrans=
                getSupportFragmentManager().beginTransaction();
        fragmentTrans.replace(R.id.items,frag);
        fragmentTrans.addToBackStack(null);
        fragmentTrans.commit();
        if (view.getId() == exchanges.getId()) {
            dialog.show();
        } else if (view.getId() == save.getId()) {
            dialog.hide();
        }
    }
    @Subscribe
    public void getMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
    public void init_modal_bottomsheet() {
        View modalbottomsheet = getLayoutInflater().inflate(R.layout.exchange, null);
        RecyclerView recycler_exchange_view=(RecyclerView)modalbottomsheet.findViewById(R.id.recycler_exchange_view);
        String SeatNumber=store.getSeatNumber();
        if(SeatNumber!=null){
            dialog = new BottomSheetDialog(this);
            dialog.setContentView(modalbottomsheet);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
            recycler_exchange_view.setLayoutManager(layoutManager);
            recycler_exchange_view.setItemAnimator(new DefaultItemAnimator());
            String usertotalpurchasestring=store.getUserPurchasedList();
            ArrayList<UserList>userList = new ArrayList<UserList>();
            ArrayList<UserPurchasedItems>iuserpurchaseitems = new ArrayList<UserPurchasedItems>();

            try {
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<UserList>>() {
                }.getType();
                String google = store.getUserPurchasedList();
                userList = gson.fromJson(google, type);
            }catch (Exception e){
                e.printStackTrace();
            }
            if(usertotalpurchasestring!=null){
                if(userList.size()==0){
                    Toast.makeText(getApplicationContext(),"No Exchange list!",Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<UserPurchasedItems>pi = new ArrayList<UserPurchasedItems>();
                for(int k=0;k<userList.size();k++){
                    UserList userList1=userList.get(k);
                    String seatnumber=userList1.getSeatnumber();
                    if(SeatNumber.equals(seatnumber)){
                        pi=userList1.getUserPurchaseList();
                    }

                }

                iuserpurchaseitems=pi;

                    adapterexchange=new AdapterExchange(MainActivity.this,iuserpurchaseitems,SeatNumber);
                    recycler_exchange_view.setAdapter(adapterexchange);

                    adapterexchange.notifyDataSetChanged();



            }else {
                Toast.makeText(getApplicationContext(),"User dont have any purchases",Toast.LENGTH_SHORT).show();
            }


            save = (Button) modalbottomsheet.findViewById(R.id.save);
            save.setOnClickListener(this);
        }


    }

    public boolean getUserExist(String value){
        for(int i=0; i<totaluserlist.size();i++){
            UserList userList=totaluserlist.get(i);
            String existing=userList.getSeatnumber();
            Log.d("USERNAME LIST: ", existing+ " SIZE: "+totaluserlist.size()+"for loop size : "+i);
            if(existing.equals(value)){
                Log.d("existing ",existing+ " / "+" old: "+ value+"true");
                return true;
            }else {
                Log.d("existing ",existing+ " / "+" old: "+ value+"FALSE");

                return false;
            }
        }
        return false;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.dashboard) {
            String location=store.getLocation();
            if(location!=null){
                Intent intent=new Intent(MainActivity.this,DashBoard.class);
                startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(),"Please select the location first!",Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.stocks) {
            Intent i=new Intent(MainActivity.this,StockFull.class);
            startActivity(i);

//        } else if (id == R.id.exchange) {
//          // exchange.setOnClickListener(this);
//           String seatnumber= store.getSeatNumber();
//            if(seatnumber!=null){
//                init_modal_bottomsheet();
//                drawer.closeDrawer(GravityCompat.START);
//                dialog.show();
//
//            }else{
//                Toast.makeText(getApplicationContext(),"Please select the seat number!",Toast.LENGTH_SHORT).show();
//            }



        }
        return true;
    }

}
