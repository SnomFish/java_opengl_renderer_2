package github.snomfish.graphics.shader;

import java.util.List;

public class SceneShader extends Shader{


    private static final int MAX_LIGHTS = 16;


    public SceneShader() {
        super(
            "scene/Vertex.glsl",
            "scene/Fragment.glsl"
        );

        createUniform("model");
        createUniform("view");
        createUniform("projection");
        createUniform("viewPosition");

        createUniform("lightCount");
        createUniform(
            "lights",
            MAX_LIGHTS,
            List.of(
                "position",
                "colour",
                "intensity",
                "farPlane"
            )
        );

        createUniform("shadowMaps", MAX_LIGHTS);

        createUniform(
            "material",
            List.of(
                "diffuse",
                "specular",
                "shininess"
            )
        );
    }
}
