package renderer.drawables;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import core.Vector;
import renderer.EntitySprite;
import simulation.entity.DialogEntity;

public class DialogEntityDrawable implements Drawable {

	private EntitySprite sprite;
	private DialogEntity dialogEntity;
	private static final String SIGNPOST_IMAGE_FILE = "ui/signpost.png";
	
	public DialogEntityDrawable(DialogEntity dialogEntity) {
		this.dialogEntity = dialogEntity;
		sprite = new EntitySprite(SIGNPOST_IMAGE_FILE, dialogEntity.getSize());
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
		return dialogEntity.getPosition();
	}
}
