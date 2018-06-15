package com.redsondemo.test;

import com.redson.IJSONObject;
import com.redson.JSON;
import com.redson.JSONStringer;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Linhh on 22/02/2018.
 */

public class TrendingSubject implements Serializable,IJSONObject {
    public int position;
    public int id;
    public int type;
    public String bi_uri;
    public ImageSubject image_subject;
    public ArrayList<HotTopicItemModel> text_subject = new ArrayList();
    public ArrayList<NewsCloseFeedBackModel> label = new ArrayList();

    public TrendingSubject() {
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

