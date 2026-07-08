package github.snomfish.scene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import github.snomfish.scene.components.CameraCmp;
import github.snomfish.scene.components.MeshCmp;
import github.snomfish.scene.components.TransformCmp;

public class Scene {
    

    private final List<Entity> entities;
    private final Map<Class<?>, Map<Integer, Component>> components;


    public Scene() {
        entities = new ArrayList<>();
        components = new HashMap<>();

        newComponent(CameraCmp.class);
        newComponent(MeshCmp.class);
        newComponent(TransformCmp.class);
    }


    public Entity newEntity() {
        Entity e = new Entity();
        entities.add(e);
        return e;
    }




    // components
    public void newComponent(Class<? extends Component> clazz) {
        components.put(clazz, new HashMap<>());
    }


    public void addComponent(Integer id, Component component) {
        Class<? extends Component> clazz = component.getClass();

        if (!components.containsKey(clazz)) {
            throw new RuntimeException("failed to add component to " + clazz.getName() + ", not added to components map");
        }

        components.get(clazz).put(id, component);
    }


    public void removeComponent(Integer id, Class<? extends Component> clazz) {
        
        if (!components.containsKey(clazz)) {
            throw new RuntimeException("failed to remove component from " + clazz.getName() + ", not added to components map");
        }

        components.get(clazz).remove(id);
    }


    @SuppressWarnings("unchecked")
    public <T extends Component> T getComponent(Integer id, Class<T> clazz) {

        if (components.containsKey(clazz)) {
            throw new RuntimeException("failed to get component " + clazz.getName());
        }

        return (T) components.get(clazz).get(id);
    }


    public Set<Integer> getEntitiesWith(Class<? extends Component> clazz) {

        if (components.containsKey(clazz)) {
            throw new RuntimeException("failed to get component " + clazz.getName());
        }

        return components.get(clazz).keySet();
    }
}
