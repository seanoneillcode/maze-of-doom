package simulation.story;

import simulation.entity.MovingEntity;

public class Actor {

    MovingEntity entity;

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    boolean isMoving = false;

    public MovingEntity getEntity() {
        return entity;
    }

    public Actor(MovingEntity entity) {
        this.entity = entity;
    }


}
