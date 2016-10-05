package simulation.entity;

import simulation.entity.state.DamageState;
import simulation.entity.state.EntityState;

public interface CanBeDamaged {

	public int getHealth();
	public int getMaxHealth();
	public void damage(int amount);
	public EntityState getEntityState();
	public boolean canBeDamaged();
	public DamageState getDamageState();
}
