package github.snomfish.scene.system;

import java.util.List;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import github.snomfish.graphics.shader.ShadowShader;
import github.snomfish.graphics.shadows.DepthCubeMap;
import github.snomfish.scene.Scene;
import github.snomfish.scene.System;
import github.snomfish.scene.components.MeshCmp;
import github.snomfish.scene.components.PointShadowCmp;
import github.snomfish.scene.components.ShadowCasterCmp;
import github.snomfish.scene.components.TransformCmp;

public class RenderShadowsSystem extends System {
    

    public void render(Scene scene, ShadowShader shader, int screenWidth, int screenHeight) {

        List<Integer> meshIds = scene.getEntitiesWith(List.of(
            MeshCmp.class,
            ShadowCasterCmp.class,
            TransformCmp.class
        ));

        for (Integer id : scene.getEntitiesWith(List.of(
            PointShadowCmp.class,
            TransformCmp.class
        ))) {
            
            PointShadowCmp shadow = scene.get(id, PointShadowCmp.class);
            TransformCmp transform = scene.get(id, TransformCmp.class);

            Matrix4f[] shadowMatrices = shadow.getShadowMatrices();
            DepthCubeMap depthCubeMap = shadow.getDepthCubeMap();

            depthCubeMap.bindForWriting();

            shader.bind();
            shader.setUniform("lightPosition", transform.getPosition());
            shader.setUniform("farPlane", shadow.getFarPlane());

            for (int i = 0; i < 6; i ++) {

                depthCubeMap.attachFace(i);
                GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);

                shader.setUniform("shadowMatrix", shadowMatrices[i]);

                for (Integer meshId : meshIds) {
                    MeshCmp mesh = scene.get(meshId, MeshCmp.class);
                    TransformCmp MeshTransform = scene.get(meshId, TransformCmp.class);
                    shader.setUniform("model", MeshTransform.getModelMatrix());
                    mesh.getMesh().render();
                }
                depthCubeMap.detachFace(i);
            }

            depthCubeMap.unbindForWriting(screenWidth, screenHeight);
            shader.unbind();
        }
    }
}
