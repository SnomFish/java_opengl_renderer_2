package github.snomfish.scene.system;

import github.snomfish.scene.Scene;
import github.snomfish.scene.components.PointLightCmp;

public class PointLightSystem {
    

    public void update(Scene scene, float deltaTime) {

        for (Integer id : scene.getEntitiesWith(PointLightCmp.class)) {

            PointLightCmp l = scene.get(id, PointLightCmp.class);

            if (l == null) continue;

            float r = l.getColour().x;
            float g = l.getColour().y;
            float b = l.getColour().z;

            l.getColour().set(r, g, b);
        }
    }
}
