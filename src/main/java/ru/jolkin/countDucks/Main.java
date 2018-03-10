package ru.jolkin.countDucks;

import ru.jolkin.countDucks.duck.Type;
import ru.jolkin.countDucks.form.PictureBox;
import ru.jolkin.countDucks.form.StatusBar;
import ru.jolkin.countDucks.project.Project;
import ru.jolkin.countDucks.project.ProjectManager;
import ru.jolkin.countDucks.utils.ColorUtil;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
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

            Project project;
            JFrame frame = new JFrame();
            if (manager.projectExist(file.getName())) {

                int n = JOptionPane.showConfirmDialog(
                        frame,
                        "Проект с таким названием уже существует, восстановить?",
                        "Восстановление",
                        JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    project = manager.recover(file.getName());
                } else {
                    manager.remove(file.getName());
                    project = manager.create(file.getName(), file);
                }
            } else {
                project = manager.create(file.getName(), file);
            }

            runProject(frame, project);
        }
    }

    private void refreshStatusBar(StatusBar statusBar, PictureBox box, Project project)
    {
        String total = String.valueOf(project.getDuckManager().fetchAll().size());
        String zoom = String.valueOf(box.getPicture().getZoom());
        StringBuilder buffer = new StringBuilder();

//        buffer.append("<font> Pos: ");
//        buffer.append(box.getPicture().getPosition());
//        buffer.append("        </font>");
//
//        buffer.append("<font> Zoom: ");
//        buffer.append(zoom);
//        buffer.append("        </font>");

        buffer.append("<font> Total: ");
        buffer.append(total);
        buffer.append("        </font>");

        for (Map.Entry<Type, Integer> entity: project.getDuckManager().groupByType().entrySet()) {

            String hex = ColorUtil.toHexString(entity.getKey().getColor());

            buffer.append("<font color=");
            buffer.append(hex);
            buffer.append(">");
            buffer.append(entity.getKey().getName());
            buffer.append(": " );
            buffer.append(entity.getValue());
            buffer.append(" </font>");
        }

        statusBar.setText("<html>" + buffer.toString() + "</html>");
    }

    private   void runProject(JFrame frame, Project project) {
        EventQueue.invokeLater(() -> {

            StatusBar statusBar = new StatusBar();
//            JOptionPane.showMessageDialog(frame, "Eggs are not supposed to be green.");

            frame.setTitle(project.getName());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            statusBar.registry(frame);

            PictureBox box = new PictureBox(project);

            frame.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    refreshStatusBar(statusBar, box, project);
                }
            });

            frame.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {}

                @Override
                public void keyPressed(KeyEvent e) {}

                @Override
                public void keyReleased(KeyEvent e) {

                    if (e.getKeyCode() == KeyEvent.VK_S) {

                        try {
                            manager.save(project);
                            statusBar.setText("Успешно сохранено");

                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    } else if (e.getKeyCode() == KeyEvent.VK_Z) {
                        project.getDuckManager().back();
                        statusBar.setText("Успешно отменено");

                        box.repaint();
                    }
                }
            });

            box.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    refreshStatusBar(statusBar, box, project);
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
