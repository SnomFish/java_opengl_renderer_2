package github.snomfish.scene.components;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import github.snomfish.scene.Component;

public class TransformCmp extends Component {
    
    
    private final Matrix4f modelMatrix = new Matrix4f(); 
    private Vector3f position = new Vector3f();
    private Vector3f rotation = new Vector3f();
    private Vector3f scale = new Vector3f();


    public TransformCmp(
        float px, float py, float pz, float rx, float ry, float rz, float sx, float sy, float sz
    ) {
        position.set(px, py, pz);
        rotation.set(rx, ry, rz);
        scale.set(sx, sy, sz);
    }
    public TransformCmp() {
        this(0, 0, 0, 0, 0, 0, 1, 1, 1);
    }
    

    // getter
    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }
    public Vector3f getPosition() {
        return position;
    }
    public Vector3f getRotation() {
        return rotation;
    }
    public Vector3f getScale() {
        return scale;
    }


    // setter
    public void setPosition(float x, float y, float z) {
        this.position.set(x, y, z);
        setAllDirty();
    }
    public void setRotation(float x, float y, float z) {
        this.rotation.set(x, y, z);
        setAllDirty();
    }
    public void setScale(float x, float y, float z) {
        this.scale.set(x, y, z);
        setAllDirty();
    }


    public void translate(float dx, float dy, float dz) {
        this.position.add(dx, dy, dz);
        setAllDirty();
    }
    public void translateX(float delta) {translate(delta, 0, 0);}
    public void translateY(float delta) {translate(0, delta, 0);}
    public void translateZ(float delta) {translate(0, 0, delta);}

    public void rotate(float dx, float dy, float dz) {
        this.rotation.add(dx, dy, dz);
        setAllDirty();
    }
    public void rotateX(float delta) {rotate(delta, 0, 0);}
    public void rotateY(float delta) {rotate(0, delta, 0);}
    public void rotateZ(float delta) {rotate(0, 0, delta);}

    public void scale(float dx, float dy, float dz) {
        this.scale.add(dx, dy, dz);
        setAllDirty();
    }


    public void forwardsX(float delta) {
        float sin = (float) Math.sin(Math.toRadians(rotation.y));
        float cos = (float) Math.cos(Math.toRadians(rotation.y));
        translate(
            sin * delta,
            0,
            cos * -delta
        );
        setAllDirty();
    }
    public void strafeX(float delta) {
        float sin = (float) Math.sin(Math.toRadians(rotation.y));
        float cos = (float) Math.cos(Math.toRadians(rotation.y));
        translate(
            cos * delta,
            0,
            sin * delta
        );
        setAllDirty();
    }
}
