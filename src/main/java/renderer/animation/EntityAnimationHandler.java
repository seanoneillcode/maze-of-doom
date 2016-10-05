package renderer.animation;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import simulation.entity.state.CanBeAnimated;

public class EntityAnimationHandler {

	private Map<String, Animation> animations;
	private String activeAnimation;
	
	public EntityAnimationHandler() {
		animations = new HashMap<String, Animation>();
	}
	
	public void addEntityAnimation(String name, Animation entityAnimation) {
		animations.put(name, entityAnimation);
		if (animations.size() == 1) {
			setActiveAnimation(name);
		}
	}
	
	public void setActiveAnimation(String name) {
		this.activeAnimation = name;
	}
	
	public void update(float delta, CanBeAnimated entity) {
		animations.get(activeAnimation).update(delta, entity);
	}
	
	public void draw(Batch spriteBatch) {
		animations.get(activeAnimation).draw(spriteBatch);
	}
}
