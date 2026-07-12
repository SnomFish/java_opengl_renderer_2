package github.snomfish.graphics.shadows;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.GL_TEXTURE_COMPARE_MODE;
import static org.lwjgl.opengl.GL30.*;

import java.nio.ByteBuffer;

import org.joml.Matrix4f;

public class DepthCubeMap {


    private final int size;

    private int fbo;
    private int cubeMap;


    public DepthCubeMap(int size) {
        this.size = size;
    }


    // getter
    public int getSize() {
        return size;
    }
    public int getTextureId() {
        return cubeMap;
    }
    public int getFramebufferId() {
        return fbo;
    }


    public void init() {
        createDepthCubeTexture();
        createFramebuffer();
        //createShadowMatrices();
    }


    private void createDepthCubeTexture() {

        cubeMap = glGenTextures();

        glBindTexture(GL_TEXTURE_CUBE_MAP, cubeMap);

        for (int i = 0; i < 6; i++) {

            glTexImage2D(
                GL_TEXTURE_CUBE_MAP_POSITIVE_X + i,
                0,
                GL_DEPTH_COMPONENT32F,
                size,
                size,
                0,
                GL_DEPTH_COMPONENT,
                GL_FLOAT,
                (ByteBuffer) null
            );
        }


        glTexParameteri(
                GL_TEXTURE_CUBE_MAP,
                GL_TEXTURE_MIN_FILTER,
                GL_NEAREST
        );

        glTexParameteri(
                GL_TEXTURE_CUBE_MAP,
                GL_TEXTURE_MAG_FILTER,
                GL_NEAREST
        );


        glTexParameteri(
                GL_TEXTURE_CUBE_MAP,
                GL_TEXTURE_WRAP_S,
                GL_CLAMP_TO_EDGE
        );

        glTexParameteri(
                GL_TEXTURE_CUBE_MAP,
                GL_TEXTURE_WRAP_T,
                GL_CLAMP_TO_EDGE
        );

        glTexParameteri(
                GL_TEXTURE_CUBE_MAP,
                GL_TEXTURE_WRAP_R,
                GL_CLAMP_TO_EDGE
        );

        // We manually compare depth in the shader
        glTexParameteri(
                GL_TEXTURE_CUBE_MAP,
                GL_TEXTURE_COMPARE_MODE,
                GL_NONE
        );

        glBindTexture(GL_TEXTURE_CUBE_MAP, 0);
    }



    private void createFramebuffer() {
        fbo = glGenFramebuffers();

        glBindFramebuffer(
            GL_FRAMEBUFFER,
            fbo
        );

        glDrawBuffer(GL_NONE);
        glReadBuffer(GL_NONE);

        glBindFramebuffer(
            GL_FRAMEBUFFER,
            0
        );
    }


    /**
     * Attach one face before rendering the shadow pass.
     * face:
     * 0 = +X
     * 1 = -X
     * 2 = +Y
     * 3 = -Y
     * 4 = +Z
     * 5 = -Z
     */
    public void attachFace(int face) {
        glBindFramebuffer(
            GL_FRAMEBUFFER,
            fbo
        );

        glFramebufferTexture2D(
            GL_FRAMEBUFFER,
            GL_DEPTH_ATTACHMENT,
            GL_TEXTURE_CUBE_MAP_POSITIVE_X + face,
            cubeMap,
            0
        );

        if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
            throw new RuntimeException("Depth cube framebuffer incomplete");
        }
    }
    public void detachFace(int face) {
        glFramebufferTexture2D(
            GL_FRAMEBUFFER,
            GL_DEPTH_ATTACHMENT,
            GL_TEXTURE_CUBE_MAP_POSITIVE_X + face,
            0,
            0
        );
    }



    public void bindForWriting() {
        glBindFramebuffer(GL_FRAMEBUFFER, fbo);
        glDrawBuffer(GL_NONE);
        glReadBuffer(GL_NONE);
        glViewport(0, 0, size, size);
    }
    public void unbindForWriting(int screenWidth, int screenHeight) {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        glViewport(0, 0, screenWidth, screenHeight);
    }


    public void bindTexture(int unit) {
        glActiveTexture(GL_TEXTURE0 + unit);
        glBindTexture(GL_TEXTURE_CUBE_MAP, cubeMap);
    }
    public void unbindTexture(int unit) {
        glActiveTexture(GL_TEXTURE0 + unit);
        glBindTexture(GL_TEXTURE_CUBE_MAP, 0);
    }


    


    public void cleanup() {

        glDeleteFramebuffers(fbo);
        glDeleteTextures(cubeMap);
    }
}