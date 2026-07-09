package github.snomfish.scene.components;

import github.snomfish.graphics.Material;
import github.snomfish.scene.Component;

public class MaterialCmp implements Component {
    

    private Material material;


    public MaterialCmp(Material material) {
        this.material = material;
    }


    // getter
    public Material getMaterial() {
        return material;
    }
}
