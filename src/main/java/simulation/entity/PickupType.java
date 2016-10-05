package simulation.entity;

import core.Vector;

public enum PickupType {
	HEALTH(new Vector(10,7)) {
		@Override
		public void getPickedUp(Player player) {
			player.heal(1);
		}
	},
	
	SWORD(new Vector(19,19)) {
		@Override
		public void getPickedUp(Player player) {
			player.getInventory().addItem(InventoryItem.SWORD);
		}
	},
	
	MILK(new Vector(8,8)) {
		@Override
		public void getPickedUp(Player player) {
			player.getInventory().addItem(InventoryItem.MILK);
		}
	};
	
	private Vector size;
	
	private PickupType(Vector size) {
		this.size = size;
	}
	
	public Vector getSize() {
		return size;
	}
	
	public abstract void getPickedUp(Player player);
}
