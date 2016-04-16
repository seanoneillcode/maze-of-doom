package com.halycon.maze.core;

public class VectorUtil {

	public static Vector getUnitVector(Vector a, Vector b) {
		Vector sub = new Vector(b.x - a.x, b.y - a.y);
		Vector result;
		if (Math.abs(sub.x) > Math.abs(sub.y)) {
			result = new Vector(sub.x > 0 ? 1 : -1, (sub.y / sub.x) * (sub.y > 0 ? 1 : -1));
		} else {
			result = new Vector((sub.x / sub.y) * (sub.x > 0 ? 1 : -1), sub.y > 0 ? 1 : -1);
		}
		return result;
	}
}
