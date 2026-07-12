package github.snomfish.scene.system;

import java.util.List;
import java.util.Set;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import github.snomfish.graphics.shader.ShadowShader;
import github.snomfish.graphics.shadows.DepthCubeMap;
import github.snomfish.scene.Scene;
import github.snomfish.scene.System;
import github.snomfish.scene.components.MeshCmp;
import github.snomfish.scene.components.PointLightCmp;
import github.snomfish.scene.components.PointShadowCmp;
import github.snomfish.scene.components.ShadowCasterCmp;
import github.snomfish.scene.components.TransformCmp;

public class PointShadowSystem extends System {


    private static final float RIGHT_ANGLE = (float)Math.toRadians(90.0f);
    

    public void update(Scene scene) {
        
        for (Integer id : scene.getEntitiesWith(List.of(
            PointLightCmp.class,
            PointShadowCmp.class,
            TransformCmp.class
        ))) {
            
            PointShadowCmp shadow = scene.get(id, PointShadowCmp.class);
            TransformCmp transform = scene.get(id, TransformCmp.class);

            if (shadow.isClean(PointShadowSystem.class) || 
                transform.isClean(PointShadowSystem.class)) continue;

            float nearPlane = shadow.getNearPlane();
            float farPlane = shadow.getFarPlane();
            Vector3f position = transform.getPosition();

            Matrix4f[] shadowMatrices = shadow.getShadowMatrices();
            Matrix4f projection = new Matrix4f().perspective(RIGHT_ANGLE, 1.0f, nearPlane, farPlane);
            
            shadowMatrices[0] = new Matrix4f(projection).lookAt(
                position,
                new Vector3f(position).add(1,0,0),
                new Vector3f(0,-1,0)
            );

            shadowMatrices[1] = new Matrix4f(projection).lookAt(
                position,
                new Vector3f(position).add(-1,0,0),
                new Vector3f(0,-1,0)
            );

            shadowMatrices[2] = new Matrix4f(projection).lookAt(
                position,
                new Vector3f(position).add(0,1,0),
                new Vector3f(0,0,1)
            );

            shadowMatrices[3] = new Matrix4f(projection).lookAt(
                position,
                new Vector3f(position).add(0,-1,0),
                new Vector3f(0,0,-1)
            );

            shadowMatrices[4] = new Matrix4f(projection).lookAt(
                position,
                new Vector3f(position).add(0,0,1),
                new Vector3f(0,-1,0)
            );

            shadowMatrices[5] = new Matrix4f(projection).lookAt(
                position,
                new Vector3f(position).add(0,0,-1),
                new Vector3f(0,-1,0)
            );
        }
    }


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
