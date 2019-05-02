package com.example.ex_login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListPopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SearchActivity extends Activity {
    Intent intent;
    private  int flag = 0;
    CalendarView calendarView;
    Button todaybtn;
        Date date = new Date();
        String todaydate;
        String sic;
        Spinner loc_spinner,start_spinner,use_spinner,people_spinner;
        public static final int REQUEST_CODE_ANOTHER = 1001;


        public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.seach);
        String[] loc_array = {"전체","7층","4층","2층","1층","진관"};
        String[] start_array = {"00시","01시","02시","03시","04시","05시","06시","07시","08시","09시","10시","11시","12시","13시",
                "14시","15시","16시","17시","18시","19시","20시","21시","22시","23시","24시"};
        String[] use_array = {"1시간","2시간"};
       // String[] people_array = {"2~4","2~5","3~6","3~8","4~8","4~9","5~10","9~32"};
            String[] people_array = {"2명","3명","4명","5명","6명","7명","8명","9명","10~"};


        final SimpleDateFormat today = new SimpleDateFormat("yyyy 년 MM 월 dd일 ");
        todaydate = today.format(date);
        loc_spinner = (Spinner)findViewById(R.id.location);
        start_spinner = (Spinner)findViewById(R.id.starttime);
        use_spinner = (Spinner)findViewById(R.id.usetime);
        people_spinner = (Spinner)findViewById(R.id.people);
        ArrayAdapter<String> location_adapter = new ArrayAdapter<String>(this,R.layout.spinner_item,loc_array);
        ArrayAdapter<String> start_adapter = new ArrayAdapter<String>(this,R.layout.spinner_item,start_array);
        ArrayAdapter<String> use_adpter = new ArrayAdapter<String>(this,R.layout.spinner_item,use_array);
        ArrayAdapter<String> people_adapter = new ArrayAdapter<String>(this,R.layout.spinner_item,people_array);
        loc_spinner.setAdapter(location_adapter);
        loc_spinner.setSelection(0);
        loc_spinner.setGravity(View.TEXT_ALIGNMENT_CENTER);
        start_spinner.setAdapter(start_adapter);
        start_spinner.setSelection(0);
        start_spinner.setGravity(View.TEXT_ALIGNMENT_CENTER);
        use_spinner.setAdapter(use_adpter);
        use_spinner.setSelection(0);
        use_spinner.setGravity(View.TEXT_ALIGNMENT_CENTER);
        people_spinner.setAdapter(people_adapter);
        people_spinner.setSelection(0);
        people_spinner.setGravity(View.TEXT_ALIGNMENT_CENTER);



        intent = getIntent();

        sic = intent.getExtras().getString("change");
        todaybtn = (Button) findViewById(R.id.today);

        if(sic == null) {
            todaybtn.setText(todaydate);
        }
        else {
            todaydate=intent.getExtras().getString("change");
            todaybtn.setText(todaydate);
        }
        todaybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent = new Intent(getApplicationContext(),CalendarActivity.class);
                  startActivity(intent);

                }
        });

        }

}
