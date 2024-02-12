package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

public class DesktopLauncher { //funcion principal
	public static void main (String[] arg) {
		Settings settings = new Settings();
        settings.maxWidth = 4096;
        settings.maxHeight = 4096;
        settings.alias = false;

		TexturePacker.processIfModified(settings, "./assets", "./assets",  "game"); //name del atlas
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(1280,768); //usar 16:9 (1280:768)
		config.setWindowSizeLimits(640, 480, Integer.MAX_VALUE, Integer.MAX_VALUE);
		config.setForegroundFPS(60);
		config.useVsync(true);
		config.setTitle("Redeem Game");
		config.setWindowIcon("miscAssets/logoTray.png");
		new Lwjgl3Application(new TDGame(), config); //el coso este es la primera clase que se muestra

	}
}

