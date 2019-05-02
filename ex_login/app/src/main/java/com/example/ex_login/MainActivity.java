package com.example.ex_login;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;






public class MainActivity extends AppCompatActivity {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginbtn =   (Button)findViewById(R.id.loginBtn);


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idtxt = (EditText)findViewById(R.id.usernameEntry);
                id = idtxt.getText().toString();
                passtxt = (EditText)findViewById(R.id.passwordEntry);
                password = passtxt.getText().toString();
                Intent intent = new Intent(getApplicationContext(),SearchActivity.class);
                intent.putExtra("change",changedate);
                startActivity(intent);




            }
        });

    }



}
