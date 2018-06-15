package com.redson;

/**
 * Created by Linhh on 26/02/2018.
 */

public class NormalBean implements IBean{
    Class clazz;
    IBean iBean;

    @Override
    public IBean nextBean() {
        return iBean;
    }

    @Override
    public Class beanType() {
        return clazz;
    }

    @Override
    public void setNextBean(IBean bean) {
        iBean = bean;
    }

    @Override
    public void setClass(Class c) {
        clazz = c;
    }
}
