package github.snomfish;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Window {


    private static final int defaultWidth = 1280;
    private static final int defaultHeight = 720;
    private static final String defaultTitle = "Renderer";

    private Long window;

    private int width;
    private int height;
    private final String title;

    private int fps;
    private long frameTime;
    

    // constructors
    public Window (
        int width, int height, String title
    ) {
        this.width = width;
        this.height = height;
        this.title = title;

        setFps(60);
    }
    public Window() {
        this(defaultWidth, defaultHeight, defaultTitle);
    }


    // getters
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public String getTitle() {
        return title;
    }


    // setter
    public void setFps(int fps) {
        fps = Math.max(fps, 1);
        frameTime = 1000_000_000 / (long)fps;
    }


    //
    public void init() {
        
        // initialises GLFW library, must be called before any other GLFW function
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        long window = GLFW.glfwCreateWindow(
            width,
            height,
            "Renderer",
            0,
            0
        );

        GLFW.glfwMakeContextCurrent(window);
        
        GL.createCapabilities();
    }


    public void loop() {

        long lastFrame = System.nanoTime();
        long currentTime = lastFrame;
        long deltaTime;

        while(!GLFW.glfwWindowShouldClose(window)) {

            currentTime = System.nanoTime();
            deltaTime = currentTime - lastFrame;
            if (deltaTime > frameTime) {

                lastFrame = currentTime;
                update(deltaTime);
            }
        }
    }


    public void update(
        long deltaTime
    ) {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        // draw

        GLFW.glfwSwapBuffers(window);

        GLFW.glfwPollEvents();
    }
}
