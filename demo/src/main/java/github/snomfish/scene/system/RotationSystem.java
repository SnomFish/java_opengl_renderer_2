package github.snomfish.scene.system;

import java.util.List;

import github.snomfish.scene.Scene;
import github.snomfish.scene.System;
import github.snomfish.scene.components.MeshCmp;
import github.snomfish.scene.components.RotationCmp;
import github.snomfish.scene.components.TransformCmp;

public class RotationSystem extends System {
    

    public void update(Scene scene, float deltaTime) {
        for (Integer id : scene.getEntitiesWith(List.of(
            MeshCmp.class,
            RotationCmp.class,
            TransformCmp.class
        ))) {
            
            TransformCmp t = scene.get(id, TransformCmp.class);
        
            t.rotateY(deltaTime * 30);
            t.rotateX(deltaTime * 30);
        }
    }
}
