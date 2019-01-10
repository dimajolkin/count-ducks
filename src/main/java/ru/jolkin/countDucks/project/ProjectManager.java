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
    private static String PROJECT_FILE_NAME = "ducks.json";

    private String dir;

    public ProjectManager(String projectDir) {
        this.dir = projectDir;
    }

    private String getProjectDir(String projectName)
    {
        return  this.dir + "/" + projectName;
    }

    public void remove(String projectName) {
        File dir = new File(this.dir + "/" + projectName);
        dir.delete();
    }

    public Project create(String projectName, File file) {
        File dir = new File(getProjectDir(projectName));
        dir.mkdirs();

        return new Project(projectName, file);
    }

    public Project recover(String projectName) {
        String projectDir = getProjectDir(projectName);

        File dir = new File(projectDir);
        if (!dir.exists()) {
            throw new RuntimeException();
        }

        DuckManager duckManager = null;
        try {
            String json = new String(
                    Files.readAllBytes(FileSystems.getDefault().getPath(projectDir + "/" + PROJECT_FILE_NAME))
            );
            duckManager = DuckManager.createByJson(json);

        } catch (IOException ex) {
            ex.printStackTrace();
        }


        File image = new File(projectDir + "/image.png");
        Project project = new Project(projectName, image);
        if (duckManager != null) {
            project.setDuckManager(duckManager);
        }

        return project;
    }

    public void save(Project project) throws IOException {
        String projectDir = getProjectDir(project.getName());

        BufferedImage bi = (BufferedImage) project.getImage();
        File f = new File(projectDir + "/image.png");
        ImageIO.write(bi, "png", f);

        BufferedWriter writer = new BufferedWriter(new FileWriter(projectDir + "/" + PROJECT_FILE_NAME));
        writer.write(project.getDuckManager().toJson());
        writer.close();
    }

    public boolean projectExist(String name) {
        File dir = new File(getProjectDir(name));
        return dir.exists();
    }
}
