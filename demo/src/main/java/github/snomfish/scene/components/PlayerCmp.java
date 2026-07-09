package github.snomfish.scene.components;

import github.snomfish.scene.Component;

public class PlayerCmp implements Component {
    

    private float speed;


    public PlayerCmp(float speed) {
        this.speed = speed;        
    }
    public PlayerCmp() {
        this(5.0f);    
    }


    // getter
    public float getSpeed() {
        return speed;
    }


    // setter
    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
