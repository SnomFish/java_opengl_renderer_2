package github.snomfish.scene.system;

import github.snomfish.scene.Scene;
import github.snomfish.scene.components.CameraCmp;
import github.snomfish.scene.components.TransformCmp;

public class CameraSystem {
    

    public void update(Scene scene, float aspect) {
        
        for (Integer id : scene.getEntitiesWith(CameraCmp.class)) {

            TransformCmp transform = scene.getComponent(id, TransformCmp.class);
        }
    }
}