package com.example.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class PersonInfo implements Serializable, Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(this.name);
        dest.writeString(this.contents);
        dest.writeInt(this.device);

    }

    String name;
    Integer device;
    String contents;

    public PersonInfo(String name, Integer device, String contents){
        this.name=name;
        this.device=device;
        this.contents=contents;
    }

    public PersonInfo(Parcel in){
    this.name=in.readString();
    this.device=in.readInt();
    this.contents=in.readString();
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


    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR=new Parcelable.Creator(){

        @Override
        public PersonInfo createFromParcel(Parcel in){
            return new PersonInfo(in);
        }

        @Override
        public PersonInfo[] newArray(int size){
            return new PersonInfo[size];
        }

    };
}
