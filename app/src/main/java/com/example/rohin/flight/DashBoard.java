package com.example.rohin.flight;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rohin.flight.List.UserList;
import com.example.rohin.flight.List.UserPurchasedItems;
import com.example.rohin.flight.SharedPreference.PreferenceManager;
import com.example.rohin.flight.adapter.AddCartAdapter;
import com.example.rohin.flight.adapter.DashBoardAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DashBoard extends AppCompatActivity {
    private TextView dashboard_location_textview;
    private PreferenceManager store;
    private RecyclerView recycler_dashboard;
    private DashBoardAdapter dashBoardAdapter;
    private TextView no_list_trip;
    private ArrayList<UserList>userListArrayList;
    private ArrayList<UserPurchasedItems>itemlist=new ArrayList<UserPurchasedItems>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.ios)));
        actionBar.setDisplayHomeAsUpEnabled(true);
        store=PreferenceManager.getInstance(DashBoard.this);
        dashboard_location_textview=(TextView)findViewById(R.id.dashboard_location_textview);
        no_list_trip=(TextView)findViewById(R.id.no_list_trip);
        String Location=store.getLocation();
        Location="Seleted Location is : "+Location;
        if(Location!=null){
            dashboard_location_textview.setText(Location);
        }

        try {
            ListMethod();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void ListMethod()throws Exception{
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<UserList>>() {
            }.getType();
            String google = store.getUserPurchasedList();
            userListArrayList = gson.fromJson(google, type);

        }catch (Exception e){
            e.printStackTrace();
        }
        for(int i=0; i<userListArrayList.size();i++){
            UserList userList=userListArrayList.get(i);
            String seatno=userList.getSeatnumber();
            String location=userList.getLocation();
            int id= userList.getId();
            ArrayList<UserPurchasedItems>userPurchasedItemses=userList.getUserPurchaseList();
            for(int k=0; k<userPurchasedItemses.size();k++){
                UserPurchasedItems iitems=new UserPurchasedItems();
                UserPurchasedItems userPurchasedItems=userPurchasedItemses.get(k);
                iitems.setImage(userPurchasedItems.getImage());
                iitems.setId(userPurchasedItems.getId());
                iitems.setPrice(userPurchasedItems.getPrice());
                iitems.setName(userPurchasedItems.getName());
                iitems.setType(userPurchasedItems.getType());
                iitems.setQuantity(userPurchasedItems.getQuantity());
                itemlist.add(iitems);

            }
        }
        recycler_dashboard=(RecyclerView) findViewById(R.id.recycler_dashboard);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recycler_dashboard.setLayoutManager(layoutManager);
        recycler_dashboard.setItemAnimator(new DefaultItemAnimator());
        if(itemlist.size()==0){
            recycler_dashboard.setVisibility(View.GONE);
            no_list_trip.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(),"Sorry No List in the trip!",Toast.LENGTH_SHORT).show();
            return;
        }
        dashBoardAdapter=new DashBoardAdapter(DashBoard.this,itemlist);
        recycler_dashboard.setAdapter(dashBoardAdapter);

        dashBoardAdapter.notifyDataSetChanged();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                DashBoard.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
