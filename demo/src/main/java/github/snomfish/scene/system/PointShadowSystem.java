package github.snomfish.scene.system;

import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import github.snomfish.scene.Scene;
import github.snomfish.scene.System;
import github.snomfish.scene.components.PointLightCmp;
import github.snomfish.scene.components.PointShadowCmp;
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
}
