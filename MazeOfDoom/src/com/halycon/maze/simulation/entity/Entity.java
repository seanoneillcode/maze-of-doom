package com.halycon.maze.simulation.entity;

import com.halycon.maze.core.Vector;
import com.halycon.maze.simulation.entity.state.EntityState;


public class Entity {

	public Vector pos;
	public Vector size;
	EntityState state;
	
	public Entity(Vector position, Vector size) {
		this.pos = new Vector(position);
		this.size = new Vector(size);
		this.state = EntityState.ALIVE;
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
}
