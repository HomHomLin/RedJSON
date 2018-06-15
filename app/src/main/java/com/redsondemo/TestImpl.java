package com.redsondemo;

import com.redson.IJSONObject;
import com.redson.JSON;
import com.redson.JSONStringer;
import com.redson.Redson;
import com.redson.RedsonStringWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Linhh on 11/02/2018.
 */

public class TestImpl implements IJSONObject {
//    public ArrayList<TestImpl> getList3() {
//        return list3;
//    }
//
//    public void setList3(ArrayList<TestImpl> list3) {
//        this.list3 = list3;
//    }
//
//    public ArrayList<ArrayList<ArrayList<Integer>>> getList5() {
//        return list5;
//    }
//
//    public void setList5(ArrayList<ArrayList<ArrayList<Integer>>> list5) {
//        this.list5 = list5;
//    }
//
//    public ArrayList<ArrayList<HashMap<String, ArrayList<String>>>> getList7() {
//        return list7;
//    }
//
//    public void setList7(ArrayList<ArrayList<HashMap<String, ArrayList<String>>>> list7) {
//        this.list7 = list7;
//    }
//
        int a;
    String b = "test";

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

//    public TestImpl2 getT2() {
//        return t2;
//    }
//
//    public void setT2(TestImpl2 t2) {
//        this.t2 = t2;
//    }

    public TestImpl getT3() {
        return t3;
    }

    public void setT3(TestImpl t3) {
        this.t3 = t3;
    }

    public TestImpl getT4() {
        return t4;
    }

    public void setT4(TestImpl t4) {
        this.t4 = t4;
    }

    public Test2Impl getT5() {
        return t5;
    }

    public void setT5(Test2Impl t5) {
        this.t5 = t5;
    }

    public HashMap<String, Boolean> getMap() {
        return map;
    }

    public void setMap(HashMap<String, Boolean> map) {
        this.map = map;
    }

    public HashMap<String, String> getMap2() {
        return map2;
    }

    public void setMap2(HashMap<String, String> map2) {
        this.map2 = map2;
    }

    public ArrayList<String> getList1() {
        return list1;
    }

    public void setList1(ArrayList<String> list1) {
        this.list1 = list1;
    }

    public ArrayList<TestImpl> getList3() {
        return list3;
    }

    public void setList3(ArrayList<TestImpl> list3) {
        this.list3 = list3;
    }

    public ArrayList<Integer> getList4() {
        return list4;
    }

    public void setList4(ArrayList<Integer> list4) {
        this.list4 = list4;
    }

    public ArrayList<ArrayList<ArrayList<Integer>>> getList5() {
        return list5;
    }

    public void setList5(ArrayList<ArrayList<ArrayList<Integer>>> list5) {
        this.list5 = list5;
    }

    public ArrayList<ArrayList<HashMap<String, String>>> getList6() {
        return list6;
    }

    public void setList6(ArrayList<ArrayList<HashMap<String, String>>> list6) {
        this.list6 = list6;
    }

    public ArrayList<ArrayList<HashMap<String, ArrayList<String>>>> getList7() {
        return list7;
    }

    public void setList7(ArrayList<ArrayList<HashMap<String, ArrayList<String>>>> list7) {
        this.list7 = list7;
    }

    //    Map<String, ITest> map;
//    List<ITest> list;
//    int[] as;
//    String[] bs;
//    boolean[] cs;
//    MainActivity[] ts;
//    TestImpl2 t2 = new TestImpl2();
    TestImpl t3;
    TestImpl t4;
    Test2Impl t5;
//    MainActivity t6;
//    Object object;
    HashMap<String, Boolean> map;
    HashMap<String, String> map2;
//    HashMap<String, MainActivity> map3;
    HashMap<String, TestImpl2> map4;
    public ArrayList<String> list1;
//    public ArrayList list1010;
//    public ArrayList<MainActivity> list2;
    public ArrayList<TestImpl> list3;
    public ArrayList<Integer> list4;
    public ArrayList<ArrayList<ArrayList<Integer>>> list5;
    public ArrayList<ArrayList<HashMap<String, String>>> list6;
//    @RedJsonAbsent
    public ArrayList<ArrayList<HashMap<String, ArrayList<String>>>> list7;

//    public static int asss = 1;
//    Integer integer;
//    Boolean bool;

    public TestImpl(){
        int index = 0;
//        bool = false;
//        integer = 100;
        map4 = new HashMap<>();
        map4.put("test", new TestImpl2());
        map4.put("test2", new TestImpl2());
        map4.put("test3",new TestImpl2());
        list7 = new ArrayList<>();
        for(int w = 0; w< 1000; w++) {
            ArrayList<HashMap<String, ArrayList<String>>> listba = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                HashMap<String, ArrayList<String>> map = new HashMap<>();
                for (int x = 0; x < 10; x++) {
                    ArrayList<String> list = new ArrayList<String>();
                    for (int y = 0; y < 10; y++) {
                        list.add(String.valueOf(index++));
                    }
                    map.put("test.txt" + index++, list);
                }
                listba.add(map);
            }
            list7.add(listba);
        }
        list5 = new ArrayList();
        for(int x = 0; x < 100;x ++) {
            ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
            for (int i = 0; i < 200; i++) {
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(index++);
                arrayList.add(index++);
                list.add(arrayList);
            }
            list5.add(list);
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
        jsonStringer.object();
        jsonStringer.dot();
        Object integer = ((Object)a);
        jsonStringer.writeToObject("a", integer);
        jsonStringer.endObject();
    }

