package renderer.drawables;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import core.Vector;
import renderer.EntitySprite;
import simulation.entity.obsticle.Obsticle;

public class ObsticleDrawable implements Drawable {

	private Obsticle obsticle;
	private EntitySprite sprite;
	
	public ObsticleDrawable(Obsticle obsticle) {
		this.obsticle = obsticle;
		ObsticleDrawConfig config = ObsticleDrawConfig.getObsticleDrawConfig(obsticle.getType());
		sprite = new EntitySprite(config.getImage(), obsticle.getSize());
	}

	@Override
	public int compareTo(Drawable other) {
		return (int) (other.getZ() - getZ());
	}
	
	public float getZ() {
		return getPosition().y;
	}

	@Override
	public void draw(float delta, Batch spriteBatch) {
		sprite.draw(getPosition(), spriteBatch);
	}

	@Override
	public Vector getPosition() {
		return obsticle.getPosition();
	}
	
}
