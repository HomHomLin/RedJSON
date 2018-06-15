package com.redson;

import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Linhh on 11/02/2018.
 */

public final class Redson {

    private final static class Holder{
        static final Redson mRedson = new Redson();
    }

    public static final Redson instance(){
        return Holder.mRedson;
    }

    public final <T extends IJSONObject> T parseJSON(String json, Class<T> clazz){
        try {
            T t = clazz.newInstance();
            t.fromJSON(json);
            return t;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public final <T extends IJSONObject> String toJSONString(T object){
        if(object == null){
            return null;
        }
        return object.toJSONString();
    }

    public final JSON parseJSON(String json){
        try {
            AndroidSDKJSONObject androidSDKJSONObject = new AndroidSDKJSONObject(json);
            return androidSDKJSONObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public final JSON makeNewJSON(JSONObject json){
        AndroidSDKJSONObject androidSDKJSONObject = new AndroidSDKJSONObject(json);
        return androidSDKJSONObject;
    }

    public final JSON makeNewJSON(){
        AndroidSDKJSONObject androidSDKJSONObject = new AndroidSDKJSONObject();
        return androidSDKJSONObject;
    }

    public final JSONArray makeNewJSONArray(){
        AndroidSDKJSONArray androidSDKJSONArray = new AndroidSDKJSONArray();
        return androidSDKJSONArray;
    }

    public boolean isMap(Object object){

        if(object.getClass() == HashMap.class||
                object.getClass() == LinkedHashMap.class||
                object.getClass() == TreeMap.class||
                object.getClass() == List.class){
            return true;
        }
        return false;
    }

    public boolean isTypeMap(Class object){

        if(object == HashMap.class||
                object == LinkedHashMap.class||
                object == TreeMap.class||
                object == List.class){
            return true;
        }
        return false;
    }

    public Map isMap(Class object){
        if(object == Map.class){
            return new LinkedHashMap();
        }
        if(object == HashMap.class){
            return new HashMap();
        }
        if(object == LinkedHashMap.class){
            return new LinkedHashMap();
        }
        if(object == TreeMap.class){
            return new TreeMap();
        }
        return null;
    }

    public List isList(Class object){
        if(object == List.class){
            return new LinkedList();
        }
        if(object == ArrayList.class){
            return new ArrayList();
        }
        if(object == LinkedList.class){
            return new LinkedList();
        }
        return null;
    }

    public boolean isTypeList(Class object){

        if(object == ArrayList.class||
                object == LinkedList.class ||
                object == List.class){
//            Log.i("test", "isList" + object);
            return true;
        }
        return false;
    }

    public boolean isList(Object object){

        if(object.getClass() == ArrayList.class||
                object.getClass() == LinkedList.class ||
                object.getClass() == List.class){
//            Log.i("test", "isList" + object);
            return true;
        }
        return false;
    }

    public boolean isBaseVar(Object object){

        if(object.getClass() == Integer.class||
                object.getClass() == Long.class||
                object.getClass() == Double.class||
                object.getClass() == Float.class||
                object.getClass() == Boolean.class||
                object.getClass() == String.class){
//            Log.i("test", "isBaseVar" + object);
            return true;
        }
        return false;
    }

    public Object isBaseVar(Object object, Class clazz){
        if(object == null){
            return null;
        }
        if(clazz == Integer.class){
            return Integer.valueOf(object.toString());
        }
        if(clazz == Long.class){
            return Long.valueOf(object.toString());
        }
        if(clazz == Double.class){
            return Double.valueOf(object.toString());
        }
        if(clazz == Float.class){
            return Float.valueOf(object.toString());
        }
        if(clazz == Boolean.class){
            return Boolean.valueOf(object.toString());
        }
        if(clazz == String.class){
            return object.toString();
        }

        return null;
    }

//
//    public static JSONArray list(List list){
//        JSONArray jsonArray = null;
//        if(list != null){
//            jsonArray = Redson.makeNewJSONArray();
//            for(int i = 0 ; i < list.size(); i ++){
//                Object object = list.get(i);
//                if(object != null){
//                    if(isMap(object)){
//                        JSON json = map((Map) object);
//                        jsonArray.put(i, json);
//                    }else if(isList(object)){
//                        JSONArray jsonArray1 = list((List) object);
//                        jsonArray.put(i, jsonArray1);
//                    }else if(isBaseVar(object)){
//                        jsonArray.put(i, object);
//                    }else{
//                        //我们认为如果没有选择了，那么他肯定是JSON
//                        jsonArray.put(i, ((IJSONObject)object).toJSON());
//                    }
//                }else{
//                    jsonArray.put(i, null);
//                }
//            }
//        }
//        return jsonArray;
//    }
//
//    public static JSON map(Map map){
//        JSON json = null;
//        if(map != null) {
//            json = Redson.makeNewJSON();
//            Iterator<Map.Entry> entries = map.entrySet().iterator();
//            while (entries.hasNext()) {
//                Map.Entry entry = entries.next();
//                //我们认为key只可能是string
//                String key = String.valueOf(entry.getKey());
//                Object object = entry.getValue();
//                if(object != null){
//                    if(isMap(object)){
//                        JSON json1 = map((Map) object);
//                        json.put(key, json1);
//                    }else if(isList(object)){
//                        JSONArray jsonArray1 = list((List) object);
//                        json.put(key, jsonArray1);
//                    }else if(isBaseVar(object)){
//                        json.put(key, object);
//                    }else{
//                        //我们认为如果没有选择了，那么他肯定是JSON
//                        json.put(key, ((IJSONObject)object).toJSON());
//                    }
//                }else{
//                    json.put(key, null);
//                }
//
//            }
//        }
//        return json;
//    }

    public void buildJSON(RedsonStringWriter stringBuilder, String name, List obj){
        if(obj == null){
            return;
        }
        stringBuilder.append("\"");
        stringBuilder.append(name);
        stringBuilder.append("\"");
        stringBuilder.append(":");
        stringBuilder.append(listString((List) obj));
    }

    public void buildJSON(RedsonStringWriter stringBuilder, String name, Map obj){
        if(obj == null){
            return;
        }
        stringBuilder.append("\"");
        stringBuilder.append(name);
        stringBuilder.append("\"");
        stringBuilder.append(":");
        stringBuilder.append(mapString((Map) obj));
    }

    public void buildJSON(RedsonStringWriter stringBuilder, String name, IJSONObject obj){
        if(obj == null){
            return;
        }
        stringBuilder.append("\"");
        stringBuilder.append(name);
        stringBuilder.append("\"");
        stringBuilder.append(":");
        stringBuilder.append(((IJSONObject) obj).toJSONString());
    }

    public void buildJSON(RedsonStringWriter stringBuilder, String name, String obj){
        if(obj == null){
            return;
        }
        stringBuilder.append("\"");
        stringBuilder.append(name);
        stringBuilder.append("\"");
        stringBuilder.append(":");
        stringBuilder.append("\"");
        stringBuilder.append(obj.toString());
        stringBuilder.append("\"");
    }

    public void buildJSON(RedsonStringWriter stringBuilder, String name, int obj){
        stringBuilder.append("\"");
        stringBuilder.append(name);
        stringBuilder.append("\"");
        stringBuilder.append(":");
        stringBuilder.append(obj);
    }

    public void buildJSON(RedsonStringWriter stringBuilder, String name, double obj){
        stringBuilder.append("\"");
        stringBuilder.append(name);
        stringBuilder.append("\"");
        stringBuilder.append(":");
        stringBuilder.append(obj);
    }

    public void buildJSON(RedsonStringWriter stringBuilder, String name, float obj){
        stringBuilder.append("\"");
        stringBuilder.append(name);
        stringBuilder.append("\"");
        stringBuilder.append(":");
        stringBuilder.append(obj);
    }

    public void buildJSON(RedsonStringWriter stringBuilder, String name, long obj){
        stringBuilder.append("\"");
        stringBuilder.append(name);
        stringBuilder.append("\"");
        stringBuilder.append(":");
        stringBuilder.append(obj);
    }

    public void buildJSON(RedsonStringWriter stringBuilder, String name, boolean obj){
        stringBuilder.append("\"");
        stringBuilder.append(name);
        stringBuilder.append("\"");
        stringBuilder.append(":");
        stringBuilder.append(obj);
    }

//    public static void buildJSON(StringBuilder stringBuilder, String name, Object obj){
//        if(obj == null){
//            return;
//        }
//        if(obj instanceof List) {
//            stringBuilder.append("\"");
//            stringBuilder.append(name);
//            stringBuilder.append("\"");
//            stringBuilder.append(":");
//            stringBuilder.append(Redson.listString((List) obj));
//        }else if(obj instanceof Map){
//            stringBuilder.append("\"");
//            stringBuilder.append(name);
//            stringBuilder.append("\"");
//            stringBuilder.append(":");
//            stringBuilder.append(Redson.mapString((Map) obj));
//        }else if(obj instanceof IJSONObject){
//            stringBuilder.append("\"");
//            stringBuilder.append(name);
//            stringBuilder.append("\"");
//            stringBuilder.append(":");
//            stringBuilder.append(((IJSONObject) obj).toJSONString());
//        }else{
//            stringBuilder.append("\"");
//            stringBuilder.append(name);
//            stringBuilder.append("\"");
//            stringBuilder.append(":");
//            if(obj instanceof String){
//                stringBuilder.append("\"");
//                stringBuilder.append(obj.toString());
//                stringBuilder.append("\"");
//            }else{
//                stringBuilder.append(obj.toString());
//            }
//        }
//    }

    public String listString(List list){
        RedsonStringWriter jsonArray = new RedsonStringWriter();
        if(list != null){
            jsonArray.append("[");
//            jsonArray = Redson.makeNewJSONArray();
            int i = 0;
            for(Object object : list){
                i++;
                if(object != null){
                    if(isMap(object)){
                        String json = mapString((Map) object);
                        jsonArray.append(json);
//                        jsonArray.put(i, json);
                    }else if(isList(object)){
                        String json = listString((List) object);
//                        jsonArray.put(i, jsonArray1);
                        jsonArray.append(json);
                    }else if(isBaseVar(object)){
                        if(object instanceof String){
                            jsonArray.append("\"").append(object.toString()).append("\"");
                        }else {
                            jsonArray.append(object.toString());
                        }
//                        jsonArray.put(i, object);
                    }else{
                        //我们认为如果没有选择了，那么他肯定是JSON
                        jsonArray.append(((IJSONObject)object).toJSONString());
//                        jsonArray.put(i, ((IJSONObject)object).toJSON());
                    }
                }else{
                    jsonArray.append("null");
                }
                if(i < list.size()){
                    jsonArray.append(",");
                }

            }
            jsonArray.append("]");
        }
        return jsonArray.toString();
    }

    public String mapString(Map map){
        RedsonStringWriter json = new RedsonStringWriter();
        if(map != null) {
            int i = 0;
            json.append("{");
            for (Object obj : map.entrySet()) {
                i++;
                Map.Entry<String, Object> entry = (Map.Entry<String, Object>)obj;
                String key = entry.getKey();
                Object object = entry.getValue();
                if(object != null){
                    if(isMap(object)){
                        String json1 = mapString((Map) object);
                        json.append("\"").append(key).append("\":").append(json1);

//                        json.append("{\"").append(key).append("\":").append(json1).append("}");

//                        json.append("{\"").append(key).append("\":").append(json1).append("}");
//                        json.put(key, json1);
                    }else if(isList(object)){
                        String jsonArray1 = listString((List) object);
                        json.append("\"").append(key).append("\":").append(jsonArray1);

//                        json.append("{\"").append(key).append("\":").append(jsonArray1).append("}");
//                        json.put(key, jsonArray1);
                    }else if(isBaseVar(object)){
                        if(object instanceof String){
                            json.append("\"").append(key).append("\":").append("\"").append(object.toString()).append("\"");
                        }else {
                            json.append("\"").append(key).append("\":").append(object);
                        }
//                        json.append("\"").append(key).append("\":").append(object);

//                        json.append("{\"").append(key).append("\":").append(object).append("}");

//                        json.put(key, object);
                    }else{
                        //我们认为如果没有选择了，那么他肯定是JSON
                        json.append("\"").append(key).append("\":").append(((IJSONObject)object).toJSONString());

//                        json.append("{\"").append(key).append("\":").append(((IJSONObject)object).toJSONString()).append("}");

//                        json.put(key, ((IJSONObject)object).toJSON());
                    }
                }else{
//                    json.append("{\"").append(key).append("\":").append(((IJSONObject)object).toJSONString()).append("}");

//                    json.put(key, null);
                    json.append("null");
                }
                if(i < map.entrySet().size()){
                    json.append(",");
                }
            }
//            Iterator<Map.Entry> entries = map.entrySet().iterator();
//            while (entries.hasNext()) {
//                Map.Entry entry = entries.next();
//                //我们认为key只可能是string
//
//
//            }
            json.append("}");
        }
        return json.toString();
    }

    public static void fromJson(JSON orgin_json, Map orgin_list, IBean iBean ){
        if(iBean == null || orgin_json == null || orgin_list == null){
            return;
        }
        boolean isList = Redson.instance().isTypeList(iBean.beanType());
        if(isList){
            Iterator<String> iterator = orgin_json.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                List list = Redson.instance().isList(iBean.beanType());
                JSONArray jsonArray = orgin_json.optJSONArray(key);
                fromJson(jsonArray, list, iBean.nextBean());
                orgin_list.put(key, list);
            }
            return;
        }
        boolean isMap = Redson.instance().isTypeMap(iBean.beanType());
        if(isMap){
            Iterator<String> iterator = orgin_json.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                JSON json = orgin_json.optJSON(key);
                Map map = Redson.instance().isMap(iBean.beanType());
                fromJson(json, map, iBean.nextBean());
                orgin_list.put(key, map);
            }
            return;
        }

        Iterator<String> iterator = orgin_json.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Object o = orgin_json.get(key);
            if(o != null){
                Object json = Redson.instance().isBaseVar(o,iBean.beanType());
                if(json == null){
                    //尝试json
                    try {
                        JSON jsonindex = Redson.instance().makeNewJSON((JSONObject) o);
                        IJSONObject ijsonObject = (IJSONObject) iBean.beanType().newInstance();
                        ijsonObject.fromJSON(jsonindex);
                        orgin_list.put(key, ijsonObject);
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }else{
                    orgin_list.put(key, json);
                }
            }else{
                orgin_list.put(key, null);
            }
        }


    }

    public static void fromJson(JSONArray orgin_json, List orgin_list, IBean iBean){
        if(iBean == null || orgin_json == null || orgin_list == null){
            return;
        }
        boolean isList = Redson.instance().isTypeList(iBean.beanType());
        if(isList){
            for(int i = 0; i < orgin_json.length() ; i ++){
                JSONArray jsonArray = orgin_json.optJSONArray(i);
                List list = Redson.instance().isList(iBean.beanType());
                fromJson(jsonArray, list, iBean.nextBean());
                orgin_list.add(list);
            }
            return;
        }
        boolean isMap = Redson.instance().isTypeMap(iBean.beanType());
        if(isMap){
            for(int i = 0 ; i< orgin_json.length(); i ++){
                JSON json = orgin_json.optJSON(i);
                Map map = Redson.instance().isMap(iBean.beanType());
                fromJson(json, map, iBean.nextBean());
                orgin_list.add(map);
            }
            return;
        }


        for(int i = 0 ; i< orgin_json.length(); i ++){
            Object o = orgin_json.get(i);
            if(o != null){
                Object json = Redson.instance().isBaseVar(o,iBean.beanType());
                if(json == null){
                    //尝试json
                    try {
                        JSON jsonindex = Redson.instance().makeNewJSON((JSONObject) o);
                        IJSONObject ijsonObject = (IJSONObject) iBean.beanType().newInstance();
                        ijsonObject.fromJSON(jsonindex);
                        orgin_list.add(ijsonObject);
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }else{
                    orgin_list.add(json);
                }
            }else{
                orgin_list.add( null);
            }
        }

    }
}
