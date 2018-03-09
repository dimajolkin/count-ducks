package ru.jolkin.countDucks.project;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ProjectManager {
    private String dir;

    public ProjectManager(String dir) {
        this.dir = dir;
    }

    public Project create(String name, File file) {
        return new Project(name, file);
    }

    public void save(Project project) throws IOException {
        project.saveToDir(this.dir);
    }
}
