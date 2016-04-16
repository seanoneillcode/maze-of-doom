package com.halycon.maze.renderer.drawables;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.halycon.maze.core.Vector;
import com.halycon.maze.renderer.ImageMap;

public class Font {

	private static final Vector CHAR_SIZE = new Vector(10, 10);
	private static ImageMap font = new ImageMap("ui/font.png", CHAR_SIZE, 10, 20);

	public static void drawString(String string, Vector position, SpriteBatch spriteBatch) {
		Vector drawPosition = new Vector(position);
		for (int index = 0; index < string.length(); index++) {
			char currentChar = string.charAt(index);
			drawChar(currentChar, drawPosition, spriteBatch);
			drawPosition.x += 8;
		}
	}
	
	private static void drawChar(char drawChar, Vector position, SpriteBatch spriteBatch) {
		Vector index = getIndexVector(drawChar);
		font.draw(spriteBatch, (int)index.x, (int)index.y, position);
	}
	
	private static Vector getIndexVector(char ch) {
		Vector index = new Vector();
		int value = (int) ch;
		index.x = (int) value % 10;
		index.y = (int) value / 10;
		return index;
	}
}
