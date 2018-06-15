package com.redson;

/**
 * Created by Linhh on 28/02/2018.
 */

public class RedsonStringWriter implements IRedsonStringWriter{
    StringBuilder builder = new StringBuilder();
    @Override
    public RedsonStringWriter append(boolean b) {
        builder.append(b);
        return this;
    }

    @Override
    public RedsonStringWriter append(String s) {
        builder.append(s);
        return this;
    }

    @Override
    public RedsonStringWriter append(long s) {
        builder.append(s);
        return this;
    }

    @Override
    public RedsonStringWriter append(float s) {
        builder.append(s);
        return this;
    }

    @Override
    public RedsonStringWriter append(double s) {
        builder.append(s);
        return this;
    }

    @Override
    public RedsonStringWriter append(int s) {
        builder.append(s);
        return this;
    }

    @Override
    public RedsonStringWriter append(Object s) {
        builder.append(s);
        return this;
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}
