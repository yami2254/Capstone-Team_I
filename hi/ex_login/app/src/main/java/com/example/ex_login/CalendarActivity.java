package com.example.ex_login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarActivity extends Activity {
    CalendarView calendarView;
    String changedate;
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);
        Button okbtn  = (Button)findViewById(R.id.okbtn);

        calendarView = (CalendarView)findViewById(R.id.calendarView1);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth)
            {
                month++;

                changedate = year + " 년 "+ month  +" 월 "+ dayOfMonth+" 일";

            }
        });

        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reintent = new Intent(getApplicationContext(),SearchActivity.class);
                reintent.putExtra("change",changedate);

                startActivity(reintent);
                finish();

                Toast.makeText(getBaseContext(),changedate,Toast.LENGTH_SHORT).show();
            }
        });

    }
}