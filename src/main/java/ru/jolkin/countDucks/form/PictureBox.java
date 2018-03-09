package ru.jolkin.countDucks.form;

import ru.jolkin.countDucks.duck.Duck;
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

    public PictureBox(Project project) {
        this.project = project;
        this.picture = new Picture(project.getImage());
        this.previous = new Point(0, 0);
        this.position = new Point(0, 0);

        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);

        setOpaque(false);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!this.picture.crossing(e.getPoint())) {
            return;
        }

        int newX = e.getX() - previous.x;
        int newY = e.getY() - previous.y;

        previous.translate(newX, newY);

//        picture.getPosition().translate(newX, newY);
        position.translate(newX, newY);

        repaint();
    }



    @Override
    public void mouseMoved(MouseEvent e) {

//        System.out.println(picture.getWidth());
//        System.out.println(picture.convertToPoint(e.getPoint()));

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
            project.getDuckManager().add(new Duck(picture.convertToPoint(e.getPoint()), Color.RED));
            repaint();
        }
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        AffineTransform tx = new AffineTransform();
        AffineTransform tx2 = new AffineTransform();

        tx.scale(picture.getZoom(), picture.getZoom());

//        tx2.translate(picture.getPosition().x, picture.getPosition().y);
        tx2.translate(position.x, position.y);
        tx.concatenate(tx2);


        g2d.drawImage(picture.getImage(), tx, this);

        Point leftTop = new Point(
                (int) tx.getTranslateX(),
                (int) tx.getTranslateY()
        );
        picture.setPosition(leftTop);

        for (Duck d : project.getDuckManager().fetchAll()) {
            Point t = picture.convertToGlobal(d.getPosition());

            g2d.setColor(d.getColor());

            g2d.fillOval(t.x - (d.getSize() / 2), t.y - (d.getSize() / 2), d.getSize(), d.getSize());
        }

        g2d.dispose();
    }
}
