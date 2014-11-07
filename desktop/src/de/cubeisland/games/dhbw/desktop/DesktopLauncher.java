package de.cubeisland.games.dhbw.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.cubeisland.games.dhbw.DHBWGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.height=720;
        config.width=1240;
		new LwjglApplication(new DHBWGame(), config);
	}
}
