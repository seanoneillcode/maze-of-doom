package simulation.story.actions;


import core.Vector;
import simulation.story.Actor;

public class PositionActionType implements SceneActionType {

    Vector position;
    boolean isDone;
    Actor actor;

    public PositionActionType(Actor actor, Vector position) {
        this.position = position;
        isDone = false;
        this.actor = actor;
    }

    @Override
    public void start() {
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public void update(float delta) {
        if (!isDone) {
            actor.getEntity().getEntity().setPosition(position);
            isDone = true;
        }
    }

}
