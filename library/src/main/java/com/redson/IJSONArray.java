package com.redson;

/**
 * Created by Linhh on 11/02/2018.
 */

public interface IJSONArray {

    public String toJSON();

    public void fromJSON(String json);

    public void fromJSON(JSONArray jsonArray);

    public int length();

//    public T get(int index);
}
