package github.snomfish;

public class Shader {
    

    private final String vertexPath;
    private final String fragmentPath;


    public Shader(
        String vertexPath, String fragmentPath
    ) {
        this.vertexPath = vertexPath;
        this.fragmentPath = fragmentPath;
    }
}
