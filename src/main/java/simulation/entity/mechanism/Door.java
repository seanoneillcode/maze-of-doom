package simulation.entity.mechanism;

import java.util.List;

import core.Vector;
import simulation.entity.CollisionHandler;
import simulation.entity.MovingEntity;

public class Door extends Mechanism {

	public Door(Vector position, MechanismType type, boolean active, String id, List<String> pairIds){
		super(position, type, active, id, pairIds);
	}
	
	public Door(Mechanism mechanism){
		super(mechanism);
	}
	
	public void handleCollision(MovingEntity entity, float delta) {
		if (isActive()) {
			CollisionHandler.resolveCollision(entity, this, delta);
		}
	}

	@Override
	public void update(MovingEntity entity, float delta) {
		setActive(true);
	}

	@Override
	public void receive(boolean active) {
		setActive(active);
	}

	@Override
	public void send(String id, boolean value) {
		// nothing
	}
}
