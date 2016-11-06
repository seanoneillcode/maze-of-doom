package simulation.story.actions;


import core.Vector;
import simulation.story.Actor;

public class PositionActionType implements SceneActionType {

    Vector position;
    boolean isDone;

    public PositionActionType(Vector position) {
        this.position = position;
        isDone = false;
    }

    @Override
    public void start() {
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public void update(Actor actor, float delta) {
        if (!isDone) {
            actor.getEntity().setPosition(position);
            isDone = true;
        }
    }

}
