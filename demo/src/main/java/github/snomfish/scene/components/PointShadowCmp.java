package github.snomfish.scene.components;

import org.joml.Matrix4f;

import github.snomfish.graphics.shadows.DepthCubeMap;
import github.snomfish.scene.Component;

public class PointShadowCmp extends Component {
    

    private DepthCubeMap depthCubeMap;
    private Matrix4f[] shadowMatrices = new Matrix4f[6];
    private float nearPlane;
    private float farPlane;


    public PointShadowCmp(
        int size, float nearPlane, float farPlane
    ) {
        depthCubeMap = new DepthCubeMap(size);
        depthCubeMap.init(); // this is bad as it could cause a crash, maybe some sort of cmp init method is needed? add the init into a system if it detects the cmp hasnt been activated
        
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;
    }
    public PointShadowCmp() {
        this(1024, 0.1f, 100.0f);
    }


    // getter
    public DepthCubeMap getDepthCubeMap() {
        return depthCubeMap;
    }
    public Matrix4f[] getShadowMatrices() {
        return shadowMatrices;
    }
    public float getNearPlane() {
        return nearPlane;
    }
    public float getFarPlane() {
        return farPlane;
    }


    // setter
    public void setNearPlane(float nearPlane) {
        this.nearPlane = nearPlane;
        setAllDirty();
    }
    public void setFarPlane(float farPlane) {
        this.farPlane = farPlane;
        setAllDirty();
    }
}
