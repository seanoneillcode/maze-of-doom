package com.halycon.maze.simulation.entity.mechanism;

import java.util.List;

import com.halycon.maze.core.Vector;
import com.halycon.maze.simulation.entity.MovingEntity;

public class ActivePressurePlate extends Mechanism {

	public ActivePressurePlate(Mechanism mechanism) {
		super(mechanism);
	}
	
	public ActivePressurePlate(Vector position, MechanismType type, boolean active, String id, List<String> pairIds) {
		super(position, type, active, id, pairIds);
	}
	
	@Override
	public void update(MovingEntity entity, float delta) {
		if (isActive()) {
			for (String pairId : pairIds) {
				send(pairId, false);
			}
		}
	}

	@Override
	public void receive(boolean value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleCollision(MovingEntity entity, float delta) {
		setActive(true);
	}
}
