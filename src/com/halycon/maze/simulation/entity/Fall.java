package com.halycon.maze.simulation.entity;

import com.halycon.maze.core.Vector;

public class Fall extends Entity {

	private String link;
	
	public Fall(Vector position, Vector size, String link) {
		super(position, size);
		this.link = link;
	}

	public Fall(Fall fall) {
		super(fall.getPosition(), fall.getSize());
		link = fall.getLink();
	}
	
	public String getLink() {
		return link;
	}
}
