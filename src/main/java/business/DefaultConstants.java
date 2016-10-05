package business;


import core.Vector;

public class DefaultConstants {
	private DefaultConstants(){}
	
	public static final float UNIT_SIZE = 1.0f;
	public static final float TILE_SIZE = UNIT_SIZE * 16;
	public static final Vector DEFAULT_SPEED = new Vector(48, 48);
	public static final Vector PLAYER_SPEED = new Vector(48, 48);
	public static final Vector PLAYER_SIZE = new Vector(12, 10);
	public static final Vector USE_SIZE = new Vector(2, 2);
	public static final Vector PLAYER_OFFSET = new Vector(-2, 0);
	public static final int PLAYER_WALK_FRAMES = 8;
	public static final Vector SCREEN_SIZE = new Vector(TILE_SIZE*13.5f, TILE_SIZE*10);
	public static final int DEFAULT_PLAYER_HEALTH = 3;
	public static final Vector WEAPON_SIZE = new Vector(12,12);
	public static boolean DEBUG_ENABLED = false;
	public static final float DEATH_DURATION = 4.0f;
	public static final float MAP_LOAD_DURATION = 0.5f;
}
