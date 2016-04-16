package com.halycon.maze.renderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.halycon.maze.business.DefaultConstants;
import com.halycon.maze.core.Vector;
import com.halycon.maze.renderer.drawables.DialogDrawable;
import com.halycon.maze.renderer.drawables.DialogEntityDrawable;
import com.halycon.maze.renderer.drawables.Drawable;
import com.halycon.maze.renderer.drawables.EnemyDrawable;
import com.halycon.maze.renderer.drawables.MechanismDrawable;
import com.halycon.maze.renderer.drawables.ObsticleDrawable;
import com.halycon.maze.renderer.drawables.PickupDrawable;
import com.halycon.maze.renderer.drawables.PlayerDrawable;
import com.halycon.maze.simulation.Simulation;
import com.halycon.maze.simulation.dialog.Dialog;
import com.halycon.maze.simulation.entity.DialogEntity;
import com.halycon.maze.simulation.entity.Enemy;
import com.halycon.maze.simulation.entity.Entity;
import com.halycon.maze.simulation.entity.Pickup;
import com.halycon.maze.simulation.entity.Player;
import com.halycon.maze.simulation.entity.mechanism.Mechanism;
import com.halycon.maze.simulation.entity.obsticle.Obsticle;
import com.halycon.maze.simulation.level.Level;
import com.halycon.maze.simulation.level.Node;


public class Renderer {

	private OrthographicCamera camera;

	OrthogonalTiledMapRenderer mapRenderer;
	SpriteBatch spriteBatch;
	
	
	boolean mapIsLoaded;
	private static float SCALE_POSITION = 100.0f;
	EntitySprite heart, emptyHeart;
	EntitySprite blankScreen;
	List<Drawable> drawables;
	DialogDrawable dialogDrawable;
	
