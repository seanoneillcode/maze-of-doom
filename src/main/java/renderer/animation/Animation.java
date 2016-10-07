package renderer.animation;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import simulation.entity.state.CanBeAnimated;

public interface Animation {
	void draw(Batch spriteBatch);
	void update(float delta, CanBeAnimated entity);
}
