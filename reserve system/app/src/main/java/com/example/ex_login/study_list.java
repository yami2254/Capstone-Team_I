package com.example.ex_login;

import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class study_list {

    private String name;
    private  String time;
    private String num;
    private String people;
    private int timestart;
    private int timeend;

    private ArrayList<String> reserveL = new ArrayList<String>();




    public void setName(String name) {
        this.name = name;
    }
    public void setTimeend(String timeend) {
        this.timeend = Integer.parseInt(timeend);
    }
    public void setTimestart(String timestart) { this.timestart = Integer.parseInt(timestart); }
    public void setTime(String time) { this.time = time; }
    public void setNum(String num) {
        this.num = num;
    }
    public void setPeople(String people) { this.people = people;}
    public void setReserveL (String a)
    {
        this.reserveL.add(a);
    }

    public String getPeople() { return  people;}
    public int getTimestart(){
        return timestart;
    }
    public int getTimeend(){
        return timeend;
    }
    public String getName(){
        return name;
    }
    public String getTime(){
        return time;
    }
    public String getNum() { return num; }
    public ArrayList getReserveL() {return reserveL;}
}
