package com.example.ex_login;

public class reserve_list {
    String reserve_date;
    String reserve_time;
    String reserve_name;

    reserve_list(String reserve_date,String reserve_time,String reserve_name)
    {

        this.reserve_date = reserve_date;
        this.reserve_time = reserve_time;
        this.reserve_name = reserve_name;

    }
    public  String getReserve_date()
    {
        return  this.reserve_date;
    }

    public String getReserve_time()
    {
        return  this.reserve_time;
    }
    public  String getReserve_name()
    {

        return this.reserve_name;
    }


}
