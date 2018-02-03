package com.chesskabaddi.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.chesskabaddi.game.ChessKabaddi;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height=750;
		config.width=1200;
		new LwjglApplication(new ChessKabaddi(), config);
	}
}
