package github.snomfish.scene.components;

import org.joml.Vector3f;

import github.snomfish.scene.Component;

public class PointLightCmp implements Component {
    

    private Vector3f colour = new Vector3f();
    private Float intensity;


    public PointLightCmp(
        float r, float g, float b, float i
    ) {
        this.colour.set(r, g, b);
        this.intensity = i;
    }
    public PointLightCmp() {
        this(0.5f, 0.5f, 1.0f, 1.0f);
    }


    // getter
    public Vector3f getColour() {
        return colour;
    }
    public Float getIntensity() {
        return intensity;
    }


    // setter
    public void setColour(Vector3f colour) {
        this.colour = colour;
    }
    public void setIntensity(Float intensity) {
        this.intensity = intensity;
    }
}
