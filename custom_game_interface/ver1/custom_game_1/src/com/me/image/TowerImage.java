package com.me.image;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class TowerImage extends Image {
	protected int TYPE;
	
	final static int HEIGHT = 32;
	final static int WIDTH = 32;
	protected int level = 0;
	protected int maxLevel = 1;
	protected float range = 100.0f;
	protected int cost = 100;
	protected int damage = 10;
	protected float timeToAction = .5f;
	protected float timeDelay = 0.0f;

	protected EnemyImage currTarget;
	protected ArrayList<Bullet> listBullet;

	float centerX;
	float centerY;
	int col;
	int row;
	public void setAttributes(int level) {

	}

	public TowerImage(TextureAtlas atlas, int type,int col, int row) {
		
		super(atlas.findRegion("anim/phai", 1));
		
		this.TYPE = type;

		this.setSize(32, 32);
		this.setPosition(col * 32, row * 32);
		
		this.col = col;
		this.row = row;
		
		centerX = col * 32 + 16;
		centerY = row * 32 + 16;
		
		listBullet = new ArrayList<Bullet>();
	}

	public void faceTarget() {

	}

	public void sold() {

	}
	public int getLevel(){
		return this.level;
	}
	public void upgrade(int level) {
		if (level > maxLevel) {
			return;
		}
		setAttributes(level);
	}
	
	public int getCol(){
		return this.col;
	}
	public int getRow(){
		return this.row;
	}
	
	public float getRange() {
		return this.range;
	}

	public EnemyImage getTarget() {
		return this.currTarget;
	}

	public void fire(float delta, ArrayList<EnemyImage> arr){
		timeDelay+= delta;
		if (timeDelay >= timeToAction) {
			timeDelay=0.0f;
			if (currTarget == null) {
				currTarget = findTarget(arr);
			}else if (CalcDistance(this.centerX, this.centerY,
		    currTarget.getCenterX(), currTarget.getCenterY()) > this.range || !this.currTarget.isAlive()) {
				// find new target
				currTarget = findTarget(arr);
			}
			if (currTarget != null) {
				// fire
				Bullet newBullet = new Bullet(this.centerX, this.centerY, currTarget);
				this.listBullet.add(newBullet);
			}
		}
		Iterator<Bullet> iter = this.listBullet.iterator();
		while (iter.hasNext()) {
			Bullet bullet = iter.next();
			bullet.updateTarget();
			if (bullet.isShot()) {
				iter.remove();
				// set gold
			}
		}
	}
	public float CalcDistance(float x1,float y1, float x2, float y2){
		return (float) Math.sqrt((double)(x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	}
	public EnemyImage findTarget(ArrayList<EnemyImage> arr){
		Iterator<EnemyImage> iter = arr.iterator();
		while (iter.hasNext()) {
			EnemyImage enemy = (EnemyImage) iter.next();
			if (CalcDistance(centerX, centerY, enemy.getCenterX(), enemy.getCenterY()) < this.range) {
				return enemy;
			}
		}
		return null;
	}
	public ArrayList<Bullet> getArrBullet(){
		return this.listBullet;
	}
	
	
}
