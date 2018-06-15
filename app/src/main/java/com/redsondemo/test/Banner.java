package com.redsondemo.test;

import android.support.annotation.Nullable;

import com.redson.IJSONObject;
import com.redson.JSON;
import com.redson.JSONStringer;

import java.io.Serializable;

/**
 * Created by Linhh on 22/02/2018.
 */

public class Banner implements Serializable, IJSONObject {
    @Nullable
    public String img_url;
    @Nullable
    public String redirect_url;

    public Banner() {
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
