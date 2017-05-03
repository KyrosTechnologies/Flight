package com.example.rohin.flight.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rohin.flight.List.BeveragesList;
import com.example.rohin.flight.List.NonvegList;
import com.example.rohin.flight.List.UserList;
import com.example.rohin.flight.List.UserPurchasedItems;
import com.example.rohin.flight.List.VegList;
import com.example.rohin.flight.R;
import com.example.rohin.flight.SharedPreference.PreferenceManager;
import com.google.gson.Gson;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Rohin on 28-04-2017.
 */

public class AdapterExchange extends RecyclerView.Adapter<AdapterExchange.MyViewHolder> {
    private Context mContext;
    public String seatnumber;
    private PreferenceManager store;
    private ArrayList<UserPurchasedItems>userPurchasedItemseslist;
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView exchange_name,exchange_price;
        public ImageView exchange_image;
        public LinearLayout exchange_minus;


        public MyViewHolder(View itemView) {
            super(itemView);
            exchange_minus=(LinearLayout)itemView.findViewById(R.id.exchange_minus);
            exchange_image=(ImageView) itemView.findViewById(R.id.exchange_image);
            exchange_name=(TextView)itemView.findViewById(R.id.exchange_name);
            exchange_price=(TextView)itemView.findViewById(R.id.exchange_price);

        }
    }
    public AdapterExchange(Context mContext,ArrayList<UserPurchasedItems>userPurchasedItemseslist,String seatnumber){
        this.userPurchasedItemseslist=userPurchasedItemseslist;
        this.mContext=mContext;
        this.seatnumber=seatnumber;
    }
    @Override
    public AdapterExchange.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_exchange_layout,parent,false);
        store=PreferenceManager.getInstance(mContext);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterExchange.MyViewHolder holder, final int position) {
        UserPurchasedItems userPurchasedItems=userPurchasedItemseslist.get(position);
        store=PreferenceManager.getInstance(mContext);

        String name=userPurchasedItems.getName();
        String price=userPurchasedItems.getPrice();
        int image=userPurchasedItems.getImage();
        if(name!=null){
            holder.exchange_name.setText(name);
        }if(price!=null){
            holder.exchange_price.setText(price);
        }if(image!=0){
            holder.exchange_image.setBackgroundResource(image);

        }
        holder.exchange_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"Item removed!",Toast.LENGTH_SHORT).show();

                userPurchasedItemseslist.remove(position);
                AdapterExchange.this.notifyDataSetChanged();

                try{
                    Async async=new Async(position,mContext,seatnumber);
                    async.execute();

                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        });

    }
public class Async extends AsyncTask<String ,String,String >{
    private ArrayList<UserList> userLists=new ArrayList<UserList>();
    private ArrayList<UserList> iuserLists=new ArrayList<UserList>();
    private ArrayList<UserPurchasedItems>userPurchasedItemses=new ArrayList<UserPurchasedItems>();
    private Context mContext;
    private String  seatnumber;
    private PreferenceManager store;

