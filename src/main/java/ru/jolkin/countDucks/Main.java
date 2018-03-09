package ru.jolkin.countDucks;

import ru.jolkin.countDucks.form.PictureBox;
import ru.jolkin.countDucks.project.Project;
import ru.jolkin.countDucks.project.ProjectManager;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Main {
    public ProjectManager manager = new ProjectManager(Config.PROJECT_DIR);

    public static void main(String[] args) throws Exception {
        new Main();
    }

    Main() {
        JFileChooser fileOpen = new JFileChooser();
        int ret = fileOpen.showDialog(null, "Открыть файл");
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileOpen.getSelectedFile();

            Project project = manager.create(file.getName(), file);
            runProject(project);
        }
    }

    public final static String toHexString(Color colour) throws NullPointerException {
        String hexColour = Integer.toHexString(colour.getRGB() & 0xffffff);
        if (hexColour.length() < 6) {
            hexColour = "000000".substring(0, 6 - hexColour.length()) + hexColour;
        }
        return "#" + hexColour;
    }


    private   void runProject(Project project) {
        EventQueue.invokeLater(() -> {

            JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
            statusBar.setBorder(new CompoundBorder(new LineBorder(Color.DARK_GRAY),
                    new EmptyBorder(4, 4, 4, 4)));
            final JLabel status = new JLabel();
            statusBar.add(status);

            JFrame frame = new JFrame();
//            JOptionPane.showMessageDialog(frame,
//                    "Eggs are not supposed to be green.");

            frame.setTitle(project.getName());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(statusBar, BorderLayout.SOUTH);
            PictureBox box = new PictureBox(project);
            box.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
//                    JLabel title = new JLabel("I love stackoverflow!", JLabel.CENTER);
                    String total = String.valueOf(project.getDuckManager().fetchAll().size());
                    StringBuilder buffer = new StringBuilder();

                    buffer.append("<font> Total: ");
                    buffer.append(total);
                    buffer.append("        </font>");


                    for (Map.Entry<Color, Integer> entity: project.getDuckManager().groupByColor().entrySet()) {

                        String hex = toHexString(entity.getKey());

                        buffer.append("<font color=");
                        buffer.append(hex);
                        buffer.append(">");
                        buffer.append(entity.getValue());
                        buffer.append("        </font>");
                    }

                    status.setText("<html>" + buffer.toString() + "</html>");
//                    status.setText("Count: " + String.valueOf(project.getDuckManager().fetchAll().size()));
                }

                @Override
                public void mousePressed(MouseEvent e) {

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
            });

            frame.add(box);

            frame.setSize(Config.DEFAULT_WIDTH, Config.DEFAULT_HEIGHT);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

}
