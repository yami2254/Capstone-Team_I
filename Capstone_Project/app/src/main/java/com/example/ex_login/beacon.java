package com.example.ex_login;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class beacon extends FragmentActivity {

    long mNow;
    Date mDate;
    Date mDate1;
    private BeaconManager beaconManager;
    private Region region;
    private long starttime;
    String start;
    String curdate;
    private long endtime;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    SimpleDateFormat mFormat1 = new SimpleDateFormat("yyyyMMdd");
    private boolean isConnected;

    String uuid;
    int major;
    int minor;
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        isConnected = false;
        beaconManager = new BeaconManager(this);

        Intent intent = getIntent();
        processIntent(intent);

        region = new Region("ranged region",
                UUID.fromString(uuid), major, minor); // 본인이 연결할 Beacon의 ID와 Major / Minor Code를 알아야 한다.

        SystemRequirementsChecker.checkWithDefaultDialogs(this);
// add this below:

        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {

                count ++ ;
                System.out.println(count);
                if(starttime < Long.parseLong(getTime()) && Long.parseLong(getTime()) < endtime) {
                    if (!list.isEmpty()) {
                        Beacon nearestBeacon = list.get(0);
                        Log.d("Airport", "Nearest places: " + nearestBeacon.getRssi());

                        // nearestBeacon.getRssi() : 비콘의 수신 강도

                        System.out.println(region);
                        System.out.println(starttime);
                        System.out.println(endtime);


                        if (!isConnected && nearestBeacon.getRssi() > -70) {
                            isConnected = true;
                            AlertDialog.Builder dialog = new AlertDialog.Builder(beacon.this);
                            dialog.setTitle("알림")
                                    .setTitle("입실")
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    }).create();
                            AlertDialog alert = dialog.create();
                            //alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(255,50,50,50)));
                            alert.show();
                            onPause();

                        } else if (nearestBeacon.getRssi() < -70) {
                            isConnected = false;
                            Toast.makeText(beacon.this, "신호가 잡히지 않습니다.", Toast.LENGTH_SHORT).show();
                            finish();
                        }


                    }
                }
                else {
                    Toast.makeText(beacon.this, "입실 인정 시간이 아닙니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                if(count == 4)
                {
                    isConnected = false;
                    Toast.makeText(beacon.this, "신호가 잡히지 않습니다.", Toast.LENGTH_SHORT).show();
                    count = 0;
                    onPause();
                    finish();
                }
            }
        });


    }
    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }
    private String getTimeyymmdd(){
        mNow = System.currentTimeMillis();
        mDate1 = new Date(mNow);
        return mFormat1.format(mDate1);
    }
    @Override
    protected void onResume() {
        super.onResume();

        // 블루투스 권한 승낙 및 블루투스 활성화시키는 코드드
        SystemRequirementsChecker.checkWithDefaultDialogs(this);

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(region);
            }
        });
    }


    @Override
    protected void onPause() {
        beaconManager.stopRanging(region);

        super.onPause();
    }

    private void processIntent(Intent intent)
    {
        major = Integer.parseInt(intent.getExtras().getString("major"));
        minor = Integer.parseInt(intent.getExtras().getString("minor"));
        uuid = intent.getExtras().getString("uuid");
        start = intent.getExtras().getString("starttime");

        starttime = Long.parseLong(getTimeyymmdd()+ start+"0000");
        endtime = Long.parseLong(getTimeyymmdd()+start+"5900");

        System.out.println(starttime);
        System.out.println(endtime);
        System.out.println(Long.parseLong(getTime()));
        System.out.println(starttime < Long.parseLong(getTime()) && Long.parseLong(getTime()) < endtime);
    }

}
