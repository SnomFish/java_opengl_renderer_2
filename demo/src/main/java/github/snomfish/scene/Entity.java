package github.snomfish.scene;

public class Entity {
    

    private static int nextId;
    private final int id;


    public Entity() {
        this.id = nextId++;
    }


    // getter
    public int getId() {
        return id;
    }
}
