package com.halycon.maze.simulation.level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;



import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.halycon.maze.business.DefaultConstants;
import com.halycon.maze.core.Vector;
import com.halycon.maze.simulation.common.Direction;
import com.halycon.maze.simulation.dialog.Dialog;
import com.halycon.maze.simulation.entity.DialogEntity;
import com.halycon.maze.simulation.entity.Enemy;
import com.halycon.maze.simulation.entity.EnemyType;
import com.halycon.maze.simulation.entity.Fall;
import com.halycon.maze.simulation.entity.Pickup;
import com.halycon.maze.simulation.entity.PickupType;
import com.halycon.maze.simulation.entity.mechanism.Mechanism;
import com.halycon.maze.simulation.entity.mechanism.MechanismFactory;
import com.halycon.maze.simulation.entity.mechanism.MechanismType;
import com.halycon.maze.simulation.entity.obsticle.Obsticle;
import com.halycon.maze.simulation.entity.obsticle.ObsticleFactory;
import com.halycon.maze.simulation.entity.obsticle.ObsticleType;

public class NodeLoader {

	private static final String LINK_NAME = "link";
	private static final String NODE_NAME_KEY = "node";
	private static final String DIRECTION_KEY = "direction";
	private static final String ENEMY_NAME = "enemy";
	private static final String PICKUP_NAME = "pickup";
	private static final String TYPE_KEY = "type";
	private static final String OBJECT_LAYER = "objects";
	private static final String WALL_LAYER = "walls";
	private static final String OBSTICLE_NAME = "obsticle";
	private static final String MECHANISM_NAME = "mechanism";
	private static final String COVER_LAYER = "cover";
	private static final String ACTIVE_KEY = "active";
	private static final String ID_KEY = "id";
	private static final String PAIR_ID_KEY = "pairIds";
	private static final String FALL_TYPE = "fall";
	private static final String DIALOG_TYPE = "dialog";
	private static final String TEXT_KEY = "text";
	
	private static final String ID_SPLIT_REGEX = ",";
	
	
	private static final float OBSTICLE_OFFSET = 0.5f;
	private static final float FALL_OFFSET = 8.0f;
	
	
	public static Node load(String mapFile, String name) {
		Node node = new Node();
		
		TiledMap map = new TmxMapLoader().load(mapFile);
		node.setMap(map);
		node.setSize(getSize(map));
		node.setWalls(getWalls(map));
		node.setLinks(getLinks(map));
		node.setPosition(new Vector());
		node.setName(name);
		node.setEnemies(getEnemies(map));
		node.setPickups(getPickups(map));
		node.setObsticles(getObsticles(map));
		node.setCoverLayer(getCover(map));
		node.setMechanisms(getMechanisms(map));
		node.setFalls(getFalls(map));
		node.setDialogEntitys(getDialogEntitys(map));
		return node;
	}
	
	private static List<DialogEntity> getDialogEntitys(TiledMap map) {
		List<DialogEntity> dialogEntitys = new ArrayList<DialogEntity>();
		
		MapLayer layer = map.getLayers().get(OBJECT_LAYER);
		MapObjects mapObjects = layer.getObjects();
		
		Iterator<MapObject> iterator = mapObjects.iterator();
		
		while(iterator.hasNext()) {
			MapObject mapObject = iterator.next();
			if (mapObject.getName().equals(DIALOG_TYPE)) {
				RectangleMapObject rectangleObject = (RectangleMapObject)mapObject;
				Rectangle rectangle = rectangleObject.getRectangle();
				Vector position = new Vector(rectangle.x, rectangle.y);
				Vector size = new Vector(rectangle.width, rectangle.height);
				MapProperties properties = mapObject.getProperties();
				String text = (String)properties.get(TEXT_KEY);
				dialogEntitys.add(new DialogEntity(position, size, new Dialog(text)));
			}
		}
		
		return dialogEntitys;
	}
	
