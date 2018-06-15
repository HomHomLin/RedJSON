package com.redsondemo.test;

import android.support.annotation.Nullable;

import com.redson.IJSONObject;
import com.redson.JSON;
import com.redson.JSONStringer;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Linhh on 22/02/2018.
 */

public class CommunityFeedWrapModel  implements Serializable, IJSONObject {
    public ArrayList<HomeEntranceModel> entrances = new ArrayList();
    public ArrayList<CommunityFeedModel> docs = new ArrayList();
    public boolean is_show_hospital_card;
    public boolean is_show_recommend_forum_card;
    public ArrayList<Integer> top_topic_cancle;
    public ArrayList<CommunityFeedModel> activities;
    public String search_keyword_conf;
    public TrendingSubject trendingSubject;
    @Nullable
    public Banner top_ad_cfg;

    public CommunityFeedWrapModel() {
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

