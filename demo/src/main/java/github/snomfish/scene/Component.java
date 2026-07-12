package github.snomfish.scene;

import java.util.HashMap;
import java.util.Map;

public abstract class Component {
    
    
    private static final boolean CLEAN = true;
    private static final boolean DIRTY = false;
    private Map<Class<? extends System>, Boolean> isClean = new HashMap<>();


    public <T extends System> boolean isClean(Class<T> clazz) {
        if (!isClean.containsKey(clazz)) {
            isClean.put(clazz, DIRTY);
        }
        return isClean.get(clazz);
    }


    public <T extends System> void setClean(Class<T> clazz) {
        isClean.put(clazz, CLEAN);
    }


    public <T extends System> void setAllDirty() {
        for (Class<? extends System> clazz : isClean.keySet()) {
            isClean.put(clazz, DIRTY);
        }
    }
}
