package ru.jolkin.countDucks;

import ru.jolkin.countDucks.form.PictureBox;
import ru.jolkin.countDucks.project.Project;
import ru.jolkin.countDucks.project.ProjectManager;

import java.awt.*;
import java.io.File;
import javax.swing.*;

public class Main {
    public ProjectManager manager = new ProjectManager(Config.PROJECT_DIR);
    public Project project;

    public static void main(String[] args) throws Exception {
        new Main();
    }

    Main() {
        JFileChooser fileOpen = new JFileChooser();
        int ret = fileOpen.showDialog(null, "Открыть файл");
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileOpen.getSelectedFile();
            Project project = manager.create("Test", file);
            runProject(project);
        }
    }


    private   void runProject(Project project) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            PictureBox box = new PictureBox(project);

            frame.add(box);

            frame.setSize(640, 480);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

}
