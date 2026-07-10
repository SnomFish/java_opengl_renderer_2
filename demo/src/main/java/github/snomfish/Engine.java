package github.snomfish;

import github.snomfish.graphics.AssetManager;
import github.snomfish.graphics.Material;
import github.snomfish.graphics.Renderer;
import github.snomfish.graphics.Shader;
import github.snomfish.input.Input;
import github.snomfish.scene.Scene;
import github.snomfish.scene.SceneBuilder;
import github.snomfish.scene.components.CameraCmp;
import github.snomfish.scene.components.LightCmp;
import github.snomfish.scene.components.MaterialCmp;
import github.snomfish.scene.components.MeshCmp;
import github.snomfish.scene.components.PlayerCmp;
import github.snomfish.scene.components.RotationCmp;
import github.snomfish.scene.components.TransformCmp;
import github.snomfish.scene.system.CameraSystem;
import github.snomfish.scene.system.InputSystem;
import github.snomfish.scene.system.LightSystem;
import github.snomfish.scene.system.RenderSystem;
import github.snomfish.scene.system.RotationSystem;
import github.snomfish.scene.system.TransformSystem;
import github.snomfish.window.Window;

public class Engine {


    private Window window;
    private Shader shader;
    private Renderer renderer;
    private Scene scene;

    private int playerId;
    private CameraSystem cameraSystem;
    private InputSystem inputSystem;
    private LightSystem lightSystem;
    private RenderSystem renderSystem;
    private RotationSystem rotationSystem;
    private TransformSystem transformSystem;

    private int fps;
    private long frameInterval;


    public Engine() {
        fps = 60;
        frameInterval = 1_000_000_000 / fps;
    }


    public void setFps(int fps) {
        this.fps = fps;
        frameInterval = 1_000_000_000 / fps;
    }


    public void run() {
        init();
        loop();
        cleanup();
    }


    private void init() {

        window = new Window(1280, 720, "Renderer");
        window.init();

        renderer = new Renderer();
        renderer.init();

        shader = new Shader(
            "Vertex.glsl", 
            "Fragment.glsl"
        );

        scene = new Scene();
        SceneBuilder sceneBuilder = new SceneBuilder(scene);

        playerId = sceneBuilder.newEntity()
            .add(new CameraCmp())
            .add(new PlayerCmp())
            .add(new TransformCmp(0, 0, 0, 0, 0, 0, 1, 1, 1))
            .returnId();

        sceneBuilder.newEntity() // light
            .add(new LightCmp(0.0f, 0.0f, 1.0f, 3.0f))
            .add(new MaterialCmp(AssetManager.get("wood", Material.class)))
            .add(new MeshCmp(MeshBuilder.cube()))
            .add(new TransformCmp(10, 0, -10, 0, 0, 0, 1, 1, 1));

        sceneBuilder.newEntity() // floor
            .add(new MaterialCmp(AssetManager.get("wood", Material.class)))
            .add(new MeshCmp(MeshBuilder.square()))
            .add(new TransformCmp(0, -4, -10, 0, 0, 0, 100, 100, 100));

        sceneBuilder.newEntity()
            .add(new MaterialCmp(AssetManager.get("wood", Material.class)))
            .add(new MeshCmp(MeshBuilder.cube()))
            .add(new RotationCmp())
            .add(new TransformCmp(0, 0, -10, 0, 45, 0, 1, 1, 1));
        
        cameraSystem = new CameraSystem();
        inputSystem = new InputSystem();
        lightSystem = new LightSystem();
        renderSystem = new RenderSystem();
        rotationSystem = new RotationSystem();
        transformSystem = new TransformSystem();
    }


    private void loop() {
        long lastFrame = System.nanoTime();
        long currentTime = lastFrame;
        long deltaTime;

        while(!window.shouldClose()) {

            currentTime = System.nanoTime();
            deltaTime = currentTime - lastFrame;

            if (deltaTime >= frameInterval) {
                lastFrame = currentTime;
                update(deltaTime / 1_000_000_000f);
                render(deltaTime / 1_000_000_000f);
            }
        }
    }


    private void update(float deltaTime) {
        window.update();
        inputSystem.update(scene, deltaTime);
        lightSystem.update(scene, deltaTime);
        rotationSystem.update(scene, deltaTime);
        transformSystem.update(scene);
        cameraSystem.update(scene, window.getAspect());

        Input.endFrame();
    }


    private void render(float deltaTime) {
        renderer.beginFrame();
        renderSystem.render(scene, shader, window.getAspect(), playerId);
        renderer.endFrame();
    }


    private void cleanup() {
        window.cleanup();
        shader.cleanup();
    }
}
