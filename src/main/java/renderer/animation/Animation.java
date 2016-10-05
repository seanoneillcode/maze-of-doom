package renderer.animation;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import simulation.entity.state.CanBeAnimated;

public interface Animation {
	public void draw(Batch spriteBatch);
	public void update(float delta, CanBeAnimated entity);
}
