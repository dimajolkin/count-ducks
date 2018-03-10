package ru.jolkin.countDucks.form;

import ru.jolkin.countDucks.duck.Duck;
import ru.jolkin.countDucks.duck.Type;
import ru.jolkin.countDucks.elements.Picture;
import ru.jolkin.countDucks.project.Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class PictureBox extends JPanel implements MouseListener, MouseWheelListener, MouseMotionListener {

    private Project project;
    private Picture picture;
    private Point previous;
    private Point position;
    private Point cursor;

    public PictureBox(Project project) {
        this.project = project;
        this.picture = new Picture(project.getImage());
        this.previous = new Point(0, 0);
        this.position = new Point(0, 0);
        this.cursor = new Point(0, 0);

        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);

        setOpaque(false);
    }

    public Picture getPicture()
    {
        return picture;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!this.picture.crossing(e.getPoint())) {
            return;
        }

        int newX = e.getX() - previous.x;
        int newY = e.getY() - previous.y;

        previous.translate(newX, newY);

        picture.getPosition().translate(newX, newY);
        position.translate(newX, newY);

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
//        cursor = e.getPoint();
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
            picture.zoom(.1 * -(double) e.getWheelRotation());
            repaint();
        }
    }

    public void mousePressed(MouseEvent e) {
        previous = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (picture.crossing(e.getPoint())) {
            Type type = Type.UNDEFINED;

            if (SwingUtilities.isLeftMouseButton(e)) {
                type = Type.WOMEN;
            } else if (SwingUtilities.isRightMouseButton(e)) {
                type = Type.MEN;
            } else {
//                cursor = e.getPoint();
            }

            if (type != null) {
                project.getDuckManager().add(new Duck(
                        picture.convertToPoint(e.getPoint()),
                        type
                ));
            }
            repaint();
        }
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        AffineTransform tx = new AffineTransform();

        tx.translate(cursor.x, cursor.y);
        tx.scale(picture.getZoom(), picture.getZoom());
        tx.translate(-cursor.x, -cursor.y);
        tx.translate(position.x, position.y);

        g2d.drawImage(picture.getImage(), tx, this);

        Point leftTop = new Point(
                (int) tx.getTranslateX(),
                (int) tx.getTranslateY()
        );
        picture.setPosition(leftTop);

        for (Duck d : project.getDuckManager().fetchAll()) {
            Point t = picture.convertToGlobal(d.getPosition());

            g2d.setColor(d.getType().getColor());

            g2d.fillOval(t.x - (d.getSize() / 2), t.y - (d.getSize() / 2), d.getSize(), d.getSize());
        }

        g2d.dispose();
    }
}
