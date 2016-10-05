package com.halycon.maze.simulation.entity;

import com.halycon.maze.core.Vector;
import com.halycon.maze.simulation.entity.state.AttackState;
import com.halycon.maze.simulation.entity.state.CanBeAnimated;
import com.halycon.maze.simulation.entity.state.DamageState;
import com.halycon.maze.simulation.entity.state.EntityState;

public class Enemy extends MovingEntity implements CanBeDamaged, CanBeAnimated {
	
	private int health;
	private EnemyIntelligence ai;
	private DamageState damageState;
	private float damageCooldown;
	private static final float DAMAGE_COOLDOWN_START = 0.6f;
	private int maxHealth;
	
	public Enemy(Vector position, Vector size, Vector speed, int health) {
		super(position, size, speed);
		this.health = health;
		this.maxHealth = health;
		ai = new EnemyIntelligence();
	}
	
	public Enemy(Enemy enemy) {
		this(enemy.getPosition(), enemy.getSize(), enemy.getSpeed(), enemy.getHealth());
	}
	
	public Enemy(Vector position, EnemyType enemyType) {
		this(position, enemyType.getSize(), enemyType.getSpeed(), enemyType.getHealth());
	}
	
	@Override
	public int getHealth() {
		return health;
	}
	
	@Override
	public int getMaxHealth() {
		return maxHealth;
	}

	@Override
	public void damage(int amount) {
		if (canBeDamaged()) {
			health -= amount;
			if (health <= 0) {
				getEntity().die();
			}
			damageState = DamageState.HURTING;
			damageCooldown = DAMAGE_COOLDOWN_START;
		}
	}

	@Override
	public EntityState getEntityState() {
		return getEntity().getState();
	}

	@Override
	public boolean canBeDamaged() {
		return getEntity().getState() == EntityState.ALIVE && damageState == DamageState.NONE;
	}

	@Override
	public DamageState getDamageState() {
		return DamageState.NONE;
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		if (damageState == DamageState.HURTING) {
			damageCooldown -= delta;
		}
		if (damageCooldown <= 0) {
			damageState = DamageState.NONE;
		}
	}
	
	public void think(Player player, float delta) {
		ai.think(player, this, delta);
	}

	@Override
	public AttackState getAttackState() {
		// TODO Auto-generated method stub
		return null;
	}
}
