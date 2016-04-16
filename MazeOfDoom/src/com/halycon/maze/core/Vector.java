package com.halycon.maze.core;

public class Vector {
	public float x;
	public float y;
	
	public Vector() {
		x = 0f;
		y = 0f;
	}
	
	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector(Vector vector) {
		this.x = vector.x;
		this.y = vector.y;
	}
	
	public Vector multiply(float amount) {
		return new Vector(x * amount, y * amount);
	}
	
	public Vector multiply(Vector amount) {
		return new Vector(x * amount.x, y * amount.y);
	}
	
	public Vector add(Vector amount) {
		return new Vector(x + amount.x, y + amount.y);
	}
	
	public Vector add(float amount) {
		return new Vector(x + amount, y + amount);
	}
	
	public Vector sub(Vector amount) {
		return new Vector(x - amount.x, y - amount.y);
	}
	
	public Vector sub(float amount) {
		return new Vector(x - amount, y - amount);
	}
}
