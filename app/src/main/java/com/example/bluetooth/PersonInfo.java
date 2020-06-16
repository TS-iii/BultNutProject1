package com.example.bluetooth;

import java.io.Serializable;

public class PersonInfo implements Serializable {


    String name;
    Integer device;
    String contents;

    public PersonInfo(String name, Integer device, String contents){
        this.name=name;
        this.device=device;
        this.contents=contents;
    }

    public String getName(){return name;}
    public void setName(String name) { this.name=name;}

    public Integer getDevice(){return device;}
    public void setDevice(Integer device){this.device=device;}

    public String getContents(){return contents;}
    public void setContents(String contents){this.contents=contents;}

    @Override
    public String toString(){
        return "PersonInfo{"+
                "name='" + name + '\'' +
                ", author='" + device + '\'' +
            ", contents='" + contents + '\'' +
            '}';
    }

}
