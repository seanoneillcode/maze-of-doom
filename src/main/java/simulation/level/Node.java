package simulation.level;

import java.util.ArrayList;
import java.util.List;


import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import core.Vector;
import simulation.entity.DialogEntity;
import simulation.entity.Enemy;
import simulation.entity.Entity;
import simulation.entity.EntityType;
import simulation.entity.Fall;
import simulation.entity.Pickup;
import simulation.entity.mechanism.Mechanism;
import simulation.entity.mechanism.MechanismFactory;
import simulation.entity.obsticle.Obsticle;
import simulation.entity.obsticle.ObsticleFactory;

public class Node {
	
	private Vector position;
	private Vector size;
	boolean[][] walls;
	TiledMap map;
	List<Link> links;
	String name;
	List<Enemy> enemies;
	List<Obsticle> obsticles;
	TiledMapTileLayer coverLayer;
	List<Mechanism> mechanisms;
	List<DialogEntity> dialogEntitys;
	List<Entity> entities = new ArrayList<Entity>();
	
	public void setDialogEntitys(List<DialogEntity> dialogEntitys) {
		this.dialogEntitys = dialogEntitys;
	}
	
	public List<DialogEntity> getDialogEntitys() {
		List<DialogEntity> copy = new ArrayList<DialogEntity>();
		for (DialogEntity dialogEntity : dialogEntitys) {
			copy.add(new DialogEntity(dialogEntity));
		}
		return copy;
	}
	
	public void setMechanisms(List<Mechanism> mechanisms) {
		this.mechanisms = mechanisms;
	}
	
	public List<Mechanism> getMechanisms() {
		List<Mechanism> copy = new ArrayList<Mechanism>();
		for (Mechanism mechanism : mechanisms) {
			copy.add(MechanismFactory.getMechanism(mechanism));
		}
		return copy;
	}
	
	public TiledMapTileLayer getCoverLayer() {
		return coverLayer;
	}
	
	public void setCoverLayer(TiledMapTileLayer coverLayer) {
		this.coverLayer = coverLayer;
	}
	
	public void setObsticles(List<Obsticle> obsticles) {
		this.obsticles = obsticles;
	}
	
	public List<Obsticle> getObsticles() {
		List<Obsticle> copy = new ArrayList<Obsticle>();
		for (Obsticle obsticle : obsticles) {
			copy.add(ObsticleFactory.getObsticle(obsticle));
		}
		return copy;
	}
	
	public List<Enemy> getEnemies() {
		List<Enemy> copy = new ArrayList<Enemy>();
		for (Enemy enemy : enemies) {
			copy.add(new Enemy(enemy));
		}
		return copy;
	}

	public void addEntities(List<Entity> entities) {
		this.entities.addAll(entities);
	}

	public List<Entity> getEntities() {
		List<Entity> copy = new ArrayList<Entity>();
		for (Entity entity : entities) {
			if (entity.getType() == EntityType.FALL) {
				copy.add(new Fall((Fall) entity));
			}
			if (entity.getType() == EntityType.PICKUP) {
				copy.add(new Pickup((Pickup) entity));
			}
		}
		return copy;
	}
	
	public void setEnemies(List<Enemy> enemies) {
		this.enemies = enemies;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public Vector getPosition() {
		return position;
	}
	
	public void setPosition(Vector position) {
		this.position = position;
	}
	
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	
	public void setMap(TiledMap map) {
		this.map = map;
	}
	
	public void setWalls(boolean[][] walls) {
		this.walls = walls;
	}
	
	public boolean[][] getWalls() {
		return walls;
	}
	
	public TiledMap getMap() {
		return map;
	}
	
	public void setSize(Vector size) {
		this.size = new Vector(size);
	}
	
	public Vector getSize() {
		return new Vector(size);
	}
	
	public List<Link> getLinks() {
		return links;
	}
	
}
