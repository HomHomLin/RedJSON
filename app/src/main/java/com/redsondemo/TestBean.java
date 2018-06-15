package com.redsondemo;

import com.redson.IJSONObject;
import com.redson.JSON;
import com.redson.JSONArray;
import com.redson.JSONStringer;
import com.redson.Redson;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Linhh on 22/02/2018.
 */

public class TestBean implements IJSONObject,Serializable{

    String a;
    String b;
    TestBean bean;
    String[] list;
    ArrayList<TestBean> testlist;
    String[] testlist55;

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

    public void fromJSON(String var1) {
        JSON var2 = Redson.instance().parseJSON(var1);
        this.fromJSON(var2);
    }

    public void fromJSON(JSON var1) {
        this.a = var1.optString("a");
        this.b = var1.optString("b");
        if(IJSONObject.class.isAssignableFrom(TestBean.class)) {
            JSON var2 = var1.optJSON("bean");
            if(var2 != null) {
                if(this.bean == null) {
                    this.bean = new TestBean();
                }

                this.bean.fromJSON(var2);
            }
        }

        JSONArray var3 = var1.optJSONArray("list");
        if(var3 != null) {
            if(this.list == null) {
                this.list = new String[var3.length()];
            }

            for(int var4 = 0; var4 < this.list.length && var4 < var3.length(); ++var4) {
                String var5 = var3.optString(var4);
                this.list[var4] = var5;
            }
        }

        JSONArray var10 = var1.optJSONArray("testlist");
        if(var10 != null && IJSONObject.class.isAssignableFrom(TestBean.class)) {
            if(this.testlist == null) {
                this.testlist = new ArrayList();
            } else {
                this.testlist.clear();
            }

            for(int var11 = 0; var11 < var10.length(); ++var11) {
                JSON var6 = var10.optJSON(var11);
                TestBean var7 = null;
                if(var6 != null) {
                    var7 = new TestBean();
                    ((IJSONObject)var7).fromJSON(var6);
                }

                this.testlist.add(var7);
            }
        }

        JSONArray var12 = var1.optJSONArray("testlist55");
        if(var12 != null) {
            if(this.testlist55 == null) {
                this.testlist55 = new String[var12.length()];
            }

            for(int var13 = 0; var13 < this.testlist55.length && var13 < var12.length(); ++var13) {
                String var14 = var12.optString(var13);
                this.testlist55[var13] = var14;
            }
        }

    }
}
