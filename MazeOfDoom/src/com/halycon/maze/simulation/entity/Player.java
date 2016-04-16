package com.halycon.maze.simulation.entity;

import com.halycon.maze.business.DefaultConstants;
import com.halycon.maze.core.Vector;
import com.halycon.maze.simulation.entity.state.AttackState;
import com.halycon.maze.simulation.entity.state.CanBeAnimated;
import com.halycon.maze.simulation.entity.state.DamageState;
import com.halycon.maze.simulation.entity.state.EntityState;


public class Player extends MovingEntity implements CanBeAnimated, CanBeDamaged {

	private Weapon weapon;
	private int health;
	private DamageState damageState;
	private float damageCooldown;
	private static final float DAMAGE_COOLDOWN_START = 0.3f;
	private int maxHealth;
	private boolean dashing;
	private float dashTimer;
	private static final float DASH_TIME_LIMIT = 0.4f;
	private static final float DASH_INITIAL = 4f;
	private Entity useEntity;
	private boolean isUsing;
	private Inventory inventory;
	
	public Player(Vector position) {
		super(position, DefaultConstants.PLAYER_SIZE, DefaultConstants.PLAYER_SPEED);
		weapon = new Weapon("sword", position);
		health = maxHealth = DefaultConstants.DEFAULT_PLAYER_HEALTH;
		damageState = DamageState.NONE;
		damageCooldown = 0f;
		dashTimer = 0;
		isUsing = false;
		useEntity = new Entity(position, DefaultConstants.USE_SIZE);
		inventory = new Inventory();
		inventory.addItem(InventoryItem.SWORD);
	}
	
	public void attack() {
		if (hasSword() && weapon.canAttack()) {
			weapon.attack(getDirection());
		}
	}
	
	private boolean hasSword() {
		return inventory.hasItem(InventoryItem.SWORD);
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public boolean canMove() {
		return true;
	}
	
	public Entity getWeaponEntity(){
		return weapon;
	}
	
	public AttackState getAttackState() {
		return weapon.getAttackState();
	}
	
	@Override
	public void update(float delta) {
		if (damageState == DamageState.HURTING) {
			damageCooldown -= delta;
		}
		if (damageCooldown <= 0) {
			damageState = DamageState.NONE;
		}
		weapon.update(delta, getPosition());
		if (dashing) {
			dashTimer -= delta;
		}
		if (dashTimer < 0) {
			dashing = false;
		}
		super.update(delta);
		updateUseEntity();
	}

	private void updateUseEntity() {
		Vector pos = new Vector(getPosition());
		Vector offset = getSize().multiply(0.5f);
		pos = pos.add(offset);
		
		Vector size = useEntity.getSize();
		if (isUsing) {
			switch (getDirection()) {
			case DOWN:
				pos.y -= offset.y + size.y;
				break;
			case UP:
				pos.y += offset.y;
				break;
			case LEFT:
				pos.x -= offset.x + size.x;
				break;
			case RIGHT:
				pos.x += offset.x;
				break;
			}
		}
		useEntity.pos = pos;
		isUsing = false;
	}
	
	public void use() {
		isUsing = true;
	}
	
	public void stopUsing() {
		isUsing = false;
	}
	
	public Entity getUseEntity() {
		return useEntity;
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
			if (health < 0) {
				getEntity().die();
			}
			damageState = DamageState.HURTING;
			damageCooldown = DAMAGE_COOLDOWN_START;
		}
	}

	public void heal(int amount) {
		health += amount;
		if (health > maxHealth) {
			health = maxHealth;
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
		return damageState;
	}
		
	public void dash() {
		if (!dashing) {
			dashing = true;
			dashTimer = DASH_TIME_LIMIT;
			impulse(getDirection().getVector().multiply(DASH_INITIAL));
		}
	}
}
