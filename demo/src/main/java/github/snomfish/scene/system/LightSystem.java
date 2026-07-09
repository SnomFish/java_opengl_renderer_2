package github.snomfish.scene.system;

import github.snomfish.scene.Scene;
import github.snomfish.scene.components.LightCmp;

public class LightSystem {
    

    public void update(Scene scene, float deltaTime) {

        for (Integer id : scene.getEntitiesWith(LightCmp.class)) {

            LightCmp l = scene.get(id, LightCmp.class);

            if (l == null) continue;

            float r = l.getColour().x;
            float g = l.getColour().y;
            float b = l.getColour().z;

            l.getColour().set(r, g, b);
        }
    }
}
