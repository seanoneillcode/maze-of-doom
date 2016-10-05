package com.halycon.maze.simulation.entity.obsticle;

import com.halycon.maze.core.Vector;

public enum ObsticleType {

	BOULDER(new Vector(15, 15), new Vector(64,64));
	
	private Vector size;
	private Vector speed;
	
	private ObsticleType(Vector size, Vector speed) {
		this.size = size;
		this.speed = speed;
	}
	
	public Vector getSize() {
		return size;
	}
	
	public Vector getSpeed() {
		return speed;
	}
}
