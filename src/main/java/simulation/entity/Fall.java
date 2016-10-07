package simulation.entity;

import core.Vector;

public class Fall extends Entity {

	private String link;
	
	public Fall(Vector position, Vector size, String link) {
		super(position, size, EntityType.FALL);
		this.link = link;
	}

	public Fall(Fall fall) {
		super(fall.getPosition(), fall.getSize(), EntityType.FALL);
		link = fall.getLink();
	}
	
	public String getLink() {
		return link;
	}
}
