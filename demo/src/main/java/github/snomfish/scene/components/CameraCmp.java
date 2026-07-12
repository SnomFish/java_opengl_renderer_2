package github.snomfish.scene.components;

import org.joml.Matrix4f;

import github.snomfish.scene.Component;

public class CameraCmp extends Component {
    

    private final Matrix4f view = new Matrix4f();
    private final Matrix4f projection = new Matrix4f();

    private float fov;
    private float near;
    private float far;

    private boolean updateFlag;


    public CameraCmp(
        float fov, float near, float far
    ) {
        this.fov = fov;
        this.near = near;
        this.far = far;

        this.updateFlag = true;
    }
    public CameraCmp() {
        this(70f, 0.1f, 1000f);
    }


    // getter
    public Matrix4f getView() {
        return view;
    }
    public Matrix4f getProjection() {
        return projection;
    }
    public float getFov() {
        return fov;
    }
    public float getNear() {
        return near;
    }
    public float getFar() {
        return far;
    }
    public boolean getUpdateFlag() {
        return updateFlag;
    }


    // setter
    public void setFov(float fov) {
        this.fov = fov;
        updateFlag = true;
    }
    public void setNear(float near) {
        this.near = near;
        updateFlag = true;
    }
    public void setFar(float far) {
        this.far = far;
        updateFlag = true;
    } 
    public void setUpdateFlag(boolean updateFlag) {
        this.updateFlag = updateFlag;
    }
}
