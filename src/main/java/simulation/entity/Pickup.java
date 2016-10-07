package simulation.entity;

import core.Vector;

public class Pickup extends Entity {

	private PickupType type;
	
	public Pickup(Vector position, PickupType type) {
		super(position, type.getSize(), EntityType.PICKUP);
		this.type = type;
	}

	public PickupType getPickupType() {
		return type;
	}
	
	public Pickup(Pickup pickup) {
		this(pickup.getPosition(), pickup.getPickupType());
	}
	
	public void getPickedUp(Player player) {
		type.getPickedUp(player);
		this.die();
	}
}
