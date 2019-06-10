package com.example.ex_login;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class MypageActivity extends FragmentActivity {



    Button logoutbtn;
    Button enteredbtn;
    Button outbtn;
    Button reserve_statbtn;
    Button address_btn;
    String myid;
    boolean flag = true;
    ReserveFragment reserveFragment;
    AddressFragment addressFragment;
    ViewPager viewPager;
    pagerAdapter pagerAdapter;
    String changedate;
    public static Activity Mypage_Activity;

    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    public static int selcolor = Color.rgb(157 , 157, 233);
    public static int nonscolor = Color.rgb(0, 0   , 0);
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);
        Mypage_Activity = MypageActivity.this;
        // 넘겨받은 학번을 통해 mypage를 띄운다.
        //processintent 메소드를 통헤
        Intent intent = getIntent();
        processIntent(intent);

        reserveFragment = new ReserveFragment();
        addressFragment = new AddressFragment();

        ImageButton  backbtn = (ImageButton)findViewById(R.id.back);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(getApplicationContext(),SearchActivity.class);


                setResult(RESULT_OK,Intent);
                startActivityForResult(Intent,0);
                finish();
            }
        });

        reserve_statbtn = (Button)findViewById(R.id.reserve_stat);
        address_btn = (Button)findViewById(R.id.address);
        logoutbtn = (Button)findViewById(R.id.logout);
        viewPager = (ViewPager)findViewById(R.id.viewpager2);
/*
        FragmentTransaction fragmentTransaction;
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,reserveFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout,addressFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(addressFragment).commit();*/

        pagerAdapter = new pagerAdapter(getSupportFragmentManager());
        pagerAdapter.addItem(reserveFragment);
        pagerAdapter.addItem(addressFragment);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if(flag == true && i ==1)
                {
                    flag = false;
                    address_btn.setBackgroundResource(R.drawable.selected);
                    reserve_statbtn.setBackgroundResource(R.drawable.nonselect);
                    address_btn.setTextColor(Color.WHITE);
                    reserve_statbtn.setTextColor(selcolor);


                }
                else if(i == 0 && flag == false)
                {
                    address_btn.setBackgroundResource(R.drawable.nonselect);
                    reserve_statbtn.setBackgroundResource(R.drawable.selected);
                    address_btn.setTextColor(selcolor);
                    reserve_statbtn.setTextColor(Color.WHITE);
                    flag = true;

                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        address_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == true)
                {
                    flag = false;
                    address_btn.setBackgroundResource(R.drawable.selected);
                    reserve_statbtn.setBackgroundResource(R.drawable.nonselect);
                    address_btn.setTextColor(Color.WHITE);
                    reserve_statbtn.setTextColor(selcolor);
                    viewPager.setCurrentItem(1);

                }

            }
        });

        reserve_statbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == false)
                {
                    address_btn.setBackgroundResource(R.drawable.nonselect);
                    reserve_statbtn.setBackgroundResource(R.drawable.selected);
                    address_btn.setTextColor(selcolor);
                    reserve_statbtn.setTextColor(Color.WHITE);
                    flag = true;
                    viewPager.setCurrentItem(0);
                }

            }
        });

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = auto.edit();
                //editor.clear()는 auto에 들어있는 모든 정보를 기기에서 지웁니다.
                editor.clear();
                editor.commit();

                Intent intentgo = new Intent(getApplicationContext(), MainActivity.class);
                intentgo.putExtra("change", changedate);
                startActivity(intentgo);
                finish();
            }
        });



    }

    private void processIntent(Intent intent)
    {


    }


    private class pagerAdapter extends FragmentStatePagerAdapter
    {
        ArrayList<Fragment> items = new ArrayList<Fragment>();

        public pagerAdapter(android.support.v4.app.FragmentManager fm)
        {
            super(fm);
        }
        public void addItem(Fragment item){
            items.add(item);
        }
        @Override
        public android.support.v4.app.Fragment getItem(int position)
        {
            return items.get(position);
        }
        @Override
        public int getCount()
        {
            return 2;
        }
    }
    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {

        } else {
            backPressedTime = tempTime;

            Intent Intent = new Intent(getApplicationContext(),SearchActivity.class);


            setResult(RESULT_OK,Intent);
            startActivityForResult(Intent,0);
            finish();

        }


    }

}
