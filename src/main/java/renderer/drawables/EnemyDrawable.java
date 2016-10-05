package renderer.drawables;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import business.DefaultConstants;
import core.Direction;
import core.Vector;
import renderer.ImageList;
import renderer.animation.EnemyWalkAnimation;
import renderer.animation.EntityAnimationHandler;
import renderer.animation.EntityHurtAnimation;
import simulation.entity.Enemy;
import simulation.entity.EnemyType;
import simulation.entity.state.EntityState;

public class EnemyDrawable implements Drawable {

	private EntityAnimationHandler enemyHandler;
	private Enemy enemy;
	
	public EnemyDrawable(Enemy enemy) {
		this.enemy = enemy;
		enemyHandler = new EntityAnimationHandler();
		Map<Direction, ImageList> enemyImages = new HashMap<Direction, ImageList>();
		enemyImages.put(Direction.DOWN, new ImageList("characters/blob/walkdown.png", 
				new Vector(DefaultConstants.TILE_SIZE, DefaultConstants.TILE_SIZE),
				2,
				new Vector(EnemyType.BLOB.getOffset())));
		enemyImages.put(Direction.UP, new ImageList("characters/blob/walkup.png", 
				new Vector(DefaultConstants.TILE_SIZE, DefaultConstants.TILE_SIZE),
				2,
				new Vector(EnemyType.BLOB.getOffset())));
		enemyImages.put(Direction.LEFT, new ImageList("characters/blob/walkleft.png", 
				new Vector(DefaultConstants.TILE_SIZE, DefaultConstants.TILE_SIZE),
				2,
				new Vector(EnemyType.BLOB.getOffset())));
		enemyImages.put(Direction.RIGHT, new ImageList("characters/blob/walkright.png", 
				new Vector(DefaultConstants.TILE_SIZE, DefaultConstants.TILE_SIZE),
				2,
				new Vector(EnemyType.BLOB.getOffset())));
		enemyHandler.addEntityAnimation("walk", new EnemyWalkAnimation(enemyImages));
		enemyHandler.setActiveAnimation("walk");
		enemyHandler.addEntityAnimation("dead", new EntityHurtAnimation(new ImageList(
				"characters/blob/dead.png", 
				new Vector(DefaultConstants.TILE_SIZE, DefaultConstants.TILE_SIZE), 
				3,
				new Vector())));
	}
	
	@Override
	public int compareTo(Drawable other) {
		return (int) (other.getZ() - getZ());
	}
	
	public float getZ() {
		return getPosition().y;
	}
	
	public void draw(float delta, Batch spriteBatch) {
		if (enemy.getEntityState() == EntityState.ALIVE) {
			enemyHandler.setActiveAnimation("walk");
		} else {
			enemyHandler.setActiveAnimation("dead");
		}
		enemyHandler.update(delta, enemy);
		enemyHandler.draw(spriteBatch);
	}

	@Override
	public Vector getPosition() {
		return enemy.getPosition();
	}
}
