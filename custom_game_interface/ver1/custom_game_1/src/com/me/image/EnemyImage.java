package com.me.image;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.me.image.MyAtlasRegion;

public class EnemyImage extends Image {
	public final static int UP = 0;
	public final static int DOWN = 1;
	public final static int LEFT = 2;
	public final static int RIGHT = 3;

	final static int HEIGHT = 32;
	final static int WIDTH = 32;

	final static int FAST = 300;
	final static int SLOW = 100;

	final static float F_FrameDura = 0.25f;
	final static float S_FrameDura = 0.5f;

	Animation UpAnimation;
	Animation DownAnimation;
	Animation LeftAnimation;
	Animation RightAnimation;

	Animation CurrAnimation;

	MyAtlasRegion currentFrame;
	float stateTime = 0;

	private float FrameDura = S_FrameDura;
	private int speed = SLOW;
	private int direction = RIGHT;
	private boolean isStanding = true;

	private boolean success = false;

	TiledMap tiledMap;
	TiledMapRenderer tileMapRenderer;
	TiledMapTileLayer passLayer;
	MapLayer layer;
	
	float originHealth=100;
	float currHealth=100;
	
	public EnemyImage(TextureAtlas atlas) {
		super(atlas.findRegion("anim/phai", 1));

		this.setSize(WIDTH, HEIGHT);
		this.setPosition(0, 10 * 32); // xuat phat tai o [0][10]

		MyAtlasRegion[] UpWalking = new MyAtlasRegion[] {
				new MyAtlasRegion(atlas.findRegion("anim/len", 1)),
				new MyAtlasRegion(atlas.findRegion("anim/len", 2)),
				new MyAtlasRegion(atlas.findRegion("anim/len", 3)) };

		MyAtlasRegion[] DownWalking = new MyAtlasRegion[] {
				new MyAtlasRegion(atlas.findRegion("anim/xuong", 1)),
				new MyAtlasRegion(atlas.findRegion("anim/xuong", 2)),
				new MyAtlasRegion(atlas.findRegion("anim/xuong", 3)) };

		MyAtlasRegion[] LeftWalking = new MyAtlasRegion[] {
				new MyAtlasRegion(atlas.findRegion("anim/trai", 1)),
				new MyAtlasRegion(atlas.findRegion("anim/trai", 2)),
				new MyAtlasRegion(atlas.findRegion("anim/trai", 3)) };

		MyAtlasRegion[] RightWalking = new MyAtlasRegion[] {
				new MyAtlasRegion(atlas.findRegion("anim/phai", 1)),
				new MyAtlasRegion(atlas.findRegion("anim/phai", 2)),
				new MyAtlasRegion(atlas.findRegion("anim/phai", 3)) };

		UpAnimation = new Animation(FrameDura, UpWalking);
		DownAnimation = new Animation(FrameDura, DownWalking);
		LeftAnimation = new Animation(FrameDura, LeftWalking);
		RightAnimation = new Animation(FrameDura, RightWalking);
	}

	public void setTileMap(TiledMap tiledMap, TiledMapRenderer tileMapRenderer,
			TiledMapTileLayer passLayer) {
		this.tiledMap = tiledMap;
		this.tileMapRenderer = tileMapRenderer;
		this.passLayer = passLayer;
	}

	public void ChangeImage(MyAtlasRegion aregion) {
		this.setDrawable(aregion.getDrawable());
	}

	public void setDirection(int direct) {
		this.direction = direct;
	}

	public int getDirection() {
		return this.direction;
	}

	public void setStand(boolean value) {
		isStanding = value;
	}

	public boolean isStanding() {
		return isStanding;
	}

	public boolean isSuccess() {
		return success;
	}
	private void setSuccess(boolean success){
		this.success = success;
	}
	public void setFast() {
		speed = FAST;
		FrameDura = F_FrameDura;
	}

	public void setSlow() {
		speed = SLOW;
		FrameDura = S_FrameDura;
	}

	public void setSpeed(int s) {
		speed = s * 5;
		FrameDura = (float) speed / 100.0f;
	}

	public void Walking(float delta) {
		// if (isStanding)
		// return;

		if (direction == UP)
			WalkUp(delta);
		else if (direction == DOWN)
			WalkDown(delta);
		else if (direction == LEFT)
			WalkLeft(delta);
		else if (direction == RIGHT)
			WalkRight(delta);
	}

