package com.redson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by Linhh on 11/02/2018.
 */

public class AndroidSDKJSONObject implements JSON{
    private JSONObject mJSONObject;

    public AndroidSDKJSONObject(){
        mJSONObject = new JSONObject();
    }

    public AndroidSDKJSONObject(String json) throws Exception{
        mJSONObject = new JSONObject(json);
    }

    public AndroidSDKJSONObject(JSONObject jsonObject){
        mJSONObject = jsonObject;
    }

    @Override
    public int optInt(String name) {
        return mJSONObject.optInt(name);
    }

    @Override
    public double optDouble(String name) {
        return mJSONObject.optDouble(name);
    }

    @Override
    public long optLong(String name) {
        return mJSONObject.optLong(name);
    }

    @Override
    public String optString(String name) {
        return mJSONObject.optString(name);
    }

    @Override
    public boolean optBoolean(String name) {
        return mJSONObject.optBoolean(name);
    }

    @Override
    public float optFloat(String name) {
        try{
            return (float) mJSONObject.optDouble(name);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public JSON optJSON(String name) {
        AndroidSDKJSONObject androidSDKJSONObject = null;
        try {
            JSONObject json = mJSONObject.optJSONObject(name);
            if(json != null) {
                androidSDKJSONObject = new AndroidSDKJSONObject(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return androidSDKJSONObject;
    }

    @Override
    public Object get(String name) {
        try {
            mJSONObject.get(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JSONArray optJSONArray(String name) {
        AndroidSDKJSONArray androidSDKJSONArray = null;
        try {
            org.json.JSONArray jsonArray = mJSONObject.optJSONArray(name);
            if(jsonArray != null) {
                androidSDKJSONArray = new AndroidSDKJSONArray(jsonArray);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return androidSDKJSONArray;
    }

    @Override
    public Iterator<String> keys() {
        return mJSONObject.keys();
    }

    @Override
    public void put(String name, Object value) {
        try {
            if (value instanceof AndroidSDKJSONObject) {
                mJSONObject.put(name, ((AndroidSDKJSONObject) value).getOrginJSONObject());
            }else if(value instanceof AndroidSDKJSONArray){
                mJSONObject.put(name, ((AndroidSDKJSONArray) value).getOrginJSONArray());
            }else {
                mJSONObject.put(name, value);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public JSONObject getOrginJSONObject(){
        return mJSONObject;
    }

    @Override
    public void put(String name, int value) {
        try{
            mJSONObject.put(name, value);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void put(String name, double value) {
        try{
            mJSONObject.put(name, value);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void put(String name, long value) {
        try{
            mJSONObject.put(name, value);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void put(String name, boolean value) {
        try{
            mJSONObject.put(name, value);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String toJSONString() {
        return mJSONObject.toString();
    }
}
