package com.halycon.maze.simulation.entity;

import com.halycon.maze.business.DefaultConstants;
import com.halycon.maze.core.Vector;

public class EnemyIntelligence {
	
	private static final float ATTACK_RANGE_SQUARED = DefaultConstants.TILE_SIZE * DefaultConstants.TILE_SIZE * 25;
	private static final float AWAKE_RANGE_SQUARED = DefaultConstants.TILE_SIZE * DefaultConstants.TILE_SIZE * 36;
	
	public void think(Player player, Enemy self, float delta) {
		Vector ppos = player.getPosition();
		Vector spos = self.getPosition();
		
		if (isPlayerWithinRange(player, self)) {
			float movex = ppos.x - spos.x;
			float movey = ppos.y - spos.y;
			
			if (Math.abs(movex) > Math.abs(movey)) {
				moveVertical(self, movey, delta);
				moveHorizontal(self, movex, delta);
			} else {
				moveHorizontal(self, movex, delta);
				moveVertical(self, movey, delta);
			}
		}
		
	}
	
	private boolean isPlayerWithinRange(Player player, Enemy self) {
		Vector ppos = player.getPosition();
		Vector spos = self.getPosition();
		float distanceSquared = ((ppos.x - spos.x) * (ppos.x - spos.x)) + ((ppos.y - spos.y) * (ppos.y - spos.y));
		return distanceSquared < ATTACK_RANGE_SQUARED;
	}
	
	private void moveHorizontal(Enemy self, float amount, float delta) {
		if (Math.abs(amount) >= self.getSize().x) {
			if (amount > 0) {
				self.moveRight(delta);
			} else {
				self.moveLeft(delta);
			}
		}
	}
	
	private void moveVertical(Enemy self, float amount, float delta) {
		if (Math.abs(amount) >= self.getSize().y) {
			if (amount > 0) {
				self.moveUp(delta);
			} else {
				self.moveDown(delta);
			}
		}
	}
}
