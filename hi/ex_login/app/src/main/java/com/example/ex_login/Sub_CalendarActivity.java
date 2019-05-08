package com.example.ex_login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Sub_CalendarActivity extends Activity {
    CalendarMonthView monthView;
    CalendarMonthAdapter monthViewAdapter;
    TextView monthText;
    int curYear;
    int curMonth;
    String curDay2;
    int curDay;
    String changedate;
    boolean flag = false;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_calendar);
        Button okbtn  = (Button)findViewById(R.id.ok2btn);
        java.util.Date date = new Date();
        final SimpleDateFormat today = new SimpleDateFormat("dd");
        curDay2  = today.format(date);
        curDay = Integer.parseInt(curDay2);

        monthView = (CalendarMonthView) findViewById(R.id.monthView);
        monthViewAdapter = new CalendarMonthAdapter(this);
        monthView.setAdapter(monthViewAdapter);


        monthView.setOnDataSelectionListener(new OnDataSelectionListener() {
            public void onDataSelected(AdapterView parent, View v, int position, long id) {
                // 현재 선택한 일자 정보 표시

                MonthItem curItem = (MonthItem) monthViewAdapter.getItem(position);
                int day = curItem.getDay();
                // if(!(curDay <= day && day> curDay +7))


                if(curItem.getDay()>=curDay &&curItem.getDay()<curDay+8)
                {
                    flag = true;
                    changedate = curYear + "년 " + (curMonth + 1) + "월 "+curItem.getDay()+"일";
                }
                else {
                    flag = false;
                    changedate = curYear + "년 " + (curMonth + 1) + "월 "+(curDay)+"일";
                }

            }

        });

        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reintent = new Intent();
                reintent.putExtra("change",changedate);

                setResult(RESULT_OK,reintent);
                finish();

            }
        });

        monthText = (TextView) findViewById(R.id.monthText);
        setMonthText();

        // 이전 월로 넘어가는 이벤트 처리
        Button monthPrevious = (Button) findViewById(R.id.monthPrevious);
        monthPrevious.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                monthViewAdapter.setPreviousMonth();
                monthViewAdapter.notifyDataSetChanged();

                setMonthText();
            }
        });

        // 다음 월로 넘어가는 이벤트 처리
        Button monthNext = (Button) findViewById(R.id.monthNext);
        monthNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                monthViewAdapter.setNextMonth();
                monthViewAdapter.notifyDataSetChanged();

                setMonthText();
            }
        });

    }

    /**
     * 월 표시 텍스트 설정
     */
    private void setMonthText() {
        curYear = monthViewAdapter.getCurYear();
        curMonth = monthViewAdapter.getCurMonth();

        monthText.setText(curYear + "년 " + (curMonth + 1) + "월");
    }

}




class MonthItemView extends android.support.v7.widget.AppCompatTextView {
    public static int baseColor = Color.rgb(100, 100, 100);
    private MonthItem item;

    public MonthItemView(Context context) {
        super(context);

        init();
    }

    public MonthItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }


    private void init() {

        setBackgroundColor(baseColor);

    }


    public MonthItem getItem() {
        return item;
    }


    public void setItem(MonthItem item) {
        this.item = item;

        int day = item.getDay();
        if (day != 0) {
            setText(String.valueOf(day));
        } else {
            setText("");
        }

    }


}

interface OnDataSelectionListener {


    public void onDataSelected(AdapterView parent, View v, int position, long id);

}