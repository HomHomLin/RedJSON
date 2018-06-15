package com.redsondemo.test;

import com.redson.IJSONObject;
import com.redson.JSON;
import com.redson.JSONStringer;

import java.io.Serializable;

/**
 * Created by Linhh on 22/02/2018.
 */

public class HotTopicItemModel  implements Serializable, Cloneable , IJSONObject{
    public String name;
    public String icon;
    public String redirect_uri;
    public int subject_id;

    public HotTopicItemModel() {
    }

    public HotTopicItemModel(int subject_id, String name, String icon, String redirect_uri) {
        this.subject_id = subject_id;
        this.name = name;
        this.icon = icon;
        this.redirect_uri = redirect_uri;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (Exception var2) {
            var2.printStackTrace();
            return new HotTopicItemModel();
        }
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
