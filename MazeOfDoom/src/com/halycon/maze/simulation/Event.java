package com.halycon.maze.simulation;

public class Event {

	private float timer;
	private float length;
	private boolean done;
	private boolean started;
	
	public Event(float length) {
		this.length = length;
		reset();
	}
	
	public boolean isStarted() {
		return started;
	}
	
	public void start() {
		started = true;
	}
	
	public boolean isDone() {
		return done;
	}
	
	public void update(float delta) {
		if (started) {
			timer += delta;
			if (timer >= length) {
				done = true;
			}			
		}
	}
	
	public float percentDone() {
		return (timer / length) * 100;
	}
	
	public void reset() {
		done = false;
		started = false;
		timer = 0;
	}
}
