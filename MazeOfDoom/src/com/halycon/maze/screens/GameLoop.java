package com.halycon.maze.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.halycon.maze.business.DefaultConstants;
import com.halycon.maze.renderer.Renderer;
import com.halycon.maze.simulation.Simulation;
import com.halycon.maze.simulation.SimulationListener;
import com.halycon.maze.simulation.entity.Player;



public class GameLoop extends MazeScreen implements SimulationListener {

	private Simulation simulation;
	
	private Renderer renderer;
	
	private boolean isDone = false;
	
	private boolean keyDown = false;
	private boolean keyDownUse = false;
	
	
	public GameLoop() {
		load();
	}
	
	private void load() {
		simulation = new Simulation();
		renderer = new Renderer(simulation);
		renderer.loadMap(simulation);
	}
	
	@Override
	public void update(float delta) {
		simulation.update(delta);
		if (simulation.isDone()) {
			load();
		}
		
		Player player = simulation.getPlayer();
		if (Gdx.input.isKeyPressed(Keys.LEFT) && isPlayerActive()) {
			if (player.canMove()) {
				player.moveLeft(delta);
			}
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT) && isPlayerActive()) {
			if (player.canMove()) {
				player.moveRight(delta);
			}
		}
		if (Gdx.input.isKeyPressed(Keys.UP) && isPlayerActive()) {
			if (player.canMove()) {
				player.moveUp(delta);
			}
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN) && isPlayerActive()) {
			if (player.canMove()) {
				player.moveDown(delta);
			}
		}
		if (Gdx.input.isKeyPressed(Keys.SPACE) && isPlayerActive()) {
			player.dash();
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			if (isPlayerActive() && !keyDownUse) {
				player.use();
			}
			if (!keyDownUse) {
				simulation.getLevel().use(player);
				keyDownUse = true;
			}
		} else {
			player.stopUsing();
			keyDownUse = false;
		}
		
		if (Gdx.input.isKeyPressed(Keys.F) && isPlayerActive()) {
			if (!keyDown) {
				player.attack();
				keyDown = true;
			}
		} else {
			keyDown = false;
		}
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			isDone = true;
		}
		if (Gdx.input.isKeyPressed(Keys.COMMA)) {
			DefaultConstants.DEBUG_ENABLED = true;
		}
		if (Gdx.input.isKeyPressed(Keys.PERIOD)) {
			DefaultConstants.DEBUG_ENABLED = false;
		}
		
	}
	
	private boolean isPlayerActive() {
		return !simulation.getLevel().isPaused() && !simulation.getLevel().isLoading();
	}

	@Override
	public void draw(float delta) {
		renderer.draw(simulation, delta);
	}

	@Override
	public boolean isDone() {
		return isDone;
	}

	@Override
	public void dispose() {
		renderer.dispose();
	}
}
