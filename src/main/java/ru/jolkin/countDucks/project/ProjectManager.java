package ru.jolkin.countDucks.project;

import ru.jolkin.countDucks.duck.DuckManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;

public class ProjectManager {
    private String dir;

    public ProjectManager(String dir) {
        this.dir = dir;
    }

    public void remove(String name)
    {
        File dir = new File(this.dir + "/" + name);
        dir.delete();
    }

    public Project create(String name, File file) {
        File dir = new File(this.dir + "/" + name);
        dir.mkdirs();

        return new Project(name, file);
    }

    public Project recover(String name)
    {
        String projectDir = this.dir + "/" + name;

        File dir = new File(projectDir);
        if (!dir.exists()) {
            throw new RuntimeException();
        }

        DuckManager duckManager = null;
        try {
            String json = new String(Files.readAllBytes(FileSystems.getDefault().getPath(projectDir + "/ducks.json")));
            duckManager = DuckManager.createByJson(json);

        } catch (IOException ex) {
            ex.printStackTrace();
        }


        File image = new File(projectDir + "/image.png");
        Project project = new Project(name, image);
        if (duckManager != null) {
            project.setDuckManager(duckManager);
        }

        return project;
    }

    public void save(Project project) throws IOException {
        String projectDir = this.dir + "/" + project.getName();

        BufferedImage bi = (BufferedImage)project.getImage();
        File f = new File(projectDir + "/image.png");
        ImageIO.write(bi, "png", f);

        BufferedWriter writer = new BufferedWriter( new FileWriter(projectDir + "/ducks.json"));
        writer.write(project.getDuckManager().toJson());
        writer.close();
    }

    public boolean projectExist(String name) {
        String projectDir = this.dir + "/" + name;

        File dir = new File(projectDir);
        return  dir.exists();
    }
}
