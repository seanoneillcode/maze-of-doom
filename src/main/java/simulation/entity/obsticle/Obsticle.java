package simulation.entity.obsticle;

import core.Vector;
import simulation.entity.MovingEntity;
import simulation.entity.Player;

public abstract class Obsticle extends MovingEntity {

	private ObsticleType type;
	
	public Obsticle(Vector position, ObsticleType type) {
		super(position, type.getSize(), type.getSpeed());
		this.type = type;
	}

	public Obsticle(Obsticle obsticle) {
		super(obsticle.getPosition(), obsticle.getSize(), obsticle.getSpeed());
		this.type = obsticle.getType();
	}
	
	public ObsticleType getType() {
		return type;
	}
	
	public abstract void handleCollision(Player player);
}
