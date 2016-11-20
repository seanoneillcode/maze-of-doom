package core;


public enum Direction {

	UP(new Vector(0,1)),
	DOWN(new Vector(0,-1)),
	LEFT(new Vector(-1,0)),
	RIGHT(new Vector(1,0));
	
	private Vector vector;
	
	private Direction(Vector vector) {
		this.vector = vector;
	}
	
	public Vector getVector() {
		return vector;
	}

	public static Direction getDirection(Vector from, Vector to) {
		float xdiff = to.x - from.x;
		float ydiff = to.y - from.y;
		if (Math.abs(xdiff) > Math.abs(ydiff)) {
			return xdiff > 0 ? RIGHT : LEFT;
		} else {
			return ydiff > 0 ? UP : DOWN;
		}
	}
}
