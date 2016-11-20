package simulation.story.actions;

public interface SceneActionType {
    void start();
    boolean isDone();
    void update(float delta);
}
