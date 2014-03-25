package com.me.image;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.me.setting.GameSpecific;

public class BuildingImage extends Image{
	protected int TYPE;

	final static int HEIGHT = 64;
	final static int WIDTH = 64;
	protected String name;
	protected float cost;

	float centerX;
	float centerY;
	int col;
	int row;

	Sound soundFire;
	
	public BuildingImage(int type, int col, int row) {

		super(new Texture(Gdx.files.internal("building/build"+(type+1)+".png")));

		this.TYPE = type;

		this.setSize(WIDTH, HEIGHT);
		this.setPosition(col * 32, row * 32);

		this.col = col;
		this.row = row;

		centerX = col * 32 + 32;
		centerY = row * 32 + 32;
		
		this.cost = GameSpecific.buildingCost[type];

	}
	public float getCost() {
		return this.cost;
	}
	
	public int getCol() {
		return this.col;
	}

	public int getRow() {
		return this.row;
	}
}
