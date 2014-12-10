package com.willie.compsci.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.willie.compsci.WayRillie;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new WayRillie(), config);
		config.title ="Death Ray Rainbow";
		config.width = 800;
		config.height = 450;
		config.resizable = true;
	}
}
