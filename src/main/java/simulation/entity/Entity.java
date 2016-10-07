package simulation.entity;

import core.Vector;
import simulation.entity.state.EntityState;


public class Entity {

	public Vector pos;
	public Vector size;
	EntityState state;
	EntityType type;
	
	public Entity(Vector position, Vector size) {
		this.pos = new Vector(position);
		this.size = new Vector(size);
		this.state = EntityState.ALIVE;
		this.type = EntityType.NONE;
	}

	public Entity(Vector position, Vector size, EntityType type) {
		this.pos = new Vector(position);
		this.size = new Vector(size);
		this.state = EntityState.ALIVE;
		this.type = type;
	}
	
	public EntityState getState() {
		return state;
	}
	
	protected void die() {
		this.state = EntityState.DEAD;
	}
	
	public Vector getSize() {
		return size;
	}
	
	public Vector getPosition() {
		return pos;
	}

	public EntityType getType() { return type; }
}
