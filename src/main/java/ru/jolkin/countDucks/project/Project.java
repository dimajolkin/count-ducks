package ru.jolkin.countDucks.project;

import ru.jolkin.countDucks.duck.DuckManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Project {


    private String name;
    private Image image;
    private DuckManager duckManager;

    public Project(String name, File file) {
        this.duckManager = new DuckManager();
        this.name = name;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Image getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public DuckManager getDuckManager() {
        return duckManager;
    }

    public void setDuckManager(DuckManager duckManager) {
        this.duckManager = duckManager;
    }
}
