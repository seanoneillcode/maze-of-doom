package com.halycon.maze.simulation.entity;

import com.halycon.maze.simulation.entity.state.DamageState;
import com.halycon.maze.simulation.entity.state.EntityState;

public interface CanBeDamaged {

	public int getHealth();
	public int getMaxHealth();
	public void damage(int amount);
	public EntityState getEntityState();
	public boolean canBeDamaged();
	public DamageState getDamageState();
}
