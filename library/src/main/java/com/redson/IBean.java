package com.redson;

/**
 * Created by Linhh on 26/02/2018.
 */

public interface IBean {

    public IBean nextBean();
    public Class beanType();
    public void setNextBean(IBean bean);
    public void setClass(Class clazz);
}
