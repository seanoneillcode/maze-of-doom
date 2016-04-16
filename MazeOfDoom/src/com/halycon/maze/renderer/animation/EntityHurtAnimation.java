package com.halycon.maze.renderer.animation;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.halycon.maze.core.Vector;
import com.halycon.maze.renderer.ImageList;
import com.halycon.maze.simulation.entity.state.CanBeAnimated;

public class EntityHurtAnimation implements Animation {

	private static final float FRAME = 0.1f;
	private ImageList imageList;
	private Vector position;
	private int index;
	private float timer = 0f;
	
	public EntityHurtAnimation(ImageList imageList) {
		this.imageList = imageList;
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch) {
		imageList.draw(spriteBatch, index, position);
	}

	@Override
	public void update(float delta, CanBeAnimated entity) {
		timer += delta;
		if (timer > FRAME) {
			timer = 0;
			index++;
		}
		if (index >= imageList.getNumFrames()) {
			index = 0;
		}
		this.position = entity.getPosition();
	}
}
