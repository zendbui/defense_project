package com.me.mygdxgame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "custom_game_1";
		cfg.useGL20 = true;
		cfg.width = 800;
		cfg.height = 480;

		new LwjglApplication(new DefenseGame(), cfg);
	}
}
