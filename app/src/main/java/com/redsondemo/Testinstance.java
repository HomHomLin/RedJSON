package com.redsondemo;

import com.redson.Redson;

import java.util.ArrayList;

public class Testinstance {
    public void test(){
        Redson.instance().buildJSON(null, "list7", new ArrayList());
    }
}
