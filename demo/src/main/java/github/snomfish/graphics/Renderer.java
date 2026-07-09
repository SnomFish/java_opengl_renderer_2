package github.snomfish.graphics;

import org.lwjgl.opengl.GL11;

public class Renderer {


    public Renderer() {}


    public void init() {

        GL11.glViewport(0, 0, 1280, 720);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        /*GL11.glClearColor(
            0.f,
            0.1f,
            0.15f,
            1.0f
        );*/
    }


    public void beginFrame() {
        // remove all from previous frame
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }


    public void endFrame() {

    }
}
