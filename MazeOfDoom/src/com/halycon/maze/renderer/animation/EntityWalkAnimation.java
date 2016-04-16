package com.halycon.maze.renderer.animation;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.halycon.maze.core.Vector;
import com.halycon.maze.renderer.ImageList;
import com.halycon.maze.simulation.common.Direction;
import com.halycon.maze.simulation.entity.state.CanBeAnimated;



public class EntityWalkAnimation implements Animation {
	
	private float walkTimer = 0f;
	private static final float LIMIT =  0.1f;
	private Vector position;
	ImageList image;
	Direction direction;
	
	public EntityWalkAnimation(ImageList image) {
		this.image = image;
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch) {
		image.draw(spriteBatch, getWalkAnimationIndex(), position);
	}
	
	@Override
	public void update(float delta, CanBeAnimated entity) {
		if (entity.isMoving()) {
			walkTimer += delta;
			if (walkTimer > LIMIT) {
				walkTimer = -LIMIT;
			}
		}
		this.position = entity.getPosition();
		this.direction = entity.getDirection();
	}
	
	private int getWalkAnimationIndex() {
		int index = 0;
		
		switch (direction) {
		case DOWN:
			index = 0;
			break;
		case UP:
			index = 1;
			break;
		case LEFT:
			index = 3;
			break;
		case RIGHT:
			index = 2;
			break;
		}
		if (walkTimer > 0) {
			index += 4;
		}
		return index;
	}
}
