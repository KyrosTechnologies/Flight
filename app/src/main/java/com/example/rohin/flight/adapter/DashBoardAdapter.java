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

import com.example.rohin.flight.List.UserPurchasedItems;
import com.example.rohin.flight.R;

import java.util.ArrayList;

/**
 * Created by Rohin on 29-04-2017.
 */

public class DashBoardAdapter extends RecyclerView.Adapter<DashBoardAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<UserPurchasedItems>userPurchasedItemses;
    public DashBoardAdapter(Context mContext,ArrayList<UserPurchasedItems>userPurchasedItemses){
        this.mContext=mContext;
        this.userPurchasedItemses=userPurchasedItemses;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public CardView location_card_view;
        public ImageView location_image;
        public TextView location_name,location_price,location_type;

        public MyViewHolder(View itemView) {
            super(itemView);
            location_card_view=(CardView)itemView.findViewById(R.id.location_card_view);
            location_image=(ImageView)itemView.findViewById(R.id.location_image);
            location_name=(TextView)itemView.findViewById(R.id.location_name);
            location_price=(TextView)itemView.findViewById(R.id.location_price);
            location_type=(TextView)itemView.findViewById(R.id.location_type);
        }
    }
    @Override
    public DashBoardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.dash_board_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DashBoardAdapter.MyViewHolder holder, int position) {
        UserPurchasedItems userPurchasedItems=userPurchasedItemses.get(position);
        int id=userPurchasedItems.getId();
        int image=userPurchasedItems.getImage();
        String name=userPurchasedItems.getName();
        String  price=userPurchasedItems.getPrice();
        String quantity=userPurchasedItems.getQuantity();
        String type =userPurchasedItems.getType();
        if(id!=0){

        }if(image!=0){
            holder.location_image.setImageResource(image);
        }if(name!=null){
            holder.location_name.setText(name);
        }if(price!=null){
            holder.location_price.setText(price);
        }if(quantity!=null){

        }if(type!=null){
            holder.location_type.setText(type);
        }
        holder.location_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"Card clidked!",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return userPurchasedItemses.size();
    }
}
