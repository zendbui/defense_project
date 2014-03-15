package com.me.image;

import java.awt.Point;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class TowerImage extends Image {
	final static int HEIGHT = 32;
	final static int WIDTH = 32;
	protected int level;
	protected int range = 50;
	protected int cost = 100;
	protected int damage = 10;
	protected float timeToAction = .5f;
	
	protected EnemyImage currTarget;
	protected ArrayList<Point> listBullet;
	
	float centerX;
	float centerY;
	
	public void setAttributes(int level) {

	}

	public TowerImage(TextureAtlas atlas, int col, int row) {
		super(atlas.findRegion("anim/phai", 1));

		this.setSize(32, 32);
		this.setPosition(col * 32, row * 32);
		centerX = col * 32 + 16;
		centerY = row * 32 + 16;

	}
	public void fire(){
		
	}
	public void faceTarget() {

	}

	public void sold() {

	}

	public void upgrade() {

	}

	public int getRange() {
		return this.range;
	}

	public EnemyImage getTarget() {
		return this.currTarget;
	}
}
