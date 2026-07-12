package github.snomfish.graphics.shader;

public class ShadowShader extends Shader {

    public ShadowShader() {
        super(
            "pointShadow/Vertex.glsl",
            "pointShadow/Fragment.glsl"
        );

        createUniform("model");
        createUniform("shadowMatrix");
        createUniform("lightPosition");
        createUniform("farPlane");
    } 
}
