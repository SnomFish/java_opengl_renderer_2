package github.snomfish.graphics;

import java.util.HashMap;
import java.util.Map;

import github.snomfish.FileManager;

public class AssetManager {
    

    private static final String materialFilePath = "materials/materials.json";

    private static final Map<AssetKey, Asset> assets = new HashMap<>();


    private AssetManager() {}


    static {
        load(materialFilePath, Material.class, Material[].class);
    }


    private static <T extends Asset> void load(String path, Class<T> clazz, Class<T[]> clazzA) {  
        for (T obj : FileManager.parse(path, clazzA)) {
            AssetKey key = new AssetKey(obj.getName(), clazz);
            assets.put(key, obj);
        }
    }
    
    
    @SuppressWarnings("unchecked")
    public static <T extends Asset> T get(String name, Class<T> clazz) {
        AssetKey key = new AssetKey(name, clazz);
        return (T) assets.get(key);
    }


    public static <T extends Asset> void add(String name, Asset asset) {
        AssetKey key = new AssetKey(name, asset.getClass());
        assets.put(key, asset);
    } 


    public static <T extends Asset> void remove(String name, Class<T> clazz) {
        AssetKey key = new AssetKey(name, clazz);
        assets.remove(key);
    }


    public static <T extends Asset> boolean has(String name, Class<T> clazz) {
        AssetKey key = new AssetKey(name, clazz);
        return assets.containsKey(key);
    }
}
