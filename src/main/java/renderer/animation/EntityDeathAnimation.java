package renderer.animation;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import core.Direction;
import core.Vector;
import renderer.ImageList;
import simulation.entity.state.CanBeAnimated;

public class EntityDeathAnimation implements Animation {
	private float timer = 0f;
	private static final float FRAME =  0.2f;
	private Vector position;
	ImageList imageList;
	Direction direction;
	private int index = 0;
	
	public EntityDeathAnimation(ImageList image) {
		this.imageList = image;
	}
	
	@Override
	public void draw(Batch spriteBatch) {
		imageList.draw(spriteBatch, index, position);
	}
	
	@Override
	public void update(float delta, CanBeAnimated entity) {
		timer += delta;
		if (timer > FRAME) {
			timer = 0;
			if (index + 1 < imageList.getNumFrames()) {
				index++;
			}
		}
		
		this.position = entity.getPosition();
	}
}
