package com.example.ex_login;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ReserveViewAdapter extends RecyclerView.Adapter<ReserveViewAdapter.MyViewHolder> {

    private  ArrayList<reserve_list> datalist;
    Context mcontext;
    public ReserveViewAdapter(ArrayList<reserve_list> reseve_Arr , Context context)
    {
        this.datalist = reseve_Arr;
        mcontext = context;
    }

    @NonNull
    @Override
    public ReserveViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reserve_item,viewGroup,false);
       MyViewHolder viewHolder1 = new MyViewHolder(view);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        final reserve_list data = datalist.get(i);
        myViewHolder.reserve_time.setText(data.getReserve_time());
        myViewHolder.reserve_date.setText(data.getReserve_date());
        myViewHolder.reserve_name.setText(data.getReserve_name());



    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public   class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public TextView reserve_date;
        public TextView reserve_time;
        public TextView reserve_name;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            reserve_date = (TextView)itemView.findViewById(R.id.reserve_date);
            reserve_name = (TextView)itemView.findViewById(R.id.reserve_room_name);
            reserve_time = (TextView)itemView.findViewById(R.id.reserve_time);


        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        }
    }




}
