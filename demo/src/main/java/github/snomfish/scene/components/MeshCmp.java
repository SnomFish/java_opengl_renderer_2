package github.snomfish.scene.components;

import github.snomfish.graphics.Mesh;
import github.snomfish.scene.Component;

public class MeshCmp implements Component {
    

    private Mesh mesh;


    public MeshCmp(Mesh mesh) {
        this.mesh = mesh;
    }


    public Mesh getMesh() {
        return mesh;
    }
}
