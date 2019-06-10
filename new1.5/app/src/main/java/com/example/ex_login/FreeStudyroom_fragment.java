package com.example.ex_login;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class FreeStudyroom_fragment extends Fragment {

    private  RecyclerView.LayoutManager layoutManager;
    ArrayList<study_list> study_roomArr = null;
    SwipeRefreshLayout swipeRefreshLayout;
    private Free_RecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.free_studyroom, container, false);

        adapter = new Free_RecyclerViewAdapter(getActivity());
        recyclerView = (RecyclerView)view.findViewById(R.id.FreeStudy_room_RecyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swope_refresh2);

        SimpleDateFormat today = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String todaydate = today.format(date).toString()+"";
        HttpTask httpTask = new HttpTask();
        httpTask.setdate(todaydate);
        httpTask.execute();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SimpleDateFormat today = new SimpleDateFormat("yyyyMMdd");
                Date date = new Date();
                String todaydate = today.format(date).toString()+"";
                HttpTask httpTask = new HttpTask();
                httpTask.setdate(todaydate);
                httpTask.execute();
                swipeRefreshLayout.setRefreshing(false);

            }
        });
        return view;
    }
    public void onResume()
    {
        super.onResume();

    }
    class HttpTask extends AsyncTask<Void, Void, String> {

        private  String  curdate;
        private  String urlPath = "http://interface518.dothome.co.kr/caps/fillter2.php?cdate=";

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
        String R_free = null;
        String SR_major;
        String SR_minor;
        String SR_uuid;

        boolean flag = true;
        boolean flaguse = true;
        int flagusenum = 0;
        int temp;

        try {

            JSONArray jarray = new JSONObject(jsonString).getJSONArray("room");
            for (int i = 0; i < jarray.length(); i++) {

                JSONObject jObject = jarray.getJSONObject(i);

                SR_id = jObject.optString("SR_id");
                SR_place = jObject.optString("SR_place");
                SR_starttime = jObject.optString("SR_starttime");
                SR_endtime = jObject.optString("SR_endtime");
                SR_minperson = jObject.optString("SR_minperson");
                SR_maxperson = jObject.optString("SR_maxperson");
                S_NowReservate = jObject.optString("S_NowReservate");
                reserv = jObject.optString("reserv");
                SR_major= jObject.optString("SR_Major");
                SR_minor= jObject.optString("SR_Minor");
                SR_uuid= jObject.optString("SR_beacon");

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
                        R_free = jO.optString("R_free");
                        data.setReserveL(R_starttime);
                        if (R_usetime.equals("2")) {
                            data.setReserveL(String.valueOf(Integer.parseInt(R_starttime) + 1));
                        }
                        if(R_free.equals("2"))
                        {
                            flag = false;
                        }
                    }
                }
                ArrayList<String> list = data.getReserveL();

                data.setName(SR_place); //스터디룸 장소 이름

                data.setNum(SR_id);  //스터디룸 식별 번호

                data.setPeople(SR_minperson + "명 ~ " + SR_maxperson + "명");

                data.setMaxpeople(SR_maxperson);
                data.setMinpeople(SR_minperson);
                data.setTime(SR_starttime + ":00 ~ " + SR_endtime + ":00");
                data.setUuid(SR_uuid);
                data.setMajor(SR_major);
                data.setMinor(SR_minor);
                data.setTimestart(SR_starttime);
                data.setTimeend(SR_endtime);

                if(flag == false) {
                    flag = true;
                    adapter.addItem(data);
                }
            }
            adapter.notifyDataSetChanged();


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
