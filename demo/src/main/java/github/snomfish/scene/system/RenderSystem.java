package github.snomfish.scene.system;

import java.util.List;

import github.snomfish.graphics.Material;
import github.snomfish.graphics.Mesh;
import github.snomfish.graphics.Shader;
import github.snomfish.scene.Scene;
import github.snomfish.scene.components.CameraCmp;
import github.snomfish.scene.components.PointLightCmp;
import github.snomfish.scene.components.MaterialCmp;
import github.snomfish.scene.components.MeshCmp;
import github.snomfish.scene.components.PlayerCmp;
import github.snomfish.scene.components.TransformCmp;

public class RenderSystem {

    
    public void render(Scene scene, Shader shader, float aspect, Integer playerId) {

        PlayerCmp p = scene.get(playerId, PlayerCmp.class);
        CameraCmp c = scene.get(playerId, CameraCmp.class);
        TransformCmp ct = scene.get(playerId, TransformCmp.class);
        
        if (p == null || c == null || ct == null) {
            return;
        }

        shader.bind();
        shader.setUniform("view", c.getView());
        shader.setUniform("projection", c.getProjection());
        shader.setUniform("viewPosition", ct.getPosition());


        List<Integer> lightIds = scene.getEntitiesWith(List.of(
            PointLightCmp.class,
            TransformCmp.class
        ));
        for (int i = 0; i < lightIds.size(); i ++) {
            int id = lightIds.get(i);
            String name = "lights[" + i + "].";

            PointLightCmp l = scene.get(id, PointLightCmp.class);
            TransformCmp t = scene.get(id, TransformCmp.class);

            shader.setUniform(name + "position", t.getPosition());
            shader.setUniform(name + "colour", l.getColour());
            shader.setUniform(name + "intensity", l.getIntensity());
        }


        for (Integer id : scene.getEntitiesWith(List.of(
            MaterialCmp.class,
            MeshCmp.class,
            TransformCmp.class
        ))) {

            Material material = scene.get(id, MaterialCmp.class).getMaterial();
            Mesh mesh = scene.get(id, MeshCmp.class).getMesh();
            TransformCmp t = scene.get(id, TransformCmp.class);

            shader.setUniform("model", t.getModelMatrix());
            shader.setUniform("material.diffuse", material.getDiffuse());
            shader.setUniform("material.specular", material.getSpecular());
            shader.setUniform("material.shininess", material.getShininess());
            mesh.render();
        }

        shader.unbind();
    }
}
