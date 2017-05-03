package com.example.rohin.flight;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rohin.flight.List.UserList;
import com.example.rohin.flight.List.UserPurchasedItems;
import com.example.rohin.flight.adapter.AddCartAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AddCartActivity extends AppCompatActivity {
    private String seatnumber=null;
    private com.example.rohin.flight.SharedPreference.PreferenceManager store;
    ArrayList<UserList>userListArrayList;
    private RecyclerView recycler_cart;
    private AddCartAdapter addCartAdapter;
    private Button proceed_cart;
    private TextView seat_number_cart,not_purchased;
    private ArrayList<UserList>another_list;
    private TextView price_and_quantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cart);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.ios)));
        actionBar.setDisplayHomeAsUpEnabled(true);
        store= com.example.rohin.flight.SharedPreference.PreferenceManager.getInstance(this);
        seatnumber=store.getSeatNumber();
        proceed_cart=(Button)findViewById(R.id.proceed_cart);
        seat_number_cart=(TextView)findViewById(R.id.seat_number_cart);
        not_purchased=(TextView)findViewById(R.id.not_purchased);
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<UserList>>() {
            }.getType();
            String google = store.getUserPurchasedList();
            userListArrayList = gson.fromJson(google, type);
            another_list=userListArrayList;
        }catch (Exception e){
            e.printStackTrace();
        }



        if(seatnumber==null){
            Toast.makeText(getApplicationContext(),"Seat Number is empty!",Toast.LENGTH_SHORT).show();
        }else{
            setTExt(seatnumber);
            AsyncLoop asyncLoop=new AsyncLoop(AddCartActivity.this);
            asyncLoop.execute();
        }
        proceed_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Asynccheckout asynccheckout=new Asynccheckout(AddCartActivity.this);
                asynccheckout.execute();

              AddCartActivity.this.finish();
            }
        });
    }
private void setTExt(String value){
    value="Selected seat number is : "+value;
    seat_number_cart.setText(value);
}
    @Override
    protected void onStop() {
        super.onStop();
        try{
            AsyncLoop asyncLoop=new AsyncLoop();
            asyncLoop.cancel(true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        try{
            AsyncLoop asyncLoop=new AsyncLoop();
            asyncLoop.cancel(true);
        }catch (Exception e){
            e.printStackTrace();
        }

        super.onDestroy();
    }
public class Asynccheckout extends AsyncTask<String , String,String>{
Context mContext;
public Asynccheckout(Context mContext){
    this.mContext=mContext;
}
public Asynccheckout(){}
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(mContext.getApplicationContext(),"Please wait!",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try{
            Gson gson=new Gson();
            String waggonapitransferlist12=gson.toJson(userListArrayList);
            store.putUserPurchasedList(waggonapitransferlist12);
            Log.d("values veg",waggonapitransferlist12);

        }catch (Exception es){
            es.printStackTrace();

        }

    }

    @Override
    protected String doInBackground(String... params) {

        for(int i=0; i<another_list.size();i++){
            UserList userList=new UserList();
            String seatnumberloop=another_list.get(i).getSeatnumber();
            boolean purchased=another_list.get(i).isPaymentdone();
           ArrayList<UserPurchasedItems> purchaseidtem=userListArrayList.get(i).getUserPurchaseList();
            if(seatnumberloop!=null&&seatnumberloop.equals(seatnumber)&&!purchased){

                userList.setSeatnumber(seatnumberloop);
                userList.setPaymentdone(true);
                userList.setUserPurchaseList(purchaseidtem);
                another_list.set(i,userList);

            }

        }

        return null;
    }
}
    public class AsyncLoop extends AsyncTask<String,String ,String >{
        Context mContext;
        ArrayList<UserList>newuserListArrayList=new ArrayList<UserList>();
        ArrayList<UserPurchasedItems>newuserPurchasedItemses=new ArrayList<UserPurchasedItems>();
        public AsyncLoop(){

        }
        public AsyncLoop(Context mContext){
            this.mContext=mContext;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(mContext,"Please wait!",Toast.LENGTH_SHORT).show();

        }

        @Override
        protected void onPostExecute(String s) {
            recycler_cart=(RecyclerView) findViewById(R.id.recycler_cart);
            RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
            recycler_cart.setLayoutManager(layoutManager);
            recycler_cart.setItemAnimator(new DefaultItemAnimator());
            if(newuserPurchasedItemses.size()==0){
                Toast.makeText(mContext,"This seat number does not purchased anything",Toast.LENGTH_SHORT).show();
                not_purchased.setVisibility(View.VISIBLE);
                return;
            }
            addCartAdapter=new AddCartAdapter(mContext,newuserPurchasedItemses);
            recycler_cart.setAdapter(addCartAdapter);

            addCartAdapter.notifyDataSetChanged();
            int finaliprice=0;
            for(int j=0;j<newuserPurchasedItemses.size();j++){
                UserPurchasedItems iitems=newuserPurchasedItemses.get(j);
                String iprice=iitems.getPrice();
                int totaliprice=Integer.parseInt(iprice);
                finaliprice=finaliprice+totaliprice;

            }
            String sss="Proceed to Pay Rs : "+finaliprice;
            proceed_cart.setText(sss);


            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... params) {
            UserList userList=new UserList();
            ArrayList<UserPurchasedItems>userPurchasedItemses;

            for(int i=0; i<userListArrayList.size();i++){
                String seatnumberloop=userListArrayList.get(i).getSeatnumber();
                boolean purchased=userListArrayList.get(i).isPaymentdone();
                if(seatnumberloop.equals(seatnumber)&&!purchased){

                    userList.setSeatnumber(seatnumberloop);
                    userList.setPaymentdone(purchased);
                 //  UserList idfs=userListArrayList.get(i);

//                    userPurchasedItemses=idfs.getUserPurchaseList();
                    userPurchasedItemses=userListArrayList.get(i).getUserPurchaseList();

                    for(int k=0 ;k<userPurchasedItemses.size();k++){
                        UserPurchasedItems userPurchasedItems=new UserPurchasedItems();

                        int image=userPurchasedItemses.get(k).getImage();
                        int id=userPurchasedItemses.get(k).getId();
                        String name=userPurchasedItemses.get(k).getName();
                        String price=userPurchasedItemses.get(k).getPrice();
                        String quantity=userPurchasedItemses.get(k).getQuantity();
                        String  type=userPurchasedItemses.get(k).getType();

                        userPurchasedItems.setImage(image);
                        userPurchasedItems.setId(id);
                        userPurchasedItems.setName(name);
                        userPurchasedItems.setPrice(price);
                        userPurchasedItems.setQuantity(quantity);
                        userPurchasedItems.setType(type);
                        newuserPurchasedItemses.add(userPurchasedItems);


                    }
                    userList.setUserPurchaseList(newuserPurchasedItemses);
                    newuserListArrayList.add(userList);


                }


            }

            return null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                AddCartActivity.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
