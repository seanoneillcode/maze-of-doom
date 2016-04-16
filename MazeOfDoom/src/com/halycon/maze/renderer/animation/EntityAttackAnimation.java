package com.halycon.maze.renderer.animation;

import java.util.Map;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.halycon.maze.core.Vector;
import com.halycon.maze.renderer.ImageList;
import com.halycon.maze.simulation.common.Direction;
import com.halycon.maze.simulation.entity.state.CanBeAnimated;


public class EntityAttackAnimation implements Animation {

	private Map<Direction, ImageList> images;
	private Vector position;
	private int index;
	private ImageList currentImageList;
	
	public EntityAttackAnimation(Map<Direction, ImageList> images) {
		this.images = images;
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch) {
		currentImageList.draw(spriteBatch, index, position);
	}

	@Override
	public void update(float delta, CanBeAnimated entity) {
		this.position = new Vector(entity.getPosition());
		index = 0;
		switch (entity.getAttackState()) {
		case IDLE:
			index = 0;
			break;
		case START:
			index = 0;
			break;
		case MIDDLE:
			index = 1;
			break;
		case END:
			index = 2;
			break;
		}
		currentImageList = images.get(entity.getDirection());
	}
	
}