    public int position;
    public Async (int position,Context mContext,String seatnumber){
        this.position=position;
        this.mContext=mContext;
        this.seatnumber=seatnumber;
    }
    @Override
    protected void onPreExecute() {
        Toast.makeText(mContext,"Please wait",Toast.LENGTH_SHORT).show();
        store=PreferenceManager.getInstance(mContext);
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        try{
            Gson gson=new Gson();
            String waggonapitransferlist12=gson.toJson(iuserLists);
            store.putUserPurchasedList(waggonapitransferlist12);
            Log.d("values bev",waggonapitransferlist12);


        }catch (Exception es){
            es.printStackTrace();
        }
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<UserList>>() {
            }.getType();
            String google = store.getUserPurchasedList();
            userLists = gson.fromJson(google, type);
        }catch (Exception e){
            e.printStackTrace();
        }
        String type="";
        String name= "";
        for(int i=0; i<userLists.size();i++){
            UserList userList=userLists.get(i);
            String seat=userList.getSeatnumber();
            int id=userList.getId();
            boolean payment=userList.isPaymentdone();
            ArrayList<UserPurchasedItems>iuserPurchasedItemses=userList.getUserPurchaseList();


            if(seat.equals(seatnumber)){
                ArrayList<UserPurchasedItems>userPurchasedItemses=userList.getUserPurchaseList();

                    type=userPurchasedItemses.get(position).getType();
                name=userPurchasedItemses.get(position).getName();

                userPurchasedItemses.remove(position);
                UserList userList1=new UserList();
                userList1.setUserPurchaseList(userPurchasedItemses);
                userList1.setSeatnumber(seat);
                userList1.setPaymentdone(payment);
                userList1.setId(id);

                iuserLists.add(userList1);
            }else{
                UserList userList1=new UserList();
                userList1.setUserPurchaseList(iuserPurchasedItemses);
                userList1.setSeatnumber(seat);
                userList1.setPaymentdone(payment);
                userList1.setId(id);
                iuserLists.add(userList1);
            }

        }
        switch (type){
            case "veg":
                try {
                    veglist(name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "nonveg":
                try {
                    nonveg(name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "bev":
                try {
                    bev(name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }


        return null;
    }
}
public void bev(String name)throws  Exception{
    String veg=store.getBevelist();
    ArrayList<BeveragesList>vegLists=new ArrayList<BeveragesList>();
    ArrayList<BeveragesList>ivegLists=new ArrayList<BeveragesList>();
    ArrayList<BeveragesList>newivegLists=new ArrayList<BeveragesList>();

    try{
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<BeveragesList>>() {
            }.getType();
            String google = store.getBevelist();
            vegLists = gson.fromJson(google, type);
        }catch (Exception e){
            e.printStackTrace();
        }
        int pos=0;
        for(int i=0 ;i<vegLists.size();i++){
            BeveragesList ibeveragesList=new BeveragesList();
            String name1=vegLists.get(i).getName();
            String price1=vegLists.get(i).getPrice();
            String quantity1=vegLists.get(i).getQuantity();
            int  Id1=vegLists.get(i).getId();
            int image1=vegLists.get(i).getImage();
//            ibeveragesList.setName(name1);
//            ibeveragesList.setPrice(price1);
//            ibeveragesList.setQuantity(quantity1);
//            ibeveragesList.setImage(image1);
//            ibeveragesList.setId(Id1);

//            ivegLists.add(ibeveragesList);

            if(name.equals(name1)){
                String price=vegLists.get(i).getQuantity();
                int pint=Integer.parseInt(price);
                pint=pint+1;
                BeveragesList beveragesList=new BeveragesList();
                beveragesList.setId(Id1);
                beveragesList.setImage(image1);
                beveragesList.setPrice(price1);
                beveragesList.setQuantity(String.valueOf(pint));
                beveragesList.setName(name1);
                ivegLists.add(beveragesList);


            }else{
                BeveragesList beveragesList=new BeveragesList();
                beveragesList.setId(Id1);
                beveragesList.setImage(image1);
                beveragesList.setPrice(price1);
                beveragesList.setQuantity(quantity1);
                beveragesList.setName(name1);
                ivegLists.add(beveragesList);
            }

        }

    }catch (Exception e){
        e.printStackTrace();
    }
    try{
        Gson gson=new Gson();
        String waggonapitransferlist12=gson.toJson(ivegLists);
        store.putBevelist(waggonapitransferlist12);
        Log.d("values bev",waggonapitransferlist12);


    }catch (Exception es){
        es.printStackTrace();
    }

}
public void nonveg(String name)throws  Exception{
    String veg=store.getNonVeglist();
    ArrayList<NonvegList>vegLists=new ArrayList<NonvegList>();
    ArrayList<NonvegList>ivegLists=new ArrayList<NonvegList>();
    try{
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<NonvegList>>() {
            }.getType();
            String google = store.getNonVeglist();
            vegLists = gson.fromJson(google, type);
        }catch (Exception e){
            e.printStackTrace();
        }
        int pos=0;

        for(int i=0 ;i<vegLists.size();i++){
            NonvegList ibeveragesList=new NonvegList();

            String name1=vegLists.get(i).getName();
            String price1=vegLists.get(i).getPrice();
            String quantity1=vegLists.get(i).getQuantity();
            int  Id1=vegLists.get(i).getId();
            int image1=vegLists.get(i).getImage();
//            ibeveragesList.setName(name1);
//            ibeveragesList.setPrice(price1);
//            ibeveragesList.setQuantity(quantity1);
//            ibeveragesList.setImage(image1);
//            ibeveragesList.setId(Id1);
//
//            ivegLists.add(ibeveragesList);

            if(name.equals(name1)){
                pos=i;
                String price=vegLists.get(i).getQuantity();
                int pint=Integer.parseInt(price);
                pint=pint+1;
                NonvegList beveragesList=new NonvegList();
                beveragesList.setId(Id1);
                beveragesList.setImage(image1);
                beveragesList.setPrice(price1);
                beveragesList.setQuantity(String.valueOf(pint));
                beveragesList.setName(name1);
                ivegLists.add(beveragesList);

            }else{
                NonvegList beveragesList=new NonvegList();
                beveragesList.setId(Id1);
                beveragesList.setImage(image1);
                beveragesList.setPrice(price1);
                beveragesList.setQuantity(quantity1);
                beveragesList.setName(name1);
                ivegLists.add(beveragesList);
            }
        }

    }catch (Exception e){
        e.printStackTrace();
    }
    try{
        Gson gson=new Gson();
        String waggonapitransferlist12=gson.toJson(ivegLists);
        store.putNonVeglist(waggonapitransferlist12);
        Log.d("values bev",waggonapitransferlist12);


    }catch (Exception es){
        es.printStackTrace();
    }
}
public void veglist(String name)throws  Exception{
    String veg=store.getVeglist();
    ArrayList<VegList>vegLists=new ArrayList<VegList>();
    ArrayList<VegList>ivegLists=new ArrayList<VegList>();
    try{
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<VegList>>() {
            }.getType();
            String google = store.getVeglist();
            vegLists = gson.fromJson(google, type);
        }catch (Exception e){
            e.printStackTrace();
        }
        int pos=0;
        for(int i=0 ;i<vegLists.size();i++){
            VegList ibeveragesList=new VegList();

            String name1=vegLists.get(i).getName();
            String price1=vegLists.get(i).getPrice();
            String quantity1=vegLists.get(i).getQuantity();
            int  Id1=vegLists.get(i).getId();
            int image1=vegLists.get(i).getImage();
//            ibeveragesList.setName(name1);
//            ibeveragesList.setPrice(price1);
//            ibeveragesList.setQuantity(quantity1);
//            ibeveragesList.setImage(image1);
//            ibeveragesList.setId(Id1);
//
//            ivegLists.add(ibeveragesList);
            if(name.equals(name1)){
                 pos=i;
                String price=vegLists.get(i).getQuantity();
                int pint=Integer.parseInt(price);
                pint=pint+1;
                VegList beveragesList=new VegList();
                beveragesList.setId(Id1);
                beveragesList.setImage(image1);
                beveragesList.setPrice(price1);
                beveragesList.setQuantity(String.valueOf(pint));
                beveragesList.setName(name1);
                ivegLists.add(beveragesList);
            }else{
                VegList beveragesList=new VegList();
                beveragesList.setId(Id1);
                beveragesList.setImage(image1);
                beveragesList.setPrice(price1);
                beveragesList.setQuantity(quantity1);
                beveragesList.setName(name1);
                ivegLists.add(beveragesList);
            }
        }

    }catch (Exception e){
        e.printStackTrace();
    }
    try{
        Gson gson=new Gson();
        String waggonapitransferlist12=gson.toJson(ivegLists);
        store.putVeglist(waggonapitransferlist12);
        Log.d("values bev",waggonapitransferlist12);


    }catch (Exception es){
        es.printStackTrace();
    }

}
    @Override
    public int getItemCount() {
        return userPurchasedItemseslist.size();
    }
}
