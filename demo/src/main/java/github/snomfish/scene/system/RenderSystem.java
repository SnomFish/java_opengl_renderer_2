package github.snomfish.scene.system;

import java.util.List;

import org.joml.Vector3f;

import github.snomfish.graphics.Material;
import github.snomfish.graphics.Mesh;
import github.snomfish.graphics.Shader;
import github.snomfish.scene.Scene;
import github.snomfish.scene.components.CameraCmp;
import github.snomfish.scene.components.LightCmp;
import github.snomfish.scene.components.MaterialCmp;
import github.snomfish.scene.components.MeshCmp;
import github.snomfish.scene.components.PlayerCmp;
import github.snomfish.scene.components.TransformCmp;

public class RenderSystem {

    
    public void render(Scene scene, Shader shader, float aspect, Integer playerId, Integer lightId) {

        PlayerCmp p = scene.get(playerId, PlayerCmp.class);
        LightCmp l = scene.get(lightId, LightCmp.class);
        TransformCmp lt = scene.get(lightId, TransformCmp.class);
        CameraCmp c = scene.get(playerId, CameraCmp.class);
        TransformCmp ct = scene.get(playerId, TransformCmp.class);
        
        if (p == null || l == null || lt == null || c == null || ct == null) {
            return;
        }

        shader.bind();
        shader.setUniform("view", c.getView());
        shader.setUniform("projection", c.getProjection());

        shader.setUniform("lightColour", l.getColour());
        shader.setUniform("lightPosition", lt.getPosition());
        shader.setUniform("lightIntensity", l.getIntensity());
        shader.setUniform("viewPosition", ct.getPosition());

        for (Integer id : scene.getEntitiesWith(List.of(
            MaterialCmp.class,
            MeshCmp.class,
            TransformCmp.class
        ))) {

            Material material = scene.get(id, MaterialCmp.class).getMaterial();
            Mesh mesh = scene.get(id, MeshCmp.class).getMesh();
            TransformCmp t = scene.get(id, TransformCmp.class);

            shader.setUniform("model", t.getModelMatrix());
            shader.setUniform("materialDiffuse", material.getDiffuse());
            shader.setUniform("materialSpecular", material.getSpecular());
            shader.setUniform("materialShininess", material.getShininess());
            mesh.render();
        }

        shader.unbind();
    }
}
