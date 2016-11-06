package simulation.story.actions;

import core.Vector;
import simulation.entity.Entity;
import simulation.entity.MovingEntity;
import simulation.story.Actor;

public class MoveActionType implements SceneActionType {

    Vector destination;
    Vector movement;
    Vector speed = new Vector(48,48);
    boolean isDone = false;

    public MoveActionType(Vector destination) {
        this.destination = destination;
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
        Entity entity = actor.getEntity();
        Vector source = entity.getPosition();
        movement = Vector.getUnitVector(source, destination);
        movement = new Vector(movement.x * speed.x * delta, movement.y * speed.y * delta);
        source = source.add(movement);
        entity.setPosition(source);
        if (source.isNear(destination, speed.x * delta)) {
            entity.setPosition(destination);
            isDone = true;
        }
    }
}