	private static List<Fall> getFalls(TiledMap map) {
		List<Fall> obsticles = new ArrayList<Fall>();
		
		MapLayer layer = map.getLayers().get(OBJECT_LAYER);
		MapObjects mapObjects = layer.getObjects();
		
		Iterator<MapObject> iterator = mapObjects.iterator();
		
		while(iterator.hasNext()) {
			MapObject mapObject = iterator.next();
			if (mapObject.getName().equals(FALL_TYPE)) {
				RectangleMapObject rectangleObject = (RectangleMapObject)mapObject;
				Rectangle rectangle = rectangleObject.getRectangle();
				Vector position = new Vector(rectangle.x, rectangle.y);
				Vector size = new Vector(rectangle.width, rectangle.height);
				MapProperties properties = mapObject.getProperties();
				String link = (String)properties.get(LINK_NAME);
				position = position.add(FALL_OFFSET);
				size = size.sub(FALL_OFFSET * 2);
				obsticles.add(new Fall(position, size, link));
			}
		}
		
		return obsticles;
	}
	
	private static List<Mechanism> getMechanisms(TiledMap map) {
		List<Mechanism> obsticles = new ArrayList<Mechanism>();
		
		MapLayer layer = map.getLayers().get(OBJECT_LAYER);
		MapObjects mapObjects = layer.getObjects();
		
		Iterator<MapObject> iterator = mapObjects.iterator();
		
		while(iterator.hasNext()) {
			MapObject mapObject = iterator.next();
			if (mapObject.getName().equals(MECHANISM_NAME)) {
				MapProperties properties = mapObject.getProperties();
				String type = (String)properties.get(TYPE_KEY);
				String id = (String)properties.get(ID_KEY);
				String pairIdString = (String)properties.get(PAIR_ID_KEY);
				List<String> pairIds = new ArrayList<String>();
				if (pairIdString != null && !pairIdString.isEmpty()) {
					pairIds.addAll(Arrays.asList((pairIdString).split(ID_SPLIT_REGEX)));
				}
				MechanismType mechanismType = MechanismType.valueOf(type.toUpperCase());
				boolean active = Boolean.valueOf((String)properties.get(ACTIVE_KEY));
				RectangleMapObject rectangleObject = (RectangleMapObject)mapObject;
				Rectangle rectangle = rectangleObject.getRectangle();
				Vector position = new Vector(rectangle.x, rectangle.y);
				obsticles.add(MechanismFactory.getMechanism(position, mechanismType, active, id, pairIds));
			}
		}
		
		return obsticles;
	}
	
	private static TiledMapTileLayer getCover(TiledMap map) {
		TiledMapTileLayer coverLayer = null;
		MapLayer layer = map.getLayers().get(COVER_LAYER);
		if (layer instanceof TiledMapTileLayer) {
			coverLayer = (TiledMapTileLayer)layer;
		}
		return coverLayer;
	}
	
	private static List<Obsticle> getObsticles(TiledMap map) {
		List<Obsticle> obsticles = new ArrayList<Obsticle>();
		
		MapLayer layer = map.getLayers().get(OBJECT_LAYER);
		MapObjects mapObjects = layer.getObjects();
		
		Iterator<MapObject> iterator = mapObjects.iterator();
		
		while(iterator.hasNext()) {
			MapObject mapObject = iterator.next();
			if (mapObject.getName().equals(OBSTICLE_NAME)) {
				MapProperties properties = mapObject.getProperties();
				String type = (String)properties.get(TYPE_KEY);
				ObsticleType obsticleType = ObsticleType.valueOf(type.toUpperCase());
				RectangleMapObject rectangleObject = (RectangleMapObject)mapObject;
				Rectangle rectangle = rectangleObject.getRectangle();
				Vector position = new Vector(rectangle.x, rectangle.y);
				position = position.add(OBSTICLE_OFFSET);
				obsticles.add(ObsticleFactory.getObsticle(position, obsticleType));
			}
		}
		
		return obsticles;
	}
	
