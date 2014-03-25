package com.me.image;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.me.setting.GameSpecific;

public class TowerImage extends Image {

	protected TextureAtlas atlas;

	protected int TYPE;

	final static int HEIGHT = 64;
	final static int WIDTH = 64;
	protected String name;
	protected int currLevel = 0;
	protected int maxLevel = 1;
	protected float cost;
	protected float upgradeCost;
	protected boolean isAntiAir;
	protected boolean isAntiGround;
	protected float range;
	protected float sellRate;
	protected int damage;
	protected int speed;
	protected float timeDelay;
	protected float timeWait = 0.0f;

	protected MonsterImage currTarget;
	protected ArrayList<Bullet> listBullet;

	float centerX;
	float centerY;
	int col;
	int row;

	Sound soundFire;

	public void setAttributes() {
		this.name = GameSpecific.gunName[this.TYPE];
		this.cost = GameSpecific.gunCost[this.TYPE];
		this.upgradeCost = GameSpecific.gunUpgradeCost[this.TYPE];
		if (GameSpecific.isAntiAir[this.TYPE] == 0) {
			this.isAntiAir = false;
		} else {
			this.isAntiAir = true;
		}
		if (GameSpecific.isAntiGround[this.TYPE] == 0) {
			this.isAntiGround = false;
		} else {
			this.isAntiGround = true;
		}
		this.timeDelay = GameSpecific.gunDelay[this.TYPE];
		this.speed = GameSpecific.gunSpeed[this.TYPE];
		this.damage = GameSpecific.gunBasicDamage[this.TYPE];
		this.range = GameSpecific.gunBasicRange[this.TYPE];
		this.sellRate = GameSpecific.sellRate;
	}

	public TowerImage(TextureAtlas atlas, int type, int col, int row) {

		super(atlas.findRegion("tower" + (type + 1), -1));

		this.atlas = atlas;

		this.TYPE = type;

		this.setSize(WIDTH, HEIGHT);
		this.setPosition(col * 32, row * 32);

		this.col = col;
		this.row = row;

		centerX = col * 32 + 32;
		centerY = row * 32 + 32;

		listBullet = new ArrayList<Bullet>();
		setAttributes();
	}

	public void faceTarget() {

	}

	public float sold() {
		return this.cost * this.sellRate;
	}

	public float getCost() {
		return this.cost;
	}

	public float getUpgradeCost() {
		return this.upgradeCost;
	}

	public int getLevel() {
		return this.currLevel;
	}

	public void upgrade() {
		if (currLevel == maxLevel) {
			return;
		}
		this.currLevel++;
		this.damage = GameSpecific.gunUpgradeDamage[this.TYPE];
		this.range = GameSpecific.gunUpgradeRange[this.TYPE];
		this.cost += this.upgradeCost;
		Image newImage = new Image(new Texture(Gdx.files.internal("tower/tower"
				+ (this.TYPE + 1) + "up.png")));
		this.setDrawable(newImage.getDrawable());
	}

	public int getCol() {
		return this.col;
	}

	public int getRow() {
		return this.row;
	}

	public float getRange() {
		return this.range;
	}

	public MonsterImage getTarget() {
		return this.currTarget;
	}

	public void fire(float delta, ArrayList<MonsterImage> arr) {
		timeWait += delta;
		if (timeWait >= timeDelay) {
			timeWait = 0.0f;
			if (currTarget == null) {
				currTarget = findTarget(arr);
			} else if (CalcDistance(this.centerX, this.centerY,
					currTarget.getCenterX(), currTarget.getCenterY()) > this.range
					|| !this.currTarget.isAlive()) {
				// find new target
				currTarget = findTarget(arr);
			}
			if (currTarget != null) {
				// fire
				Bullet newBullet = new Bullet(this.centerX, this.centerY,
						this.damage, this.speed, currTarget, this.TYPE);
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

	public float CalcDistance(float x1, float y1, float x2, float y2) {
		return (float) Math.sqrt((double) (x1 - x2) * (x1 - x2) + (y1 - y2)
				* (y1 - y2));
	}

	public MonsterImage findTarget(ArrayList<MonsterImage> arr) {
		Iterator<MonsterImage> iter = arr.iterator();
		while (iter.hasNext()) {
			MonsterImage enemy = (MonsterImage) iter.next();
			if (CalcDistance(centerX, centerY, enemy.getCenterX(),
					enemy.getCenterY()) < this.range) {
				return enemy;
			}
		}
		return null;
	}

	public ArrayList<Bullet> getArrBullet() {
		return this.listBullet;
	}

	public boolean isMaxLevel() {
		if (currLevel == maxLevel) {
			return true;
		} else {
			return false;
		}
	}

	public float getCenterX() {
		return this.centerX;
	}

	public float getCenterY() {
		return this.centerY;
	}
}
