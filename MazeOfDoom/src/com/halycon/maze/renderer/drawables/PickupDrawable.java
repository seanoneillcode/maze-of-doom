package com.halycon.maze.renderer.drawables;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.halycon.maze.core.Vector;
import com.halycon.maze.renderer.EntitySprite;
import com.halycon.maze.simulation.entity.Pickup;
import com.halycon.maze.simulation.entity.state.EntityState;

public class PickupDrawable implements Drawable {

	private EntitySprite sprite;
	private Pickup pickup;
	
	public PickupDrawable(Pickup pickup) {
		this.pickup = pickup;
		PickupDrawConfig config = PickupDrawConfig.getPickupDrawConfig(pickup.getType());
		sprite = new EntitySprite(config.getImage(), config.getPickupType().getSize(), config.getOffset());
	}
	
	@Override
	public void draw(float delta, SpriteBatch spriteBatch) {
		if (pickup.getState() == EntityState.ALIVE) {
			sprite.draw(pickup.getPosition(), spriteBatch);
		}
	}

	@Override
	public int compareTo(Drawable other) {
		return (int) (other.getZ() - getZ());
	}
	
	public float getZ() {
		return getPosition().y;
	}

	@Override
	public Vector getPosition() {
		return pickup.getPosition();
	}
}
