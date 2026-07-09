package github.snomfish.graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Mesh {


    private int vao;
    private int vbo;
    private int ebo;
    private int vertexCount;


    public Mesh(
        float[] vertices,
        int[] indices
    ) {
        this.vertexCount = indices.length;

        createVAO();
        createVBO(vertices);
        createEBO(indices);
        configureVertexAttributes();
        unbind();
    }


    private void createVAO() {
        vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);
    }
    private void createVBO(
        float[] vertices
    ) {
        vbo = GL15.glGenBuffers();

        GL15.glBindBuffer(
            GL15.GL_ARRAY_BUFFER, 
            vbo
        );

        GL15.glBufferData(
            GL15.GL_ARRAY_BUFFER, 
            vertices, 
            GL15.GL_STATIC_DRAW
        );
    }
    private void createEBO(
        int[] indices
    ) {
        ebo = GL15.glGenBuffers();

        GL15.glBindBuffer(
            GL15.GL_ELEMENT_ARRAY_BUFFER, 
            ebo
        );

        GL15.glBufferData(
            GL15.GL_ELEMENT_ARRAY_BUFFER, 
            indices, 
            GL15.GL_STATIC_DRAW
        );
    }


    private void configureVertexAttributes() {
        GL20.glVertexAttribPointer(
            0,
            3,
            GL11.GL_FLOAT,
            false,
            6 * Float.BYTES,
            0
        );

        GL20.glEnableVertexAttribArray(0);

        GL20.glVertexAttribPointer(
            1,
            3,
            GL11.GL_FLOAT,
            false,
            6 * Float.BYTES,
            3 * Float.BYTES
        );

        GL20.glEnableVertexAttribArray(1);
    }


    private void unbind() {
        GL30.glBindVertexArray(0);
    }


    public void render() {

        GL30.glBindVertexArray(vao);
        GL11.glDrawElements(
            GL11.GL_TRIANGLES,
            vertexCount,
            GL11.GL_UNSIGNED_INT,
            0
        );

        GL30.glBindVertexArray(0);
    }


    public void cleanup() {
        GL20.glDisableVertexAttribArray(0);
        GL15.glDeleteBuffers(vbo);
        GL15.glDeleteBuffers(ebo);
        GL30.glDeleteVertexArrays(vao);
    }
}
