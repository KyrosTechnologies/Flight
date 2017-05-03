package com.example.rohin.flight;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rohin.flight.List.BeveragesList;
import com.example.rohin.flight.List.NonvegList;
import com.example.rohin.flight.List.VegList;
import com.example.rohin.flight.SharedPreference.PreferenceManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rohin on 21-04-2017.
 */

public class StockFull extends AppCompatActivity{

    private String quantity_enter,price;
    private int Listitemvalue;

    private TextView thalli,thalli_quantity,fruit,fruit_quan,paneer,paneer_quan,vegsoup,soup_quan,vegsnack,snack_quan,veg_extra,extra_quan,egg_price,egg_quan,
            fish_price,fish_quan,nvcombo_price,nv_quan,chicken_price,chicken_quan,mutton_price,mutton_quan,nonveg_extra,nonveg_quan,water_price,water_quan,coke_price,
            coke_quan,apple_price,apple_quan,orange_price,orange_quan,sprite_price,sprite_quan,bev_price,bev_quan;
    private CardView thalli_card,salad_card,tikka_card,soup_card,snack_card,veg_extra_card,egg_card,fish_card,nv_card,chicken_card,mutton_card,nvextra_card,water_card,
            coke_card,apple_card,orange_card,sprite_card,bev_card;

