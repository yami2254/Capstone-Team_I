package com.example.ex_login;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import java.util.ArrayList;
import java.util.Vector;

public class ReserveFragment extends Fragment {

    ArrayList<reserve_list> reserve_listArr = null;
    private String myid;

    private RecyclerView.LayoutManager  layoutManager;
    private ReserveViewAdapter adapter;
    RecyclerView reserveView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (View)inflater.inflate(R.layout.reserve,container,false);

        adapter = new ReserveViewAdapter(getActivity());
        reserveView = (RecyclerView)view.findViewById(R.id.reserve_recycleview);
        layoutManager = new LinearLayoutManager(getActivity());
        reserveView.setHasFixedSize(true);
        reserveView.setLayoutManager(layoutManager);
        return view;
    }

    public void onResume()
    {
        super.onResume();

        HttpTask httpTask = new HttpTask();
        httpTask.execute();
    }

    class HttpTask extends AsyncTask<Void, Void, String> {

        private  String urlPath = "http://interface518.dothome.co.kr/caps/myreservation.php?userid="+MainActivity.id;

        @Override
        protected String doInBackground(Void... voids) {
            // TODO Auto-generated method stub
            try{
                System.out.println(urlPath);
                HttpPost request = new HttpPost(urlPath);
                //전달할 인자들
                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();

                nameValue.add(new BasicNameValuePair("userid", MainActivity.id));
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

            System.out.println(getId());
            if(value.length() > 12)
            {
                adapter.clear();
                if(value.charAt(value.length()-1) == ']')
                {
                    value +="}";
                }
                reservejsonlist(value);
                reserveView.setAdapter(adapter);
            }
            else
            {
                adapter.clear();
                reserveView.setAdapter(adapter);
            }


        }
    }

    public void reservejsonlist(String jsonString) {

        String SR_id = null;
        String SR_name = null;
        String SR_time = null;
        String SR_usetime = null;
        String SR_date = null;
        String SR_major;
        String SR_minor;
        String SR_uuid;

        boolean flag = true;
        boolean flaguse = true;
        int flagusenum = 0;
        int temp;

        try {

            JSONArray jarray = new JSONObject(jsonString).getJSONArray("result");
            for (int i = 0; i < jarray.length(); i++) {

                flag = true;
                flaguse = true;
                flagusenum = 0;

                JSONObject jObject = jarray.getJSONObject(i);

                SR_id = jObject.optString("sid");
                SR_name = jObject.optString("srname");
                SR_time = jObject.optString("stime");
                SR_usetime = jObject.optString("usetime");
                SR_date = jObject.optString("rdate");
                SR_major= jObject.optString("srmajor");
                SR_minor= jObject.optString("srminor");
                SR_uuid= jObject.optString("srbeacon");

                reserve_list data = new reserve_list();

                data.setReserve_name(SR_name); //스터디룸 장소 이름

                data.setReserve_date(SR_date);

                data.setReserve_time(SR_time);
                data.setUuid(SR_uuid);
                data.setMajor(SR_major);
                data.setMinor(SR_minor);
                data.setReserve_usetime(SR_usetime);
                adapter.addItem(data);


                adapter.notifyDataSetChanged();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
