package com.example.ex_login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListPopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Vector;

import static android.app.Activity.RESULT_OK;

public class Studyroom_Fragment extends Fragment {
    Spinner loc_spinner,start_spinner,use_spinner,people_spinner;

    private  RecyclerView.LayoutManager layoutManager;
    ArrayList<study_list> study_roomArr = null;
    String loc = "전체";
    String start= "전체";
    String people= "전체";
    String use = "전체";
    int sendcount = 0;
    Layout layout;
    RecyclerView recyclerView;
    Button todatbtn;
    public String curdate;
    public String curdate2;
    private RecyclerViewAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    public static final int REQUEST_CODE_ANOTHER = 1001;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (View)inflater.inflate(R.layout.studyroom,container,false);

        todatbtn = (Button)getActivity().findViewById(R.id.today);
        loc_spinner = (Spinner)view.findViewById(R.id.location);
        start_spinner = (Spinner)view.findViewById(R.id.starttime);
        use_spinner = (Spinner)view.findViewById(R.id.usetime);
        people_spinner = (Spinner)view.findViewById(R.id.people);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh1);

        // 스피너 길이 제한
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            ListPopupWindow window = (ListPopupWindow)popup.get(start_spinner);
            window.setHeight(700); //pixel
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            ListPopupWindow window = (ListPopupWindow)popup.get(people_spinner);
            window.setHeight(700); //pixel
        } catch (Exception e) {
            e.printStackTrace();
        }



        // 필터링 스피너
        String[] loc_array = {"전체","7F","4F","2F","1F","진관"};
        String[] start_array = {"전체","00시","01시","02시","03시","04시","05시","06시","07시","08시","09시","10시","11시","12시","13시",
                "14시","15시","16시","17시","18시","19시","20시","21시","22시","23시"};
        String[] use_array = {"전체","1시간","2시간"};
        String[] people_array = {"전체","2","3","4","5","6","7","8","9","10~"};

        ArrayAdapter<String> location_adapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_item,loc_array);
        ArrayAdapter<String> start_adapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_item,start_array);
        ArrayAdapter<String> use_adpter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_item,use_array);
        ArrayAdapter<String> people_adapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_item,people_array);
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

        adapter = new RecyclerViewAdapter(getActivity());

        curdate = todatbtn.getText().toString();
        curdate = curdate.substring(0,4)+curdate.substring(6,8)+curdate.substring(10,12);
        HttpTask httpTask = new HttpTask();
        httpTask.setdate(curdate);
        httpTask.execute();
        recyclerView = (RecyclerView)view.findViewById(R.id.Study_room_RecyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        //adapter = new RecyclerViewAdapter(study_roomArr);

        todatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(),Sub_CalendarActivity.class);
                startActivityForResult(intent,REQUEST_CODE_ANOTHER);

            }
        });

        //adapter = new RecyclerViewAdapter(study_roomArr);
        loc_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                curdate = todatbtn.getText().toString();
                curdate = curdate.substring(0,4)+curdate.substring(6,8)+curdate.substring(10,12);
                loc = (String)loc_spinner.getSelectedItem();
                if(sendcount >3) {
                    HttpTask httpTask = new HttpTask();
                    httpTask.setdate(curdate);
                    httpTask.execute();
                }
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
                curdate = todatbtn.getText().toString();
                curdate = curdate.substring(0,4)+curdate.substring(6,8)+curdate.substring(10,12);
                if(sendcount >3)
                {
                    HttpTask httpTask = new HttpTask();
                    httpTask.setdate(curdate);
                    httpTask.execute();
                }
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
                curdate = todatbtn.getText().toString();
                curdate = curdate.substring(0,4)+curdate.substring(6,8)+curdate.substring(10,12);
                if(sendcount >3)
                {
                    HttpTask httpTask = new HttpTask();
                    httpTask.setdate(curdate);
                    httpTask.execute();
                }
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
                curdate = todatbtn.getText().toString();
                curdate = curdate.substring(0,4)+curdate.substring(6,8)+curdate.substring(10,12);
                if(sendcount >3)
                {
                    HttpTask httpTask = new HttpTask();
                    httpTask.setdate(curdate);
                    httpTask.execute();
                }
                sendcount++;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                people = "전체";
            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                curdate = todatbtn.getText().toString();
                curdate = curdate.substring(0, 4) + curdate.substring(6, 8) + curdate.substring(10, 12);

                HttpTask httpTask = new HttpTask();
                httpTask.setdate(curdate);
                httpTask.execute();
                swipeRefreshLayout.setRefreshing(false);

            }
        });
        return view;


    }

    public void onActivityResult(int request_code, int resultCode, Intent intent)
    {
        super.onActivityResult(request_code,resultCode,intent);
        if(resultCode == RESULT_OK)
        {
            String rechange = intent.getExtras().getString("change");
            todatbtn.setText(rechange);
            Toast.makeText(getActivity(),rechange,Toast.LENGTH_SHORT).show();

            curdate = todatbtn.getText().toString();
            curdate = curdate.substring(0,4)+curdate.substring(6,8)+curdate.substring(10,12);
            HttpTask httpTask = new HttpTask();
            httpTask.setdate(curdate);
            httpTask.execute();



        }


    }


    class HttpTask extends AsyncTask<Void, Void, String> {

        private  String  curdate;
        private  String urlPath = "http://interface518.dothome.co.kr/caps/fillter.php?cdate=";

        public void setdate(String curdate)
        {
            this.curdate = curdate;
            urlPath = urlPath + curdate;
        }

        @Override
        protected String doInBackground(Void... voids) {
            // TODO Auto-generated method stub
            try{
                HttpPost request = new HttpPost(urlPath);
                //전달할 인자들
                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
                if(loc.equals("전체")) { loc = "*"; }
                if(people.equals("전체")) { people = "*"; }
                if(loc.equals("진관")) { loc = "진관홀-B1F"; }
                if(loc.equals("7F")) { loc = "학술정보원-7F"; }
                if(loc.equals("4F")) { loc = "학술정보원-4F"; }
                if(loc.equals("2F")) { loc = "학술정보원-2F"; }
                if(loc.equals("1F")) { loc = "학술정보원-1F"; }

                nameValue.add(new BasicNameValuePair("loc", loc));
                nameValue.add(new BasicNameValuePair("start", start));
                nameValue.add(new BasicNameValuePair("use", use));
                nameValue.add(new BasicNameValuePair("people", people));
                //웹 접속 - utf-8 방식으로
                if(loc.equals("*")) { loc = "전체"; }
                if(people.equals("*")) { people = "전체"; }
                if(loc.equals("진관홀-B1F")) { loc = "진관"; }
                if(loc.equals("학술정보원-7F")) { loc = "7F"; }
                if(loc.equals("학술정보원-4F")) { loc = "4F"; }
                if(loc.equals("학술정보원-2F")) { loc = "2F"; }
                if(loc.equals("학술정보원-1F")) { loc = "1F"; }

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

            if(value.length() > 15)
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
                adapter.clear();
                recyclerView.setAdapter(adapter);
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
                        Integer.parseInt(start) < Integer.parseInt(SR_endtime)
                        &&!(Integer.parseInt(start) == Integer.parseInt(SR_endtime)-1&&use.equals("2시간"))) {
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

                        data.setMaxpeople(SR_maxperson);

                        data.setMinpeople(SR_minperson);

                        data.setTimestart(SR_starttime);

                        data.setCurdate(curdate);

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
