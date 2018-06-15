package com.redsondemo.test;

import com.redson.IJSONObject;
import com.redson.JSON;
import com.redson.JSONStringer;

import java.io.Serializable;

/**
 * Created by Linhh on 22/02/2018.
 */

public class ImageSubject implements Serializable, Cloneable, IJSONObject {
    public int style;
    public String name;
    public String image;
    public String introduction;
    public String redirect_uri;
    public int subject_id;

    public ImageSubject() {
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (Exception var2) {
            var2.printStackTrace();
            return new ImageSubject();
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

