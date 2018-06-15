package com.redson;


import org.json.JSONException;

import java.util.List;
import java.util.Map;

/**
 * Created by Linhh on 12/02/2018.
 */

public class JSONStringer {

    RedsonStringWriter mRedsonStringWriter = new RedsonStringWriter();

    public JSONStringer(){

    }

    public void writeToList(List list) {
        objectList();
        if(list != null){
            for (int i = 0; i < list.size(); i ++) {
                if(i != 0){
                    mRedsonStringWriter.append(",");
                }
                Object object = list.get(i);
                value(object);
            }
        }
        endList();
    }

    public void writeToMap(Map map) {
        object();
        if(map != null) {
            int i = 0;
            for (Map.Entry<String, Object> entry : ((Map<String, Object>) map).entrySet()) {
                key(entry.getKey(), i).value(entry.getValue());
                i++;
            }
        }
        endObject();
    }

    public JSONStringer key(String key, int i){
        if(i != 0){
            mRedsonStringWriter.append(",");
        }
        mRedsonStringWriter.append("\"").append(key).append("\"").append(":");
        return this;
    }

    public void writeToObject(String key, double object){
//        object();
        mRedsonStringWriter.append("\"").append(key).append("\"").append(":");
        value(object);
//        endObject();
    }

    public void writeToObject(String key, float object){
//        object();
        mRedsonStringWriter.append("\"").append(key).append("\"").append(":");
        value(object);
//        endObject();
    }

    public void writeToObject(String key, boolean object){
//        object();
        mRedsonStringWriter.append("\"").append(key).append("\"").append(":");
        value(object);
//        endObject();
    }

    public void writeToObject(String key, long object){
//        object();
        mRedsonStringWriter.append("\"").append(key).append("\"").append(":");
        value(object);
//        endObject();
    }

    public void writeToObject(String key, int object){
//        object();
        mRedsonStringWriter.append("\"").append(key).append("\"").append(":");
        value(object);
//        endObject();
    }

    public void writeToObject(String key, Object object){
//        object();
        mRedsonStringWriter.append("\"").append(key).append("\"").append(":");
        value(object);
//        endObject();
    }

    public JSONStringer value(Object object){
        if(object == null){
            //如果是空的，则写入null
            mRedsonStringWriter.append("null");
        }else if(Redson.instance().isList(object)){
            //处理list
            writeToList((List)object);
        }else if(Redson.instance().isMap(object)){
            //处理map
            writeToMap((Map)object);
        }else if(Redson.instance().isBaseVar(object)){
            //基础数据
            if(object.getClass() == String.class){
                mRedsonStringWriter.append("\"").append(object.toString()).append("\"");
            }else{
                mRedsonStringWriter.append(object.toString());
            }

        }else{
            //说明是json
            ((IJSONObject)object).toJSONString(this);
        }
        return this;
    }

    public void objectList(){
        mRedsonStringWriter.append("[");
    }

    public void endList(){
        mRedsonStringWriter.append("]");
    }

    public void object(){
        mRedsonStringWriter.append("{");
    }

    public void endObject(){
        mRedsonStringWriter.append("}");
    }

    public void dot(){
        mRedsonStringWriter.append(",");
    }

    public String toString(){
        return mRedsonStringWriter.toString();
    }
}
