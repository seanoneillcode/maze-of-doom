package com.halycon.maze.simulation.level;

import com.halycon.maze.core.Direction;
import com.halycon.maze.core.Vector;
import com.halycon.maze.simulation.entity.Entity;


public class Link {

	private Entity entity;	
	private String nodeName;
	private Direction direction;
	
	public Link(String nodeName, Direction direction, Vector position, Vector size) {
		this.direction = direction;
		this.entity = new Entity(position, size);
		this.nodeName = nodeName;
	}
	
	public String getNodeName() {
		return nodeName;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public Entity getEntity() {
		return entity;
	}
}
