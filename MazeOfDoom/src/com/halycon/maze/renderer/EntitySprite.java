package com.halycon.maze.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.halycon.maze.core.Vector;

public class EntitySprite {

	private Texture texture;
	private Sprite sprite;
	private float alpha;
	private Vector offset;
	
	public EntitySprite(String spriteFile, Vector size, Vector offset) {
		texture = new Texture(Gdx.files.internal(spriteFile));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		sprite = new Sprite(texture);
		sprite.setSize((int)size.x, (int)size.y);
		this.alpha = 1.0f;
		this.offset = offset;
	}
	
	public EntitySprite(String spriteFile, Vector size) {
		this(spriteFile, size, new Vector());
	}
	
	public void draw(Vector position, SpriteBatch spriteBatch) {
		spriteBatch.begin();
		sprite.setPosition(position.x + offset.x, position.y + offset.y);
		sprite.draw(spriteBatch, alpha);
		spriteBatch.end();
	}
	
	public Vector getSize() {
		return new Vector(sprite.getWidth(), sprite.getHeight());
	}
	
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
}
