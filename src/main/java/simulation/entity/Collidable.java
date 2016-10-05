package simulation.entity;

import core.Vector;

public interface Collidable {

	public boolean canCollide();
	public Vector getSize();
	public Vector getPosition();
	public void handleCollision(Collidable other);
}
