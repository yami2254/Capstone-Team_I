package com.example.ex_login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import android.widget.CheckBox;
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
import java.util.Vector;





public class MainActivity extends FragmentActivity {

    Button loginbtn;
    CalendarView calendarView;
    Button todaybtn;
    private  int flag = 0;
    private String id;
    private String password;
    EditText idtxt;
    EditText passtxt;
    public int flag2 = 0;
    String changedate;
    CheckBox box;
    //자동 로그인

    //서버 주소
    private final String urlPath = "http://interface518.dothome.co.kr/caps/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences auto = getSharedPreferences("auto",0);
        String loginId = auto.getString("inputId",null);
        String loginPwd = auto.getString("inputPwd",null);

        if(loginId !=null && loginPwd !=null) {
            Intent intentgo = new Intent(getApplicationContext(), SearchActivity.class);
            intentgo.putExtra("change", changedate);
            startActivity(intentgo);
            //finish();
        }

        loginbtn =   (Button)findViewById(R.id.loginBtn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idtxt = (EditText)findViewById(R.id.usernameEntry);
                id = idtxt.getText().toString();
                passtxt = (EditText)findViewById(R.id.passwordEntry);
                password = passtxt.getText().toString();
                new HttpTask().execute();
            }
        });

    }

    class HttpTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            // TODO Auto-generated method stub
            try{
                HttpPost request = new HttpPost(urlPath);
                //전달할 인자들
                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
                nameValue.add(new BasicNameValuePair("userid", id));
                nameValue.add(new BasicNameValuePair("userpasswd", password));

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
            box = (CheckBox)findViewById(R.id.checkbox1);

            if(value.length() == 3) {
                if(box.isChecked()) {
                    SharedPreferences auto = getSharedPreferences("auto",0);
                    SharedPreferences.Editor autoLogin = auto.edit();
                    autoLogin.putString("inputId", id);
                    autoLogin.putString("inputPwd", password);
                    autoLogin.commit();
                }
                Intent intentgo = new Intent(getApplicationContext(), SearchActivity.class);
                intentgo.putExtra("change", changedate);
                startActivity(intentgo);
                finish();
            }
            else
            {
                passtxt.setText("");
                Toast.makeText(MainActivity.this,"아이디 혹은 비밀번호가 틀렸습니다.",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
