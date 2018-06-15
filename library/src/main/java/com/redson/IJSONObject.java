package com.redson;

/**
 * Created by Linhh on 11/02/2018.
 */

public interface IJSONObject {

    public String toJSONString();

    public void toJSONString(JSONStringer redsonStringWriter);

    public JSON toJSON();

    public void fromJSON(String json);

    public void fromJSON(JSON json);
}
