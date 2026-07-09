package github.snomfish;

import github.snomfish.graphics.Mesh;

public class MeshBuilder {
    

    public static final Mesh square() {
        float[] vertices = {
            0.5f,   0.5f,   0.0f,       0,0,1,          1,1,
            -0.5f,  0.5f,   0.0f,       0,0,1,          0,1,
            -0.5f,  -0.5f,  0.0f,       0,0,1,          0,0,
            0.5f,   -0.5f,  0.0f,       0,0,1,          1,0
        };
        int[] indices = {
            0, 1, 2,
            1, 2, 3,
            2, 3, 0,
            3, 0, 1
        };
        return new Mesh(vertices, indices);
    }


    public static Mesh cube() {
        float[] vertices = {
            // Front (+Z)
            -0.5f, -0.5f,  0.5f,   0.0f,  0.0f,  -1.0f,   1f, 1f,
            0.5f, -0.5f,  0.5f,   0.0f,  0.0f,  -1.0f,   1f, 0f,
            0.5f,  0.5f,  0.5f,   0.0f,  0.0f,  -1.0f,   1f, 1f,
            -0.5f,  0.5f,  0.5f,   0.0f,  0.0f,  -1.0f,   0f, 1f,

            // Back (-Z)
            0.5f, -0.5f, -0.5f,   0.0f,  0.0f, -1.0f,   0f, 0f,
            -0.5f, -0.5f, -0.5f,   0.0f,  0.0f, -1.0f,   1f, 0f,
            -0.5f,  0.5f, -0.5f,   0.0f,  0.0f, -1.0f,   1f, 1f,
            0.5f,  0.5f, -0.5f,   0.0f,  0.0f, -1.0f,   0f, 1f,

            // Left (-X)
            -0.5f, -0.5f, -0.5f,  -1.0f,  0.0f,  0.0f,   0f, 0f,
            -0.5f, -0.5f,  0.5f,  -1.0f,  0.0f,  0.0f,   1f, 0f,
            -0.5f,  0.5f,  0.5f,  -1.0f,  0.0f,  0.0f,   1f, 1f,
            -0.5f,  0.5f, -0.5f,  -1.0f,  0.0f,  0.0f,   0f, 1f,

            // Right (+X)
            0.5f, -0.5f,  0.5f,   1.0f,  0.0f,  0.0f,   0f, 0f,
            0.5f, -0.5f, -0.5f,   1.0f,  0.0f,  0.0f,   1f, 0f,
            0.5f,  0.5f, -0.5f,   1.0f,  0.0f,  0.0f,   1f, 1f,
            0.5f,  0.5f,  0.5f,   1.0f,  0.0f,  0.0f,   0f, 1f,

            // Top (+Y)
            -0.5f,  0.5f,  0.5f,   0.0f,  1.0f,  0.0f,   0f, 0f,
            0.5f,  0.5f,  0.5f,   0.0f,  1.0f,  0.0f,   1f, 0f,
            0.5f,  0.5f, -0.5f,   0.0f,  1.0f,  0.0f,   1f, 1f,
            -0.5f,  0.5f, -0.5f,   0.0f,  1.0f,  0.0f,   0f, 1f,

            // Bottom (-Y)
            -0.5f, -0.5f, -0.5f,   0.0f, -1.0f,  0.0f,   0f, 0f,
            0.5f, -0.5f, -0.5f,   0.0f, -1.0f,  0.0f,   1f, 0f,
            0.5f, -0.5f,  0.5f,   0.0f, -1.0f,  0.0f,   1f, 1f,
            -0.5f, -0.5f,  0.5f,   0.0f, -1.0f,  0.0f,   0f, 1f
        };
        int[] indices = {
            0, 1, 2,
            0, 2, 3,
            4, 5, 6,
            4, 6, 7,
            8, 9,10,
            8,10,11,
            12,13,14,
            12,14,15,
            16,17,18,
            16,18,19,
            20,21,22,
            20,22,23
        };
        return new Mesh(vertices, indices);
    }
}
