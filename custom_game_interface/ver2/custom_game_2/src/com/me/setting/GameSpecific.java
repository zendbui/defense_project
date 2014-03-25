package com.me.setting;

public class GameSpecific {

	// define enemy
	public static final int[] hitPoint = { 150, 250, 450, 200, 500, 1000 };
	public static final int[] airUnit = { 0, 0, 0, 1, 1, 0 };
	public static final int[] speed = { 2, 3, 2, 3, 4, 2 };
	public static final int[] bonus = { 10, 20, 40, 15, 40, 100 };

	// define tower
	public static final String[] gunName = { "Machine gun", "Missile turret",
			"Tesla tower", "Gulting Laser" };
	public static final int[] gunCost = { 100, 150, 200, 300 };
	public static final int[] gunUpgradeCost = { 50, 100, 100, 150 };
	public static final int[] isAntiAir = { 0, 1, 1, 1 };
	public static final int[] isAntiGround = { 1, 0, 1, 1 };
	public static final int[] gunSpeed = { 5, 3, 2, 1 };
	public static final float[] gunDelay = { 1f, 1.2f, 1.5f, 1.7f };
	public static final int[] gunBasicDamage = { 100, 150, 150, 250 };
	public static final int[] gunBasicRange = { 150, 200, 100, 100 };
	public static final int[] gunUpgradeDamage = { 150, 250, 350, 500 };
	public static final int[] gunUpgradeRange = { 200, 350, 150, 150 };

	// define building
	public static final int[] buildingCost = { 500, 650, 700, 900};

	public static final float sellRate = 0.7f;

	// define wave per map
	public static final int[][] wave_1_1 = { { 0, 5 } };
	public static final int[][] wave_1_2 = { { 0, 6 }, { 1, 3 } };
	public static final int[][] wave_1_3 = { { 1, 5 }, { 2, 6 } };
	public static final int[][] wave_1_4 = { { 0, 7 }, { 1, 6 }, { 2, 5 } };
	public static final int[][] wave_1_5 = { { 2, 7 }, { 3, 5 } };
	public static final int[][] wave_1_6 = { { 4, 3 }, { 4, 7 } };
	public static final int[][] wave_1_7 = { { 4, 8 }, { 5, 1 } };
	
	public static final int[][] wave_2_1 = { { 1, 5 }, { 1, 8 } };
	public static final int[][] wave_2_2 = { { 0, 6 }, { 1, 7 } };
	public static final int[][] wave_2_3 = { { 2, 4 }, { 3, 6 } };
	public static final int[][] wave_2_4 = { { 3, 6 }, { 4, 5 } };
	public static final int[][] wave_2_5 = { { 3, 5 }, { 2, 10 }, { 4, 8 } };
	public static final int[][] wave_2_6 = { { 2, 15 }, { 3, 9 }, { 4, 10 } };
	public static final int[][] wave_2_7 = { { 4, 9 }, { 5, 3 } };
	
	public static final int[][] wave_3_1 = { { 0, 10 }, { 1, 5 } };
	public static final int[][] wave_3_2 = { { 1, 8 }, { 2, 7 } };
	public static final int[][] wave_3_3 = { { 2, 9 }, { 3, 5 } };
	public static final int[][] wave_3_4 = { { 2, 7 }, { 3, 10 }, { 4, 8 } };
	public static final int[][] wave_3_5 = { { 3, 7 }, { 4, 8 }, { 4, 12 } };
	public static final int[][] wave_3_6 = { { 2, 15 }, { 3, 10 }, { 4, 10 } };
	public static final int[][] wave_3_7 = { { 4, 15 }, { 5, 5 } };
}
