package simulation.entity;

import java.util.HashMap;
import java.util.Map;

public class Inventory {

	Map<InventoryItem, Integer> items;
	
	public Inventory() {
		items = new HashMap<InventoryItem, Integer>();
	}
	
	public void addItem(InventoryItem item) {
		Integer amount = items.get(item);
		items.put(item, amount == null ? 1 : amount + 1);
	}
	
	public void removeItem(InventoryItem item) {
		Integer amount = items.get(item);
		items.put(item, amount == null || amount == 0 ? 0 : amount - 1);
	}
	
	public boolean hasItem(InventoryItem item) {
		return items.containsKey(item);
	}
}
