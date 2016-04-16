package com.halycon.maze.simulation.entity;

import com.halycon.maze.core.Vector;

public class Pickup extends Entity {

	private PickupType type;
	
	public Pickup(Vector position, PickupType type) {
		super(position, type.getSize());
		this.type = type;
	}

	public PickupType getType() {
		return type;
	}
	
	public Pickup(Pickup pickup) {
		this(pickup.getPosition(), pickup.getType());
	}
	
	public void getPickedUp(Player player) {
		type.getPickedUp(player);
		this.die();
	}
}
