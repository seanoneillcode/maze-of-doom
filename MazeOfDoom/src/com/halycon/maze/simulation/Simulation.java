package com.halycon.maze.simulation;

import com.halycon.maze.business.DefaultConstants;
import com.halycon.maze.core.Vector;
import com.halycon.maze.simulation.entity.CollisionHandler;
import com.halycon.maze.simulation.entity.Player;
import com.halycon.maze.simulation.entity.state.EntityState;
import com.halycon.maze.simulation.level.Level;


public class Simulation {
	
	private Player player;
	private Event postDeath;
	private boolean simulationDone;
	private Level level;
	
	public Simulation() {
		player = new Player(new Vector(80,96));
		level = new Level();
		postDeath = new Event(DefaultConstants.DEATH_DURATION);
		simulationDone = false;
	}
	
	public Event getPlayerDeathEvent() {
		return postDeath;
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
			if (!level.isLoading() && !level.isPaused()) {
				player.update(delta);				
			}
		}
	}
	
	public boolean isActive() {
		return !this.getLevel().isPaused() && !this.getLevel().isLoading();
	}
}
