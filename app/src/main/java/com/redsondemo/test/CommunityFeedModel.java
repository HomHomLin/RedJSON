package com.redsondemo.test;

import com.redson.IJSONObject;
import com.redson.JSON;
import com.redson.JSONStringer;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Linhh on 22/02/2018.
 */

public class CommunityFeedModel  implements Serializable, Cloneable, IJSONObject {
//    public static final int SEPARATOR_TYPE = -1;
//    public static final int TOPIC_ITEM_TYPE = 1;
//    public static final int SPECIAL_TOPIC_LIST_ITEM_TYPE = 2;
//    public static final int RECOMMEND_BLOCK_LIST_ITEM_TYPE = 3;
//    public static final int CARD_ITEM_TYPE = 4;
//    public static final int HOT_TOPIC_TYPE = 5;
    public int type = -1;
    public int id;
    public String redirect_url;
    public String title;
    public String content;
    public int total_review;
    public int view_times;
    public String updated_date;
    public TopicUserModel publisher;
    public int user_id;
    public String circle_name;
    public ArrayList<String> images = new ArrayList();
    public int attr_type;
    public ArrayList<TopicModel> topic_items = new ArrayList();
    public String icon;
    public String r_text;
    public ArrayList<BlockModel> forum_items = new ArrayList();
    public int num;
    public int recomm_type;
    public ArrayList<NewsCloseFeedBackModel> label = new ArrayList();
    private boolean hasRead;
    public String reviewed_date;
    public boolean is_live;
    public ArrayList<TopicVideoModel> videos = new ArrayList();
    public int total_view;
    public boolean is_ad;
    public boolean is_vote;
    public boolean is_elite = false;
    public boolean is_hot = false;
    public boolean is_new = false;
    public boolean is_activity;
    public int site;
    public String user_screen_name;
    public boolean shouldHideFirstView;

    public CommunityFeedModel() {
    }

    public int getFeedsId() {
        return this.id;
    }

    public void setReadStatus(boolean state) {
        if(this.getFeedsType() > 0) {
            this.hasRead = state;
        }

    }

    public boolean isHasRead() {
        return this.hasRead;
    }

    public int getFeedsType() {
        if(this.is_activity) {
            return -1;
        } else {
            switch(this.type) {
                case 1:
                    return 1;
                case 2:
                    return 3;
                default:
                    return -1;
            }
        }
    }

    public Object clone() {
        CommunityFeedModel cloneObject = null;

        try {
            cloneObject = (CommunityFeedModel)super.clone();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return cloneObject;
    }

    public boolean isVideoTopic() {
        return this.videos != null && this.videos.size() > 0;
    }

    public boolean isHospitalCard() {
        return this.type == 4 && this.attr_type == 1;
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
