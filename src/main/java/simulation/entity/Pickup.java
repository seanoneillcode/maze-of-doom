package simulation.entity;

import core.Vector;

public class Pickup extends Entity {

	private PickupType type;
	private boolean isVisible;

	public Pickup(Vector position, PickupType type, boolean isVisible) {
		super(position, type.getSize(), EntityType.PICKUP);
		this.type = type;
		this.isVisible = isVisible;
	}

	public PickupType getPickupType() {
		return type;
	}
	
	public Pickup(Pickup pickup) {
		this(pickup.getPosition(), pickup.getPickupType(), pickup.isVisible());
	}
	
	public void getPickedUp(Player player) {
		type.getPickedUp(player);
		this.die();
	}

	public boolean isVisible() {
		return isVisible;
	}
}
