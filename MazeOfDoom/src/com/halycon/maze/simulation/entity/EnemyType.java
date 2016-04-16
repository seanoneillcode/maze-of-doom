package com.halycon.maze.simulation.entity;

import com.halycon.maze.core.Vector;

public enum EnemyType {
	BLOB(new Vector(8,8), new Vector(20.2f,20.2f), new Vector(-2.0f, -2.0f), 2);
	
	private Vector size;
	private Vector speed;
	private Vector offset;
	private int health;
	
	private EnemyType(Vector size, Vector speed, Vector offset, int health) {
		this.speed = speed;
		this.size = size;
		this.offset = offset;
		this.health = health;
	}
	
	public Vector getOffset() {
		return offset;
	}
	
	public Vector getSpeed() {
		return speed;
	}
	
	public Vector getSize() {
		return size;
	}
	
	public int getHealth() {
		return health;
	}
}
