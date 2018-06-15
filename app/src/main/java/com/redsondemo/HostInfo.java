package com.redsondemo;

import com.redson.IJSONObject;
import com.redson.JSON;
import com.redson.JSONStringer;

public class HostInfo implements IJSONObject{
      
    public HostInfo() {  
    this.Name = "";  
    this.Ip="";  
    }  
      
    public HostInfo(String Name, String Ip) {  
    this.Name = Name;  
    this.Ip=Ip;  
    }  
      
    String Name ="";  
    String Ip="";  
      
    public String getName() {  
        return Name;  
    }  
    public void setName(String name) {  
        Name = name;  
    }  
    public String getIp() {  
        return Ip;  
    }

    @Override
    public String toJSONString() {
        return null;
    }

    @Override
    public void toJSONString(JSONStringer redsonStringWriter) {

    }

    @Override
    public JSON toJSON() {
        return null;
    }

    @Override
    public void fromJSON(String json) {

    }

    @Override
    public void fromJSON(JSON json) {

    }
}