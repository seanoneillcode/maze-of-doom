package simulation.level;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import business.DefaultConstants;
import core.Vector;
import simulation.Event;
import simulation.story.Actor;
import simulation.story.Scene;
import simulation.story.Story;
import simulation.dialog.Dialog;
import simulation.entity.*;
import simulation.entity.mechanism.Mechanism;
import simulation.entity.obsticle.Obsticle;
import simulation.entity.state.AttackState;
import simulation.entity.state.EntityState;

public class Level {

	private Map<String, Node> nodes;
	private Node activeNode;
	private List<Enemy> activeEnemies;
	private List<Obsticle> activeObsticles;
	private List<Mechanism> activeMechanisms;
	private List<Entity> activeEntities;
	private boolean loadNode;
	private Event loadMap;
	private Node newNode;
	private Link linkTo;
	private List<DialogEntity> activeDialogEntitys;
    private List<SceneEntity> activeSceneEntities;
	private Dialog activeDialog;
    private Scene activeScene;
    private Story story;

	public Level() {
        Story story = new Story();
		nodes = new HashMap<String, Node>();
        nodes.put("burning-alley", NodeLoader.load("nodes/burning-house/burning-alley.tmx", "burning-alley", story));
		nodes.put("house", NodeLoader.load("nodes/house/house.tmx", "house", story));
		nodes.put("clearing", NodeLoader.load("nodes/clearing/clearing.tmx", "clearing", story));
		nodes.put("boulder", NodeLoader.load("nodes/boulder/boulder.tmx", "boulder", story));
		nodes.put("stairs", NodeLoader.load("nodes/stairs/stairs.tmx", "stairs", story));
		nodes.put("door", NodeLoader.load("nodes/door/door.tmx", "door", story));
		nodes.put("boulderpuzzle", NodeLoader.load("nodes/boulder/boulderpuzzle01.tmx", "boulderpuzzle", story));
		nodes.put("castlelevel01", NodeLoader.load("nodes/door/castlelevel01.tmx", "castlelevel01", story));
		nodes.put("castlelevel02", NodeLoader.load("nodes/door/castlelevel02.tmx", "castlelevel02", story));
		nodes.put("burning-house", NodeLoader.load("nodes/burning-house/burning-house.tmx", "burning-house", story));

		activeNode = nodes.get("burning-alley");
		activeEnemies = activeNode.getEnemies();
		activeObsticles = activeNode.getObsticles();
		activeMechanisms = activeNode.getMechanisms();
		activeEntities = activeNode.getEntities();
		activeDialogEntitys = activeNode.getDialogEntitys();
        activeSceneEntities = activeNode.getSceneEntities();
        activeScene = null;
		loadNode = true;
		loadMap = new Event(DefaultConstants.MAP_LOAD_DURATION);
		activeDialog = null;
	}

	public boolean hasWeather() {
		return activeNode.hasWeather();
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

	public List<Entity> getEntities() {
		return activeEntities;
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

	public Vector getStartPosition() {
        Link link = activeNode.getLinks().get(0);
        Vector start = link.getEntity().getPosition();
        start = start.sub(link.getDirection().getVector().multiply(16));
		return start;
	}

	public void update(Player player, float delta) {
        if (isPaused()) {
            if (activeDialog != null) {
                activeDialog.update(delta);
            }
            if (activeScene != null) {
                Actor actor = new Actor(player.getEntity());
                activeScene.update(actor, delta);
                activeDialog = activeScene.getDialog();
                if (activeScene.isDone()) {
                    activeScene = null;
                    activeDialog = null;
                }
            }
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
                updateLinks(player, activeNode.getLinks());
                updateObsticles(player, delta);
                updateMechanisms(player, delta);
                updateEntities(player, delta);
                updateDialogEntities(player, delta);
                updateSceneEntities(player, delta);
            }
        }
	}

	public void use(Player player) {
		if (activeDialog != null) {
			activeDialog.continueProgress();
			if (activeDialog.isDone()) {
				activeDialog.reset();
				activeDialog = null;
				player.stopUsing();
				player.update(0);
			}
		}
	}

    private void updateSceneEntities(Player player, float delta) {
        if (activeSceneEntities.size() > 0 && activeScene == null) {
            for (SceneEntity sceneEntity : activeSceneEntities) {
                if (!sceneEntity.getScene().isDone()) {
                    if (CollisionHandler.isColliding(player, sceneEntity, delta)) {
                        activeScene = sceneEntity.getScene();
                        activeScene.start();
                    }
                }
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

	private void updateEntities(Player player, float delta) {
		if (activeEntities.size() > 0) {
			for (Entity entity : activeEntities) {
				if (entity.getType() == EntityType.FALL) {
					if (CollisionHandler.isColliding(entity, player.getEntity())) {
						newNode = activeNode;
						linkTo = getLinkWithNodeName(((Fall)entity).getLink(), newNode.getLinks());
						player.damage(1);
						loadMap.start();
						break;
					}
				}
				if (entity.getType() == EntityType.PICKUP) {
					if (entity.getState() == EntityState.DEAD) {
						continue;
					}
					if (CollisionHandler.isColliding(player.getEntity(), entity)) {
						((Pickup)entity).getPickedUp(player);
					}
					if (CollisionHandler.isColliding(player.getUseEntity(), entity)) {
						((Pickup)entity).getPickedUp(player);
						activeDialog = new Dialog("picked up " + ((Pickup)entity).getPickupType().name().toLowerCase());
					}
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
		return activeDialog != null || activeScene != null;
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
				if (activeEntities.size() > 0) {
					for (Entity entity : activeEntities) {
						if (entity.getType() == EntityType.FALL) {
							if (CollisionHandler.isColliding(enemy, entity, delta)) {
								CollisionHandler.resolveCollision(enemy, entity, delta);
								if (CollisionHandler.isColliding(enemy, entity, delta)) {
									enemy.damage(2);
									enemy.impulse(Vector.getUnitVector(player.getPosition(), enemy.getPosition()).multiply(3));
								}
							}
						}
					}
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
		activeObsticles = activeNode.getObsticles();
		activeMechanisms = activeNode.getMechanisms();
		activeEntities = activeNode.getEntities();
		activeDialogEntitys = activeNode.getDialogEntitys();
        activeSceneEntities = activeNode.getSceneEntities();
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
