package simulation.entity;

import core.Vector;
import simulation.level.Node;

public class CollisionHandler {

	public static boolean isColliding(Entity a, Entity b) {
		boolean colliding = false;
		if (((a.pos.x + a.size.x) > b.pos.x)
				&& (a.pos.x < (b.pos.x + b.size.x))) {
			if (((a.pos.y + a.size.y) > b.pos.y)
					&& (a.pos.y < (b.pos.y + b.size.y))) {
				return true;
			}
		}
		return colliding;
	}
	
	public static boolean isColliding(MovingEntity a, Entity b, float delta) {
		boolean colliding = false;
		Vector move = a.getMovement();
		Vector oldPos = new Vector(a.getPosition());
		float nx = oldPos.x + (move.x * delta);
		float ny = oldPos.y + (move.y * delta);
		float nsx = nx + a.getSize().x;
		float nsy = ny + a.getSize().y;
		if (((nsx) > b.pos.x)
				&& (nx < (b.pos.x + b.size.x))) {
			if (((nsy) > b.pos.y)
					&& (ny < (b.pos.y + b.size.y))) {
				return true;
			}
		}
		return colliding;
	}

	public static void resolveCollision(MovingEntity movingEntity, Entity entity, float delta) {

		Vector move = movingEntity.getMovement();

		Vector oldPos = new Vector(movingEntity.getPosition());
		float nx, ny, nsx, nsy, ox, oy, osx, osy;
		nx = oldPos.x + (move.x * delta);
		ny = oldPos.y + (move.y * delta);
		nsx = nx + movingEntity.getSize().x;
		nsy = ny + movingEntity.getSize().y;
		ox = oldPos.x;
		oy = oldPos.y;
		osx = ox + movingEntity.getSize().x;
		osy = oy + movingEntity.getSize().y;

		Vector resultMove = new Vector(1, 1);
		resultMove = resolveCollision(nx, ny, ox, oy, entity,resultMove);
		resultMove = resolveCollision(nsx, ny, osx, oy, entity, resultMove);
		resultMove = resolveCollision(nsx, nsy, osx, osy, entity, resultMove);
		resultMove = resolveCollision(nx, nsy, ox, osy, entity, resultMove);

		move.x = move.x * resultMove.x;
		move.y = move.y * resultMove.y;

		movingEntity.setMovement(move);
		movingEntity.applyFriction();
	}

	public static Vector resolveCollision(float nx, float ny, float ox, float oy, Entity entity, Vector move) {
		boolean b = pointCollidesWithEntity(nx, oy, entity);
		boolean c = pointCollidesWithEntity(nx, ny, entity);
		boolean d = pointCollidesWithEntity(ox, ny, entity);
		if (c) {
			if (b && d) {
				move.x = 0;
				move.y = 0;
			} else {
				if (d && ! b) {
					move.y = 0;
				}
				if (!d && b) {
					move.x = 0;
				}
			}
		}
		return move;
	}

	private static boolean pointCollidesWithEntity(float x, float y, Entity entity) {
		Vector ep = entity.getPosition();
		Vector es = entity.getSize();
		if (x > ep.x && x < (ep.x + es.x)) {
			if (y > ep.y && y < (ep.y + es.y)) {
				return true;
			}
		}
		return false;
	}

	public static void resolveCollision(MovingEntity movingEntity, Node node, float delta) {
		float newx, newy, sizex, sizey, oldx, oldy, oldsizex, oldsizey;
		Vector move = movingEntity.getMovement();
		Vector physics = movingEntity.getPhysics();

		oldx = movingEntity.getPosition().x;
		oldy = movingEntity.getPosition().y;
		newx = oldx + (move.x * delta);
		newy = oldy + (move.y * delta);
		sizex = newx + movingEntity.getSize().x;
		sizey = newy + movingEntity.getSize().y;
		oldsizex = oldx + movingEntity.getSize().x;
		oldsizey = oldy + movingEntity.getSize().y;

		Vector resultMove = new Vector(1, 1);
		resultMove = resolveWallCollision(resultMove, newx, newy, oldx, oldy,
				node);
		resultMove = resolveWallCollision(resultMove, sizex, newy, oldsizex,
				oldy, node);
		resultMove = resolveWallCollision(resultMove, newx, sizey, oldx,
				oldsizey, node);
		resultMove = resolveWallCollision(resultMove, sizex, sizey, oldsizex,
				oldsizey, node);

		move.x = move.x * resultMove.x;
		move.y = move.y * resultMove.y;

		movingEntity.setMovement(move);

		newx = oldx + ((physics.x));
		newy = oldy + ((physics.y));
		sizex = newx + movingEntity.getSize().x;
		sizey = newy + movingEntity.getSize().y;

		resultMove = new Vector(1, 1);
		resultMove = resolveWallCollision(resultMove, newx, newy, oldx, oldy,
				node);
		resultMove = resolveWallCollision(resultMove, sizex, newy, oldsizex,
				oldy, node);
		resultMove = resolveWallCollision(resultMove, newx, sizey, oldx,
				oldsizey, node);
		resultMove = resolveWallCollision(resultMove, sizex, sizey, oldsizex,
				oldsizey, node);

		if (resultMove.x != 1) {
			physics.x = 0;
			movingEntity.applyFriction();
		}
		if (resultMove.y != 1) {
			physics.y = 0;
			movingEntity.applyFriction();
		}
		movingEntity.setPhysics(physics);

	}

	private static Vector resolveWallCollision(Vector move, float nx, float ny,
			float ox, float oy, Node node) {
		int w = (int) node.getSize().x / 16;
		int h = (int) node.getSize().y / 16;
		int x = (int) (nx / 16);
		int y = (int) (ny / 16);
		int otx = (int) (ox / 16);
		int oty = (int) (oy / 16);
		boolean[][] walls = node.getWalls();
		if (x >= 0 && x < w && otx >= 0 && otx < w) {
			if (y >= 0 && y < h && oty >= 0 && oty < h) {
				if (walls[x][y]) {
					if (otx != x && oty != y) {
						if (walls[x][oty] && walls[otx][y]) {
							move.x = 0;
							move.y = 0;
							return move;
						}
						if (walls[x][oty] && !walls[otx][y]) {
							move.x = 0;
						}
						if (!walls[x][oty] && walls[otx][y]) {
							move.y = 0;
						}
					} else {
						if (otx != x) {
							move.x = 0;
						}
						if (oty != y) {
							move.y = 0;
						}
					}
				}
			}
		}
		return move;
	}
}
