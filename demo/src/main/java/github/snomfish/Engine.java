package github.snomfish;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import github.snomfish.graphics.Shader;
import github.snomfish.scene.Scene;
import github.snomfish.window.Window;

public class Engine {


    private Window window;
    private Shader shader;
    private Scene scene;

    private int fps;
    private long frameInterval;


    public Engine() {
        fps = 60;
        frameInterval = 1_000_000_000 / fps;
    }


    public void setFps(int fps) {
        this.fps = fps;
        frameInterval = 1_000_000_000 / fps;
    }


    public void run() {
        init();
        loop();
        cleanup();
    }


    private void init() {

        window = new Window(1280, 720, "Renderer");
        window.init();

        shader = new Shader(
            "shader/Vertex.glsl", 
            "shader/Fragment.glsl"
        );
        
    }


    private void loop() {
        long lastFrame = System.nanoTime();
        long currentTime = lastFrame;
        long deltaTime;

        while(!window.shouldClose()) {

            currentTime = System.nanoTime();
            deltaTime = currentTime - lastFrame;

            if (deltaTime >= lastFrame + frameInterval) {
                lastFrame = currentTime;
                update(deltaTime);
                render(deltaTime);
            }
        }
    }


    private void update(float deltaTime) {
        /* 
        GLFW.glfwPollEvents();

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        // draw

        GLFW.glfwSwapBuffers(windowHandle);
        */
    }


    private void render(float deltaTime) {

    }


    private void cleanup() {
        window.cleanup();
        shader.cleanup();
    }
}
