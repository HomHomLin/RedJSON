package com.redson;

import java.util.Iterator;

/**
 * Created by Linhh on 11/02/2018.
 */

public interface JSON {
    public int optInt(String name);
    public double optDouble(String name);
    public long optLong(String name);
    public String optString(String name);
    public boolean optBoolean(String name);
    public float optFloat(String name);
    public JSON optJSON(String name);
    public Object get(String name);
    public JSONArray optJSONArray(String name);
    public Iterator<String> keys();
    public void put(String name, Object value);
    public void put(String name, int value);
    public void put(String name, double value);
    public void put(String name, long value);
    public void put(String name, boolean value);
    public String toJSONString();


}
