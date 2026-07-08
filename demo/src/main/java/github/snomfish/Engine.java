package github.snomfish;

import github.snomfish.graphics.Shader;
import github.snomfish.scene.Scene;
import github.snomfish.scene.components.CameraCmp;
import github.snomfish.scene.components.PlayerCmp;
import github.snomfish.scene.components.TransformCmp;
import github.snomfish.scene.system.CameraSystem;
import github.snomfish.scene.system.RenderSystem;
import github.snomfish.scene.system.TransformSystem;
import github.snomfish.window.Window;

public class Engine {


    private Window window;
    private Shader shader;
    private Scene scene;

    private int playerId;
    private CameraSystem cameraSystem;
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
        
        Integer cameraId = scene.newEntity();
        scene.addComponent(cameraId, new CameraCmp());
        scene.addComponent(cameraId, new TransformCmp());

        playerId = scene.newEntity();
        scene.addComponent(playerId, new PlayerCmp(cameraId));
        scene.addComponent(playerId, new TransformCmp());
        
        cameraSystem = new CameraSystem();
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
                System.out.println("test");
                lastFrame = currentTime;
                update(deltaTime);
                render(deltaTime);
            }
        }
    }


    private void update(float deltaTime) {
        window.update();
        transformSystem.update(scene);
        cameraSystem.update(scene, window.getAspect());
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
