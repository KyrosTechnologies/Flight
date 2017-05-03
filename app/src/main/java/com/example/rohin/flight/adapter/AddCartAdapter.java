package com.example.rohin.flight.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rohin.flight.List.UserList;
import com.example.rohin.flight.List.UserPurchasedItems;
import com.example.rohin.flight.R;

import java.util.ArrayList;

/**
 * Created by Rohin on 28-04-2017.
 */

public class AddCartAdapter extends RecyclerView.Adapter<AddCartAdapter.MyViewHolder>{
    private Context mContext;
    private  ArrayList<UserPurchasedItems> userListArrayList;
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView cart_product_name,cart_price;
        public ImageView cart_image;
        public CardView cart_card;

       public MyViewHolder(View itemView) {
           super(itemView);
           cart_image=(ImageView)itemView.findViewById(R.id.cart_image);
           cart_product_name=(TextView)itemView.findViewById(R.id.cart_product_name);
           cart_price=(TextView)itemView.findViewById(R.id.cart_price);
           cart_card=(CardView)itemView.findViewById(R.id.cart_card);
       }
   }
   public AddCartAdapter(Context mContext,   ArrayList<UserPurchasedItems>userListArrayList){
       this.mContext=mContext;
       this.userListArrayList=userListArrayList;

   }
    @Override
    public AddCartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_adapter,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AddCartAdapter.MyViewHolder holder, int position) {
        UserPurchasedItems userList = userListArrayList.get(position);
        int image=userList.getImage();
        int id=userList.getId();
        String name=userList.getName();
        String price=userList.getPrice();
        String quantity=userList.getQuantity();
        final String type=userList.getType();

        if(image!=0){
            holder.cart_image.setBackgroundResource(image);
        }if(name!=null){
            holder.cart_product_name.setText(name);
        }if(price!=null){
            holder.cart_price.setText(price);
        }
            holder.cart_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(type!=null){
                        Toast.makeText(mContext.getApplicationContext(),"Food type is : "+type,Toast.LENGTH_SHORT).show();
                    }
                }
            });

    }

    @Override
    public int getItemCount() {
        return userListArrayList.size();
    }
}

