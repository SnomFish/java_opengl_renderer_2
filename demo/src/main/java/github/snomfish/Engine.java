package github.snomfish;

import github.snomfish.graphics.Mesh;
import github.snomfish.graphics.Shader;
import github.snomfish.input.Input;
import github.snomfish.scene.Scene;
import github.snomfish.scene.components.CameraCmp;
import github.snomfish.scene.components.LightCmp;
import github.snomfish.scene.components.MeshCmp;
import github.snomfish.scene.components.PlayerCmp;
import github.snomfish.scene.components.TransformCmp;
import github.snomfish.scene.system.CameraSystem;
import github.snomfish.scene.system.InputSystem;
import github.snomfish.scene.system.RenderSystem;
import github.snomfish.scene.system.TransformSystem;
import github.snomfish.window.Window;

public class Engine {


    private Window window;
    private Shader shader;
    private Scene scene;

    private int playerId;
    private CameraSystem cameraSystem;
    private InputSystem inputSystem;
    private RenderSystem renderSystem;
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

        shader = new Shader(
            "Vertex.glsl", 
            "Fragment.glsl"
        );

        scene = new Scene();

        playerId = scene.newEntity();
        scene.addComponent(playerId, new CameraCmp());
        scene.addComponent(playerId, new PlayerCmp());
        scene.addComponent(playerId, new TransformCmp(0, 0, 0, 0, 0, 0, 1, 1, 1));

        Integer lightId = scene.newEntity();
        scene.addComponent(lightId, new LightCmp());
        scene.addComponent(lightId, new TransformCmp(0, 3, 0, 0, 0, 0, 1, 1, 1));

        Integer meshId = scene.newEntity();
        Mesh mesh = MeshBuilder.cube();
        scene.addComponent(meshId, new MeshCmp(mesh));
        scene.addComponent(meshId, new TransformCmp(0, 0, -5, 0, 0, 0, 1, 1, 1));
        
        cameraSystem = new CameraSystem();
        inputSystem = new InputSystem();
        renderSystem = new RenderSystem();
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
        transformSystem.update(scene);
        cameraSystem.update(scene, window.getAspect());

        Input.endFrame();
    }


    private void render(float deltaTime) {
        renderSystem.beginFrame();
        renderSystem.render(scene, shader, window.getAspect(), playerId);
        renderSystem.endFrame();
    }


    private void cleanup() {
        window.cleanup();
        shader.cleanup();
    }
}
