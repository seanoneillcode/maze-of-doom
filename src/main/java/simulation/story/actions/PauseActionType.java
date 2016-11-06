package simulation.story.actions;


import simulation.story.Actor;

public class PauseActionType implements SceneActionType {

    float time = 0;
    boolean isStarted;

    public PauseActionType(float time) {
        this.time = time;
        isStarted = false;
    }

    @Override
    public void start() {
        isStarted = true;
    }

    @Override
    public boolean isDone() {
        return time < 0;
    }

    @Override
    public void update(Actor actor, float delta) {
        if (isStarted) {
            this.time = time - delta;
        }
    }
}
