package github.snomfish.graphics;

import org.joml.Vector3f;

public class Material implements Asset {

    private final String name;

    // diffuse and specular both store rgb values, diffuse uses the objects colour too
    private Vector3f diffuse = new Vector3f();
    private Vector3f specular = new Vector3f(); // highlights
    private float shininess;


    public Material(
        String name, float dx, float dy, float dz, float sx, float sy, float sz, float shininess
    ) {
        this.name = name;
        diffuse.set(dx, dy, dz);
        specular.set(sx, sy, sz);
        this.shininess = shininess;
    }


    // getter
    public String getName() {
        return name;
    }
    public Vector3f getDiffuse() {
        return diffuse;
    }
    public Vector3f getSpecular() {
        return specular;
    }
    public float getShininess() {
        return shininess;
    }


    // setter
    public void setDiffuse(float dx, float dy, float dz) {
        this.diffuse.set(dx, dy, dz);
    }
    public void setSpecular(float sx, float sy, float sz) {
        this.specular.set(sx, sy, sz);
    }
    public void setShininess(float shininess) {
        this.shininess = shininess;
    }
}
