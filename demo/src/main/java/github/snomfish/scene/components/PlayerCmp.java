package github.snomfish.scene.components;

import github.snomfish.scene.Component;

public class PlayerCmp implements Component {
    

    private int cameraId;


    public PlayerCmp(int cameraId) {
        this.cameraId = cameraId;        
    }


    // getter
    public int getCameraId() {
        return cameraId;
    }


    // setter
    public void setCameraId(int cameraId) {
        this.cameraId = cameraId;
    }
}
