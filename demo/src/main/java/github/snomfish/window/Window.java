package github.snomfish.window;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

public class Window {


    private Long windowHandle;

    private int width;
    private int height;
    private final String title;
    

    // constructors
    public Window (
        int width, int height, String title
    ) {
        this.width = width;
        this.height = height;
        this.title = title;
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


    public void init() {
        
        // initialises GLFW library, must be called before any other GLFW function
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        windowHandle = GLFW.glfwCreateWindow(
            width,
            height,
            "Renderer",
            0,
            0
        );

        GLFW.glfwMakeContextCurrent(windowHandle);
        
        GL.createCapabilities();
    }


    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(windowHandle);
    }


    public void cleanup() {
        GLFW.glfwDestroyWindow(windowHandle);
        GLFW.glfwTerminate();
    }
}
