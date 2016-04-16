package com.halycon.maze.renderer.drawables;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.halycon.maze.business.DefaultConstants;
import com.halycon.maze.core.Vector;
import com.halycon.maze.renderer.ImageList;
import com.halycon.maze.renderer.animation.EntityAnimationHandler;
import com.halycon.maze.renderer.animation.EntityAttackAnimation;
import com.halycon.maze.renderer.animation.EntityDeathAnimation;
import com.halycon.maze.renderer.animation.EntityHurtAnimation;
import com.halycon.maze.renderer.animation.EntityWalkAnimation;
import com.halycon.maze.simulation.common.Direction;
import com.halycon.maze.simulation.entity.Player;
import com.halycon.maze.simulation.entity.state.AttackState;
import com.halycon.maze.simulation.entity.state.DamageState;
import com.halycon.maze.simulation.entity.state.EntityState;

public class PlayerDrawable implements Drawable {

	private EntityAnimationHandler animationHandler;
	private Player player;
	
	public PlayerDrawable(Player player) {
		this.player = player;
		animationHandler = new EntityAnimationHandler();
		animationHandler.addEntityAnimation("walk", new EntityWalkAnimation(new ImageList(
				"characters/walkanimation01.png", 
				new Vector(DefaultConstants.TILE_SIZE, DefaultConstants.TILE_SIZE), 
				8,
				new Vector(DefaultConstants.PLAYER_OFFSET))));
		animationHandler.addEntityAnimation("hurt", new EntityHurtAnimation(new ImageList(
				"characters/hurtanimation.png", 
				new Vector(DefaultConstants.TILE_SIZE, DefaultConstants.TILE_SIZE), 
				2,
				new Vector())));
		Map<Direction, ImageList> attackImages = new HashMap<Direction, ImageList>();
		attackImages.put(Direction.DOWN, new ImageList("characters/swordattackplayerdown.png", 
				new Vector(DefaultConstants.TILE_SIZE * 2, DefaultConstants.TILE_SIZE * 2),
				3,
				new Vector(-DefaultConstants.TILE_SIZE, -DefaultConstants.TILE_SIZE)));
		attackImages.put(Direction.UP, new ImageList("characters/swordattackplayerup.png", 
				new Vector(DefaultConstants.TILE_SIZE * 2, DefaultConstants.TILE_SIZE * 2),
				3,
				new Vector()));
		attackImages.put(Direction.LEFT, new ImageList("characters/swordattackplayerleft.png", 
				new Vector(DefaultConstants.TILE_SIZE * 2, DefaultConstants.TILE_SIZE * 2),
				3,
				new Vector(-DefaultConstants.TILE_SIZE, 0)));
		attackImages.put(Direction.RIGHT, new ImageList("characters/swordattackplayerright.png", 
				new Vector(DefaultConstants.TILE_SIZE * 2, DefaultConstants.TILE_SIZE * 2),
				3,
				new Vector()));
		animationHandler.addEntityAnimation("attack", new EntityAttackAnimation(attackImages));
		
		animationHandler.addEntityAnimation("die", new EntityDeathAnimation(new ImageList(
				"characters/dieanimation.png", 
				new Vector(DefaultConstants.TILE_SIZE, DefaultConstants.TILE_SIZE), 
				4,
				new Vector(DefaultConstants.PLAYER_OFFSET))));
	}
	
	public void draw(float delta, SpriteBatch spriteBatch) {
		if (player.getDamageState() == DamageState.HURTING) {
			animationHandler.setActiveAnimation("hurt");
		} else {
			if (player.getAttackState() == AttackState.IDLE) {
				animationHandler.setActiveAnimation("walk");
			} else {
				animationHandler.setActiveAnimation("attack");
			}
		}
		if (player.getEntityState() == EntityState.DEAD) {
			animationHandler.setActiveAnimation("die");
		}
		animationHandler.update(delta, player);
		animationHandler.draw(spriteBatch);
	}

	public float getZ() {
		return getPosition().y;
	}
	
	@Override
	public int compareTo(Drawable other) {
		return (int) (other.getZ() - getZ());
	}

	@Override
	public Vector getPosition() {
		return player.getPosition();
	}
}
