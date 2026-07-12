package github.snomfish.scene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Scene {
    

    private static int nextId;
    private final List<Integer> entities;
    private final Map<Class<? extends Component>, Map<Integer, Component>> components;


    public Scene() {
        entities = new ArrayList<>();
        components = new HashMap<>();
    }


    public Integer newEntity() {
        Integer id = nextId++;
        entities.add(id);
        return id;
    }


    @SuppressWarnings("unchecked")
    public <T extends Component> T get(Integer id, Class<T> clazz) {
        if (!components.containsKey(clazz)) {
            throw new RuntimeException("unable to get, class " + clazz.getSimpleName() + " not found in scene");
        }
        return (T) components.get(clazz).get(id);
    }


    public <T extends Component> void add(Integer id, T component) {
        Class<? extends Component> clazz = component.getClass();
        if (!components.containsKey(clazz)) {
            components.put(clazz, new HashMap<>());
        }
        components.get(clazz).put(id, component);
    }


    public <T extends Component> void remove(Integer id, Class<T> clazz) {
        if (!components.containsKey(clazz)) {
            throw new RuntimeException("unable to remove, class " + clazz.getSimpleName() + " not found in scene");
        }
        components.get(clazz).remove(id);
    }


    

    public <T extends Component> Set<Integer> getEntitiesWith(Class<T> clazz) {
        if (!components.containsKey(clazz)) {
            return new HashSet<>();
        }
        return components.get(clazz).keySet(); 
    }


    private List<Integer> getEntitiesWith(List<Integer> candidates, List<Class<? extends Component>> clazzes) {
        if (clazzes.isEmpty()) {
            return candidates;
        }

        Class<? extends Component> clazz = clazzes.get(0);
        if (!components.containsKey(clazz)) {
            return new ArrayList<>();
        } 

        candidates.removeIf(id -> !components.get(clazz).containsKey(id));

        return getEntitiesWith(candidates, clazzes.subList(1, clazzes.size()));
    }
    public List<Integer> getEntitiesWith(List<Class<? extends Component>> clazzes) {
        if (clazzes.isEmpty()) {
            return new ArrayList<>();
        }

        // optimisation: sort the list in ascending size order to quickly cut down on candidates
        List<Integer> candidates = new ArrayList<>(components.get(clazzes.get(0)).keySet());

        return getEntitiesWith(candidates, clazzes.subList(1, clazzes.size())); 
    }
}
