package com.example.ex_login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.ColorSpace.Rgb;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListPopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Vector;


public class SearchActivity extends Activity {

    final SimpleDateFormat today = new SimpleDateFormat("yyyyMMdd");
    Date date = new Date();
    String todaydate = today.format(date);

    private  int flag = 0;
    boolean r_flag = true;
    CalendarView calendarView;
    Button todaybtn;
    Button f_strudy_room;
    Button strudy_room;
    String sic;
    Spinner loc_spinner,start_spinner,use_spinner,people_spinner;
    int sendcount = 0;
    public static final int REQUEST_CODE_ANOTHER = 1001;
    int request_code;
    Intent intent;
    public static int selcolor = Color.rgb(157 , 157, 233);
    public static int nonscolor = Color.rgb(0, 0   , 0);
    String loc = "전체";
    String start= "전체";
    String people= "전체";
    String use = "전체";

    RecyclerView recyclerView;
    private RecyclerViewAdapter adapter= new RecyclerViewAdapter();


    @SuppressLint("ResourceType")
        public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.seach);

        recyclerView = findViewById(R.id.Study_room_RecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


        new HttpTask().execute();

       // 꽁터디룸,스터디룸 버튼
        f_strudy_room = (Button)findViewById(R.id.Freestudy);
        strudy_room = (Button)findViewById(R.id.Study);
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

                }
            }
        });

        // 필터링 스피너
        String[] loc_array = {"전체","학술정보원-7F","학술정보원-4F","학술정보원-2F","학술정보원-1F","진관"};
        String[] start_array = {"전체","00시","01시","02시","03시","04시","05시","06시","07시","08시","09시","10시","11시","12시","13시",
                "14시","15시","16시","17시","18시","19시","20시","21시","22시","23시"};
        String[] use_array = {"전체","1시간","2시간"};
        String[] people_array = {"전체","2","3","4","5","6","7","8","9","10~"};




            //날짜

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







        //adapter = new RecyclerViewAdapter(study_roomArr);
        loc_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loc = (String)loc_spinner.getSelectedItem();
                if(sendcount >3)
                    new HttpTask().execute();
                sendcount++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                loc = "전체";
            }
        });

        start_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                start = (String)start_spinner.getSelectedItem();
                if(sendcount >3)
                    new HttpTask().execute();
                sendcount++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                start = "전체";
            }
        });

        use_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                use = (String)use_spinner.getSelectedItem();
                if(sendcount >3)
                    new HttpTask().execute();
                sendcount++;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                use = "전체";
            }
        });

        people_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                people = (String)people_spinner.getSelectedItem();
                if(sendcount >3)
                    new HttpTask().execute();
                sendcount++;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                people = "전체";
            }
        });

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
            }


        }

    class HttpTask extends AsyncTask<Void, Void, String> {


        private final String urlPath = "http://interface518.dothome.co.kr/caps/fillter.php?cdate="+"20190515";

        @Override
        protected String doInBackground(Void... voids) {
            // TODO Auto-generated method stub
            try{
                HttpPost request = new HttpPost(urlPath);
                //전달할 인자들
                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
                nameValue.add(new BasicNameValuePair("loc", loc));
                nameValue.add(new BasicNameValuePair("start", start));
                nameValue.add(new BasicNameValuePair("use", use));
                nameValue.add(new BasicNameValuePair("people", people));
                //웹 접속 - utf-8 방식으로

                HttpEntity enty = new UrlEncodedFormEntity(nameValue,"utf-8");
                request.setEntity(enty);
                System.out.println(nameValue);

                HttpClient client = new DefaultHttpClient();
                HttpResponse res = client.execute(request);
                //웹 서버에서 값받기
                HttpEntity entityResponse = res.getEntity();
                InputStream im = entityResponse.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(im, "utf-8"));

                String total = "";
                String tmp = "";
                //버퍼에있는거 전부 더해주기
                //readLine -> 파일내용을 줄 단위로 읽기
                while((tmp = reader.readLine())!= null)
                {
                    if(tmp != null)
                    {
                        total += tmp;
                    }
                }
                im.close();
                //결과창뿌려주기 - ui 변경시 에러
                //testtv.setText(total);
                return total;
            }catch(UnsupportedEncodingException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }
            //오류시 null 반환
            return null;
        }
        //asyonTask 3번째 인자와 일치 매개변수값 -> doInBackground 리턴값이 전달됨
        //AsynoTask 는 preExcute - doInBackground - postExecute 순으로 자동으로 실행됩니다.
        //ui는 여기서 변경
        protected void onPostExecute(String value){
            super.onPostExecute(value);

            System.out.println(value);

            if(value.length() > 12)
            {
                adapter.clear();
                if(value.charAt(value.length()-1) == ']')
                {
                    value +="}";
                }
                reservejsonlist(value);
                recyclerView.setAdapter(adapter);
            }
            else
            {

                Toast.makeText(SearchActivity.this,"서버문제야 씨발",Toast.LENGTH_SHORT).show();
            }


        }
    }

    public void reservejsonlist(String jsonString) {

        String SR_id = null;
        String SR_place = null;
        String SR_starttime = null;
        String SR_endtime = null;
        String SR_minperson = null;
        String SR_maxperson = null;
        String SR_beacon = null;
        String reserv = null;
        String S_NowReservate = null;
        String R_starttime = null;
        String R_usetime = null;

        boolean flag = true;
        boolean flaguse = true;
        int flagusenum = 0;
        int temp;

        try {

            JSONArray jarray = new JSONObject(jsonString).getJSONArray("room");
            for (int i = 0; i < jarray.length(); i++) {

                flag = true;
                flaguse = true;
                flagusenum = 0;

                JSONObject jObject = jarray.getJSONObject(i);

                SR_id = jObject.optString("SR_id");
                SR_place = jObject.optString("SR_place");
                SR_starttime = jObject.optString("SR_starttime");
                SR_endtime = jObject.optString("SR_endtime");
                SR_minperson = jObject.optString("SR_minperson");
                SR_maxperson = jObject.optString("SR_maxperson");
                SR_beacon = jObject.optString("SR_beacon");
                S_NowReservate = jObject.optString("S_NowReservate");
                reserv = jObject.optString("reserv");

                start = start.replace("시", "");
                if (start.equals("전체")||Integer.parseInt(start) > Integer.parseInt(SR_starttime) - 1 &&
                        Integer.parseInt(start) < Integer.parseInt(SR_endtime) + 1
                &&!(Integer.parseInt(start) == Integer.parseInt(SR_endtime)&&use.equals("2시간"))) {
                    study_list data = new study_list();

                    if (reserv.length() != 4) {
                        System.out.println(reserv);
                        if (reserv.charAt(reserv.length() - 1) == ']') {
                            reserv += "}";
                        }
                        String reservjo = "{\"res\":" + reserv;
                        System.out.println(reservjo);

                        JSONArray ja = new JSONObject(reservjo).getJSONArray("res");

                        for (int j = 0; j < ja.length(); j++) {
                            JSONObject jO = ja.getJSONObject(j);

                            R_starttime = jO.optString("R_starttime");
                            R_usetime = jO.optString("R_usetime");

                            data.setReserveL(R_starttime);
                            if (R_usetime.equals("2")) {
                                data.setReserveL(String.valueOf(Integer.parseInt(R_starttime) + 1));
                            }
                        }
                    }

                    ArrayList<String> list = data.getReserveL();
                    if (!start.equals("전체")) {
                        start = start.replace("시", "");
                        if (use.equals("1시간")) {
                            if (list.indexOf(start) != -1) {
                                flag = false;
                            }

                        } else if (use.equals("2시간")) {
                            if (list.indexOf(start) != -1 || list.indexOf(String.valueOf(Integer.parseInt(start) + 1)) != -1) {
                                flag = false;
                            }
                        }
                    } else {
                        if (use.equals("1시간")) {
                            temp = Integer.parseInt(SR_starttime) - 1;
                            for (int n = 0; n < list.size(); n++) {
                                if (Integer.parseInt(list.get(n)) - temp > 1) {
                                    break;
                                }
                                temp = Integer.parseInt(list.get(n));
                                if (n == list.size() - 1) {
                                    if (Integer.parseInt(SR_endtime) + 1 - Integer.parseInt(list.get(n)) < 2) {
                                        flaguse = false;
                                    }
                                }
                            }
                        } else if (use.equals("2시간")) {
                            temp = Integer.parseInt(SR_starttime) - 1;
                            for (int n = 0; n < list.size(); n++) {
                                if (Integer.parseInt(list.get(n)) - temp > 2) {
                                    break;
                                }
                                temp = Integer.parseInt(list.get(n));
                                if (n == list.size() - 1) {
                                    if (Integer.parseInt(SR_endtime) + 1 - Integer.parseInt(list.get(n)) < 3) {
                                        flaguse = false;
                                    }
                                }
                            }
                        }
                    }


                    if (flag == true && flaguse == true) {
                        data.setName(SR_place); //스터디룸 장소 이름

                        data.setNum(SR_id);  //스터디룸 식별 번호

                        data.setPeople(SR_minperson + "명 ~ " + SR_maxperson + "명");

                        data.setTime(SR_starttime + ":00 ~ " + SR_endtime + ":00");

                        data.setTimestart(SR_starttime);

                        data.setTimeend(SR_endtime);

                        adapter.addItem(data);
                    }
                }
                adapter.notifyDataSetChanged();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
