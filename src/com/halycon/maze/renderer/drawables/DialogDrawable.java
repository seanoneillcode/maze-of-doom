package com.halycon.maze.renderer.drawables;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.halycon.maze.core.Vector;
import com.halycon.maze.renderer.EntitySprite;
import com.halycon.maze.simulation.dialog.Dialog;

public class DialogDrawable implements Drawable {

	private Dialog dialog;
	private EntitySprite whiteBackground;
	private Vector position;
	private Vector offset;
	private Vector linePosition;
	
	
	public void setDialog(Dialog dialog) {
		this.dialog = dialog;
		whiteBackground = new EntitySprite("ui/dialogBackground.png", new Vector(214, 48));
		offset = new Vector(3, 0);
		linePosition = new Vector(7, 30);
	}
	
	@Override
	public int compareTo(Drawable arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void draw(float delta, SpriteBatch spriteBatch) {
		whiteBackground.draw(position.add(offset), spriteBatch);
		List<String> lines = dialog.getLines();
		Vector drawPosition = position.add(linePosition);
		for (String line : lines) {
			Font.drawString(line.toString(), drawPosition, spriteBatch);
			drawPosition.y -= 10;
		}
	}

	@Override
	public Vector getPosition() {
		return position;
	}
	
	public void setPosition(Vector position) {
		this.position = new Vector(position);
	}

	@Override
	public float getZ() {
		// TODO Auto-generated method stub
		return 0;
	}

}
