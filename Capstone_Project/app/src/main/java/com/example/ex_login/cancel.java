package com.example.ex_login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;

public class cancel extends FragmentActivity {

    int flag;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        processIntent(intent);


        if(flag == 1) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(cancel.this);
            dialog.setTitle("알림")
                    .setTitle("예약이 취소되었습니다.")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            finish();

                        }
                    }).create().show();
        }
        else
        {
            AlertDialog.Builder dialog = new AlertDialog.Builder(cancel.this);
            dialog.setTitle("알림")
                    .setTitle("예약 취소가 실패하였습니다.")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            finish();
                        }
                    }).create().show();
        }
    }
    private void processIntent(Intent intent)
    {
        flag = Integer.parseInt(intent.getExtras().getString("flag"));

    }
}
