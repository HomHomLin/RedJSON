package com.redsondemo.test;

import com.redson.IJSONObject;
import com.redson.JSON;
import com.redson.JSONStringer;
import com.redsondemo.TestImpl;

import java.io.Serializable;

/**
 * Created by Linhh on 22/02/2018.
 */

public class Base implements IJSONObject, Serializable{
//    public CommunityFeedWrapModel getData() {
//        return data;
//    }
//
//    public void setData(CommunityFeedWrapModel data) {
//        this.data = data;
//    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

//    CommunityFeedWrapModel data;
    int code;
    TestImpl data;
//    HashMap<String,Boolean> map;


    @Override
    public String toJSONString() {
        JSONStringer jsonStringer = new JSONStringer();
        toJSONString(jsonStringer);
        return jsonStringer.toString();
    }

    @Override
    public void toJSONString(JSONStringer jsonStringer) {
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
