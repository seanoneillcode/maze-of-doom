package com.halycon.maze.renderer.drawables;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.halycon.maze.core.Vector;
import com.halycon.maze.renderer.ImageList;
import com.halycon.maze.simulation.entity.mechanism.Mechanism;

public class MechanismDrawable implements Drawable {

	private Mechanism mechanism;
	ImageList image;
	
	public MechanismDrawable(Mechanism mechanism) {
		this.mechanism = mechanism;
		image = new ImageList(mechanism.getType().getImage(), mechanism.getSize(), 2, new Vector());
	}
	
	@Override
	public int compareTo(Drawable other) {
		return (int) (other.getZ() - getZ());
	}

	public float getZ() {
		if (!mechanism.getType().getCovers()) {
			return 999;
		}
		return mechanism.getPosition().y;
	}
	
	@Override
	public void draw(float delta, SpriteBatch spriteBatch) {
		image.draw(spriteBatch, mechanism.isActive() ? 0 : 1, mechanism.getPosition());
	}

	@Override
	public Vector getPosition() {
		return mechanism.getPosition();
	}

}
