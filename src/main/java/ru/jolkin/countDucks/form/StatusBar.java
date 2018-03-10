package ru.jolkin.countDucks.form;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class StatusBar {
    private JPanel statusBar;
    private JLabel status;
    public StatusBar() {
        statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusBar.setBorder(new CompoundBorder(new LineBorder(Color.DARK_GRAY),
                new EmptyBorder(4, 4, 4, 4)));
        status = new JLabel();
        statusBar.add(status);
    }

    public void setText(String text)
    {
        status.setText(text);
    }

    public void registry(Frame frame)
    {
        frame.add(statusBar, BorderLayout.SOUTH);
    }
}
