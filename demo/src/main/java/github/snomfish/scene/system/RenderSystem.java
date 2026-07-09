package github.snomfish.scene.system;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import github.snomfish.graphics.Shader;
import github.snomfish.scene.Scene;
import github.snomfish.scene.components.CameraCmp;
import github.snomfish.scene.components.LightCmp;
import github.snomfish.scene.components.MeshCmp;
import github.snomfish.scene.components.PlayerCmp;
import github.snomfish.scene.components.TransformCmp;

public class RenderSystem {
    

    public void beginFrame() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }


    public void endFrame() {

    }

    
    public void render(Scene scene, Shader shader, float aspect, Integer playerId, Integer lightId) {

        PlayerCmp p = scene.getComponent(playerId, PlayerCmp.class);
        LightCmp l = scene.getComponent(lightId, LightCmp.class);
        TransformCmp lt = scene.getComponent(lightId, TransformCmp.class);
        CameraCmp c = scene.getComponent(playerId, CameraCmp.class);
        
        if (p == null || l == null || c == null) {
            return;
        }

        shader.bind();
        shader.setUniform("view", c.getView());
        shader.setUniform("projection", c.getProjection());

        shader.setUniform("lightColour", l.getColour());
        shader.setUniform("lightPosition", lt.getPosition());

        for (Integer id : scene.getEntitiesWith(MeshCmp.class)) {

            MeshCmp m = scene.getComponent(id, MeshCmp.class);
            TransformCmp t = scene.getComponent(id, TransformCmp.class);

            if (m == null || t == null) continue;

            shader.setUniform("model", t.getModelMatrix());
            shader.setUniform("objectColour", new Vector3f(1.0f, 1.0f, 1.0f));
            m.getMesh().render();
        }

        shader.unbind();
    }
}
