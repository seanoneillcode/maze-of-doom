import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Maze Of Doom";
		cfg.width = 1024;
		cfg.height = 768;
		cfg.fullscreen = false;
		cfg.vSyncEnabled = true;
		new LwjglApplication(new MazeOfDoom(), cfg);
	}
}
