import ru.jolkin.countDucks.project.ProjectManager;

import java.io.File;

public class ProjectTest extends TestCase {

    private static String RESOURCE_PATCH = "src/test/resource";
    private static String TEST_IMAGE_FILE_NAME = "p1.jpg";

    private File getTestImage(String name)
    {
        return new File(RESOURCE_PATCH + "/images/" + name);
    }

    public void testFoundP1Image()
    {
       File f =  getTestImage("p1.jpg");
       System.out.println(f.getPath());
       System.out.println(f.exists());
    }

    private ProjectManager createProjectManager()
    {
        return new ProjectManager(RESOURCE_PATCH);
    }

    public void testCreateManager()
    {
        createProjectManager();
    }

    public void testCreateProject()
    {
        ProjectManager project = createProjectManager();
        project.create("testProject", getTestImage(TEST_IMAGE_FILE_NAME));
        File projectDir = new File(RESOURCE_PATCH + "/testProject");
        assertTrue(projectDir.exists());
        assertTrue(projectDir.delete());
    }

    public void testRemoveProject()
    {
        ProjectManager project = createProjectManager();

        project.create("p1", getTestImage(TEST_IMAGE_FILE_NAME));
        assertTrue(new File(RESOURCE_PATCH + "/p1").exists());

        project.remove("p1");
        assertFalse(new File(RESOURCE_PATCH + "/p1").exists());
    }
}
