package com.halycon.maze.simulation.level;

import java.util.ArrayList;
import java.util.List;


import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.halycon.maze.core.Vector;
import com.halycon.maze.simulation.entity.DialogEntity;
import com.halycon.maze.simulation.entity.Enemy;
import com.halycon.maze.simulation.entity.Fall;
import com.halycon.maze.simulation.entity.Pickup;
import com.halycon.maze.simulation.entity.mechanism.Mechanism;
import com.halycon.maze.simulation.entity.mechanism.MechanismFactory;
import com.halycon.maze.simulation.entity.obsticle.Obsticle;
import com.halycon.maze.simulation.entity.obsticle.ObsticleFactory;

public class Node {
	
	private Vector position;
	private Vector size;
	boolean[][] walls;
	TiledMap map;
	List<Link> links;
	String name;
	List<Enemy> enemies;
	List<Pickup> pickups;
	List<Obsticle> obsticles;
	TiledMapTileLayer coverLayer;
	List<Mechanism> mechanisms;
	List<Fall> falls;
	List<DialogEntity> dialogEntitys;
	
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
	
	public void setFalls(List<Fall> falls) {
		this.falls = falls;
	}
	
	public List<Fall> getFalls() {
		List<Fall> copy = new ArrayList<Fall>();
		for (Fall fall : falls) {
			copy.add(new Fall(fall));
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
	
	public List<Pickup> getPickups() {
		List<Pickup> copy = new ArrayList<Pickup>();
		for (Pickup pickup : pickups) {
			copy.add(new Pickup(pickup));
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
	
	public void setPickups(List<Pickup> pickups) {
		this.pickups = pickups;
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
