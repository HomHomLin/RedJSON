package com.redson;

import org.json.JSONException;

/**
 * Created by Linhh on 12/02/2018.
 */

public class AndroidSDKJSONArray implements JSONArray{
    private org.json.JSONArray mJSONArray;

    public AndroidSDKJSONArray(){
        mJSONArray = new org.json.JSONArray();
    }

    public AndroidSDKJSONArray(String json) throws Exception{
        mJSONArray = new org.json.JSONArray(json);
    }

    public AndroidSDKJSONArray(org.json.JSONArray array){
        mJSONArray = array;
    }

    public org.json.JSONArray getOrginJSONArray(){
        return mJSONArray;
    }

    @Override
    public int optInt(int index) {
        return mJSONArray.optInt(index);
    }

    @Override
    public double optDouble(int index) {
        return mJSONArray.optDouble(index);
    }

    @Override
    public long optLong(int index) {
        return mJSONArray.optLong(index);
    }

    @Override
    public String optString(int index) {
        return mJSONArray.optString(index);
    }

    @Override
    public boolean optBoolean(int index) {
        return mJSONArray.optBoolean(index);
    }

    @Override
    public JSON optJSON(int index) {
        AndroidSDKJSONObject androidSDKJSONObject = null;
        try {
            androidSDKJSONObject = new AndroidSDKJSONObject(mJSONArray.optJSONObject(index));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return androidSDKJSONObject;
    }

    @Override
    public JSONArray optJSONArray(int index) {
        AndroidSDKJSONArray androidSDKJSONArray = null;
        try {
            org.json.JSONArray jsonArray = mJSONArray.optJSONArray(index);
            if(jsonArray != null) {
                androidSDKJSONArray = new AndroidSDKJSONArray(mJSONArray.optJSONArray(index));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return androidSDKJSONArray;
    }

    @Override
    public Object get(int index) {
        try {
            return mJSONArray.get(index);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void put(int value) {
        mJSONArray.put(value);
    }

    @Override
    public void put(double value) {
        try {
            mJSONArray.put(value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void put(long value) {
        mJSONArray.put(value);
    }

    @Override
    public void put(boolean value) {
        mJSONArray.put(value);
    }

    @Override
    public void put(Object value) {
        if (value instanceof AndroidSDKJSONObject) {
            mJSONArray.put(((AndroidSDKJSONObject) value).getOrginJSONObject());
        }else if(value instanceof AndroidSDKJSONArray){
            mJSONArray.put( ((AndroidSDKJSONArray) value).getOrginJSONArray());
        }else {
            mJSONArray.put(value);
        }
    }

    @Override
    public void put(int index, int value) {
        try {
            mJSONArray.put(index, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void put(int index, double value) {
        try {
            mJSONArray.put(index, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void put(int index, long value) {
        try {
            mJSONArray.put(index, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void put(int index, boolean value) {
        try {
            mJSONArray.put(index, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void put(int index, Object value) {
        if (value instanceof AndroidSDKJSONObject) {
            mJSONArray.put(((AndroidSDKJSONObject) value).getOrginJSONObject());
        }else if(value instanceof AndroidSDKJSONArray){
            mJSONArray.put( ((AndroidSDKJSONArray) value).getOrginJSONArray());
        }else {
            mJSONArray.put(value);
        }
    }

    @Override
    public String toJSONString() {
        return mJSONArray.toString();
    }

    @Override
    public int length() {
        return mJSONArray.length();
    }
}
