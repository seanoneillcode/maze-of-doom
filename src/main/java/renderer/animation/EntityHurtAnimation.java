package renderer.animation;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import core.Vector;
import renderer.ImageList;
import simulation.entity.state.CanBeAnimated;

public class EntityHurtAnimation implements Animation {

	private static final float FRAME = 0.1f;
	private ImageList imageList;
	private Vector position;
	private int index;
	private float timer = 0f;
	
	public EntityHurtAnimation(ImageList imageList) {
		this.imageList = imageList;
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
			index++;
		}
		if (index >= imageList.getNumFrames()) {
			index = 0;
		}
		this.position = entity.getPosition();
	}
}
