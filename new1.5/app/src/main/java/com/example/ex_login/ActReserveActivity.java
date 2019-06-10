package com.example.ex_login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Vector;


public class ActReserveActivity extends FragmentActivity {

    Spinner use_purpose_spinner;
    TextView Reserve_StudtRoom_name;
    TextView Reserve_StudtRoom_num;
    TextView Reserve_StudtRoom_time;
    TextView Reserve_studtRoom_people;
    private Button Reserve_Act_btn;
    private Button Reserve_cancle_btn;
    private ImageButton Plus_partner;
    private ImageButton Minus_partner;
    private ImageButton Address_btn;
    private ArrayList<String> list;
    TextView[] tablel = new TextView[24];
    int starttime;
    int endtime;
    ListView partnerView;
    private PartnerViewAdapter adapter;
    Layout layout;
    int maxpeople;
    int minpeople;
    boolean flag = true;
    int peoplecount = 0;
    int reserveflag[];
    int r, r2, index;
    boolean selectflag = true;
    String peopleid;
    String peoplename;
    String curdate;
    int usetime;
    int penaltyflag = 0;
    int timecheckflag = 0;
    int reservecheckflag = 0;


    private ArrayList<EditText> find = new ArrayList<>();
    private ArrayList<partner_list> listViewItemList = new ArrayList<partner_list>();
    private ArrayList<partner_list> filterlist = listViewItemList;
    private ArrayList<String> find2 = new ArrayList<>();