	private void WalkUp(float delta) {
		this.setY(this.getY() + 1);
		if (!isPass())
			this.setY(this.getY() - 1);

		CurrAnimation = UpAnimation;
		WalkAnim(delta);

	}

	private void WalkDown(float delta) {
		this.setY(this.getY() - 1);
		if (!isPass())
			this.setY(this.getY() + 1);

		CurrAnimation = DownAnimation;
		WalkAnim(delta);
	}

	private void WalkRight(float delta) {
		this.setX(this.getX() + 1);
		if (!isPass())
			this.setX(this.getX() - 1);

		CurrAnimation = RightAnimation;
		WalkAnim(delta);
	}

	private void WalkLeft(float delta) {
		this.setX(this.getX() - 1);
		if (!isPass())
			this.setX(this.getX() + 1);

		CurrAnimation = LeftAnimation;
		WalkAnim(delta);
	}

	private void WalkAnim(float delta) {
		stateTime += delta;
		currentFrame = (MyAtlasRegion) CurrAnimation.getKeyFrame(stateTime,
				true);
		ChangeImage(currentFrame);
	}

	private boolean isPass() {
		
		if (success) {
			Gdx.app.log("success", "finish job");
			return false;
		}
		
		float x = this.getX();
		float y = this.getY();

		setStand(true);

		if (x < 0)
			return false;
		else if (y < 0)
			return false;
		if (x > 800)
			return false;
		else if (y > 480)
			return false;

		if ((direction == LEFT || direction == RIGHT) && x % 32 != 0) {
			return true;
		} else if ((direction == UP || direction == DOWN) && y % 32 != 0) {
			return true;
		}

		boolean ispass = true;
		final int w = 32;
		Vector2[] v = new Vector2[2];
		v[0] = new Vector2(x, y);
		if (direction == RIGHT) {
			v[1] = new Vector2(x + w, y);
		} else if (direction == LEFT) {
			v[1] = new Vector2(x - w, y);
		} else if (direction == UP) {
			v[1] = new Vector2(x, y + w);
		} else if (direction == DOWN) {
			v[1] = new Vector2(x, y - w);
		}

		// v[2] = new Vector2(x + w + w, y);

		for (int i = 0; i < v.length; i++) {
			int row = (int) v[i].y / 32;
			int col = (int) v[i].x / 32;

			try {
				passLayer.getCell(col, row).getTile().getProperties();
			} catch (Exception e) {
				int newDirection = changeDirection();
				setDirection(newDirection);
				setStand(true);
				return false;
			}
		}

		return ispass;
	}

	private int changeDirection() {
		float x = this.getX();
		float y = this.getY();
		if (direction == UP || direction == DOWN) {

			int row = (int) y / 32;
			int colleft = (int) (x - 32) / 32;
			int colright = (int) (x + 32) / 32;
			if (direction == UP) {
				this.setY(this.getY() + 1);
			} else {
				this.setY(this.getY() - 1);
			}
			try {
				passLayer.getCell(colright, row).getTile().getProperties();
				return RIGHT;
			} catch (Exception e) {
				try {
					passLayer.getCell(colleft, row).getTile().getProperties();
					return LEFT;
				} catch (Exception e2) {
					setSuccess(true);
					return -1;
				}

			}

		} else if (direction == LEFT || direction == RIGHT) {
			int rowup = (int) (y + 32) / 32;
			int rowdown = (int) (y - 32) / 32;
			int col = (int) x / 32;
			if (direction == RIGHT) {
				this.setX(this.getX() + 1);
			} else {
				this.setX(this.getX() - 1);
			}
			try {
				passLayer.getCell(col, rowup).getTile().getProperties();
				return UP;
			} catch (Exception e) {
				try {
					passLayer.getCell(col, rowdown).getTile().getProperties();
					return DOWN;
				} catch (Exception e2) {
					Gdx.app.log("", "end");
					setSuccess(true);
					return -1;
				}
			}
		}
		return -1;
	}
	
	public float getHealthyRate(){
		return (float)currHealth/originHealth;
	}
	public boolean isAlive(){
		if (currHealth>0) {
			return true;
		}else{
			return false;
		}
			
	}
	public float getCenterX(){
		return this.getX() + 16;
	}
	public float getCenterY(){
		return this.getY() + 16;
	}
}
