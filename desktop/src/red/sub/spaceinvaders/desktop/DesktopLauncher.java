package red.sub.spaceinvaders.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import red.sub.spaceinvaders.SpaceInvaders;

public class DesktopLauncher {
    public static final String GAME_NAME = "Space Invaders";
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                config.width = 1280;
                config.height = 720;
                config.fullscreen = false;
                config.resizable = false;
                config.title = GAME_NAME;
		new LwjglApplication(new SpaceInvaders(), config);
	}
}
