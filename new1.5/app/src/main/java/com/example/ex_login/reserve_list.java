package com.example.ex_login;

public class reserve_list {
    String reserve_date;
    String reserve_time;
    String reserve_name;
    String reserve_usetime;
    private String beacon_minor;
    private String beacon_major;
    private String beacon_uuid;

    public void setReserve_date(String date){this.reserve_date = date;}
    public void setReserve_time(String time){this.reserve_time = time;}
    public void setReserve_name(String name){this.reserve_name = name;}
    public void setMajor (String a){this.beacon_major = a;}
    public void setMinor (String a){this.beacon_minor = a;}
    public void setUuid (String a){this.beacon_uuid = a;}
    public void setReserve_usetime(String a){this.reserve_usetime =a;}

    public String getMajor(){return  beacon_major;}
    public String getMinor(){return  beacon_minor;}
    public String getUuid(){return  beacon_uuid;}
    public  String getReserve_date()
    {
        return  this.reserve_date;
    }
    public String getReserve_time()
    {
        return  this.reserve_time;
    }
    public  String getReserve_name() { return this.reserve_name; }
    public  String getReserve_usetime(){return this.reserve_usetime;}


}
