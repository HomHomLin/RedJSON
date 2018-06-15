package com.redsondemo.test;

import com.redson.IJSONObject;
import com.redson.JSON;
import com.redson.JSONStringer;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Linhh on 22/02/2018.
 */

public class NewsCloseFeedBackModel  implements Serializable,IJSONObject {
    public int id;
    public String val;
    public int type;
    public boolean isSelect;

    public NewsCloseFeedBackModel() {
    }

    public NewsCloseFeedBackModel(JSONObject obj) {
        this.id = obj.optInt("id");
        this.val = obj.optString("val");
        this.type = obj.optInt("type");
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
