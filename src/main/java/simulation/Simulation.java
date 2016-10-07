package simulation;

import business.DefaultConstants;
import core.Vector;
import simulation.entity.CollisionHandler;
import simulation.entity.Player;
import simulation.entity.state.EntityState;
import simulation.level.Level;


public class Simulation {
	
	private Player player;
	private Event postDeath;
	private boolean simulationDone;
	private Level level;
	
	public Simulation() {
		player = new Player(new Vector(128,116));
		level = new Level();
		postDeath = new Event(DefaultConstants.DEATH_DURATION);
		simulationDone = false;
	}

	public boolean isDone() {
		return simulationDone;
	}
	
	public Level getLevel() {
		return level;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public float getLoadPercentage() {
		if (player.getEntityState() == EntityState.DEAD) {
			return postDeath.percentDone();
		}
		if (level.isLoading()) {
			return level.getLoadPercentage();
		}
		return 0;
	}
	
	public void update(float delta) {
		if (player.getEntityState() == EntityState.DEAD) {
			postDeath.start();
		}
		postDeath.update(delta);
		if (postDeath.isDone()) {
			simulationDone = true;
		}
		if (!postDeath.isStarted()) {
			level.update(player, delta);
			CollisionHandler.resolveCollision(player, level.getActiveNode(), delta);
			if (isActive()) {
				player.update(delta);				
			}
		}
	}
	
	public boolean isActive() {
		return !level.isPaused() && !level.isLoading();
	}
}
