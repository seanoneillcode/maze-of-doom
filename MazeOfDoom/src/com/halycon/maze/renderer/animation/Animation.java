package com.halycon.maze.renderer.animation;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.halycon.maze.simulation.entity.state.CanBeAnimated;

public interface Animation {
	public void draw(SpriteBatch spriteBatch);
	public void update(float delta, CanBeAnimated entity);
}