    private final String urlPathmypanelty = "http://interface518.dothome.co.kr/caps/panel.php?userid="+MainActivity.id+"&username="+MainActivity.finalname;
    private final String urlPathmytimecheck = "http://interface518.dothome.co.kr/caps/timechk.php?userid="+MainActivity.id+"&rdate="+curdate;
    //private final String urlPathpanelty = "http://interface518.dothome.co.kr/caps/panel.php?userid=14011019&username=배경환";
    private final String urlPathtimecheck = "http://interface518.dothome.co.kr/caps/timechk.php?userid="+peopleid+"&rdate="+curdate;
    private final String urlPath = "http://interface518.dothome.co.kr/caps/freservate.php";

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserve_act);
        Reserve_StudtRoom_name = (TextView)findViewById(R.id.Act_reserve_roomname);
        Reserve_StudtRoom_num = (TextView)findViewById(R.id.Act_reserve_roomnum);
        Reserve_StudtRoom_time = (TextView)findViewById(R.id.Act_reserve_roomtime);
        Reserve_studtRoom_people = (TextView)findViewById(R.id.Act_reserve_people);
        Reserve_Act_btn = (Button)findViewById(R.id.Act_reserve_btn);
        Reserve_cancle_btn = (Button)findViewById(R.id.Act_reserve_canclebtn);
        partnerView = (ListView)findViewById(R.id.parnerView);
        Plus_partner = (ImageButton)findViewById(R.id.plus_partner);
        Minus_partner = (ImageButton)findViewById(R.id.minus_partner);
        Address_btn = (ImageButton)findViewById(R.id.addressbtn);

        use_purpose_spinner = (Spinner)findViewById(R.id.purpose_spinner);
        reserveflag = new int[24];

        String[] purpose_array = {"스터디 목적","강의 목적","조별과제 목적","발표연습 목적","기타 목적"};

        ArrayAdapter<String> purpose_adapter = new ArrayAdapter<String>(this,R.layout.spinner_item2,purpose_array);
        use_purpose_spinner.setAdapter(purpose_adapter);
        use_purpose_spinner.setSelection(0);
        use_purpose_spinner.setGravity(Gravity.CENTER);




        LinearLayoutManager layoutManager = new LinearLayoutManager(this);


        Intent intent = getIntent();
        processIntent(intent);

        adapter = new PartnerViewAdapter();
        partnerView.setAdapter(adapter);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(85 ,80);
        LinearLayout timetable = (LinearLayout)findViewById(R.id.timetable);

        TextView  textView = new TextView(this);
        timetable.setGravity(Gravity.CENTER);

        for(int j=0;j<24;j++)
        {
            if(j>= starttime && j< endtime) {
                reserveflag[j] = 1;
                for (int k = 0; k < list.size(); k++) {
                    if (Integer.parseInt(list.get(k)) == j) {
                        reserveflag[j] = 0;
                    }
                }
            }
            else
            {
                reserveflag[j] = 0;
            }

        }

        for(int j=starttime;j<endtime;j++) {


            textView.setText("" + j);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.BLACK);
            textView.setBackgroundResource(R.drawable.layer_nonselcetfull);
            for (int k = 0; k < list.size(); k++) {

                if (Integer.parseInt(list.get(k)) == j) {

                    textView.setBackgroundResource(R.drawable.layer_selectfull);
                }

            }

            timetable.addView(textView, layoutParams);
            tablel[j] = textView;
            textView = new TextView(this);
        }

       for(r = starttime;r<endtime;r++)
       {
           tablel[r].setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   TextView vr = (TextView)v;
                   index = Integer.parseInt(vr.getText().toString());
                    if(reserveflag[index] == 1)
                    {
                        for(r2 = 0;r2<reserveflag.length;r2++)
                        {
                            if(reserveflag[r2] == 2)
                                break;
                        }
                        if(r2 == reserveflag.length) {
                            v.setBackgroundResource(R.drawable.layer_checkfull);
                            reserveflag[index] = 2;
                        }
                        else if(selectflag == true && (reserveflag[index-1] == 2 || reserveflag[index+1] == 2)){
                            v.setBackgroundResource(R.drawable.layer_checkfull);
                            reserveflag[index] = 2;
                            selectflag = false;
                        }
                    }
                    else if(reserveflag[index] == 2)
                    {
                        v.setBackgroundResource(R.drawable.layer_nonselcetfull);
                        reserveflag[index] = 1;
                        selectflag = true;
                    }

               }
           });
       }

    for(int i=0;i<minpeople-1;i++)
    {
        adapter.addItem("","");
        adapter.notifyDataSetChanged();
    }

    Plus_partner.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(peoplecount < maxpeople-1)
            {
                adapter.addItem("","");
                adapter.notifyDataSetChanged();
                peoplecount++;
            }
        }
    });

    Minus_partner.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(peoplecount > minpeople)
            {
                adapter.delItem();
                adapter.notifyDataSetChanged();
                peoplecount--;
            }
        }
    });

    Address_btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),AddressbookActivity.class);
            intent.putExtra("EnablePeople",maxpeople-1);
            startActivityForResult(intent,1001);



        }
    });


    Reserve_Act_btn.setOnClickListener(new View.OnClickListener() {
        String namemessage = "";
        @Override
        public void onClick(View v) {
            // 여기다 예약하면된다.
            System.out.println("예약 버튼 클릭");
            usetime = 0;
            starttime = 0;
            for (int i = 0; i < reserveflag.length; i++) {
                if (reserveflag[i] == 2) {
                    usetime++;
                    if (usetime == 1) {
                        starttime = i;
                    }
                }
            }

            peopleid = MainActivity.id;
            peoplename = MainActivity.finalname;
            System.out.println(peopleid);
            System.out.println(peoplename);

            penaltyflag = 0;
            timecheckflag = 0;
            reservecheckflag = 0;

            httppenaltystart(peopleid, peoplename);

            if (reservecheckflag - 1 == listViewItemList.size()) {
                finish();
            }
        }
    });

        Reserve_cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public void onActivityResult(int request_code, int resultCode, Intent intent)
    {
        super.onActivityResult(request_code,resultCode,intent);
        if(resultCode == RESULT_OK)
        {
            int check = intent.getIntExtra("CheckPeople",0);

            /*if(minpeople-1 < check)
            {
                for(int i=0;i<check-peoplecount-1;i++)
                {
                    if(peoplecount < maxpeople-1)
                    {
                        adapter.addItem("","");
                        adapter.notifyDataSetChanged();
                        peoplecount++;
                    }
                }
            }*/
            if(check < (peoplecount -1) && check >= minpeople -1)
            {
                for(int i = 0; i<(peoplecount-1) - check;i++)
                {
                    adapter.delItem();
                    adapter.notifyDataSetChanged();
                }
            }

        for(int i= peoplecount-1;i<check;i++) {
            adapter.addItem("", "");
            adapter.notifyDataSetChanged();
            peoplecount++;
        }
            for(int i=0;i<check;i++)
            {
                String id = intent.getStringExtra("s_id"+i);
                String name = intent.getStringExtra("s_name"+i);
                adapter.setEdit(id,name,i);

            }



        }


    }

    public void httppenaltystart(String peopleid,String peoplename) {
        HttpTaskpenalty httpTaskpenalty = new HttpTaskpenalty();
        httpTaskpenalty.setpeople(peopleid,peoplename);
        httpTaskpenalty.execute();

    }
    public void httptimecheckstart(String peopleid,String curdate) {
        HttpTasktimecheck httpTasktimecheck = new HttpTasktimecheck();
        httpTasktimecheck.setpeople(peopleid,curdate);
        httpTasktimecheck.execute();

    }
    public void httpstart(String peopleid){
        HttpTaskreserve httpTaskreserve = new HttpTaskreserve();
        httpTaskreserve.setpeople(peopleid);
        httpTaskreserve.execute();
    }
    public void httpstart2(String peopleid) {
        HttpTaskreserve2 httpTaskreserve2 = new HttpTaskreserve2();
        httpTaskreserve2.setpeople(peopleid);
        httpTaskreserve2.execute();
    }


    private void processIntent(Intent intent)
    {
        Reserve_StudtRoom_name.setText(intent.getExtras().getString("StudtRoom_name"));
        Reserve_StudtRoom_num.setText(intent.getExtras().getString("StudtRoom_num"));
        Reserve_StudtRoom_time.setText(intent.getExtras().getString("StudtRoom_time"));
        maxpeople = Integer.parseInt(intent.getExtras().getString("StudtRoom_maxpeople"));
        minpeople = Integer.parseInt(intent.getExtras().getString("StudtRoom_minpeople"));
        Reserve_studtRoom_people.setText(intent.getExtras().getString("StudtRoom_people"));
        this.list =  intent.getStringArrayListExtra("StudtRoom_tablev");
        starttime = intent.getExtras().getInt("starttime");
        endtime = intent.getExtras().getInt("endtime");
        curdate = intent.getExtras().getString("curdate");
        peoplecount = minpeople;

    }

    public class PartnerViewAdapter extends BaseAdapter {

        public    String name,sid;

        @Override
        public int getCount() {
            return filterlist.size();
        }
        @Override
        public Object getItem(int position) {
            return filterlist.get(position);
        }


        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Context context = parent.getContext();
            final ViewHolder holder;


            if (convertView == null) {
                holder = new ViewHolder();
                LayoutInflater inflater =
                        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.partner_item, parent, false);
                holder.editText1 = (EditText) convertView.findViewById(R.id.partner_id);
                holder.editText2 = (EditText) convertView.findViewById(R.id.partner_name);
                convertView.setTag(holder);
            }
            else{
                holder = (ViewHolder)convertView.getTag();
            }
            holder.ref = position;

            final partner_list partnerList = filterlist.get(position);
            holder.editText1.setText(partnerList.getPartner_id());
            holder.editText2.setText(partnerList.getPartner_name());

            holder.editText1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    filterlist.get(holder.ref).setPartner_id(s.toString());
                }
            });

            holder.editText2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    filterlist.get(holder.ref).setPartner_name(s.toString());
                }
            });
            return convertView;
        }

        public void addItem(String id, String name)
        {
            partner_list item = new partner_list();
            item.setPartner_name(name);
            item.setPartner_id(id);
            listViewItemList.add(item);
        }

        public void delItem() {
            if (listViewItemList.size() < 1) {
            } else {
                listViewItemList.remove(listViewItemList.size() - 1);
            }
        }

        public void setEdit(String id, String name, int i) {
            filterlist.get(i).setPartner_id(id);
            filterlist.get(i).setPartner_name(name);
            notifyDataSetChanged();


        }
    }


    class HttpTaskreserve extends AsyncTask<Void, Void, String> {

        String peopleid;

        public void setpeople(String peopleid)
        {
            this.peopleid = peopleid;
        }
        @Override
        protected String doInBackground(Void... voids) {
            // TODO Auto-generated method stub
            try{
                HttpPost request = new HttpPost("http://interface518.dothome.co.kr/caps/freservate.php");
                //전달할 인자들
                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();

                System.out.println(peopleid + " " +String.valueOf(starttime) + " " + (String)Reserve_StudtRoom_num.getText()+ " " +String.valueOf(usetime) + " " +curdate);
                nameValue.add(new BasicNameValuePair("userid", peopleid ));
                nameValue.add(new BasicNameValuePair("rstart", String.valueOf(starttime)));
                nameValue.add(new BasicNameValuePair("rstudy",(String)Reserve_StudtRoom_num.getText()));
                nameValue.add(new BasicNameValuePair("rtimee",String.valueOf(usetime)));
                nameValue.add(new BasicNameValuePair("rdatee", curdate));

                //웹 접속 - utf-8 방식으로

                HttpEntity enty = new UrlEncodedFormEntity(nameValue,"utf-8");
                request.setEntity(enty);

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
            System.out.println("reserve = "+ value);
            System.out.println(value.length());
            if(value.charAt(value.length()-1) == 'O')
            {
                value +="K";
            }
            if(value.substring(1).equals("OK"))
            {
                for(int i=0;i<listViewItemList.size();i++) {
                    peopleid = listViewItemList.get(i).getPartner_id();
                    httpstart2(peopleid);
                }
            }
        }
    }
    class HttpTaskreserve2 extends AsyncTask<Void, Void, String> {

        String peopleid;

        public void setpeople(String peopleid)
        {
            this.peopleid = peopleid;
        }
        @Override
        protected String doInBackground(Void... voids) {
            // TODO Auto-generated method stub
            try{
                HttpPost request = new HttpPost("http://interface518.dothome.co.kr/caps/freservate2.php");
                //전달할 인자들
                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();

                System.out.println(peopleid + " " +String.valueOf(starttime) + " " + (String)Reserve_StudtRoom_num.getText()+ " " +String.valueOf(usetime) + " " +curdate);
                nameValue.add(new BasicNameValuePair("userid", peopleid ));
                nameValue.add(new BasicNameValuePair("rstart", String.valueOf(starttime)));
                nameValue.add(new BasicNameValuePair("rstudy",(String)Reserve_StudtRoom_num.getText()));
                nameValue.add(new BasicNameValuePair("rtimee",String.valueOf(usetime)));
                nameValue.add(new BasicNameValuePair("rdatee", curdate));

                //웹 접속 - utf-8 방식으로

                HttpEntity enty = new UrlEncodedFormEntity(nameValue,"utf-8");
                request.setEntity(enty);

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

            if(value.charAt(value.length()-1) == 'O')
            {
                value +="K";
            }
            
            if(value.substring(1).equals("OK"))
                reservecheckflag++;
            System.out.println("reserve = "+ value);
            System.out.println(value.length());
            if(reservecheckflag == listViewItemList.size())
            {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ActReserveActivity.this);
                dialog.setTitle("알림")
                        .setTitle("예약 되셨습니다.")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).create().show();
            }
        }
    }
    class HttpTaskpenalty extends AsyncTask<Void, Void, String> {

        String peopleid;
        String peoplename;
        private  String urlPathpenalty;

        public void setpeople(String peopleid, String peoplename)
        {
            this.peopleid = peopleid;
            this.peoplename = peoplename;
            urlPathpenalty = "http://interface518.dothome.co.kr/caps/panel.php?userid="+peopleid+"&username="+peoplename;
        }
        @Override
        protected String doInBackground(Void... voids) {
            // TODO Auto-generated method stub
            try{
                HttpPost request = new HttpPost(urlPathpenalty);
                //전달할 인자들
                System.out.println("http://interface518.dothome.co.kr/caps/panel.php?userid="+peopleid+"&username="+peoplename);
                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();

                //웹 접속 - utf-8 방식으로

                HttpEntity enty = new UrlEncodedFormEntity(nameValue,"utf-8");
                request.setEntity(enty);

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

            System.out.println("penalty = "+ value);
            System.out.println(value.length());
            if(listViewItemList.size() > penaltyflag&&!value.substring(1).equals("FAIL")) {

                    peopleid = listViewItemList.get(penaltyflag).getPartner_id();
                    peoplename = listViewItemList.get(penaltyflag).getPartner_name();
                    penaltyflag++;
                    System.out.println(peopleid);
                    System.out.println(peoplename);
                    httppenaltystart(peopleid, peoplename);


            }
            else if(value.substring(1).equals("FAIL"))
            {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ActReserveActivity.this);
                dialog.setTitle("알림")
                        .setTitle(peopleid+" 해당 계정은\n패널티가 부여된 계정입니다.")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).create().show();
            }
            else if(penaltyflag == listViewItemList.size())
            {
                peopleid = MainActivity.id;
                httptimecheckstart(peopleid,curdate);
            }

        }

    }
    class HttpTasktimecheck extends AsyncTask<Void, Void, String> {

        String peopleid;
        String curdate;
        private  String urlPathtimecheck;
        int flag = 1;


        public void setpeople(String peopleid, String curdate)
        {
            this.peopleid = peopleid;
            this.curdate = curdate;
            urlPathtimecheck = "http://interface518.dothome.co.kr/caps/timechk.php?userid="+peopleid+"&rdate="+curdate;
        }
        @Override
        protected String doInBackground(Void... voids) {
            // TODO Auto-generated method stub
            try{
                HttpPost request = new HttpPost(urlPathtimecheck);
                //전달할 인자들
                System.out.println("http://interface518.dothome.co.kr/caps/timechk.php?userid="+peopleid+"&rdate="+curdate);
                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();

                //웹 접속 - utf-8 방식으로

                HttpEntity enty = new UrlEncodedFormEntity(nameValue,"utf-8");
                request.setEntity(enty);

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

            System.out.println("timecheck = "+ value);
            System.out.println(value.length());
            if(Integer.parseInt(value.substring(1))<2 && listViewItemList.size() > timecheckflag) {

                System.out.println("1 :"+ value);
                if(Integer.parseInt(value.substring(1)) == 1)
                {
                    System.out.println("2 :"+ value);
                    if(usetime == 1)
                    {
                        peopleid = listViewItemList.get(timecheckflag).getPartner_id();
                        peoplename = listViewItemList.get(timecheckflag).getPartner_name();
                        System.out.println(peopleid);
                        timecheckflag++;
                        httptimecheckstart(peopleid, curdate);
                    }
                    else
                    {

                    }
                }else
                {
                    peopleid = listViewItemList.get(timecheckflag).getPartner_id();
                    peoplename = listViewItemList.get(timecheckflag).getPartner_name();
                    System.out.println(peopleid);
                    timecheckflag++;
                    httptimecheckstart(peopleid, curdate);
                }

            }
            else if(Integer.parseInt(value.substring(1)) >= 2)
            {

                System.out.println("1 :"+ value);
                AlertDialog.Builder dialog = new AlertDialog.Builder(ActReserveActivity.this);
                dialog.setTitle("알림")
                        .setTitle(peopleid+" 해당 계정은\n 예약 시간이 초과하였습니다.")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).create().show();
            }
            else if(timecheckflag == listViewItemList.size())
            {
                peopleid = MainActivity.id;
                httpstart(peopleid);
            }

        }
    }
}
