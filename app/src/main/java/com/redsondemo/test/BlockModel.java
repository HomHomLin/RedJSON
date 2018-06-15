package com.redsondemo.test;

import android.text.TextUtils;

import com.redson.IJSONObject;
import com.redson.JSON;
import com.redson.JSONStringer;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by Linhh on 22/02/2018.
 */

public class BlockModel  implements Serializable, Cloneable, IJSONObject {
    public int id = 0;
    public String name = "";
    public String home_name = "";
    public String more_name = "";
    public String icon2 = "";
    public String introduction = "";
    public String newest_topic_title = "";
    public int newest_topic_id;
    public int total_updates = (new Random()).nextInt(1000);
    public boolean is_joined = false;
    public boolean is_recommended = false;
    public boolean is_checkin = false;
    public boolean has_checkin = false;
    public boolean has_expert = false;
    public boolean is_new = false;
    public String order_by = "reviewed_date_desc";
    public boolean join_reply = false;
    public boolean reply_image = false;
    public boolean is_show_image = false;
    public int expert_show_type;
    public boolean is_unable_quit;
    public boolean open_pregnancy_album;
    public int limit_reply_images;
    public int category_id;

    public BlockModel() {
    }

    public boolean equals(Object o) {
        return o instanceof BlockModel && ((BlockModel)o).id == this.id;
    }

    public int hashCode() {
        return Integer.valueOf(this.id).hashCode();
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public boolean isEmpty() {
        return this.id <= 0;
    }

    public boolean isCanReply() {
        return this.is_joined || !this.join_reply;
    }

    public String getHomeName() {
        return !TextUtils.isEmpty(this.home_name)?this.home_name:(!TextUtils.isEmpty(this.name)?this.name:"");
    }

    public String getMoreName() {
        return !TextUtils.isEmpty(this.more_name)?this.more_name:(!TextUtils.isEmpty(this.name)?this.name:"");
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
