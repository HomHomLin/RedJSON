package com.redson.plugin;

/**
 * Created by Linhh on 11/02/2018.
 */

public class RedsonFieldBean {
    public String mFieldName;
    public String mDesc;
    //如果是泛型的就会有这个
    public String mSignature;

    public String getFieldName() {
        return mFieldName;
    }

    public void setFieldName(String mFieldName) {
        this.mFieldName = mFieldName;
    }

    public String getDesc() {
        return mDesc;
    }

    public void setDesc(String mDesc) {
        this.mDesc = mDesc;
    }

    public String getSignature() {
        return mSignature;
    }

    public void setSignature(String mSignature) {
        this.mSignature = mSignature;
    }

    @Override
    public String toString() {
        return "FieldName:" + mFieldName + ",Desc:" + mDesc + ",Signature:" + mSignature;
    }
}
