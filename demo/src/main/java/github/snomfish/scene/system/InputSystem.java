package github.snomfish.scene.system;

import java.util.List;

import org.lwjgl.glfw.GLFW;

import github.snomfish.input.Input;
import github.snomfish.scene.Scene;
import github.snomfish.scene.System;
import github.snomfish.scene.components.PlayerCmp;
import github.snomfish.scene.components.TransformCmp;

public class InputSystem extends System {


    public void update(Scene scene, float deltaTime) {

        for (Integer id : scene.getEntitiesWith(List.of(
            PlayerCmp.class,
            TransformCmp.class
        ))) {
            
            PlayerCmp p = scene.get(id, PlayerCmp.class);
            TransformCmp t = scene.get(id, TransformCmp.class);

            float speed = p.getSpeed();

            if (Input.isKeyDown(GLFW.GLFW_KEY_W)) {
                t.forwardsX(speed * deltaTime);
            }

            if (Input.isKeyDown(GLFW.GLFW_KEY_S)) {
                t.forwardsX(-speed * deltaTime);
            }

            if (Input.isKeyDown(GLFW.GLFW_KEY_A)) {
                t.strafeX(-speed * deltaTime);
            }

            if (Input.isKeyDown(GLFW.GLFW_KEY_D)) {
                t.strafeX(speed * deltaTime);
            }

            if (Input.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
                t.translate(0, speed * deltaTime, 0);
            }

            if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) {
                t.translate(0, -speed * deltaTime, 0);
            }

            float sensitivity = 0.09f;
            float cursorDeltaX = Input.getDeltaX();
            float cursorDeltaY = Math.max(-89f, Math.min(Input.getDeltaY(), 89f));
            t.rotate(
                cursorDeltaY * sensitivity, 
                cursorDeltaX * sensitivity,
                0
            );
        }
    }
}