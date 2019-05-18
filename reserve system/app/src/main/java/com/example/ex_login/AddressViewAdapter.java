package com.example.ex_login;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class AddressViewAdapter extends RecyclerView.Adapter<AddressViewAdapter.MyViewHolder> implements Filterable {

    private ArrayList<address_list> datalist;
    private  ArrayList<address_list> fillteredlist;
    private Context mcontext;
    public AddressViewAdapter(ArrayList<address_list> address_Arr,Context context)
    {
        mcontext = context;
        this.datalist = address_Arr;
        this.fillteredlist = address_Arr;

    }

    @NonNull
    @Override
    public AddressViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.address_item,viewGroup,false);
        MyViewHolder viewHolder1 = new MyViewHolder(view);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewAdapter.MyViewHolder myViewHolder, int i) {
     final address_list data = datalist.get(i);
    myViewHolder.address_id.setText(data.getId());
    myViewHolder.address_name.setText(data.getName());
    myViewHolder.address_num.setText(data.getNum());
    myViewHolder.checkBox.setChecked(data.getFlag());
    myViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            data.setFlag(isChecked);
        }
    });
    }


    @Override
    public int getItemCount() {
        return datalist.size();
    }

    @Override
    public Filter getFilter() {



        return null;
    }


    public  static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        public TextView address_id;
        public TextView address_num;
        public TextView address_name;
        public CheckBox checkBox;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            address_id  = (TextView)itemView.findViewById(R.id.address_id);
            address_num = (TextView)itemView.findViewById(R.id.address_num);
            address_name = (TextView)itemView.findViewById(R.id.address_name);
            checkBox = (CheckBox)itemView.findViewById(R.id.checkbox);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        }
    }
}
