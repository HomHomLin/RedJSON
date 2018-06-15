package com.redsondemo.test;

import com.redson.IJSONObject;
import com.redson.JSON;
import com.redson.JSONStringer;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Linhh on 22/02/2018.
 */

public class TopicModel  implements Serializable,IJSONObject {
//    static final long serialVersionUID = 6693445548309156170L;
    public String id = "0";
    public String forum_id = "0";
    public String forum_name = "";
    public boolean is_feeds;
    public String title = "";
    public boolean is_elite = false;
    public boolean is_favorite = false;
    public boolean is_ontop = false;
    public String content = "";
    public ArrayList<String> images = new ArrayList();
    public ArrayList<TopicVideoModel> videos = new ArrayList();
    public String total_review = "0";
    public int total_floor;
    public String published_date = "";
    public int ordianl = 0;
    public String reviewed_date = "";
    public boolean is_hot = false;
    public boolean is_new = false;
    public boolean is_ask = false;
    public String tips;
    public TopicUserModel publisher = new TopicUserModel();
    public String guide_info;
    public String share_url = "";
    public int deleted_status;
    public int privilege;
    public boolean is_ad;
    public boolean is_vote;
    public int is_followup;
    public int praise_num;
    public boolean has_praise;
    public boolean hasRead;
    public boolean is_live;
    public int total_view;
    public String redirect_url;
    public boolean have_only_image_model;
    public ArrayList<String> ad_monitor_url;
    public boolean is_activity;
    public int is_floor_host;
    public Subject subject;

    public TopicModel() {
    }

    public int getFeedsType() {
        return 1;
    }

    public void setReadStatus(boolean state) {
        this.hasRead = state;
    }

    public boolean isVideoTopic() {
        return this.videos != null && this.videos.size() > 0;
    }

    public boolean isFloorHost() {
        return this.is_floor_host != 0;
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

