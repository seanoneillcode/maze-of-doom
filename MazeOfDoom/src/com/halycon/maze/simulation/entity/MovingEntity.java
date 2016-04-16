package com.halycon.maze.simulation.entity;

import com.halycon.maze.core.Vector;
import com.halycon.maze.simulation.common.Direction;


public class MovingEntity {

	private Entity entity;
	private Vector move;
	private Vector speed;
	private Direction direction;
	private Vector physics;
	
	private static final Vector FRICTION = new Vector(0.8f, 0.8f);
	private static final float LOW_FRICTION_LIMIT = 0.001f;
	
	public MovingEntity(Vector position, Vector size, Vector speed) {
		this.entity = new Entity(position, size);
		this.move = new Vector();
		this.speed = speed;
		this.direction = Direction.DOWN;
		physics = new Vector();
	}
	
	public void update(float delta) {
		move.x = move.x * delta;
		move.y = move.y * delta;
		move.x += physics.x;
		move.y += physics.y;
		entity.pos.x += move.x;
		entity.pos.y += move.y;
		move.x = 0;
		move.y = 0;
		applyFriction();
		if (Math.abs(physics.x) <= LOW_FRICTION_LIMIT) {
			physics.x = 0;
		}
		if (Math.abs(physics.y) <= LOW_FRICTION_LIMIT) {
			physics.y = 0;
		}
	}
	
	public void applyFriction() {
		physics.x *= FRICTION.x;
		physics.y *= FRICTION.y;
	}
	
	public void impulse(Vector amount) {
		physics.x += amount.x;
		physics.y += amount.y;
	}
	
	public void moveLeft(float delta) {
		move.x -= speed.x;
		direction = Direction.LEFT;
	}

	public void moveRight(float delta) {
		move.x += speed.x;
		direction = Direction.RIGHT;
	}

	public void moveUp(float delta) {
		move.y += speed.y;
		direction = Direction.UP;
	}

	public void moveDown(float delta) {
		move.y -= speed.y;
		direction = Direction.DOWN;
	}
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public Vector getSize() {
		return entity.getSize();
	}
	
	public Vector getSpeed() {
		return speed;
	}
	
	public Vector getPosition() {
		return entity.getPosition();
	}
	
	public Vector getMovement() {
		return new Vector(move);
	}
	
	public boolean isMoving() {
		return move.x != 0 || move.y != 0;
	}
	
	public void setMovement(Vector move) {
		this.move = new Vector(move);
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public Entity getEntity() {
		return entity;
	}
	
	public Vector getPhysics() {
		return new Vector(physics);
	}
	
	public void setPhysics(Vector physics) {
		this.physics = new Vector(physics);
	}
}