	public Renderer(Simulation simulation) {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, (w / h) * DefaultConstants.TILE_SIZE * 10, DefaultConstants.TILE_SIZE * 10);
		camera.update();
		heart = new EntitySprite("ui/heart.png", new Vector(7, 7));
		emptyHeart = new EntitySprite("ui/emptyheart.png", new Vector(7, 7));
		blankScreen = new EntitySprite("ui/blankscreen.png", new Vector(224, 160));
		mapIsLoaded = false;
		dialogDrawable = new DialogDrawable();
	}

	public void loadMap(Simulation simulation) {
		Level level = simulation.getLevel();
		TiledMap map = level.getMap();
		mapRenderer = new OrthogonalTiledMapRenderer(map, DefaultConstants.UNIT_SIZE);
		mapRenderer.setView(camera);
		spriteBatch = mapRenderer.getSpriteBatch();
		drawables = new ArrayList<Drawable>();
		drawables.add(new PlayerDrawable(simulation.getPlayer()));
		drawables = loadEnemies(level.getEnemies(), drawables);
		drawables = loadPickups(level.getPickups(), drawables);
		drawables = loadObsticles(level.getObsticles(), drawables);
		drawables = loadMechanisms(level.getMechanisms(), drawables);
	}
	
	private List<Drawable> loadObsticles(List<Obsticle> obsticles, List<Drawable> drawables) {
		for (Obsticle obsticle : obsticles) {
			drawables.add(new ObsticleDrawable(obsticle));
		}
		return drawables;
	}
	
	private List<Drawable> loadEnemies(List<Enemy> enemies, List<Drawable> drawables) {
		for (Enemy enemy : enemies) {
			drawables.add(new EnemyDrawable(enemy));
		}
		return drawables;
	}
	
	private List<Drawable> loadPickups(List<Pickup> pickups, List<Drawable> drawables) {
		for (Pickup pickup : pickups) {
			drawables.add(new PickupDrawable(pickup));
		}
		return drawables;
	}
	
	private List<Drawable> loadMechanisms(List<Mechanism> mechanisms, List<Drawable> drawables) {
		for (Mechanism mechanism : mechanisms) {
			drawables.add(new MechanismDrawable(mechanism));
		}
		return drawables;
	}
	
	public void draw(Simulation simulation, float delta) {
		Gdx.gl20.glClearColor(0, 0, 0, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (simulation.getLevel().doesMapNeedLoading()) {
			if (!mapIsLoaded) {
				loadMap(simulation);
				mapIsLoaded = true;
			}
		} else {
			mapIsLoaded = false;
		}
		
		Player player = simulation.getPlayer();
		camera.position.x = player.getPosition().x;
		camera.position.y = player.getPosition().y;
		camera.position.x = Math.round(camera.position.x * SCALE_POSITION) / SCALE_POSITION;
		camera.position.y = Math.round(camera.position.y * SCALE_POSITION) / SCALE_POSITION;
		Vector offset = getPlayerEdgeOffset(simulation.getLevel().getActiveNode(), player);
		camera.position.x += offset.x;
		camera.position.y += offset.y;
		camera.update();
		
		
		
		if (mapRenderer != null) {
			mapRenderer.setView(camera);
			mapRenderer.render();
			
			Collections.sort(drawables);
			for (Drawable drawable : drawables) {
				drawable.draw(delta, spriteBatch);
			}
			
			TiledMapTileLayer coverLayer = simulation.getLevel().getActiveNode().getCoverLayer();
			if (coverLayer != null) {
				spriteBatch.begin();
				mapRenderer.renderTileLayer(coverLayer);
				spriteBatch.end();
			}
			
			if (DefaultConstants.DEBUG_ENABLED) {
				drawDebug(player, simulation.getLevel().getEnemies(), simulation.getLevel().getObsticles());
			}
			drawPlayerHealth(spriteBatch, player);
			
			drawBlankScreen(simulation.getLoadPercentage(), spriteBatch);
		}
		
		Dialog dialog = simulation.getLevel().getActiveDialog();
		if (dialog != null) {
			dialogDrawable.setPosition(new Vector(camera.position.x - 110, camera.position.y - 80));
			dialogDrawable.setDialog(dialog);
			dialogDrawable.draw(delta, spriteBatch);
		}
	}
	
	private void drawBlankScreen(float percent, SpriteBatch spriteBatch) {
		blankScreen.setAlpha(percent / 100.0f);
		Vector position = new Vector(camera.position.x - 112, camera.position.y - 80);
		blankScreen.draw(position, spriteBatch);
	}
	
	private void drawPlayerHealth(SpriteBatch spriteBatch, Player player) {
		Vector healthPosition = new Vector(camera.position.x - (DefaultConstants.SCREEN_SIZE.x * 0.45f), camera.position.y + (DefaultConstants.SCREEN_SIZE.y * 0.40f));
		int currentHealth = player.getHealth();
		for (int index = 0; index < player.getMaxHealth(); index++) {
			if (index < currentHealth) {
				heart.draw(healthPosition, spriteBatch);
			} else {
				emptyHeart.draw(healthPosition, spriteBatch);
			}
			
			healthPosition.x += heart.getSize().x + 1;
		}
	}
	
	private void drawDebugEntity(Entity entity, Color color, ShapeRenderer shapeRenderer) {
		shapeRenderer.setColor(color);
		shapeRenderer.box(
				entity.getPosition().x, 
				entity.getPosition().y, 
        		0f, 
        		entity.getSize().x, 
        		entity.getSize().y, 
        		0);
	}
	
	private void drawDebug(Player player, List<Enemy> enemies, List<Obsticle> obsticles) {
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		spriteBatch.begin();
        shapeRenderer.begin(ShapeType.Line);
        shapeRenderer.setProjectionMatrix(spriteBatch.getProjectionMatrix());
		shapeRenderer.setTransformMatrix(spriteBatch.getTransformMatrix());
		drawDebugEntity(player.getWeaponEntity(), Color.RED, shapeRenderer);
		drawDebugEntity(player.getEntity(), Color.GREEN, shapeRenderer);
		drawDebugEntity(player.getUseEntity(), Color.CYAN, shapeRenderer);
		for (Enemy enemy : enemies) {
			drawDebugEntity(enemy.getEntity(), Color.BLUE, shapeRenderer);
		}
		for (Obsticle obsticle : obsticles) {
			drawDebugEntity(obsticle.getEntity(), Color.YELLOW, shapeRenderer);
		}
        shapeRenderer.end();
        spriteBatch.end();
	}

	public Vector getPlayerEdgeOffset(Node activeNode, Player player) {
		Vector offset = new Vector();
		offset.x = getSingleAxisOffset(activeNode.getSize().x, player.getPosition().x, DefaultConstants.SCREEN_SIZE.x / 2);
		offset.y = getSingleAxisOffset(activeNode.getSize().y, player.getPosition().y, DefaultConstants.SCREEN_SIZE.y / 2);
		return offset;
	}
	
	private float getSingleAxisOffset(float map, float player, float screen) {
		float offset = 0;
		if (player - screen < 0 && map > (screen*2)) {
			offset = screen - player;
		} else {
			if (player + screen > map && map > (screen*2)) {
				offset = -(screen - (map - player));
			}
		}
		return offset;
	}
	
	public void dispose() {
		mapRenderer.dispose();
	}
}