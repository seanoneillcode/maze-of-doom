package com.halycon.maze.simulation.entity;

import java.util.HashSet;
import java.util.Set;

public class Inventory {

	Set<InventoryItem> items;
	
	public Inventory() {
		items = new HashSet<InventoryItem>();
	}
	
	public Set<InventoryItem> getItems() {
		return items;
	}
	
	public void addItem(InventoryItem item) {
		items.add(item);
	}
	
	public boolean hasItem(InventoryItem item) {
		return items.contains(item);
	}
}
