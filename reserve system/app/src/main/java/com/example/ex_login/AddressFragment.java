package com.example.ex_login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class AddressFragment extends Fragment {
    private RecyclerView addressView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<address_list> address_listArr = null;
    private String myid;
    ImageButton insertbtn;
    ImageButton deletebtn;
    EditText editname;
    EditText editid;
    int cnt = 0;
    String insertnum;
    String insertname;
    String insertid;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (View)inflater.inflate(R.layout.address,container,false);
        insertbtn  = (ImageButton)view.findViewById(R.id.insertbtn);
        deletebtn = (ImageButton)view.findViewById(R.id.deletedbtn);
        addressView = (RecyclerView)view.findViewById(R.id.address_recycleview);
        editid = (EditText)view.findViewById(R.id.editid);
        editname = (EditText)view.findViewById(R.id.editname);
        address_listArr = new ArrayList<address_list>();
        layoutManager = new LinearLayoutManager(getActivity());
        addressView.setHasFixedSize(true);
        addressView.setLayoutManager(layoutManager);
        adapter = new AddressViewAdapter(address_listArr,getActivity());

        insertbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cnt++;
                insertid = editid.getText().toString();
                editid.setText("");
                insertnum = ""+ cnt;

                insertname = editname.getText().toString();
                editname.setText("");
                address_listArr.add(new address_list(insertname,insertid,insertnum));

                adapter = new AddressViewAdapter(address_listArr,getActivity());
                addressView.setAdapter(adapter);

            }
        });

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i = 0;i<address_listArr.size();i++)
                {
                    if(address_listArr.get(i).getFlag() == true)
                    {
                        address_listArr.remove(i);
                        adapter = new AddressViewAdapter(address_listArr,getActivity());
                        addressView.setAdapter(adapter);
                        if(address_listArr.size() == 0)
                        {
                            cnt = 0;
                        }
                    }
                }
            }
        });



        layoutManager = new LinearLayoutManager(getActivity());




        return view;
    }
}
