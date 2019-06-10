package com.example.ex_login;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import java.util.ArrayList;
import java.util.Vector;

public class AddressFragment extends Fragment {
    private RecyclerView addressView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<address_list> address_listArr = null;
    private String myid;
    ImageButton insertbtn;
    ImageButton deletebtn;
    EditText editname;
    EditText editid;
    int cnt = 0;
    String insertnum;
    String insertname;
    String insertid;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (View)inflater.inflate(R.layout.address,container,false);
        insertbtn  = (ImageButton)view.findViewById(R.id.insertbtn);
        deletebtn = (ImageButton)view.findViewById(R.id.deletedbtn);
        addressView = (RecyclerView)view.findViewById(R.id.address_recycleview);
        editid = (EditText)view.findViewById(R.id.editid);
        editname = (EditText)view.findViewById(R.id.editname);
        address_listArr = new ArrayList<address_list>();
        layoutManager = new LinearLayoutManager(getActivity());
        addressView.setHasFixedSize(true);
        addressView.setLayoutManager(layoutManager);
        adapter = new AddressViewAdapter(address_listArr,getActivity());

        HttpTask httptask = new HttpTask();
        httptask.execute();

        insertbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cnt++;
                insertid = editid.getText().toString();
                editid.setText("");
                insertnum = ""+ cnt;
                insertname = editname.getText().toString();
                editname.setText("");
                if(!insertid.equals("")&&!insertname.equals("")) {
                    HttpTaskinsert httpTaskinsert = new HttpTaskinsert();
                    httpTaskinsert.execute();
                }
            }
        });

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int i=0;
                while(true)
                {
                    if(i < address_listArr.size()) {
                        if (address_listArr.get(i).getFlag() == true) {
                            HttpTaskdelete httpTaskdelete = new HttpTaskdelete();
                            httpTaskdelete.setinfo(address_listArr.get(i).name,address_listArr.get(i).id);
                            address_listArr.remove(i);
                            httpTaskdelete.execute();
                            cnt--;
                        } else {
                            i++;
                        }
                    }
                    else
                    {
                        break;
                    }
                }
                adapter = new AddressViewAdapter(address_listArr,getActivity());
                ((AddressViewAdapter) adapter).clear();
                cnt=0;
                HttpTask httptask = new HttpTask();
                httptask.execute();
            }
        });



        layoutManager = new LinearLayoutManager(getActivity());

        return view;
    }

    class HttpTask extends AsyncTask<Void, Void, String> {

        private  String  curdate;
        private  String urlPath = "http://interface518.dothome.co.kr/caps/uaddressmy.php";
        private  String starttime;
        private  String usetime;
        private  String studyroom;

        public void setdate(String curdate)
        {
            this.curdate = curdate;
            urlPath = urlPath + curdate;
        }

        public void settime(String starttime , String usetime,String studyroom)
        {
            this.starttime = starttime;
            this.usetime = usetime;
            this.studyroom = String.valueOf(Integer.parseInt(studyroom.substring(4,6)));

        }

        @Override
        protected String doInBackground(Void... voids) {
            // TODO Auto-generated method stub
            try{
                HttpPost request = new HttpPost("http://interface518.dothome.co.kr/caps/uaddressmy.php");
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

            System.out.println("주소록 : " +value);
            reservejsonlist(value);

        }
    }
    public void reservejsonlist(String jsonString) {

        String addid = null;
        String addname = null;
        String addaddress = null;

        try {

            JSONArray jarray = new JSONObject(jsonString).getJSONArray("webnautes");
            for (int i = 0; i < jarray.length(); i++) {

                JSONObject jObject = jarray.getJSONObject(i);

                addid = jObject.optString("id");
                addname = jObject.optString("name");
                addaddress = jObject.optString("address");
                cnt++;
                insertnum = cnt + "";
                address_listArr.add(new address_list(addname, addaddress, insertnum));
                adapter = new AddressViewAdapter(address_listArr, getActivity());
                addressView.setAdapter(adapter);

                }
                adapter.notifyDataSetChanged();


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class HttpTaskinsert extends AsyncTask<Void, Void, String> {

        private  String  curdate;
        private  String urlPath = "http://interface518.dothome.co.kr/caps/insert.php";
        private  String starttime;
        private  String usetime;
        private  String studyroom;

        public void setdate(String curdate)
        {
            this.curdate = curdate;
            urlPath = urlPath + curdate;
        }

        public void settime(String starttime , String usetime,String studyroom)
        {
            this.starttime = starttime;
            this.usetime = usetime;
            this.studyroom = String.valueOf(Integer.parseInt(studyroom.substring(4,6)));

        }

        @Override
        protected String doInBackground(Void... voids) {
            // TODO Auto-generated method stub
            try{
                HttpPost request = new HttpPost("http://interface518.dothome.co.kr/caps/uadressinsert.php");
                //전달할 인자들
                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();

                nameValue.add(new BasicNameValuePair("userid", MainActivity.id));
                nameValue.add(new BasicNameValuePair("adname", insertname));
                nameValue.add(new BasicNameValuePair("useraddress", insertid));
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
            if(value.substring(1).equals("OK")) {
                address_listArr.add(new address_list(insertname, insertid, insertnum));
                adapter = new AddressViewAdapter(address_listArr, getActivity());
                addressView.setAdapter(adapter);
            }
        }
    }

    class HttpTaskdelete extends AsyncTask<Void, Void, String> {

        int i;

        String deletename;
        String deleteid;

        public void setinfo(String name, String id)
        {
            this.deletename = name;
            this.deleteid = id;
        }


        @Override
        protected String doInBackground(Void... voids) {
            // TODO Auto-generated method stub
            try{
                HttpPost request = new HttpPost("http://interface518.dothome.co.kr/caps/uadressdelete.php");
                //전달할 인자들
                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();

                nameValue.add(new BasicNameValuePair("userid", MainActivity.id));
                nameValue.add(new BasicNameValuePair("adname", deletename));
                nameValue.add(new BasicNameValuePair("useraddress", deleteid));
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

            System.out.println("삭제 : " +value);
                adapter = new AddressViewAdapter(address_listArr,getActivity());
                addressView.setAdapter(adapter);

        }
    }
}
