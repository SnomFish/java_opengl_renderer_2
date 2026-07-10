package github.snomfish.scene;

public class SceneBuilder {
    

    private Scene scene;
    private Integer id;
    
    
    public SceneBuilder(Scene scene) {
        this.scene = scene;
    }


    public SceneBuilder newEntity() {
        this.id = scene.newEntity();
        return this;
    }


    public SceneBuilder add(Component cmp) {
        scene.add(id, cmp);
        return this;
    }


    public Integer returnId() {
        return id;
    }
}
