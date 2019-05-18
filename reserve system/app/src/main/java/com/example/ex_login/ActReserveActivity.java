package com.example.ex_login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ActReserveActivity extends FragmentActivity {

        TextView Reserve_StudtRoom_name;
        TextView Reserve_StudtRoom_num;
        TextView Reserve_StudtRoom_time;
        Button Reserve_Act_btn;
        Button Reserve_cancle_btn;
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserve_act);
        Reserve_StudtRoom_name = (TextView)findViewById(R.id.Act_reserve_roomname);
        Reserve_StudtRoom_num = (TextView)findViewById(R.id.Act_reserve_roomnum);
        Reserve_StudtRoom_time = (TextView)findViewById(R.id.Act_reserve_roomtime);
        Reserve_Act_btn = (Button)findViewById(R.id.Act_reserve_btn);
        Reserve_cancle_btn = (Button)findViewById(R.id.Act_reserve_canclebtn);
        Intent intent = getIntent();
        processIntent(intent);
        Reserve_Act_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 여기다 예약하면된다.


            }
        });



        Reserve_cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void processIntent(Intent intent)
    {
        String myid  = intent.getExtras().getString("StudtRoom_name") +" " + intent.getExtras().getString("StudtRoom_num")+" "+ intent.getExtras().getString("StudtRoom_time");
        Reserve_StudtRoom_name.setText(intent.getExtras().getString("StudtRoom_name"));
        Reserve_StudtRoom_num.setText(intent.getExtras().getString("StudtRoom_num"));
        Reserve_StudtRoom_time.setText(intent.getExtras().getString("StudtRoom_time"));

    }
}
