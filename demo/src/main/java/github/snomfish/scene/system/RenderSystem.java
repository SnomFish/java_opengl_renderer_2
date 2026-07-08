package github.snomfish.scene.system;

import org.lwjgl.opengl.GL11;

import github.snomfish.graphics.Shader;
import github.snomfish.scene.Scene;
import github.snomfish.scene.components.MeshCmp;
import github.snomfish.scene.components.PlayerCmp;
import github.snomfish.scene.components.TransformCmp;

public class RenderSystem {
    

    public void beginFrame() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }


    public void endFrame() {

    }

    
    public void render(Scene scene, Shader shader, float aspect, Integer playerId) {

        PlayerCmp p = scene.getComponent(playerId, PlayerCmp.class);

        int cameraId = p.getCameraId();

        shader.bind();

        for (Integer id : scene.getEntitiesWith(MeshCmp.class)) {

            MeshCmp m = scene.getComponent(id, MeshCmp.class);
            TransformCmp t = scene.getComponent(id, TransformCmp.class);

            if (m == null || t == null) continue;
        }

        shader.unbind();
    }
}
