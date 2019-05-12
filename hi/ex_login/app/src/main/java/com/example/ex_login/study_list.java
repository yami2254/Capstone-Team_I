package com.example.ex_login;

public class study_list {

    private String name;
    private  String time;
    private String num;
    private String timetable ="";
    private int timestart;
    private int timeend;
    private String timenum ="";





    public void setName(String name) {
        this.name = name;
    }
    public void setTimeend(String timeend) {
        this.timeend = Integer.parseInt(timeend);
    }
    public void setTimestart(String timestart) {
        this.timestart = Integer.parseInt(timestart);
        ;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public void setNum(String num) {
        this.num = num;
    }


    public String getName(){
        return name;
    }
    public String getTime(){
        return time;
    }
    public String getNum() { return num; }
    public String getTimenum()
    {
        for (int i = 0; i < timeend-timestart;i++)
        {
            timenum += (i+1)+ " ";
        }
        return timenum;
    }
    public String getTimetable()
    {
        for (int i = 0; i < timeend-timestart;i++)
        {
            timetable += "ㅁ ";
        }
        return timetable;
    }
}
