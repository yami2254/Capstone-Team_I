package com.example.ex_login;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ReserveFragment extends Fragment {

    private RecyclerView reserveView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager  layoutManager;
    ArrayList<reserve_list> reserve_listArr = null;
    private String myid;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (View)inflater.inflate(R.layout.reserve,container,false);

        reserve_listArr = new  ArrayList<reserve_list>();
        reserve_listArr.add(new reserve_list("2019-05-14","13:00~15:00","7F 07 스터디룸"));
        reserve_listArr.add(new reserve_list("2019-05-14","17:00~19:00","7F 04 스터디룸"));
        reserveView = (RecyclerView)view.findViewById(R.id.reserve_recycleview);

        layoutManager = new LinearLayoutManager(getActivity());
        reserveView.setHasFixedSize(true);
        reserveView.setLayoutManager(layoutManager);
        adapter = new ReserveViewAdapter(reserve_listArr,getActivity());
        reserveView.setAdapter(adapter);
        return view;
    }
}
