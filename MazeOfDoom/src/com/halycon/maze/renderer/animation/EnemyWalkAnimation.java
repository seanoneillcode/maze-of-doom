package com.halycon.maze.renderer.animation;

import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.halycon.maze.core.Vector;
import com.halycon.maze.renderer.ImageList;
import com.halycon.maze.simulation.common.Direction;
import com.halycon.maze.simulation.entity.state.CanBeAnimated;

public class EnemyWalkAnimation implements Animation {

	private Map<Direction, ImageList> images;
	private Vector position;
	private int index;
	private ImageList currentImageList;
	private float timer = 0f;
	private static final float LIMIT = 0.9f;
	
	public EnemyWalkAnimation(Map<Direction, ImageList> images) {
		this.images = images;
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch) {
		currentImageList.draw(spriteBatch, index, position);
	}

	@Override
	public void update(float delta, CanBeAnimated entity) {
		this.position = new Vector(entity.getPosition());
		currentImageList = images.get(entity.getDirection());
		timer += delta;
		if (timer > LIMIT) {
			timer = -LIMIT;
		}
		if (timer > 0) {
			index = 0;
		} else {
			index = 1;
		}
	}

}
