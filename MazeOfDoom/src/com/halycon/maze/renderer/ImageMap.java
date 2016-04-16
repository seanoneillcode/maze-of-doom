package com.halycon.maze.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.halycon.maze.core.Vector;

public class ImageMap {

	TextureRegion[][] regions;
	private Vector frameSize;
	private Texture baseTexture;
	
	public ImageMap(String file, Vector size, int sizex, int sizey) {
		this.frameSize = new Vector(size);
		this.regions = new TextureRegion[sizex][sizey];
		this.baseTexture = new Texture(Gdx.files.internal(file));
		baseTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		for (int i = 0; i < sizex; ++i) {
			for (int j = 0; j < sizey; ++j) {
				this.regions[i][j] = new TextureRegion(baseTexture, i * (int)frameSize.x, j * (int)frameSize.y, (int)frameSize.x, (int)frameSize.y);
				
			}
		}
	}
	
	public void draw(SpriteBatch spriteBatch, int x, int y, Vector position) {
		spriteBatch.begin();
		spriteBatch.draw(regions[x][y], position.x, position.y, (int)frameSize.x, (int)frameSize.y);
		spriteBatch.end();
	}
}
