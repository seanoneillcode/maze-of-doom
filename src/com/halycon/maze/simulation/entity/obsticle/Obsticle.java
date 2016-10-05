package com.halycon.maze.simulation.entity.obsticle;

import com.halycon.maze.core.Vector;
import com.halycon.maze.simulation.entity.MovingEntity;
import com.halycon.maze.simulation.entity.Player;

public abstract class Obsticle extends MovingEntity {

	private ObsticleType type;
	
	public Obsticle(Vector position, ObsticleType type) {
		super(position, type.getSize(), type.getSpeed());
		this.type = type;
	}

	public Obsticle(Obsticle obsticle) {
		super(obsticle.getPosition(), obsticle.getSize(), obsticle.getSpeed());
		this.type = obsticle.getType();
	}
	
	public ObsticleType getType() {
		return type;
	}
	
	public abstract void handleCollision(Player player);
}
