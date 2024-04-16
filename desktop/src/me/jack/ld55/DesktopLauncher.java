package me.jack.ld55;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import me.jack.ld55.LD55Game;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(640 + 128+175 + 64,640 +128);
		config.setForegroundFPS(60);
		config.setTitle("LD55 - Overrun");
		new Lwjgl3Application(new LD55Game(), config);
	}
}
