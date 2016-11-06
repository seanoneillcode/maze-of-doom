package core;

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

	public static Vector getUnitVector(Vector a, Vector b) {
		Vector sub = b.sub(a);
		Vector result;
		if (Math.abs(sub.x) > Math.abs(sub.y)) {
			result = new Vector(sub.x > 0 ? 1 : -1, (sub.y / sub.x) * (sub.y > 0 ? 1 : -1));
		} else {
			result = new Vector((sub.x / sub.y) * (sub.x > 0 ? 1 : -1), sub.y > 0 ? 1 : -1);
		}
		return result;
	}

	public boolean isNear(Vector other, float tolerance) {
        float xtol = Math.abs(other.x) - Math.abs(this.x);
        float ytol = Math.abs(other.y) - Math.abs(this.y);
        if (Math.abs(xtol) < tolerance && Math.abs(ytol) < tolerance) {
            return true;
        }
        return false;
    }
}
