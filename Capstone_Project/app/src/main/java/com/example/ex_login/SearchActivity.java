package com.example.ex_login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.ColorSpace.Rgb;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class SearchActivity extends FragmentActivity {

    private  int flag = 0;
    boolean r_flag = true;

    Button todaybtn;
    Button f_strudy_room;
    Button strudy_room;
    Button mypagebtn;
    Date date = new Date();
    String todaydate;
    private Studyroom_Fragment studyroom_fragment;
    private  FreeStudyroom_fragment freestudyroom_fragment;
    public static final int REQUEST_CODE_ANOTHER = 1001;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    Intent intent;
    public static int selcolor = Color.rgb(157 , 157, 233);
    public static int nonscolor = Color.rgb(0, 0   , 0);

    private pagerAdapter pagerAdapter;
    ViewPager viewPager;

    @SuppressLint("ResourceType")
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.seach);
        // 꽁터디룸,스터디룸 버튼
        f_strudy_room = (Button)findViewById(R.id.Freestudy);
        strudy_room = (Button)findViewById(R.id.Study);
        mypagebtn = (Button)findViewById(R.id.mypage);
        todaybtn = (Button) findViewById(R.id.today);
        viewPager = (ViewPager)findViewById(R.id.viewpager1);
        studyroom_fragment  = new Studyroom_Fragment();
        freestudyroom_fragment = new FreeStudyroom_fragment();


        mypagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mypageIntent = new Intent(getApplicationContext(),MypageActivity.class);

                mypageIntent.putExtra("id","14011021");
                setResult(RESULT_OK,mypageIntent);
                startActivityForResult(mypageIntent,REQUEST_CODE_ANOTHER);
                finish();

            }
        });
        pagerAdapter = new pagerAdapter(getSupportFragmentManager());
        pagerAdapter.addItem(studyroom_fragment);
        pagerAdapter.addItem(freestudyroom_fragment);
        viewPager.setAdapter(pagerAdapter);

       viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
           public void onPageScrolled(int i, float v, int i1) {

           }

           @Override
           public void onPageSelected(int i) {
                if(i == 0)
                {
                    if(r_flag == false)
                    {   todaybtn.setEnabled(true);
                        r_flag = true;
                        strudy_room.setBackgroundResource(R.drawable.selected);
                        f_strudy_room.setBackgroundResource(R.drawable.nonselect);
                        f_strudy_room.setTextColor(selcolor);
                        strudy_room.setTextColor(Color.WHITE);

                    }
                }
                else if(i==1)
                {
                    todaybtn.setEnabled(false);
                    r_flag = false;
                    f_strudy_room.setBackgroundResource(R.drawable.selected);
                    strudy_room.setBackgroundResource(R.drawable.nonselect);
                    f_strudy_room.setTextColor(Color.WHITE);
                    strudy_room.setTextColor(selcolor);

                }

           }

           @Override
           public void onPageScrollStateChanged(int i) {

           }
       });


        f_strudy_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(r_flag == true)
                {   todaybtn.setEnabled(false);
                    r_flag = false;
                    f_strudy_room.setBackgroundResource(R.drawable.selected);
                    strudy_room.setBackgroundResource(R.drawable.nonselect);
                    f_strudy_room.setTextColor(Color.WHITE);
                    strudy_room.setTextColor(selcolor);
                    viewPager.setCurrentItem(1);
                }
            }
        });
        strudy_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(r_flag == false)
                {   todaybtn.setEnabled(true);
                    r_flag = true;
                    strudy_room.setBackgroundResource(R.drawable.selected);
                    f_strudy_room.setBackgroundResource(R.drawable.nonselect);
                    f_strudy_room.setTextColor(selcolor);
                    strudy_room.setTextColor(Color.WHITE);
                    viewPager.setCurrentItem(0);
                }
            }
        });






        //날짜
        SimpleDateFormat today = new SimpleDateFormat("yyyy");
        todaydate = today.format(date).toString() + "년 ";
        today = new SimpleDateFormat("MM");
        todaydate =todaydate+ today.format(date) + "월 ";
        today = new SimpleDateFormat("dd");
        todaydate =todaydate+ today.format(date) + "일";
        today = new SimpleDateFormat("yyyyMMdd");

        todaybtn.setText(todaydate);


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

            showMessge();
        }


    }


    public void showMessge(){

        //다이얼로그 객체 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //속성 지정
        builder.setTitle("안내");
        builder.setMessage("종료 하시겠습니까?");
        //아이콘
        builder.setIcon(android.R.drawable.ic_dialog_alert);


        //예 버튼 눌렀을 때
        builder.setPositiveButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //텍스트 뷰 객체를 넣어줌..

            }
        });


        //예 버튼 눌렀을 때
        builder.setNegativeButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //텍스트 뷰 객체를 넣어줌..
                finish();
            }
        });

        //만들어주기
        AlertDialog dialog = builder.create();
        dialog.show();
    }



}