    private com.example.rohin.flight.SharedPreference.PreferenceManager store;
    ArrayList<VegList>veg;
    ArrayList<BeveragesList>bev;
    ArrayList<NonvegList>noveg;
    List<String>vegpricelist=new ArrayList<String>();
    List<String>nonvegpricelist=new ArrayList<String>();
    List<String>bevpricelist=new ArrayList<String>();
    List<String>vegquantitylist=new ArrayList<String>();
    List<String>nonvegquantitylist=new ArrayList<String>();
    List<String>bevquantitylist=new ArrayList<String>();


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.stock_full);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.ios)));
        actionBar.setDisplayHomeAsUpEnabled(true);
            store= com.example.rohin.flight.SharedPreference.PreferenceManager.getInstance(StockFull.this);
            add();

            thalli=(TextView)findViewById(R.id.thalli);
            thalli_quantity=(TextView)findViewById(R.id.thalli_quantity);
            fruit=(TextView)findViewById(R.id.fruit);
            fruit_quan=(TextView)findViewById(R.id.fruit_quan);
            paneer=(TextView)findViewById(R.id.paneer);
            paneer_quan=(TextView)findViewById(R.id.paneer_quan);
            vegsoup=(TextView)findViewById(R.id.vegsoup);
            soup_quan=(TextView)findViewById(R.id.soup_quan);
            vegsnack=(TextView)findViewById(R.id.vegsnack);
            snack_quan=(TextView)findViewById(R.id.snack_quan);
            veg_extra=(TextView)findViewById(R.id.veg_extra);
            extra_quan=(TextView)findViewById(R.id.extra_quan);
            egg_price=(TextView)findViewById(R.id.egg_price);
            egg_quan=(TextView)findViewById(R.id.egg_quan);
            fish_price=(TextView)findViewById(R.id.fish_price);
            fish_quan=(TextView)findViewById(R.id.fish_quan);
            nvcombo_price=(TextView)findViewById(R.id.nvcombo_price);
            nv_quan=(TextView)findViewById(R.id.nv_quan);
            chicken_price=(TextView)findViewById(R.id.chicken_price);
            chicken_quan=(TextView)findViewById(R.id.chicken_quan);
            mutton_price=(TextView)findViewById(R.id.mutton_price);
            mutton_quan=(TextView)findViewById(R.id.mutton_quan);
            nonveg_extra=(TextView)findViewById(R.id.nonveg_extra);
            nonveg_quan=(TextView)findViewById(R.id.nonveg_quan);
            water_price=(TextView)findViewById(R.id.water_price);
            water_quan=(TextView)findViewById(R.id.water_quan);
            coke_price=(TextView)findViewById(R.id.coke_price);
            coke_quan=(TextView)findViewById(R.id.coke_quan);
            apple_price=(TextView)findViewById(R.id.apple_price);
            apple_quan=(TextView)findViewById(R.id.apple_quan);
            orange_price=(TextView)findViewById(R.id.orange_price);
            orange_quan=(TextView)findViewById(R.id.orange_quan);
            sprite_price=(TextView)findViewById(R.id.sprite_price);
            sprite_quan=(TextView)findViewById(R.id.sprite_quan);
            bev_price=(TextView)findViewById(R.id.bev_price);
            bev_quan=(TextView)findViewById(R.id.bev_quan);
            thalli_card=(CardView)findViewById(R.id.thalli_card);
            salad_card=(CardView)findViewById(R.id.salad_card);
            tikka_card=(CardView)findViewById(R.id.tikka_card);
            soup_card=(CardView)findViewById(R.id.soup_card);
            snack_card=(CardView)findViewById(R.id.snack_card);
            veg_extra_card=(CardView)findViewById(R.id.veg_extra_card);
            egg_card=(CardView)findViewById(R.id.egg_card);
            fish_card=(CardView)findViewById(R.id.fish_card);
            nv_card=(CardView)findViewById(R.id.nv_card);
            chicken_card=(CardView)findViewById(R.id.chicken_card);
            mutton_card=(CardView)findViewById(R.id.mutton_card);
            nvextra_card=(CardView)findViewById(R.id.nvextra_card);
            water_card=(CardView)findViewById(R.id.water_card);
            coke_card=(CardView)findViewById(R.id.coke_card);
            apple_card=(CardView)findViewById(R.id.apple_card);
            orange_card=(CardView)findViewById(R.id.orange_card);
            sprite_card=(CardView)findViewById(R.id.sprite_card);
            bev_card=(CardView)findViewById(R.id.bev_card);

        try {

            for (int k=0;k<veg.size();k++){
                VegList p=veg.get(k);
                vegpricelist.add(p.getPrice());
                vegquantitylist.add(p.getQuantity());
                int s=k;
                switch (s) {
                    case 0:
                        thalli.setText(p.getPrice());
                        thalli_quantity.setText(p.getQuantity());
                        break;
                    case 1:
                        fruit.setText(p.getPrice());
                        fruit_quan.setText(p.getQuantity());
                        break;
                    case 2:
                        paneer.setText(p.getPrice());
                        paneer_quan.setText(p.getQuantity());
                        break;
                    case 3:
                        vegsoup.setText(p.getPrice());
                        soup_quan.setText(p.getQuantity());
                        break;
                    case 4:
                        vegsnack.setText(p.getPrice());
                        snack_quan.setText(p.getQuantity());
                        break;
                    case 5:
                        veg_extra.setText(p.getPrice());
                        extra_quan.setText(p.getQuantity());
                        break;

                }
            }


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
                        egg_price.setText(q.getPrice());
                        egg_quan.setText(q.getQuantity());
                        break;
                    case 1:
                        fish_price.setText(q.getPrice());
                        fish_quan.setText(q.getQuantity());
                        break;
                    case 2:
                        nvcombo_price.setText(q.getPrice());
                        nv_quan.setText(q.getQuantity());
                        break;
                    case 3:
                        chicken_price.setText(q.getPrice());
                        chicken_quan.setText(q.getQuantity());
                        break;
                    case 4:
                        mutton_price.setText(q.getPrice());
                        mutton_quan.setText(q.getQuantity());
                        break;
                    case 5:
                        nonveg_extra.setText(q.getPrice());
                        nonveg_quan.setText(q.getQuantity());
                        break;

                }

            }

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
                        water_price.setText(r.getPrice());
                        water_quan.setText(r.getQuantity());
                        break;
                    case 1:
                        coke_price.setText(r.getPrice());
                        coke_quan.setText(r.getQuantity());
                        break;
                    case 2:
                        apple_price.setText(r.getPrice());
                        apple_quan.setText(r.getQuantity());
                        break;
                    case 3:
                        orange_price.setText(r.getPrice());
                        orange_quan.setText(r.getQuantity());
                        break;
                    case 4:
                        sprite_price.setText(r.getPrice());
                        sprite_quan.setText(r.getQuantity());
                        break;
                    case 5:
                        bev_price.setText(r.getPrice());
                        bev_quan.setText(r.getQuantity());
                        break;

                }

            }


        }catch (Exception e){
            e.printStackTrace();
        }

            thalli_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent k=new Intent(StockFull.this,StockAdd.class);
                    k.putExtra("Listitemvalue",0);
                    k.putExtra("item","veg");
                    startActivity(k);

                }
            });

            salad_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent k=new Intent(StockFull.this,StockAdd.class);
                    k.putExtra("Listitemvalue",1);
                    k.putExtra("item","veg");
                    startActivity(k);

                }
            });

            tikka_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent k=new Intent(StockFull.this,StockAdd.class);
                    k.putExtra("Listitemvalue",2);
                    k.putExtra("item","veg");
                    startActivity(k);

                }
            });

            soup_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent k=new Intent(StockFull.this,StockAdd.class);
                    k.putExtra("Listitemvalue",3);
                    k.putExtra("item","veg");
                    startActivity(k);

                }
            });

            snack_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent k=new Intent(StockFull.this,StockAdd.class);
                    k.putExtra("Listitemvalue",4);
                    k.putExtra("item","veg");
                    startActivity(k);

                }
            });

            veg_extra_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent k=new Intent(StockFull.this,StockAdd.class);
                    k.putExtra("Listitemvalue",5);
                    k.putExtra("item","veg");
                    startActivity(k);

                }
            });

            egg_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent k=new Intent(StockFull.this,StockAdd.class);
                    k.putExtra("Listitemvalue",0);
                    k.putExtra("item","nonveg");
                    startActivity(k);

                }
            });

            fish_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent k=new Intent(StockFull.this,StockAdd.class);
                    k.putExtra("Listitemvalue",1);
                    k.putExtra("item","nonveg");
                    startActivity(k);

                }
            });

            nv_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent k=new Intent(StockFull.this,StockAdd.class);
                    k.putExtra("Listitemvalue",2);
                    k.putExtra("item","nonveg");
                    startActivity(k);

                }
            });

            chicken_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent k=new Intent(StockFull.this,StockAdd.class);
                    k.putExtra("Listitemvalue",3);
                    k.putExtra("item","nonveg");
                    startActivity(k);

                }
            });

            mutton_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent k=new Intent(StockFull.this,StockAdd.class);
                    k.putExtra("Listitemvalue",4);
                    k.putExtra("item","nonveg");
                    startActivity(k);

                }
            });

            nvextra_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent k=new Intent(StockFull.this,StockAdd.class);
                    k.putExtra("Listitemvalue",5);
                    k.putExtra("item","nonveg");
                    startActivity(k);

                }
            });

            water_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent k=new Intent(StockFull.this,StockAdd.class);
                    k.putExtra("Listitemvalue",0);
                    k.putExtra("item","bev");
                    startActivity(k);

                }
            });

            coke_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent k=new Intent(StockFull.this,StockAdd.class);
                    k.putExtra("Listitemvalue",1);
                    k.putExtra("item","bev");
                    startActivity(k);

                }
            });

            apple_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent k=new Intent(StockFull.this,StockAdd.class);
                    k.putExtra("Listitemvalue",2);
                    k.putExtra("item","bev");
                    startActivity(k);

                }
            });

            orange_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent k=new Intent(StockFull.this,StockAdd.class);
                    k.putExtra("Listitemvalue",3);
                    k.putExtra("item","bev");
                    startActivity(k);

                }
            });

            sprite_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent k=new Intent(StockFull.this,StockAdd.class);
                    k.putExtra("Listitemvalue",4);
                    k.putExtra("item","bev");
                    startActivity(k);

                }
            });

            bev_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent k=new Intent(StockFull.this,StockAdd.class);
                    k.putExtra("Listitemvalue",5);
                    k.putExtra("item","bev");
                    startActivity(k);

                }
            });

