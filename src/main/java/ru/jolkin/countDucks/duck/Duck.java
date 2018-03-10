package ru.jolkin.countDucks.duck;

import com.google.gson.Gson;

import java.awt.*;

public class Duck {
    private Point position;
    private Type type;
    private int size =  10;

    public Duck(Point position, Type type) {
        this.position = position;
        this.type = type;
    }

    public static Duck createByJson(String json)
    {
        return new Gson().fromJson(json, Duck.class);
    }

    public Point getPosition() {
        return position;
    }

    public int getSize() {
        return size;
    }

    public Type getType() {
        return type;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
