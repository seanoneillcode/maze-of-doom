package com.halycon.maze.renderer.drawables;

import com.halycon.maze.core.Vector;
import com.halycon.maze.simulation.entity.PickupType;

public enum PickupDrawConfig {

	HEALTH(PickupType.HEALTH, "ui/healthpickup.png", new Vector()),
	SWORD(PickupType.SWORD, "ui/swordPickup.png", new Vector(5,9)),
	MILK(PickupType.MILK, "ui/milk.png", new Vector());
	
	private PickupType pickupType;
	private String image;
	private Vector offset;
	
	private PickupDrawConfig(PickupType pickupType, String image, Vector offset) {
		this.pickupType = pickupType;
		this.image = image;
		this.offset = offset;
	}
	
	public Vector getOffset() {
		return offset;
	}
	
	public PickupType getPickupType() {
		return pickupType;
	}
	
	public String getImage() {
		return image;
	}
	
	public static PickupDrawConfig getPickupDrawConfig(PickupType type) {
		for (PickupDrawConfig config : values()) {
			if (config.getPickupType().equals(type)) {
				return config;
			}
		}
		return null;
	}
}
