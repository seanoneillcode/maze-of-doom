package com.halycon.maze.simulation.entity.state;

import com.halycon.maze.core.Direction;

public interface HasMovement {
	public Direction getDirection();
	public boolean isMoving();
}
