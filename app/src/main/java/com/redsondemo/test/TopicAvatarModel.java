package com.redsondemo.test;

import com.redson.IJSONObject;
import com.redson.JSON;
import com.redson.JSONStringer;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Linhh on 22/02/2018.
 */

public class TopicAvatarModel  implements Serializable,IJSONObject {
//    private static final long serialVersionUID = 12308L;
    public String large;
    public String medium;
    public String small;

    public TopicAvatarModel() {
    }

    public TopicAvatarModel(JSONObject jsonObject) {
        try {
            this.large = jsonObject.optString("large");
            this.medium = jsonObject.optString("medium");
            this.small = jsonObject.optString("small");
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public TopicAvatarModel(String medium) {
        try {
            this.medium = medium;
        } catch (Exception var3) {
            var3.printStackTrace();
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