    @Override
    public JSON toJSON() {
        //根json
//        long time = System.currentTimeMillis();
        JSON json = Redson.instance().makeNewJSON();
//        json.put("list3", Redson.list(list3));
//        json.put("list5", Redson.list(list5));
//        json.put("list7", Redson.list(list7));
//        json.put("map2",Redson.map(map2));
//        Log.i("testjson", String.valueOf(System.currentTimeMillis() - time));
//        if(list7 != null){
//            JSONArray jsonArray = Redson.makeNewJSONArray();
//            for(int i = 0; i < list7.size();i ++){
//                JSONArray jsonArray2 = Redson.makeNewJSONArray();
//                ArrayList<HashMap<String, ArrayList<String>>> list8 =  list7.get(i);
//                for(int x = 0; x < list8.size(); x ++){
//                    HashMap<String, ArrayList<String>> map = list8.get(x);
//                    Iterator<Map.Entry<String, ArrayList<String>>> entries = map.entrySet().iterator();
//                    JSON json1 = Redson.makeNewJSON();
//                    while (entries.hasNext()) {
//                        Map.Entry<String, ArrayList<String>> entry = entries.next();
//                        JSONArray jsonArray3 = Redson.makeNewJSONArray();
//                        ArrayList<String> list9 = entry.getValue();
//                        for(int y = 0 ; y < list9.size();y ++){
//                            jsonArray3.put(y, list9.get(y));
//                        }
//                        json1.put(String.valueOf(entry.getKey()), jsonArray3);
//                    }
//                    jsonArray2.put(x, json1);
//                }
//                jsonArray.put(i, jsonArray2);
//            }
//            json.put("list4", jsonArray);
//        }
//        if(map != null){
//
//        }
//        if(list1 != null){
//            JSONArray jsonArray = Redson.makeNewJSONArray();
//            for(int i = 0; i < list1.size();i ++){
//                String var =  list1.get(i);
//                jsonArray.put(i, var);
//            }
//            json.put("list1", jsonArray);
//        }
//        if(list4 != null){
//            JSONArray jsonArray = Redson.makeNewJSONArray();
//            for(int i = 0; i < list4.size();i ++){
//                Integer integer =  list4.get(i);
//                jsonArray.put(i, integer);
//            }
//            json.put("list4", jsonArray);
//        }
//        JSON var1 = Redson.makeNewJSON();
//        var1.put("code", this.code);
//        if(t6 != null) {
//            json.put("data", ((IJSONObject)t6).toJSON());
//        }

//        return var1;
//        if(map != null){
//            Iterator<Map.Entry<String, Boolean>> entries = map.entrySet().iterator();
//            JSON json1 = Redson.makeNewJSON();
//            while (entries.hasNext()) {
//                Map.Entry<String, Boolean> entry = entries.next();
//                Boolean.valueOf(((Long)222l).longValue());
//                json1.put(String.valueOf(entry.getKey()), Boolean.valueOf(entry.getValue()));
//            }
//            json.put("map",json1);
//        }




        return json;
    }

    @Override
    public void fromJSON(String var1) {
//        try {
//            fromJSON(new AndroidSDKJSONObject(var1));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void fromJSON(JSON var1) {
//        this.list5 = new LinkedList();
//        JSON var5 = var1.optJSON("t5");
//        if(var5 != null) {
//            if(this.t5 == null) {
//                this.t5 = new Test2Impl();
//            }
//
//            this.t5.fromJSON(var5);
//        }
//        int i = var1.optInt("integer");
//        integer = Integer.valueOf(i);
//
//        boolean x = var1.optBoolean("bool");
//        bool = Boolean.valueOf(x);
//
//        double y = var1.optDouble("bool");
//         = Float.valueOf(y);

//        JSONArray jsonArray = json.optJSONArray("list3");
//        if(jsonArray != null) {
//            IBean iBean = new NormalBean();
//            iBean.setClass(ArrayList.class);
//
//            IBean iBean1 = new NormalBean();
//            iBean1.setClass(TestImpl.class);
//
//            iBean.setNextBean(iBean1);
//
//            list3 = new ArrayList<>();
//            Redson.fromJson(jsonArray, list3, iBean1);
//        }
//
//        if(list5 == null) {
//            list5 = new ArrayList<>();
//        }else{
//            list5.clear();
//        }
//        JSONArray jsonArray2 = json.optJSONArray("list5");
//        if(jsonArray2 != null) {
//            IBean iBean2 = new NormalBean();
//            iBean2.setClass(ArrayList.class);
//
//
//            IBean iBean3 = new NormalBean();
//            iBean3.setClass(ArrayList.class);
//            iBean2.setNextBean(iBean3);
//
//
//            IBean iBean4 = new NormalBean();
//            iBean4.setClass(Integer.class);
//            iBean3.setNextBean(iBean4);
//            if(list5 == null) {
//                list5 = new ArrayList<>();
//            }else{
//                list5.clear();
//            }
//            Redson.fromJson(jsonArray2, list5, iBean2);
//        }

//
//        JSONArray jsonArray3 = json.optJSONArray("list7");
//        IBean iBean17 = new NormalBean();
//        iBean17.setClass(ArrayList.class);
//
//
//        IBean iBean27 = new NormalBean();
//        iBean27.setClass(HashMap.class);
//        iBean17.setNextBean(iBean27);
//
//
//        IBean iBean37 = new NormalBean();
//        iBean37.setClass(ArrayList.class);
//        iBean27.setNextBean(iBean37);
//
//        IBean iBean47 = new NormalBean();
//        iBean47.setClass(String.class);
//        iBean37.setNextBean(iBean47);
//
//        list7 = new ArrayList<>();
//        Redson.fromJson(jsonArray3, list7, iBean17);

    }
}
