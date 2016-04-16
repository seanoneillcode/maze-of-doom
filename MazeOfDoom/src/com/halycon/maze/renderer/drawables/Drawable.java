package com.halycon.maze.renderer.drawables;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.halycon.maze.core.Vector;

public interface Drawable extends Comparable<Drawable> {
	public void draw(float delta, SpriteBatch spriteBatch);
	public Vector getPosition();
	public float getZ();
}
