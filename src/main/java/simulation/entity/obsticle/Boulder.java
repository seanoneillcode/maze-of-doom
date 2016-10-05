package simulation.entity.obsticle;

import core.Vector;
import simulation.entity.Player;

public class Boulder extends Obsticle {

	public Boulder(Obsticle obsticle) {
		super(obsticle);
	}
	
	public Boulder(Vector position, ObsticleType type) {
		super(position, type);
	}

	@Override
	public void handleCollision(Player player) {
		this.impulse(player.getDirection().getVector().multiply(3.2f));
	}
}
