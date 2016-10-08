package simulation.entity;

import core.Vector;

public enum EnemyType {
	BLOB(new Vector(8,8), new Vector(20.2f,20.2f), new Vector(-2.0f, -2.0f), 2),
	FIRE(new Vector(10,10), new Vector(0,0), new Vector(0, 0), 1);

	private Vector size;
	private Vector speed;
	private Vector offset;
	private int health;
	
	EnemyType(Vector size, Vector speed, Vector offset, int health) {
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
