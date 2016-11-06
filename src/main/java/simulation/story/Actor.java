package simulation.story;


import simulation.entity.Entity;

public class Actor {

    Entity entity;

    public Entity getEntity() {
        return entity;
    }

    public Actor(Entity entity) {
        this.entity = entity;
    }
}
