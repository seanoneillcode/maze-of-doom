package com.halycon.maze.simulation.entity.obsticle;

import com.halycon.maze.core.Vector;
import com.halycon.maze.simulation.entity.Player;

public class Boulder extends Obsticle {

	public Boulder(Obsticle obsticle) {
		super(obsticle);
	}
	
	public Boulder(Vector position, ObsticleType type) {
		super(position, type);
	}

	@Override
	public void handleCollision(Player player) {
		this.impulse(player.getDirection().getVector().multiply(0.15f));
	}
}
