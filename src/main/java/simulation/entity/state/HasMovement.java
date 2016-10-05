package simulation.entity.state;

import core.Direction;

public interface HasMovement {
	public Direction getDirection();
	public boolean isMoving();
}
