package de.cubeisland.games.dhbw.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.cubeisland.games.dhbw.DHBWGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1280;
        config.height = 720;
        config.samples = 4;
        config.title = "Exmatrikulation - Die Geschichte der DHBW";
        config.addIcon("images/logo16.ico", Files.FileType.Internal);
        config.addIcon("images/logo32.ico", Files.FileType.Internal);
        config.addIcon("images/logo64.ico", Files.FileType.Internal);
        config.addIcon("images/logo128.ico", Files.FileType.Internal);
        new LwjglApplication(new DHBWGame(), config);
    }
}
