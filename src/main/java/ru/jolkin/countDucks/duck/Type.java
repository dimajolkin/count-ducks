package ru.jolkin.countDucks.duck;

import java.awt.*;

public class Type {

    public static final Type MEN = new Type(Color.BLUE, "Men");
    public static final Type WOMEN = new Type(Color.RED, "Women");
    public static final Type UNDEFINED = new Type(Color.GRAY, "Undefined");


    private Color color;
    private String name;

    public Type(Color color, String name) {
        this.color = color;
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return color.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Type) {
            return obj.hashCode() == hashCode();
        }

        return super.equals(obj);
    }
}
