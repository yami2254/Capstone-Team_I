package com.example.ex_login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.ColorSpace.Rgb;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
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
    CalendarView calendarView;
    Button todaybtn;
    Button f_strudy_room;
    Button strudy_room;
    Button mypagebtn;
    Date date = new Date();
    String todaydate;
    Studyroom_Fragment studyroom_fragment;
    FreeStudyroom_fragment freestudyroom_fragment;
    public static final int REQUEST_CODE_ANOTHER = 1001;

    Intent intent;
    public static int selcolor = Color.rgb(157 , 157, 233);
    public static int nonscolor = Color.rgb(0, 0   , 0);


    @SuppressLint("ResourceType")
        public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.seach);
       // 꽁터디룸,스터디룸 버튼
        f_strudy_room = (Button)findViewById(R.id.Freestudy);
        strudy_room = (Button)findViewById(R.id.Study);
        mypagebtn = (Button)findViewById(R.id.mypage);

        studyroom_fragment  = new Studyroom_Fragment();
        freestudyroom_fragment = new FreeStudyroom_fragment();
        FragmentTransaction fragmentTransaction;
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout2,studyroom_fragment).commit();

        mypagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mypageIntent = new Intent(getApplicationContext(),MypageActivity.class);

                mypageIntent.putExtra("id","14011021");
                setResult(RESULT_OK,mypageIntent);
                startActivityForResult(mypageIntent,REQUEST_CODE_ANOTHER);

            }
        });



        f_strudy_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(r_flag == true)
                {
                 r_flag = false;
                 f_strudy_room.setBackgroundResource(R.drawable.selected);
                 strudy_room.setBackgroundResource(R.drawable.nonselect);
                 f_strudy_room.setTextColor(selcolor);
                 strudy_room.setTextColor(nonscolor);
                    getSupportFragmentManager().beginTransaction().show(freestudyroom_fragment).commit();
                    getSupportFragmentManager().beginTransaction().hide(studyroom_fragment).commit();
                }
            }
        });
        strudy_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(r_flag == false)
                {   r_flag = true;
                    strudy_room.setBackgroundResource(R.drawable.selected);
                    f_strudy_room.setBackgroundResource(R.drawable.nonselect);
                    f_strudy_room.setTextColor(nonscolor);
                    strudy_room.setTextColor(selcolor);
                    getSupportFragmentManager().beginTransaction().show(studyroom_fragment).commit();
                    getSupportFragmentManager().beginTransaction().hide(freestudyroom_fragment).commit();
                }
            }
        });




            //날짜
        final SimpleDateFormat today = new SimpleDateFormat("yyyyMMdd");
        todaydate = today.format(date);

        todaybtn = (Button) findViewById(R.id.today);
        todaybtn.setText(todaydate);
       /* if(sic == null) {
            todaybtn.setText(todaydate);
        }
        else {
            todaydate=intent.getExtras().getString("change");
            todaybtn.setText(todaydate);
        }*/
        todaybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(),Sub_CalendarActivity.class);
                  startActivityForResult(intent,REQUEST_CODE_ANOTHER);

                }
        });

        }


        protected void onActivityResult(int request_code,int resultCode, Intent intent)
        {
            super.onActivityResult(request_code,resultCode,intent);
            if(resultCode == RESULT_OK)
            {
                String rechange = intent.getExtras().getString("change");
                todaybtn.setText(rechange);
                Toast.makeText(getApplicationContext(),rechange,Toast.LENGTH_SHORT).show();
            }


        }

}
