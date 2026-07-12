package github.snomfish.graphics.shader;

public class ShadowShader extends Shader {

    public ShadowShader() {
        super(
            "PointShadowVertex.glsl",
            "PointShadowFragment.glsl"
        );

        createUniform("model");
        createUniform("shadowMatrix");
        createUniform("lightPosition");
        createUniform("farPlane");
    } 
}
