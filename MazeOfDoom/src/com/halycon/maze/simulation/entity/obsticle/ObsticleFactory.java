package com.halycon.maze.simulation.entity.obsticle;

import com.halycon.maze.core.Vector;

public class ObsticleFactory {

	public static Obsticle getObsticle(Vector position, ObsticleType type) {
		Obsticle obsticle = null;
		if (type == ObsticleType.BOULDER) {
			obsticle = new Boulder(position, type);
		}
		return obsticle;
	}
	
	public static Obsticle getObsticle(Obsticle obsticle) {
		Obsticle returnObsticle = null;
		if (obsticle.getType() == ObsticleType.BOULDER) {
			returnObsticle = new Boulder(obsticle);
		}
		return returnObsticle;
	}
}
