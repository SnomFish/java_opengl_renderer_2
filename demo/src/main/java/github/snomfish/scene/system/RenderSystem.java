package github.snomfish.scene.system;

import java.util.List;

import org.lwjgl.opengl.GL11;

import github.snomfish.graphics.Mesh;
import github.snomfish.graphics.assets.Material;
import github.snomfish.graphics.shader.SceneShader;
import github.snomfish.graphics.shadows.DepthCubeMap;
import github.snomfish.scene.Scene;
import github.snomfish.scene.System;
import github.snomfish.scene.components.CameraCmp;
import github.snomfish.scene.components.PointLightCmp;
import github.snomfish.scene.components.PointShadowCmp;
import github.snomfish.scene.components.MaterialCmp;
import github.snomfish.scene.components.MeshCmp;
import github.snomfish.scene.components.PlayerCmp;
import github.snomfish.scene.components.TransformCmp;

public class RenderSystem extends System {


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

    
    public void render(Scene scene, SceneShader shader, float aspect, Integer playerId) {

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
            String arrayString = "[" + i + "]";

            PointLightCmp l = scene.get(id, PointLightCmp.class);
            PointShadowCmp s = scene.get(id, PointShadowCmp.class);
            TransformCmp t = scene.get(id, TransformCmp.class);

            DepthCubeMap depthCubeMap = s.getDepthCubeMap();

            depthCubeMap.bindTexture(i);
            shader.setUniform("lightCount", lightIds.size());
            shader.setUniform("lights" + arrayString + ".position", t.getPosition());
            shader.setUniform("lights" + arrayString + ".colour", l.getColour());
            shader.setUniform("lights" + arrayString + ".intensity", l.getIntensity());
            shader.setUniform("lights" + arrayString + ".farPlane", s.getFarPlane());
            shader.setUniform("shadowMaps" + arrayString, i);
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

        for (int i = 0; i < lightIds.size(); i ++) {
            int id = lightIds.get(i);
            PointShadowCmp s = scene.get(id, PointShadowCmp.class);
            DepthCubeMap depthCubeMap = s.getDepthCubeMap();
            depthCubeMap.unbindTexture(i);
        }

        shader.unbind();
    }
}
