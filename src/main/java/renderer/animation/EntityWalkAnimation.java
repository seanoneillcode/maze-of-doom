package renderer.animation;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import core.Direction;
import core.Vector;
import renderer.ImageList;
import simulation.entity.state.CanBeAnimated;



public class EntityWalkAnimation implements Animation {
	
	private float walkTimer = 0f;
	private static final float LIMIT =  0.15f;
	private Vector position;
	ImageList image;
	Direction direction;
	
	public EntityWalkAnimation(ImageList image) {
		this.image = image;
	}
	
	@Override
	public void draw(Batch spriteBatch) {
		image.draw(spriteBatch, getWalkAnimationIndex(), position);
	}
	
	@Override
	public void update(float delta, CanBeAnimated entity) {
		if (entity.isMoving()) {
			walkTimer += delta;
			if (walkTimer > LIMIT) {
				walkTimer = -LIMIT;
			}
		}
		this.position = entity.getPosition();
		this.direction = entity.getDirection();
	}
	
	private int getWalkAnimationIndex() {
		int index = 0;
		
		switch (direction) {
		case DOWN:
			index = 0;
			break;
		case UP:
			index = 1;
			break;
		case LEFT:
			index = 3;
			break;
		case RIGHT:
			index = 2;
			break;
		}
		if (walkTimer > 0) {
			index += 4;
		}
		return index;
	}
}
