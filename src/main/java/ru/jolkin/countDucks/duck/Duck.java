package ru.jolkin.countDucks.duck;

import java.awt.*;

public class Duck {
    private Point position;
    private Color color;
    private int size =  10;

    public Duck(Point position, Color color) {
        this.position = position;
        this.color = color;
    }

    public Point getPosition() {
        return position;
    }

    public Color getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }
}
