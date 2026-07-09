package github.snomfish.scene.system;

import java.util.List;

import org.joml.Vector3f;

import github.snomfish.scene.Scene;
import github.snomfish.scene.components.CameraCmp;
import github.snomfish.scene.components.TransformCmp;

public class CameraSystem {
    

    public void update(Scene scene, float aspect) {
        
        for (Integer id : scene.getEntitiesWith(List.of(
            CameraCmp.class, 
            TransformCmp.class
        ))) {

            CameraCmp c = scene.get(id, CameraCmp.class);
            TransformCmp t = scene.get(id, TransformCmp.class);
            
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