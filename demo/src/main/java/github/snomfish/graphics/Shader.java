package github.snomfish.graphics;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryStack;

import github.snomfish.FileManager;

public class Shader {

    private static final int MAX_LIGHTS = 16;
    
    private static final String FOLDER_PATH = "shaders/";

    private final Map<String, Integer> uniforms;
    private int programId;
    private int vertexShaderId;
    private int fragmentShaderId;


    public Shader(
        String vertexPath, String fragmentPath
    ) {
        uniforms = new HashMap<>();
    
        createProgram();
        loadVertexShader(vertexPath);
        loadFragmentShader(fragmentPath);
        linkProgram();

        createUniform("model");
        createUniform("view");
        createUniform("projection");
        createUniform("viewPosition");

        createUniform("lights", MAX_LIGHTS, List.of(
            "position", 
            "colour", 
            "intensity"
        ));

        createUniform("material", List.of(
            "diffuse", 
            "specular", 
            "shininess"
        ));
    }




    // INITIATION
    private void createProgram() {
        programId = GL20.glCreateProgram();

        if (programId == 0) {
            throw new RuntimeException("Failed to create program");
        }
    }


    private void loadVertexShader(String path) {
        String source = FileManager.read(FOLDER_PATH + path);
        vertexShaderId = compileShader(
            source,
            GL20.GL_VERTEX_SHADER
        );
    }


    private void loadFragmentShader(String path) {
        String source = FileManager.read(FOLDER_PATH + path);
        fragmentShaderId = compileShader(
            source,
            GL20.GL_FRAGMENT_SHADER
        );
    }


    public int compileShader(String source, int type) {
        int shaderId = GL20.glCreateShader(type);

        GL20.glShaderSource(
            shaderId,
            source
        );
        
        GL20.glCompileShader(shaderId);

        if (GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            throw new RuntimeException(GL20.glGetShaderInfoLog(shaderId));
        }

        GL20.glAttachShader(
            programId,
            shaderId
        );

        return shaderId;
    }


    private void linkProgram() {
        GL20.glLinkProgram(programId);

        if (GL20.glGetProgrami(programId, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
            throw new RuntimeException(GL20.glGetShaderInfoLog(programId));
        }
    }




    // BIND
    public void bind() {
        GL20.glUseProgram(programId);
    }


    public void unbind() {
        GL20.glUseProgram(0);
    }




    // UNIFORMS
    public void createUniform(String name) {

        int location = GL20.glGetUniformLocation(programId, name);

        if (location == -1) {
            // check there isnt a typo, or the uniform may have been optimized out of the shader
            System.out.println("uniform location not found for " + name);
            return;
        }

        uniforms.put(name, location);
    }
    public void createUniform(String name, List<String> attributes) {
        for (String attribute : attributes) {
            createUniform(name + "." + attribute);
        }
    }
    public void createUniform(String name, int arrayMax, List<String> attributes) {
        for (int i = 0; i < arrayMax; i ++) {
            for (String attribute : attributes) {
                createUniform(name + "[" + i + "]." + attribute);
            }
        }
    }


    public void setUniform(String name, int value) {
        Integer location = uniforms.get(name);
        if (location == null) return;

        GL20.glUniform1i(location, value);
    }
    public void setUniform(String name, float value) {
        Integer location = uniforms.get(name);
        if (location == null) return;

        GL20.glUniform1f(location, value);
    }
    public void setUniform(String name, Vector3f value) {
        Integer location = uniforms.get(name);
        if (location == null) return;

        GL20.glUniform3f(location, value.x, value.y, value.z);
    }
    public void setUniform(String name, Matrix4f matrix) {
        Integer location = uniforms.get(name);
        if (location == null) return;

        try (MemoryStack stack = MemoryStack.stackPush()) {

            FloatBuffer buffer = stack.mallocFloat(16);
            matrix.get(buffer);

            GL20.glUniformMatrix4fv(location, false, buffer);
        }
    }
    public void setLightUniform(int i, Vector3f position, Vector3f colour, float intensity) {
        setUniform("lights[" + i + "].position", position);
        setUniform("lights[" + i + "].colour", colour);
        setUniform("lights[" + i + "].intensity", intensity);
    }



    // cleanup
    public void cleanup() {
        unbind();

        if (programId != 0) {
            GL20.glDeleteShader(vertexShaderId);
            GL20.glDeleteShader(fragmentShaderId);
            GL20.glDeleteProgram(programId);
        }
    }
}
