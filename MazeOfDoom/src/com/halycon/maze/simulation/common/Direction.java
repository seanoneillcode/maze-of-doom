package com.halycon.maze.simulation.common;

import com.halycon.maze.core.Vector;

public enum Direction {

	UP(new Vector(0,1)),
	DOWN(new Vector(0,-1)),
	LEFT(new Vector(-1,0)),
	RIGHT(new Vector(1,0));
	
	private Vector vector;
	
	private Direction(Vector vector) {
		this.vector = vector;
	}
	
	public Vector getVector() {
		return vector;
	}
}
