package simulation.entity;

import core.Vector;
import simulation.entity.state.AttackState;
import simulation.entity.state.CanBeAnimated;
import simulation.entity.state.DamageState;
import simulation.entity.state.EntityState;

public class Enemy extends MovingEntity implements CanBeDamaged, CanBeAnimated {
	
	private int health;
	private EnemyIntelligence ai;
	private DamageState damageState;
	private float damageCooldown;
	private static final float DAMAGE_COOLDOWN_START = 0.6f;
	private int maxHealth;
	private EnemyType enemyType;

	public Enemy(Vector position, Vector size, Vector speed, int health, EnemyType enemyType) {
		super(position, size, speed);
		this.health = health;
		this.maxHealth = health;
		ai = new EnemyIntelligence();
		this.enemyType = enemyType;
	}
	
	public Enemy(Enemy enemy) {
		this(enemy.getPosition(), enemy.getSize(), enemy.getSpeed(), enemy.getHealth(), enemy.getEnemyType());
	}
	
	public Enemy(Vector position, EnemyType enemyType) {
		this(position, enemyType.getSize(), enemyType.getSpeed(), enemyType.getHealth(), enemyType);
	}

	public EnemyType getEnemyType() {
		return enemyType;
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
		return getEntity().getState() == EntityState.ALIVE
				&& damageState == DamageState.NONE
				&& enemyType == EnemyType.BLOB;
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
		if (enemyType == EnemyType.BLOB) {
			ai.think(player, this, delta);
		}
	}

	@Override
	public AttackState getAttackState() {
		// TODO Auto-generated method stub
		return null;
	}
}
