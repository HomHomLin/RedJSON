package com.redson;

/**
 * Created by Linhh on 12/02/2018.
 */

public interface JSONArray {
    public int optInt(int index);
    public double optDouble(int index);
    public long optLong(int index);
    public String optString(int index);
    public boolean optBoolean(int index);
    public JSON optJSON(int index);
    public JSONArray optJSONArray(int index);
    public Object get(int index);
    public void put(int value);
    public void put(double value);
    public void put(long value);
    public void put(boolean value);
    public void put(Object value);
    public void put(int index, int value);
    public void put(int index, double value);
    public void put(int index, long value);
    public void put(int index, boolean value);
    public void put(int index, Object value);
    public String toJSONString();
    public int length();
}
