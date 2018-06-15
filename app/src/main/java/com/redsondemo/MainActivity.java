package com.redsondemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.redson.IJSONObject;
import com.redsondemo.R;
import com.redsondemo.test.Base;

import org.json.JSONObject;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.signature.SignatureReader;
import org.objectweb.asm.signature.SignatureVisitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
//            fastJsonDemo2.main(null);
//            Log.d("jsontes222t", "-----------------------------");

//            fastJsonDemo.main(null);
            String test_json = getFromAssets("1.txt");
//            long tim1e1 = System.currentTimeMillis();
//            TestImpl impl = new TestImpl();
//            impl.fromJSON(test_json);
//            ObjectMapper mapper = new ObjectMapper();
//            String jsonsss = mapper.writeValueAsString(impl);
//            Log.d("jsontes222t1", "redson pk:" + (System.currentTimeMillis() - tim1e1));
//            tim1e1 = System.currentTimeMillis();
////            TestImpl t22 = JSON.parseObject(test_json,TestImpl.class);
//            Log.d("jsontes222t1", "fastjson pk:" + (System.currentTimeMillis() - tim1e1));
//            tim1e1 = System.currentTimeMillis();
//            String sss = impl.toJSONString();
//            Log.d("jsontes222t1", "redson string pk:" + (System.currentTimeMillis() - tim1e1));
//            tim1e1 = System.currentTimeMillis();
//            String sst = JSON.toJSONString(new TestImpl());

//            String s = new Gson().toJson(impl);
//            Log.d("jsontes222t1", "fastjson string pk:" + (System.currentTimeMillis() - tim1e1));

//            ArrayList<TestImpl> list = new ArrayList<>();
//        list.add(new TestImpl());

            long tim1e = System.currentTimeMillis();
            TestImpl test555 = new TestImpl();
        String s = test555.toJSONString();

//            test555.fromJSON(test_json);
//        String s = test555.toJSONString();
//            Log.d("jsontes222t", "redson pk:" + (System.currentTimeMillis() - tim1e));
//            tim1e = System.currentTimeMillis();
//            String ts = JSON.toJSONString(test555);
            Log.d("jsontes222t", "redson toString:" + (System.currentTimeMillis() - tim1e));
            tim1e = System.currentTimeMillis();
            TestImpl test2 = new TestImpl();
            test2.fromJSON(s);
            Log.d("jsontes222t", "redson parseObject:" + (System.currentTimeMillis() - tim1e));

            tim1e = System.currentTimeMillis();
            String j = JSON.toJSONString(test555);
            Log.d("jsontes222t", "fastjson toString:" + (System.currentTimeMillis() - tim1e));

            tim1e = System.currentTimeMillis();
            TestImpl test = JSON.parseObject(s, TestImpl.class);
            Log.d("jsontes222t", "fastjson parseObject:" + (System.currentTimeMillis() - tim1e));


//            TestfastJSON t =     JSON.parseObject( getFromAssets("fastjson.txt"),TestfastJSON.class);
//
//
//        String json = getFromAssets("json_tataquan.txt");
//        long time = System.currentTimeMillis();
//        Base t2 = new Base();
//        t2.fromJSON(json);
//        Log.d("jsontest", "redson pk:" + (System.currentTimeMillis() - time));
//        time = System.currentTimeMillis();
//        Base test = JSON.parseObject(json,Base.class);
////        Log.d("jsontest", "result start:");
//        Log.d("jsontest", "fastjson pk:" + (System.currentTimeMillis() - time));
//        time = System.currentTimeMillis();
//        boolean b = (IJSONObject.class.isAssignableFrom(TestImpl.class));
//        Log.d("jsontest", "result:" +b + (System.currentTimeMillis() - time));
//        Log.d("jsontest", "Ljava/lang/String;".substring(1, "Ljava/lang/String;".length() - 1));
////        Log.d("jsontest", "result:" +b);
//
//
//
//
//            time = System.currentTimeMillis();
////            for(int i = 0; i < 100; i ++) {
//                Class clazz = Class.forName("com.redsondemo.TestImpl");
//                Object object = clazz.newInstance();
////            }
//
//            Log.d("jsontest", "init clazz" + (System.currentTimeMillis() - time));
//
//
//            time = System.currentTimeMillis();
//            JSONObject jsonObject = new JSONObject(json);
//            Log.d("jsontest", "native end" + (System.currentTimeMillis() - time));
//            time = System.currentTimeMillis();
//            com.alibaba.fastjson.JSONObject jsonObject2 = JSON.parseObject(json);
//            Log.d("jsontest", "fastjson end"+ (System.currentTimeMillis() - time));
//            time = System.currentTimeMillis();
//            JsonElement jsonElement = new JsonParser().parse(json);
//            Log.d("jsontest", "gson end"+ (System.currentTimeMillis() - time));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        SignatureReader signatureReader = new SignatureReader("Ljava/util/ArrayList<Ljava/util/ArrayList<Ljava/util/HashMap<Ljava/lang/String;Ljava/util/ArrayList<Ljava/lang/String;>;>;>;>;");
//        signatureReader.acceptType(new SignatureVisitor(Opcodes.ASM5) {
//            String lastType = null;
//            String text = "";
//            int i = -1;
//            @Override
//            public void visitFormalTypeParameter(String name) {
//                super.visitFormalTypeParameter(name);
//            }
//
//            @Override
//            public SignatureVisitor visitClassBound() {
//                return super.visitClassBound();
//            }
//
//            @Override
//            public SignatureVisitor visitInterfaceBound() {
//                return super.visitInterfaceBound();
//            }
//
//            @Override
//            public SignatureVisitor visitSuperclass() {
//                return super.visitSuperclass();
//            }
//
//            @Override
//            public SignatureVisitor visitInterface() {
//                return super.visitInterface();
//            }
//
//            @Override
//            public SignatureVisitor visitParameterType() {
//                return super.visitParameterType();
//            }
//
//            @Override
//            public SignatureVisitor visitReturnType() {
//                return super.visitReturnType();
//            }
//
//            @Override
//            public SignatureVisitor visitExceptionType() {
//                return super.visitExceptionType();
//            }
//
//            @Override
//            public void visitBaseType(char descriptor) {
//                super.visitBaseType(descriptor);
//            }
//
//            @Override
//            public void visitTypeVariable(String name) {
//                super.visitTypeVariable(name);
//            }
//
//            @Override
//            public SignatureVisitor visitArrayType() {
//                return super.visitArrayType();
//            }
//
//            @Override
//            public void visitClassType(String name) {
//                super.visitClassType(name);
//                i++;
//                if(i == 0){
//                    return;
//                }
//                text =text+name+"----";
//            }
//
//            @Override
//            public void visitInnerClassType(String name) {
//                super.visitInnerClassType(name);
//            }
//
//            @Override
//            public void visitTypeArgument() {
//                super.visitTypeArgument();
//            }
//
//            @Override
//            public SignatureVisitor visitTypeArgument(char wildcard) {
//                return super.visitTypeArgument(wildcard);
//            }
//
//            @Override
//            public void visitEnd() {
//                super.visitEnd();
//            }
//        });
////        test(list);
//        Log.d("jsontest", "gson end");
    }

    public String getFromAssets(String fileName) throws IOException{
        InputStreamReader inputReader = new InputStreamReader( getResources().getAssets().open(fileName),"GB2312");

        BufferedReader bufReader = new BufferedReader(inputReader);
        String line="";
        String Result="";
        while((line = bufReader.readLine()) != null)
            Result += line;
        return Result;
    }

    public void test(List<ITest> tests){
//        throw new Exception();
    }
}
