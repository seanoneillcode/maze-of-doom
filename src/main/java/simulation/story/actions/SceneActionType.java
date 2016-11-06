package simulation.story.actions;

import simulation.story.Actor;

public interface SceneActionType {
    void start();
    boolean isDone();
    void update(Actor actor, float delta);
}