	private static List<Pickup> getPickups(TiledMap map) {
		List<Pickup> pickups = new ArrayList<Pickup>();
		
		MapLayer layer = map.getLayers().get(OBJECT_LAYER);
		MapObjects mapObjects = layer.getObjects();
		
		Iterator<MapObject> iterator = mapObjects.iterator();
		
		while(iterator.hasNext()) {
			MapObject mapObject = iterator.next();
			if (mapObject.getName().equals(PICKUP_NAME)) {
				MapProperties properties = mapObject.getProperties();
				String type = (String)properties.get(TYPE_KEY);
				PickupType pickupType = PickupType.valueOf(type.toUpperCase());
				RectangleMapObject rectangleObject = (RectangleMapObject)mapObject;
				Rectangle rectangle = rectangleObject.getRectangle();
				Vector position = new Vector(rectangle.x, rectangle.y);
				pickups.add(new Pickup(position, pickupType));
			}
		}
		
		return pickups;
	}
	
	private static List<Enemy> getEnemies(TiledMap map) {
		List<Enemy> enemies = new ArrayList<Enemy>();
		
		MapLayer layer = map.getLayers().get(OBJECT_LAYER);
		MapObjects mapObjects = layer.getObjects();
		
		Iterator<MapObject> iterator = mapObjects.iterator();
		
		while(iterator.hasNext()) {
			MapObject mapObject = iterator.next();
			if (mapObject.getName().equals(ENEMY_NAME)) {
				MapProperties properties = mapObject.getProperties();
				String type = (String)properties.get(TYPE_KEY);
				EnemyType enemyType = EnemyType.valueOf(type.toUpperCase());
				RectangleMapObject rectangleObject = (RectangleMapObject)mapObject;
				Rectangle rectangle = rectangleObject.getRectangle();
				Vector position = new Vector(rectangle.x, rectangle.y);
				enemies.add(new Enemy(position, enemyType));
			}
		}
		
		return enemies;
	}	
	
	private static Vector getSize(TiledMap map) {
		Vector size = new Vector();
		MapLayer layer = map.getLayers().get(WALL_LAYER);
		if (layer instanceof TiledMapTileLayer) {
			TiledMapTileLayer tiledLayer = (TiledMapTileLayer)layer;
			size.x = tiledLayer.getWidth() * DefaultConstants.TILE_SIZE;
			size.y = tiledLayer.getHeight() * DefaultConstants.TILE_SIZE;
		}
		return size;
	}
	
	private static List<Link> getLinks(TiledMap map) {
		List<Link> links = new ArrayList<Link>();
		
		MapLayer layer = map.getLayers().get(OBJECT_LAYER);
		MapObjects mapObjects = layer.getObjects();
		
		Iterator<MapObject> iterator = mapObjects.iterator();
		
		while(iterator.hasNext()) {
			MapObject mapObject = iterator.next();
			if (mapObject.getName().equals(LINK_NAME)) {
				MapProperties properties = mapObject.getProperties();
				String name = (String)properties.get(NODE_NAME_KEY);
				String direction = ((String)properties.get(DIRECTION_KEY)).toUpperCase();
				RectangleMapObject rectangleObject = (RectangleMapObject)mapObject;
				Rectangle rectangle = rectangleObject.getRectangle();
				Vector position = new Vector(rectangle.x, rectangle.y);
				Vector size = new Vector(rectangle.width, rectangle.height);
				links.add(new Link(name, Direction.valueOf(direction), position, size));
			}
		}
		
		return links;
	}
	
	private static boolean[][] getWalls(TiledMap map) {
		boolean[][] walls = null;
		MapLayer layer = map.getLayers().get(WALL_LAYER);
		if (layer instanceof TiledMapTileLayer) {
			TiledMapTileLayer tiledLayer = (TiledMapTileLayer)layer;
			walls = new boolean[tiledLayer.getWidth()][tiledLayer.getHeight()];
			for (int i = 0; i < tiledLayer.getWidth(); ++i) {
				for (int j = 0; j < tiledLayer.getHeight(); ++j) {
					walls[i][j] = false;
					Cell cell = tiledLayer.getCell(i, j);
					if (cell != null) {
						TiledMapTile tile = cell.getTile();
						if (tile != null) {
							walls[i][j] = true;
						}
					}
				}
			}
		}
		return walls;
	}
}
