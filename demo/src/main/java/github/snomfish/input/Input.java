package github.snomfish.input;

import org.lwjgl.glfw.GLFW;

import java.util.HashSet;
import java.util.Set;

public class Input {


    // keys
    private static final Set<Integer> downKeys = new HashSet<>();

    // mouse
    private static double lastX;
    private static double lastY;
    private static float deltaX;
    private static float deltaY;

    private static boolean firstMouse = true;


    public static void keyCallback(long window, int key, int scancode, int action, int mods) {

        if (action == GLFW.GLFW_PRESS || action == GLFW.GLFW_REPEAT) downKeys.add(key);
        if (action == GLFW.GLFW_RELEASE) downKeys.remove(key);
        
    }

    public static boolean isKeyDown(int key) {
        return downKeys.contains(key);
    }


    public static void mouseCallback(long window, double xpos, double ypos) {

        if (firstMouse) {
            lastX = xpos;
            lastY = ypos;
            firstMouse = false;
        }

        deltaX += (float)(xpos - lastX);
        deltaY += (float)(ypos - lastY);

        lastX = xpos;
        lastY = ypos;
    }

    
    public static float getDeltaX() {return deltaX;}
    public static float getDeltaY() {return deltaY;}


    public static void endFrame() {
        deltaX = 0;
        deltaY = 0;
    }
}

