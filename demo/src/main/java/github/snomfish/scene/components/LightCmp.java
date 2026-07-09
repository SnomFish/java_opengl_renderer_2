package github.snomfish.scene.components;

import org.joml.Vector3f;

import github.snomfish.scene.Component;

public class LightCmp implements Component {
    

    private Vector3f colour = new Vector3f();


    public LightCmp(
        float r, float g, float b
    ) {
        colour.set(r, g, b);
    }
    public LightCmp() {
        this(1.0f, 0.8f, 0.8f);
    }


    // getter
    public Vector3f getColour() {
        return colour;
    }


    // setter
    public void setColour(Vector3f colour) {
        this.colour = colour;
    }
}
