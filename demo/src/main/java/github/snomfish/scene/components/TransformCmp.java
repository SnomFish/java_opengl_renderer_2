package github.snomfish.scene.components;

import org.joml.Vector3f;

import github.snomfish.scene.Component;

public class TransformCmp implements Component {
    
    
    private Vector3f position;
    private Vector3f rotation;
    private Vector3f scale;

    private boolean dirtyFlag;


    public TransformCmp(
        float px, float py, float pz, float rx, float ry, float rz, float sx, float sy, float sz
    ) {
        position.set(px, py, pz);
        rotation.set(rx, ry, rz);
        scale.set(sx, sy, sz);
        dirtyFlag = true;
    }
    public TransformCmp() {
        this(0, 0, 0, 0, 0, 0, 1, 1, 1);
    }
    

    // getter
    public Vector3f getPosition() {
        return position;
    }
    public Vector3f getRotation() {
        return rotation;
    }
    public Vector3f getScale() {
        return scale;
    }
    public boolean getDirtyFlag() {
        return dirtyFlag;
    }


    // setter
    public void setPosition(float x, float y, float z) {
        this.position.set(x, y, z);
        dirtyFlag = true;
    }
    public void setRotation(float x, float y, float z) {
        this.rotation.set(x, y, z);
        dirtyFlag = true;
    }
    public void setScale(float x, float y, float z) {
        this.scale.set(x, y, z);
        dirtyFlag = true;
    }
    public void setDirtyFlag(boolean dirtyFlag) {
        this.dirtyFlag = dirtyFlag;
    }


    public void translate(float dx, float dy, float dz) {
        this.position.add(dx, dy, dz);
        dirtyFlag = false;
    }
    public void rotate(float dx, float dy, float dz) {
        this.rotation.add(dx, dy, dz);
        dirtyFlag = false;
    }
    public void scale(float dx, float dy, float dz) {
        this.scale.add(dx, dy, dz);
        dirtyFlag = false;
    }
}
