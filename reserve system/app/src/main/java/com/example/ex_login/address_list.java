package com.example.ex_login;

public class address_list {
    String name;
    String id;
    String num;
    boolean flag = true;
    address_list(String name, String id, String num)
    {
        this.id  = id;
        this.name = name;
        this.num = num;
    }
    public  String getName()
    {return name;}

    public String getId() {
        return id;
    }

    public String getNum() {
        return num;
    }
    public void setFlag(boolean flag)
    {
        this.flag = flag;
    }
    public boolean getFlag()
    {
        return this.flag;
    }

}
