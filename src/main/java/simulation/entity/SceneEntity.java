package simulation.entity;


import core.Vector;
import simulation.story.Scene;

public class SceneEntity extends Entity {

    Scene scene;

    public SceneEntity(Vector position, Vector size, Scene scene) {
        super(position, size);
        this.scene = scene;
    }

    public SceneEntity(SceneEntity sceneEntity) {
        super(sceneEntity.getPosition(), sceneEntity.getSize());
        this.scene = sceneEntity.getScene();
    }

    public Scene getScene() {
        return scene;
    }
}
