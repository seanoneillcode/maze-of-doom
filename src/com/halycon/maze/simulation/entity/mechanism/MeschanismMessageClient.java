package com.halycon.maze.simulation.entity.mechanism;

public interface MeschanismMessageClient {

	public void send(String id, boolean value);
	public void receive(boolean value);
}
