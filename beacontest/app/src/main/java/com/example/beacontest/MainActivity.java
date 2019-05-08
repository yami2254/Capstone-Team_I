package com.example.beacontest;

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

public class MainActivity extends AppCompatActivity {

    long mNow;
    Date mDate;
    private BeaconManager beaconManager;
    private Region region;
    private long starttime = 20190508162000L;
    private long endttime = 20190508165000L;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private boolean isConnected;

    private TextView tvId;
    private TextView testtv;
    private Button btt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvId = (TextView) findViewById(R.id.tvld);
        testtv = (TextView) findViewById(R.id.testtv);
        btt = (Button)findViewById(R.id.btt);

        beaconManager = new BeaconManager(this);


// add this below:
        SystemRequirementsChecker.checkWithDefaultDialogs(this);

        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                if (!list.isEmpty()) {
                    Beacon nearestBeacon = list.get(0);
                    Log.d("Airport", "Nearest places: " + nearestBeacon.getRssi());

                    // nearestBeacon.getRssi() : 비콘의 수신 강도
                    tvId.setText(nearestBeacon.getRssi() + "");

                    if ( !isConnected && nearestBeacon.getRssi() > -70 ) {
                        isConnected = true;


                    }
                    else if( nearestBeacon.getRssi() < -70 ){
                        isConnected = false;

                    }
                }

            }
        });


        btt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                testtv.setText(getTime());
                if(starttime < Long.parseLong(getTime()) && Long.parseLong(getTime()) < endttime)
                {
                    if (isConnected == true) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                        dialog.setTitle("알림")
                                .setTitle("입실")
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).create().show();
                        btt.setEnabled(false);
                    } else {
                        Toast.makeText(MainActivity.this, "신호가 잡히지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "입실 인정 시간이 아닙니다.", Toast.LENGTH_SHORT).show();
                }
            }

        });

        region = new Region("ranged region",
                UUID.fromString("e2c56db5-dffb-48d2-b060-d0f5a71096e0"), 30001, 24424); // 본인이 연결할 Beacon의 ID와 Major / Minor Code를 알아야 한다.


    }
    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
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
}

