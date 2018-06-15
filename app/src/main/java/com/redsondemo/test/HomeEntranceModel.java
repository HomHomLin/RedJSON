package com.redsondemo.test;

import com.redson.IJSONObject;
import com.redson.JSON;
import com.redson.JSONStringer;

import java.io.Serializable;

/**
 * Created by Linhh on 22/02/2018.
 */

public class HomeEntranceModel implements Serializable,IJSONObject {
    public String id;
    public String icon;
    public String name;
    public String redirect_url;

    public HomeEntranceModel() {
    }


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
