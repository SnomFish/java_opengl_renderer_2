package github.snomfish.graphics;

import java.nio.FloatBuffer;
import java.util.List;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL31;

import github.snomfish.scene.Scene;
import github.snomfish.scene.components.LightCmp;
import github.snomfish.scene.components.TransformCmp;

public class LightBuffer {


    private static final int MAX_LIGHTS = 16;

    private int bufferId;


    public LightBuffer() {

        bufferId = GL15.glGenBuffers();

        GL15.glBindBuffer(
            GL31.GL_UNIFORM_BUFFER,
            bufferId
        );

        GL15.glBufferData(
            GL31.GL_UNIFORM_BUFFER,
            16 + MAX_LIGHTS * 48,
            GL15.GL_DYNAMIC_DRAW
        );

        GL15.glBindBuffer(
            GL31.GL_UNIFORM_BUFFER,
            0
        );
    }


    // getter
    public int getBufferId() {
        return bufferId;
    }


    public void update(Scene scene) {
        List<Integer> lightIds = scene.getEntitiesWith(List.of(
            LightCmp.class,
            TransformCmp.class
        ));

        int lightCount = Math.min(lightIds.size(), MAX_LIGHTS);

        FloatBuffer buffer = BufferUtils.createFloatBuffer(4 + (lightCount * 12));

        buffer.put(lightCount);
        buffer.put(0);
        buffer.put(0);
        buffer.put(0);

        // clear if lights are removed?

        for (int i = 0; i < lightCount; i ++) {
            Integer id = lightIds.get(i);

            LightCmp light = scene.get(id, LightCmp.class);
            TransformCmp lightT = scene.get(id, TransformCmp.class);

            Vector3f position = lightT.getPosition();
            Vector3f colour = light.getColour();


            // vec4 position
            buffer.put(position.x);
            buffer.put(position.y);
            buffer.put(position.z);
            buffer.put(0);


            // vec4 colour
            buffer.put(colour.x);
            buffer.put(colour.y);
            buffer.put(colour.z);
            buffer.put(0);


            // float intensity + std140 padding
            buffer.put(light.getIntensity());
            buffer.put(0);
            buffer.put(0);
            buffer.put(0);
        }


        buffer.flip();


        GL15.glBindBuffer(
            GL31.GL_UNIFORM_BUFFER,
            bufferId
        );

        GL15.glBufferSubData(
            GL31.GL_UNIFORM_BUFFER,
            0,
            buffer
        );

        GL15.glBindBuffer(
            GL31.GL_UNIFORM_BUFFER,
            0
        );
    }
}
