package com.example.rohin.flight;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rohin.flight.List.BeveragesList;
import com.example.rohin.flight.List.NonvegList;
import com.example.rohin.flight.List.VegList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rohin on 21-04-2017.
 */

public class StockAdd extends AppCompatActivity {

    private Button save_stock;
    private EditText quantity_enter,price;
    private int Listitemvalue;
    private com.example.rohin.flight.SharedPreference.PreferenceManager store;
    ArrayList<VegList> veg;
    ArrayList<BeveragesList>bev;
    ArrayList<NonvegList>noveg;
    private String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_add);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.ios)));
        actionBar.setDisplayHomeAsUpEnabled(true);
        save_stock=(Button)findViewById(R.id.save_stock);
        price=(EditText)findViewById(R.id.price);
        quantity_enter=(EditText)findViewById(R.id.quantity_enter);
        store= com.example.rohin.flight.SharedPreference.PreferenceManager.getInstance(StockAdd.this);
        try{

            Bundle bundle=getIntent().getExtras();
            Listitemvalue=bundle.getInt("Listitemvalue");
            item=bundle.getString("item");

        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            Gson gson=new Gson();
            Type type1=new TypeToken<List<VegList>>(){}.getType();
            String google=store.getVeglist();
            veg=gson.fromJson(google,type1);

        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            Gson gson=new Gson();
            Type type1=new TypeToken<List<NonvegList>>(){}.getType();
            String google=store.getNonVeglist();
            noveg=gson.fromJson(google,type1);

        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            Gson gson=new Gson();
            Type type1=new TypeToken<List<BeveragesList>>(){}.getType();
            String google=store.getBevelist();
            bev=gson.fromJson(google,type1);

        }catch (Exception e){
            e.printStackTrace();
        }


        save_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                store.putquantity_enter(quantity_enter.getText().toString());
//                store.putPrice(price.getText().toString());
//                store.putListitemvalue(Listitemvalue);
                String pricevalue=price.getText().toString();
                String quantityvalue=quantity_enter.getText().toString();
                switch (item){
                    case "veg":
                        veg.get(Listitemvalue).setPrice(pricevalue);
                        veg.get(Listitemvalue).setQuantity(quantityvalue);
                        try{
                            Gson gson=new Gson();
                            String waggonapitransferlist12=gson.toJson(veg);
                            store.putVeglist(waggonapitransferlist12);
                            Log.d("values veg",waggonapitransferlist12);

                        }catch (Exception es){
                            es.printStackTrace();

                        }
                        break;

                    case "nonveg":
                        noveg.get(Listitemvalue).setPrice(pricevalue);
                        noveg.get(Listitemvalue).setQuantity(quantityvalue);
                        try{
                            Gson gson=new Gson();
                            String waggonapitransferlist12=gson.toJson(noveg);
                            store.putNonVeglist(waggonapitransferlist12);
                            Log.d("values nonveg",waggonapitransferlist12);


                        }catch (Exception es){
                            es.printStackTrace();

                        }
                        break;

                    case "bev":
                        bev.get(Listitemvalue).setPrice(pricevalue);
                        bev.get(Listitemvalue).setQuantity(quantityvalue);
                        try{
                            Gson gson=new Gson();
                            String waggonapitransferlist12=gson.toJson(bev);
                            store.putBevelist(waggonapitransferlist12);
                            Log.d("values bev",waggonapitransferlist12);


                        }catch (Exception es){
                            es.printStackTrace();
                        }
                        break;
                }
              StockAdd.this.finish();


            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                StockAdd.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
