package github.snomfish.scene.system;

import org.joml.Vector3f;

import github.snomfish.scene.Scene;
import github.snomfish.scene.components.TransformCmp;

public class TransformSystem {
    

    public void update(Scene scene) {

        for (Integer id : scene.getEntitiesWith(TransformCmp.class)) {

            TransformCmp t = scene.get(id, TransformCmp.class);

            // prevents meshes that havent moved from wasting time recalcing the same model matrix
            if (!t.getUpdateFlag()) continue;

            Vector3f position = t.getPosition();
            Vector3f rotation = t.getRotation();
            Vector3f scale = t.getScale();

            t.getModelMatrix().identity()
                .translate(position)
                .rotateXYZ(
                    (float)Math.toRadians(rotation.x),
                    (float)Math.toRadians(rotation.y),
                    (float)Math.toRadians(rotation.z)
                )
                .scale(scale);

            t.setUpdateFlag(false);
        }
    }
}
