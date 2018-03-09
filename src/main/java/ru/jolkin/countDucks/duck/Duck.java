package ru.jolkin.countDucks.duck;

import java.awt.*;

public class Duck {
    private Point position;
    private Type type;
    private int size =  10;

    public Duck(Point position, Type type) {
        this.position = position;
        this.type = type;
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
}
