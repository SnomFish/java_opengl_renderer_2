package github.snomfish.window;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

import github.snomfish.input.Input;

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
    public float getAspect() {
        return (float) width / (float) height;
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

        if (windowHandle == 0) {
            throw new RuntimeException("Failed to create window");
        }

        GLFW.glfwSetInputMode(
            windowHandle,
            GLFW.GLFW_CURSOR,
            GLFW.GLFW_CURSOR_DISABLED
        );
        GLFW.glfwMakeContextCurrent(windowHandle);
        GLFW.glfwSetKeyCallback(windowHandle, Input::keyCallback);
        GLFW.glfwSetCursorPosCallback(windowHandle, Input::mouseCallback);
        
        GL.createCapabilities();
    }


    public void update() {
        GLFW.glfwSwapBuffers(windowHandle);
        GLFW.glfwPollEvents();
    }


    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(windowHandle);
    }


    public void cleanup() {
        GLFW.glfwDestroyWindow(windowHandle);
        GLFW.glfwTerminate();
    }
}
