package com.halycon.maze.simulation.entity;

import com.halycon.maze.core.Vector;

public interface Collidable {

	public boolean canCollide();
	public Vector getSize();
	public Vector getPosition();
	public void handleCollision(Collidable other);
}
