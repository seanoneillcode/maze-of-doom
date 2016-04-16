package com.halycon.maze.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.halycon.maze.core.Vector;


public class ImageList {

	TextureRegion[] regions;
	private Vector frameSize;
	private Texture baseTexture;
	private Vector offset;
	private int numFrames;
	
	public ImageList(String animationFile, Vector size, int numFrames, Vector offset) {
		this.frameSize = new Vector(size);
		this.regions = new TextureRegion[numFrames];
		this.baseTexture = new Texture(Gdx.files.internal(animationFile));
		baseTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		this.offset = offset;
		for (int i = 0; i < numFrames; ++i) {
			this.regions[i] = new TextureRegion(baseTexture, i * (int)frameSize.x, 0, (int)frameSize.x, (int)frameSize.y);
		}
		this.numFrames = numFrames;
	}
	
	public int getNumFrames() {
		return numFrames;
	}
	
	public void draw(SpriteBatch spriteBatch, int index, Vector position) {
		spriteBatch.begin();
		spriteBatch.draw(regions[index], position.x + offset.x, position.y + offset.y, (int)frameSize.x, (int)frameSize.y);
		spriteBatch.end();
	}
}
