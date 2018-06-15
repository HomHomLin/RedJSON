package com.redson;

/**
 * Created by Linhh on 28/02/2018.
 */

public interface IRedsonStringWriter {
    public IRedsonStringWriter append(boolean b);
    public IRedsonStringWriter append(String s);
    public IRedsonStringWriter append(long s);
    public IRedsonStringWriter append(float s);
    public IRedsonStringWriter append(double s);
    public IRedsonStringWriter append(int s);
    public IRedsonStringWriter append(Object s);
}
