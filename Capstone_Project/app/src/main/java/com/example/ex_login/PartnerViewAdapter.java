package com.example.ex_login;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

class ViewHolder {
    EditText editText2;
    EditText editText1;

    int ref;
}

public class PartnerViewAdapter extends BaseAdapter {
    Context mcontext;
    int Maxpeople;
    ArrayList<partner_list> datalist = new ArrayList<partner_list>();
    ArrayList<partner_list> filterlist = datalist;
    String name,sid;







    @Override
    public int getCount() {
        return filterlist.size();
    }

    @Override
    public Object getItem(int position) {
        return filterlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.partner_item, parent, false);
            holder.editText1 = (EditText) convertView.findViewById(R.id.partner_id);
            holder.editText2 = (EditText) convertView.findViewById(R.id.partner_name);
            convertView.setTag(holder);
        }
        else{
         holder = (ViewHolder)convertView.getTag();
        }
        holder.ref = position;

        final partner_list partnerList = filterlist.get(position);
        holder.editText1.setText(partnerList.getPartner_id());
        holder.editText2.setText(partnerList.getPartner_name());
        name+=holder.editText1.getText()+"#";
        sid+=holder.editText1.getText()+"#";
        holder.editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterlist.get(holder.ref).setPartner_id(s.toString());
            }
        });

        holder.editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterlist.get(holder.ref).setPartner_name(s.toString());
            }
        });


            return convertView;
        }

        public void addItem(String id, String name)
        {
            partner_list item = new partner_list();
            item.setPartner_name(name);
            item.setPartner_id(id);
            datalist.add(item);
        }



        public void clear()
    {
        datalist.clear();
    }
    }