package github.snomfish.scene.system;

import java.util.List;

import org.joml.Vector3f;

import github.snomfish.graphics.Shader;
import github.snomfish.scene.Scene;
import github.snomfish.scene.components.CameraCmp;
import github.snomfish.scene.components.LightCmp;
import github.snomfish.scene.components.MeshCmp;
import github.snomfish.scene.components.PlayerCmp;
import github.snomfish.scene.components.TransformCmp;

public class RenderSystem {

    
    public void render(Scene scene, Shader shader, float aspect, Integer playerId, Integer lightId) {

        PlayerCmp p = scene.get(playerId, PlayerCmp.class);
        LightCmp l = scene.get(lightId, LightCmp.class);
        TransformCmp lt = scene.get(lightId, TransformCmp.class);
        CameraCmp c = scene.get(playerId, CameraCmp.class);
        
        if (p == null || l == null || c == null) {
            return;
        }

        shader.bind();
        shader.setUniform("view", c.getView());
        shader.setUniform("projection", c.getProjection());

        shader.setUniform("lightColour", l.getColour());
        shader.setUniform("lightPosition", lt.getPosition());

        for (Integer id : scene.getEntitiesWith(List.of(
            MeshCmp.class,
            TransformCmp.class
        ))) {

            MeshCmp m = scene.get(id, MeshCmp.class);
            TransformCmp t = scene.get(id, TransformCmp.class);

            shader.setUniform("model", t.getModelMatrix());
            shader.setUniform("objectColour", new Vector3f(1.0f, 1.0f, 1.0f));
            m.getMesh().render();
        }

        shader.unbind();
    }
}
