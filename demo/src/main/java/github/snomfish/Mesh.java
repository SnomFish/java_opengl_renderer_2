package github.snomfish;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class Mesh {


    public static Mesh square() {
        float[] square = {
            0.5f, 0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            -0.5f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f
        };
        return new Mesh(square);
    }
    

    private float[] vertices;
    private int vbo;
    private int vao;


    public Mesh(
        float[] vertices
    ) {
        this.vertices = vertices;
    }


    public void init() {
        vbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_STATIC_DRAW);

        vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);
        GL30.glVertexAttribPointer(
            0,
            3,
            GL30.GL_FLOAT,
            false,
            3 * Float.BYTES,
            0
        );
        GL30.glEnableVertexAttribArray(0);
    }
}
