package simulation.story.actions;

import core.Direction;
import core.Vector;
import simulation.entity.MovingEntity;
import simulation.story.Actor;

public class MoveActionType implements SceneActionType {

    Vector destination;
    Vector movement;
    Vector speed = new Vector(48,48);
    boolean isDone = false;
    Actor actor;

    public MoveActionType(Actor actor, Vector destination) {
        this.destination = destination;
        this.actor = actor;
    }

    @Override
    public void start() {
        actor.setMoving(true);
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public void update(float delta) {
        MovingEntity entity = actor.getEntity();
        Vector source = entity.getPosition();
        movement = Vector.getUnitVector(source, destination);
        movement = new Vector(movement.x * speed.x * delta, movement.y * speed.y * delta);
        source = source.add(movement);
        entity.getEntity().setPosition(source);
        entity.setDirection(Direction.getDirection(source, destination));
        if (source.isNear(destination, speed.x * delta)) {
            entity.getEntity().setPosition(destination);
            isDone = true;
            actor.setMoving(false);
        }
    }
}
