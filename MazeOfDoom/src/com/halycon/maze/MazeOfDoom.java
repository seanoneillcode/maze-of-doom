package com.halycon.maze;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import com.halycon.maze.screens.GameLoop;
import com.halycon.maze.screens.MazeScreen;

public class MazeOfDoom extends Game {
	
	@Override
	public void create() {
		setScreen(new GameLoop());
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void render() {		
		MazeScreen currentScreen = getScreen();
		currentScreen.render(Gdx.graphics.getDeltaTime());
		if (currentScreen.isDone()) {
			currentScreen.dispose();
			if (currentScreen instanceof GameLoop) {
				Gdx.app.exit();				
			}
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	
	@Override
	public MazeScreen getScreen() {
		return (MazeScreen)super.getScreen();
	}
}
