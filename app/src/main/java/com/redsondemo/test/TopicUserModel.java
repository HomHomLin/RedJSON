package com.redsondemo.test;

import com.redson.IJSONObject;
import com.redson.JSON;
import com.redson.JSONStringer;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Linhh on 22/02/2018.
 */

public class TopicUserModel implements Serializable,IJSONObject {
    public String id = "0";
    public String screen_name = "";
    public String strUserImageUrl;
    public String icon;
    public String admin_icon;
    public String expert_icon;
    public String master_icon;
    public String learn_master_icon;
    public String expert_name;
    public int score_level = 0;
    public boolean is_ask_follow;
    public String baby_info;
    public String avatar;
    public TopicAvatarModel user_avatar = new TopicAvatarModel();
    public boolean is_owner;
    public ArrayList<MedalModel> medal_list;
    public int error;
    public int isvip;

    public TopicUserModel() {
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
