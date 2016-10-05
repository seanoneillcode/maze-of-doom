package simulation.level;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import business.DefaultConstants;
import core.Vector;
import simulation.Event;
import simulation.dialog.Dialog;
import simulation.entity.CollisionHandler;
import simulation.entity.DialogEntity;
import simulation.entity.Enemy;
import simulation.entity.Fall;
import simulation.entity.Pickup;
import simulation.entity.Player;
import simulation.entity.mechanism.Mechanism;
import simulation.entity.obsticle.Obsticle;
import simulation.entity.state.AttackState;
import simulation.entity.state.EntityState;

public class Level {

	private Map<String, Node> nodes;
	private Node activeNode;
	private List<Enemy> activeEnemies;
	private List<Pickup> activePickups;
	private List<Obsticle> activeObsticles;
	private List<Mechanism> activeMechanisms;
	private List<Fall> activeFalls;	
	private boolean loadNode;
	private Event loadMap;
	private Node newNode;
	private Link linkTo;
	private List<DialogEntity> activeDialogEntitys;
	private Dialog activeDialog;
	
	public Level() {
		nodes = new HashMap<String, Node>();
		nodes.put("house", NodeLoader.load("nodes/house/house.tmx", "house"));
//		nodes.put("clearing", NodeLoader.load("nodes/clearing/clearing.tmx", "clearing"));
		nodes.put("boulder", NodeLoader.load("nodes/boulder/boulder.tmx", "boulder"));
		nodes.put("stairs", NodeLoader.load("nodes/stairs/stairs.tmx", "stairs"));
		nodes.put("door", NodeLoader.load("nodes/door/door.tmx", "door"));
		nodes.put("boulderpuzzle", NodeLoader.load("nodes/boulder/boulderpuzzle01.tmx", "boulderpuzzle"));
		nodes.put("castlelevel01", NodeLoader.load("nodes/door/castlelevel01.tmx", "castlelevel01"));
		nodes.put("castlelevel02", NodeLoader.load("nodes/door/castlelevel02.tmx", "castlelevel02"));
		nodes.put("clearing", NodeLoader.load("nodes/house-front/front.tmx", "clearing"));
		activeNode = nodes.get("house");
		activeEnemies = activeNode.getEnemies();
		activePickups = activeNode.getPickups();
		activeObsticles = activeNode.getObsticles();
		activeMechanisms = activeNode.getMechanisms();
		activeFalls = activeNode.getFalls();
		activeDialogEntitys = activeNode.getDialogEntitys();
		loadNode = true;
		loadMap = new Event(DefaultConstants.MAP_LOAD_DURATION);
		activeDialog = null;
	}
	
	public List<DialogEntity> getDialogEntitys() {
		return activeDialogEntitys;
	}
	
	public Dialog getActiveDialog() {
		return activeDialog;
	}
	
	public List<Mechanism> getMechanisms() {
		return activeMechanisms;
	}
	
	public List<Obsticle> getObsticles() {
		return activeObsticles;
	}
	
	public Node getActiveNode() {
		return activeNode;
	}
	
	public TiledMap getMap() {
		return activeNode.getMap();
	}
	
	public List<Enemy> getEnemies() {
		return activeEnemies;
	}
	
	public List<Pickup> getPickups() {
		return activePickups;
	}
	
	public boolean doesMapNeedLoading() {
		return loadNode;
	}
	
	private Link getLinkWithNodeName(String name, List<Link> links) {
		for (Link link : links) {
			if (link.getNodeName().equals(name)) {
				return link;
			}
		}
		return null;
	}
	
	public void update(Player player, float delta) {
		if (activeDialog != null) {
			activeDialog.update(delta);
		} else {
			loadMap.update(delta);
			if (loadMap.isDone()) {
				if (!loadNode) {
					actuallyLoadTheNextMap(player);
					loadMap.reset();
				}
				loadNode = true;
			} else {
				loadNode = false;
			}
			if (!loadMap.isStarted()) {
				updateEnemies(player, delta);
				updatePickups(player, delta);
				updateLinks(player, activeNode.getLinks());
				updateObsticles(player, delta);
				updateMechanisms(player, delta);
				updateFalls(player, delta);
				updateDialogEntities(player, delta);
			}
		}
	}
	
	public void use(Player player) {
		if (activeDialog != null) {
			if (activeDialog.isDone()) {
				activeDialog.reset();
				activeDialog = null;
				player.stopUsing();
				player.update(0);
			}
		}
	}
	
	private void updateDialogEntities(Player player, float delta) {
		if (activeDialogEntitys.size() > 0) {
			for (DialogEntity dialogEntity : activeDialogEntitys) {
				
				if (CollisionHandler.isColliding(player, dialogEntity, delta)) {
					CollisionHandler.resolveCollision(player, dialogEntity, delta);
				}
				if (CollisionHandler.isColliding(dialogEntity, player.getUseEntity())) {
					activeDialog = dialogEntity.getDialog();
				}
			}
		}
	}
	
	private void updateFalls(Player player, float delta) {
		if (activeFalls.size() > 0) {
			for (Fall fall : activeFalls) {
				if (CollisionHandler.isColliding(fall, player.getEntity())) {
					
					newNode = activeNode;
					linkTo = getLinkWithNodeName(fall.getLink(), newNode.getLinks());
					player.damage(1);
					loadMap.start();
					break;
				}
			}
		}
	}
	
