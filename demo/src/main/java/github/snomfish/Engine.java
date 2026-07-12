package github.snomfish;

import github.snomfish.graphics.AssetManager;
import github.snomfish.graphics.assets.Material;
import github.snomfish.graphics.shader.SceneShader;
import github.snomfish.graphics.shader.ShadowShader;
import github.snomfish.input.Input;
import github.snomfish.scene.Scene;
import github.snomfish.scene.SceneBuilder;
import github.snomfish.scene.components.*;
import github.snomfish.scene.system.*;
import github.snomfish.window.Window;

public class Engine {


    private Window window;
    private SceneShader sceneShader;
    private ShadowShader shadowShader;
    private Scene scene;

    private int playerId;
    private CameraSystem cameraSystem;
    private InputSystem inputSystem;
    private RenderShadowsSystem renderShadowsSystem;
    private RenderSystem renderSystem;
    private RotationSystem rotationSystem;
    private PointShadowSystem pointShadowSystem;
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

        sceneShader = new SceneShader();
        shadowShader = new ShadowShader();

        scene = new Scene();
        SceneBuilder sceneBuilder = new SceneBuilder(scene);

        playerId = sceneBuilder.newEntity()
            .add(new CameraCmp())
            .add(new PlayerCmp())
            .add(new TransformCmp(0, 0, 0, 0, 0, 0, 1, 1, 1))
            .returnId();
        
        sceneBuilder.newEntity() // light
            .add(new PointLightCmp(0.0f, 0.0f, 1.0f, 3.0f))
            .add(new PointShadowCmp())
            .add(new TransformCmp(10, 5, -10, 0, 0, 0, 1, 1, 1));

        sceneBuilder.newEntity() // room
            .add(new MaterialCmp(AssetManager.get("wood", Material.class)))
            .add(new MeshCmp(MeshBuilder.hSquare()))
            .add(new ShadowCasterCmp())
            .add(new TransformCmp(0, -3, -10, 0, 0, 0, 100, 1, 100));

        sceneBuilder.newEntity()
            .add(new MaterialCmp(AssetManager.get("wood", Material.class)))
            .add(new MeshCmp(MeshBuilder.cube()))
            .add(new RotationCmp())
            .add(new ShadowCasterCmp())
            .add(new TransformCmp(0, 0, -10, 0, 0, 0, 1, 1, 1));
        
        cameraSystem = new CameraSystem();
        inputSystem = new InputSystem();
        renderSystem = new RenderSystem();
        renderShadowsSystem = new RenderShadowsSystem();
        rotationSystem = new RotationSystem();
        pointShadowSystem = new PointShadowSystem();
        transformSystem = new TransformSystem();

        renderSystem.init();
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
        pointShadowSystem.update(scene);
        transformSystem.update(scene);
        cameraSystem.update(scene, window.getAspect());

        rotationSystem.update(scene, deltaTime);

        Input.endFrame();
    }


    private void render(float deltaTime) {
        renderSystem.beginFrame();

        renderShadowsSystem.render(scene, shadowShader, window.getWidth(), window.getHeight());
        renderSystem.render(scene, sceneShader, window.getAspect(), playerId);

        renderSystem.endFrame();
    }


    private void cleanup() {
        window.cleanup();
        sceneShader.cleanup();
        shadowShader.cleanup();
    }
}
