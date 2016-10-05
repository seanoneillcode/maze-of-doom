package renderer.drawables;

import com.badlogic.gdx.graphics.g2d.Batch;
import core.Vector;

public interface Drawable extends Comparable<Drawable> {
	void draw(float delta, Batch spriteBatch);
	Vector getPosition();
	float getZ();
}
