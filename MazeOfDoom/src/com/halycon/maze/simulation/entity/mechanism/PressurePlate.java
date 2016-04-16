package com.halycon.maze.simulation.entity.mechanism;

import java.util.List;

import com.halycon.maze.core.Vector;
import com.halycon.maze.simulation.entity.MovingEntity;

public class PressurePlate extends Mechanism {
	
	public PressurePlate(Vector position, MechanismType type, boolean active, String id, List<String> pairIds) {
		super(position, type, active, id, pairIds);
	}

	public PressurePlate(Mechanism mechanism) {
		super(mechanism);
	}
	
	public void handleCollision(MovingEntity entity, float delta) {
		setActive(true);
		for (String pairId : pairIds) {
			send(pairId, false);
		}
	}

	@Override
	public void receive(boolean active) {
		
	}

	@Override
	public void update(MovingEntity entity, float delta) {
		setActive(false);
	}
}
