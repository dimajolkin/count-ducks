package ru.jolkin.countDucks.elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class Picture {

    private Point position = new Point(0, 0);
    private double zoom = 1;
    private Image image;

    public Picture(Image image) {
        this.image = image;
    }

    public Point getPosition() {
        return this.position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public double getZoom() {
        return zoom;
    }

    public void zoom(double amount) {

        zoom += amount;
        zoom = Math.max(0.00001, zoom);
    }


    public boolean crossing(Point point) {
        return point.getX() - position.x < getWidth() &&
                point.getX() - position.x > 0 &&
                point.getY() - position.y < getHeight() &&
                point.getY() - position.y > 0;
    }

    public Point convertToPoint(Point point) {
        return new Point(
                (int) ((point.x - position.x) / zoom),
                (int) ((point.y - position.y) / zoom)
        );
    }

    public Point convertToGlobal(Point point) {
        return new Point(
                (int) (point.x * zoom) + this.position.x,
                (int) (point.y * zoom) + this.position.y
        );
    }

    public Image getImage() {
        return image;
    }

    public int getWidth() {
        return (int) (image.getWidth(null) * zoom);
    }

    public int getHeight() {
        return (int) (image.getHeight(null) * zoom);
    }
}