	private void updateMechanisms(Player player, float delta) {
		if (activeMechanisms.size() > 0) {
			for (Mechanism mechanism : activeMechanisms) {
				
				
				if (CollisionHandler.isColliding(mechanism, player.getEntity())) {
					mechanism.handleCollision(player, delta);
				}
				for (Obsticle obsticle : activeObsticles) {
					if (CollisionHandler.isColliding(mechanism, obsticle.getEntity())) {
						mechanism.handleCollision(obsticle, delta);
					}
				}
				mechanism.update(player, delta);
			}
		}
	}
	
	public boolean isLoading() {
		return loadMap.isStarted() && !loadMap.isDone();
	}
	
	public boolean isPaused() {
		return activeDialog != null;
	}
	
	public float getLoadPercentage() {
		return loadMap.percentDone();
	}
	
	private void updateEnemies(Player player, float delta) {
		if (activeEnemies.size() > 0) {
			for (Enemy enemy : activeEnemies) {
				if (enemy.getEntityState() == EntityState.DEAD) {
					continue;
				}
				enemy.update(delta);
				enemy.think(player, delta);
				CollisionHandler.resolveCollision(enemy, activeNode, delta);
				if (CollisionHandler.isColliding(player.getEntity(), enemy.getEntity())) {
					if (player.canBeDamaged()) {
						player.damage(1);
						player.impulse(Vector.getUnitVector(enemy.getPosition(), player.getPosition()).multiply(4));
					}
				}
				if (player.getAttackState() == AttackState.END && CollisionHandler.isColliding(player.getWeaponEntity(), enemy.getEntity())) {
					if (enemy.canBeDamaged()) {
						enemy.damage(1);
						enemy.impulse(Vector.getUnitVector(player.getPosition(), enemy.getPosition()).multiply(6));
					}
				}
				for (Enemy enemyOther : activeEnemies) {
					if (enemyOther != enemy && enemyOther.getEntityState() != EntityState.DEAD) {
						if (CollisionHandler.isColliding(enemyOther.getEntity(), enemy.getEntity())) {
							enemy.impulse(Vector.getUnitVector(enemyOther.getPosition(), enemy.getPosition()).multiply(2));
							enemyOther.impulse(Vector.getUnitVector(enemy.getPosition(), enemyOther.getPosition()).multiply(2));
						}
					}
				}
				if (activeFalls.size() > 0) {
					for (Fall fall : activeFalls) {
						if (CollisionHandler.isColliding(enemy, fall, delta)) {
							CollisionHandler.resolveCollision(enemy, fall, delta);
							if (CollisionHandler.isColliding(enemy, fall, delta)) {
								enemy.damage(2);
								enemy.impulse(Vector.getUnitVector(player.getPosition(), enemy.getPosition()).multiply(3));
							}
						}
					}
				}
			}
		}
	}
	
	private void updatePickups(Player player, float delta) {
		if (activePickups.size() > 0) {
			for (Pickup pickup : activePickups) {
				if (pickup.getState() == EntityState.DEAD) {
					continue;
				}
				if (CollisionHandler.isColliding(player.getEntity(), pickup)) {
					pickup.getPickedUp(player);
				}
			}
		}
	}
	
	private void updateObsticles(Player player, float delta) {
		if (activeObsticles.size() > 0) {
			for (Obsticle obsticle : activeObsticles) {
				if (obsticle.getEntity().getState() == EntityState.DEAD) {
					continue;
				}
				obsticle.update(delta);
				if (CollisionHandler.isColliding(player, obsticle.getEntity(), delta)) {
					CollisionHandler.resolveCollision(player, obsticle.getEntity(), delta);
				}
				if (CollisionHandler.isColliding(player.getUseEntity(), obsticle.getEntity())) {
					obsticle.handleCollision(player);
				}
				CollisionHandler.resolveCollision(obsticle, activeNode, delta);
			}
		}
	}
	
	private void updateLinks(Player player, List<Link> links) {
		for (Link link : links) {
			if (CollisionHandler.isColliding(link.getEntity(), player.getEntity())) {
				loadMap.start();
				newNode = nodes.get(link.getNodeName());
				linkTo = getLinkWithNodeName(activeNode.getName(), newNode.getLinks());
				break;
			}
		}
	}
	
	private void actuallyLoadTheNextMap(Player player) {
		Node oldNode = activeNode;
		Link linkFrom = getLinkWithNodeName(newNode.getName(), oldNode.getLinks());
		Vector offset = new Vector();
		if (linkFrom != null) {
			offset = linkFrom.getEntity().getPosition().sub(player.getPosition());
		}
		player.getEntity().pos = new Vector(linkTo.getEntity().pos);
		activeNode = newNode;
		activeEnemies = activeNode.getEnemies();
		activePickups = activeNode.getPickups();
		activeObsticles = activeNode.getObsticles();
		activeMechanisms = activeNode.getMechanisms();
		activeFalls = activeNode.getFalls();
		activeDialogEntitys = activeNode.getDialogEntitys();
		activeDialog = null;
		player.setMovement(new Vector());
		player.setPhysics(new Vector());
		switch (linkTo.getDirection()) {
		case DOWN:
			player.getEntity().pos.y += DefaultConstants.TILE_SIZE;
			player.getEntity().pos.x -= offset.x;
			break;
		case UP:
			player.getEntity().pos.y -= DefaultConstants.TILE_SIZE;
			player.getEntity().pos.x -= offset.x;
			break;
		case RIGHT:
			player.getEntity().pos.x -= DefaultConstants.TILE_SIZE;
			player.getEntity().pos.y -= offset.y;
			break;
		case LEFT:
			player.getEntity().pos.x += DefaultConstants.TILE_SIZE;
			player.getEntity().pos.y -= offset.y;
			break;
		}
	}
}