//            switch (Listitemvalue){
//                case 1:
//                    thalli.setText(price);
//                    thalli_quantity.setText(quantity_enter);
//                    break;
//
//                case 2:
//                    fruit.setText(price);
//                    fruit_quan.setText(quantity_enter);
//                    break;
//
//                case 3:
//                    paneer.setText(price);
//                    paneer_quan.setText(quantity_enter);
//                    break;
//
//                case 4:
//                    vegsoup.setText(price);
//                    soup_quan.setText(quantity_enter);
//                    break;
//
//                case 5:
//                    vegsnack.setText(price);
//                    snack_quan.setText(quantity_enter);
//                    break;
//
//                case 6:
//                    veg_extra.setText(price);
//                    extra_quan.setText(quantity_enter);
//                    break;
//
//                case 7:
//                    egg_price.setText(price);
//                    egg_quan.setText(quantity_enter);
//                    break;
//
//                case 8:
//                    fish_price.setText(price);
//                    fish_quan.setText(quantity_enter);
//                    break;
//
//                case 9:
//                    nvcombo_price.setText(price);
//                    nv_quan.setText(quantity_enter);
//                    break;
//
//                case 10:
//                    chicken_price.setText(price);
//                    chicken_quan.setText(quantity_enter);
//                    break;
//
//                case 11:
//                    mutton_price.setText(price);
//                    mutton_quan.setText(quantity_enter);
//                    break;
//
//                case 12:
//                    nonveg_extra.setText(price);
//                    nonveg_quan.setText(quantity_enter);
//                    break;
//
//                case 13:
//                    water_price.setText(price);
//                    water_quan.setText(quantity_enter);
//                    break;
//
//                case 14:
//                    coke_price.setText(price);
//                    coke_quan.setText(quantity_enter);
//                    break;
//
//                case 15:
//                    apple_price.setText(price);
//                    apple_quan.setText(quantity_enter);
//                    break;
//
//                case 16:
//                    orange_price.setText(price);
//                    orange_quan.setText(quantity_enter);
//                    break;
//
//                case 17:
//                    sprite_price.setText(price);
//                    sprite_quan.setText(quantity_enter);
//                    break;
//
//                case 18:
//                    bev_price.setText(price);
//                    bev_quan.setText(quantity_enter);
//                    break;
//            }


        }

        public void add(){
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

            try {

                for (int k=0;k<veg.size();k++){
                    VegList p=veg.get(k);
                    vegpricelist.add(p.getPrice());
                    vegquantitylist.add(p.getQuantity());
                    int s=k;
                    switch (s) {
                        case 0:
                            thalli.setText(p.getPrice());
                            thalli_quantity.setText(p.getQuantity());
                            break;
                        case 1:
                            fruit.setText(p.getPrice());
                            fruit_quan.setText(p.getQuantity());
                            break;
                        case 2:
                            paneer.setText(p.getPrice());
                            paneer_quan.setText(p.getQuantity());
                            break;
                        case 3:
                            vegsoup.setText(p.getPrice());
                            soup_quan.setText(p.getQuantity());
                            break;
                        case 4:
                            vegsnack.setText(p.getPrice());
                            snack_quan.setText(p.getQuantity());
                            break;
                        case 5:
                            veg_extra.setText(p.getPrice());
                            extra_quan.setText(p.getQuantity());
                            break;

                    }
                }


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
                            egg_price.setText(q.getPrice());
                            egg_quan.setText(q.getQuantity());
                            break;
                        case 1:
                            fish_price.setText(q.getPrice());
                            fish_quan.setText(q.getQuantity());
                            break;
                        case 2:
                            nvcombo_price.setText(q.getPrice());
                            nv_quan.setText(q.getQuantity());
                            break;
                        case 3:
                            chicken_price.setText(q.getPrice());
                            chicken_quan.setText(q.getQuantity());
                            break;
                        case 4:
                            mutton_price.setText(q.getPrice());
                            mutton_quan.setText(q.getQuantity());
                            break;
                        case 5:
                            nonveg_extra.setText(q.getPrice());
                            nonveg_quan.setText(q.getQuantity());
                            break;

                    }

                }

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
                            water_price.setText(r.getPrice());
                            water_quan.setText(r.getQuantity());
                            break;
                        case 1:
                            coke_price.setText(r.getPrice());
                            coke_quan.setText(r.getQuantity());
                            break;
                        case 2:
                            apple_price.setText(r.getPrice());
                            apple_quan.setText(r.getQuantity());
                            break;
                        case 3:
                            orange_price.setText(r.getPrice());
                            orange_quan.setText(r.getQuantity());
                            break;
                        case 4:
                            sprite_price.setText(r.getPrice());
                            sprite_quan.setText(r.getQuantity());
                            break;
                        case 5:
                            bev_price.setText(r.getPrice());
                            bev_quan.setText(r.getQuantity());
                            break;

                    }

                }


            }catch (Exception e){
                e.printStackTrace();
            }
        }



    public void views (){

        try{

            quantity_enter=store.getQuantity_enter();
            price=store.getPrice();
            Listitemvalue=store.getListitemvalue();
        }catch (Exception e){
            e.printStackTrace();
        }
//        switch (Listitemvalue){
//            case 1:
//                thalli.setText(price);
//                thalli_quantity.setText(quantity_enter);
//                break;
//
//            case 2:
//                fruit.setText(price);
//                fruit_quan.setText(quantity_enter);
//                break;
//
//            case 3:
//                paneer.setText(price);
//                paneer_quan.setText(quantity_enter);
//                break;
//
//            case 4:
//                vegsoup.setText(price);
//                soup_quan.setText(quantity_enter);
//                break;
//
//            case 5:
//                vegsnack.setText(price);
//                snack_quan.setText(quantity_enter);
//                break;
//
//            case 6:
//                veg_extra.setText(price);
//                extra_quan.setText(quantity_enter);
//                break;
//
//            case 7:
//                egg_price.setText(price);
//                egg_quan.setText(quantity_enter);
//                break;
//
//            case 8:
//                fish_price.setText(price);
//                fish_quan.setText(quantity_enter);
//                break;
//
//            case 9:
//                nvcombo_price.setText(price);
//                nv_quan.setText(quantity_enter);
//                break;
//
//            case 10:
//                chicken_price.setText(price);
//                chicken_quan.setText(quantity_enter);
//                break;
//
//            case 11:
//                mutton_price.setText(price);
//                mutton_quan.setText(quantity_enter);
//                break;
//
//            case 12:
//                nonveg_extra.setText(price);
//                nonveg_quan.setText(quantity_enter);
//                break;
//
//            case 13:
//                water_price.setText(price);
//                water_quan.setText(quantity_enter);
//                break;
//
//            case 14:
//                coke_price.setText(price);
//                coke_quan.setText(quantity_enter);
//                break;
//
//            case 15:
//                apple_price.setText(price);
//                apple_quan.setText(quantity_enter);
//                break;
//
//            case 16:
//                orange_price.setText(price);
//                orange_quan.setText(quantity_enter);
//                break;
//
//            case 17:
//                sprite_price.setText(price);
//                sprite_quan.setText(quantity_enter);
//                break;
//
//            case 18:
//                bev_price.setText(price);
//                bev_quan.setText(quantity_enter);
//                break;
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        views();
        add();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                StockFull.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}