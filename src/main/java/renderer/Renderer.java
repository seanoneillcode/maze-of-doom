package renderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import business.DefaultConstants;
import core.Vector;
import renderer.drawables.*;
import simulation.Simulation;
import simulation.dialog.Dialog;
import simulation.entity.Enemy;
import simulation.entity.Entity;
import simulation.entity.EntityType;
import simulation.entity.Pickup;
import simulation.entity.Player;
import simulation.entity.mechanism.Mechanism;
import simulation.entity.obsticle.Obsticle;
import simulation.level.Level;
import simulation.level.Node;
import simulation.story.Actor;
import simulation.story.Story;


public class Renderer {

	private OrthographicCamera camera;

	OrthogonalTiledMapRenderer mapRenderer;
	Batch spriteBatch;
	
	
	boolean mapIsLoaded;
	private static float SCALE_POSITION = 100.0f;
	EntitySprite heart, emptyHeart;
	EntitySprite blankScreen, testScreen;
	List<Drawable> drawables;
	DialogDrawable dialogDrawable;
	EntitySprite weatherLayer;
	Vector weatherOffest;
	boolean hasWeather;

	public Renderer(Simulation simulation) {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, (w / h) * DefaultConstants.TILE_SIZE * 10, DefaultConstants.TILE_SIZE * 10);
		camera.update();
		heart = new EntitySprite("ui/heart.png", new Vector(7, 7));
		emptyHeart = new EntitySprite("ui/emptyheart.png", new Vector(7, 7));
		blankScreen = new EntitySprite("ui/blankscreen.png", new Vector(224, 160));
		testScreen = new EntitySprite("story/test-screen.png", new Vector(224, 160));
		mapIsLoaded = false;
		dialogDrawable = new DialogDrawable();
		weatherLayer = new EntitySprite("characters/smoke.png", new Vector(32, 32));
		weatherOffest = new Vector(0,0);
	}

	public void loadMap(Simulation simulation) {
		Level level = simulation.getLevel();
		TiledMap map = level.getMap();
		mapRenderer = new OrthogonalTiledMapRenderer(map, DefaultConstants.UNIT_SIZE);
		mapRenderer.setView(camera);
		spriteBatch = mapRenderer.getBatch();
		drawables = new ArrayList<>();
		drawables.add(new PlayerDrawable(simulation.getPlayer()));
		drawables.addAll(loadEnemies(level.getEnemies()));
		drawables.addAll(loadEntities(level.getEntities()));
		drawables.addAll(loadObsticles(level.getObsticles()));
		drawables.addAll(loadMechanisms(level.getMechanisms()));
		drawables.addAll(loadActors(simulation.getStory()));
		hasWeather = simulation.getLevel().hasWeather();
	}

	private List<Drawable> loadActors(Story story) {
        List<Drawable> drawables = new ArrayList<>();
        List<Actor> actors = story.getActors();
        for (Actor actor : actors) {
            drawables.add(new ActorDrawable(actor));
        }
        return drawables;
	}

	private List<Drawable> loadObsticles(List<Obsticle> obsticles) {
        List<Drawable> drawables = new ArrayList<>();
		for (Obsticle obsticle : obsticles) {
			drawables.add(new ObsticleDrawable(obsticle));
		}
		return drawables;
	}
	
	private List<Drawable> loadEnemies(List<Enemy> enemies) {
		List<Drawable> drawables = new ArrayList<>();
		for (Enemy enemy : enemies) {
			drawables.add(new EnemyDrawable(enemy));
		}
		return drawables;
	}
	
	private List<Drawable> loadEntities(List<Entity> entities) {
        List<Drawable> drawables = new ArrayList<>();
		for (Entity entity : entities) {
			if (entity.getType() == EntityType.PICKUP) {
				if (((Pickup)entity).isVisible()) {
					drawables.add(new PickupDrawable((Pickup)entity));
				}
			}
		}
		return drawables;
	}
	
	private List<Drawable> loadMechanisms(List<Mechanism> mechanisms) {
        List<Drawable> drawables = new ArrayList<>();
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
			if (hasWeather) {
				drawWeather(spriteBatch, delta);
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
	
	private void drawBlankScreen(float percent, Batch spriteBatch) {
		blankScreen.setAlpha(percent / 100.0f);
		Vector position = new Vector(camera.position.x - 112, camera.position.y - 80);
		blankScreen.draw(position, spriteBatch);
	}

	private void drawWeather(Batch spriteBatch, float delta) {
		Vector position = new Vector(camera.position.x - (DefaultConstants.SCREEN_SIZE.x * 0.5f), camera.position.y - (DefaultConstants.SCREEN_SIZE.y * 0.50f));
		position = position.add(weatherOffest);

		for (int index = 0; index < 6; index++) {
			for (int otherIndex = 0; otherIndex < 8; otherIndex++) {
				weatherLayer.draw(position, spriteBatch);
				position.x += 32;
			}
			position.x -= 256;
			position.y += 32;
		}
		weatherOffest.x = weatherOffest.x - (15.0f * delta);
		weatherOffest.y = weatherOffest.y - (7.0f * delta);
		if (weatherOffest.x < -32) {
			weatherOffest.x = 0;
		}
		if (weatherOffest.y < -32) {
			weatherOffest.y = 0;
		}
	}
	
	private void drawPlayerHealth(Batch spriteBatch, Player player) {
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
