package com.halycon.maze.renderer.drawables;

import com.halycon.maze.simulation.entity.obsticle.ObsticleType;

public enum ObsticleDrawConfig {

	BOULDER(ObsticleType.BOULDER, "nodes/boulder.png");
	
	private ObsticleType type;
	private String image;
	
	private ObsticleDrawConfig(ObsticleType type, String image) {
		this.type = type;
		this.image = image;
	}
	
	public String getImage() {
		return image;
	}
	
	public ObsticleType getType() {
		return type;
	}
	
	public static ObsticleDrawConfig getObsticleDrawConfig(ObsticleType type) {
		for (ObsticleDrawConfig config : values()) {
			if (config.getType().equals(type)) {
				return config;
			}
		}
		return null;
	}
}
