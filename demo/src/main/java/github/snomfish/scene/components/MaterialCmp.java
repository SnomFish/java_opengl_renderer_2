package github.snomfish.scene.components;

import github.snomfish.graphics.assets.Material;
import github.snomfish.scene.Component;

public class MaterialCmp extends Component {
    

    private Material material;


    public MaterialCmp(Material material) {
        this.material = material;
    }


    // getter
    public Material getMaterial() {
        return material;
    }
}
