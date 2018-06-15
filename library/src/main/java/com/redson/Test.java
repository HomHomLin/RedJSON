package com.redson;

public class Test implements IJSONObject{
    public String a;
    @Override
    public String toJSONString() {
        return null;
    }

    @Override
    public void toJSONString(JSONStringer redsonStringWriter) {

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
