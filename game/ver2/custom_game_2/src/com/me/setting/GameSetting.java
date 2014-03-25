package com.me.setting;

public class GameSetting {
	public static final String none_map = "none";
	public static final String map1 = "map1.tmx";
	public static final String map2 = "map2.tmx";
	public static final String map3 = "map3.tmx";

	public static final int NONE = 0;
	public static final int EASY = 1;
	public static final int NORMAL = 2;
	public static final int INSANE = 3;

	public static final int EASY_WAVE = 3;
	public static final int NORMAL_WAVE = 5;
	public static final int INSANE_WAVE = 7;

	public static int GAME_MODE;
	public static String GAME_MAP;
	
	public static boolean GAME_MUSIC = true;
	public static boolean GAME_SOUND = true;
}
