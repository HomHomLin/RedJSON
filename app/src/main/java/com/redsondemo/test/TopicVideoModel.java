package com.redsondemo.test;

import com.redson.IJSONObject;
import com.redson.JSON;
import com.redson.JSONStringer;

import java.io.Serializable;

/**
 * Created by Linhh on 22/02/2018.
 */

public class TopicVideoModel implements Serializable,IJSONObject {
//    static final long serialVersionUID = 6693445548303214560L;
    public String thum_pic;
    public String video_url;
    public String time;
    public String size;

    public TopicVideoModel() {
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
