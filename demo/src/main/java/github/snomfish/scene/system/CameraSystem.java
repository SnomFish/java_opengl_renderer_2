package github.snomfish.scene.system;

import org.joml.Vector3f;

import github.snomfish.scene.Scene;
import github.snomfish.scene.components.CameraCmp;
import github.snomfish.scene.components.TransformCmp;

public class CameraSystem {
    

    public void update(Scene scene, float aspect) {
        
        for (Integer id : scene.getEntitiesWith(CameraCmp.class)) {

            CameraCmp c = scene.getComponent(id, CameraCmp.class);
            TransformCmp t = scene.getComponent(id, TransformCmp.class);

            if (t == null) continue;
            //if (c.getUpdateFlag()) continue;
            
            Vector3f pos = t.getPosition();
            Vector3f rot = t.getRotation();

            // view
            c.getView().identity()
                .rotateXYZ(
                    (float) Math.toRadians(rot.x),    
                    (float) Math.toRadians(rot.y),
                    (float) Math.toRadians(rot.z)
                )
                .translate(-pos.x, -pos.y, -pos.z);


            // PROJECTION
            c.getProjection().identity()
                .perspective(
                    (float)Math.toRadians(c.getFov()),
                    aspect,
                    c.getNear(),
                    c.getFar()
                );

            c.setUpdateFlag(false);
        }
    }
}