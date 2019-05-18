package com.example.ex_login;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

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

    public static int selcolor = Color.rgb(157 , 157, 233);
    public static int nonscolor = Color.rgb(0, 0   , 0);
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        // 넘겨받은 학번을 통해 mypage를 띄운다.
        //processintent 메소드를 통헤
        Intent intent = getIntent();
        processIntent(intent);

        reserveFragment = new ReserveFragment();
        addressFragment = new AddressFragment();
         FragmentTransaction fragmentTransaction;

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,reserveFragment).commit();

        ImageButton  backbtn = (ImageButton)findViewById(R.id.back);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        reserve_statbtn = (Button)findViewById(R.id.reserve_stat);
        address_btn = (Button)findViewById(R.id.address);

        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout,addressFragment).commit();


        getSupportFragmentManager().beginTransaction().hide(addressFragment).commit();
        address_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == true)
                {
                    flag = false;
                    address_btn.setBackgroundResource(R.drawable.selected);
                   reserve_statbtn.setBackgroundResource(R.drawable.nonselect);
                   address_btn.setTextColor(selcolor);
                   reserve_statbtn.setTextColor(nonscolor);

                    getSupportFragmentManager().beginTransaction().show(addressFragment).commit();
                    getSupportFragmentManager().beginTransaction().hide(reserveFragment).commit();
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
                    address_btn.setTextColor(nonscolor);
                    reserve_statbtn.setTextColor(selcolor);
                    flag = true;
                    getSupportFragmentManager().beginTransaction().show(reserveFragment).commit();
                    getSupportFragmentManager().beginTransaction().hide(addressFragment).commit();
                }

            }
        });





    }



    private void processIntent(Intent intent)
    {
        String myid  = intent.getExtras().getString("id");
        Toast.makeText(getApplicationContext(),myid,Toast.LENGTH_SHORT).show();
    }

}
