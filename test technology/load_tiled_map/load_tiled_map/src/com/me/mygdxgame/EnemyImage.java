package com.me.mygdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class EnemyImage extends Image {
	public final static int UP = 0;
	public final static int DOWN = 1;
	public final static int LEFT = 2;
	public final static int RIGHT = 3;

	final static int HEIGHT = 32;
	final static int WIDTH = 32;

	Animation UpAnimation;
	Animation DownAnimation;
	Animation LeftAnimation;
	Animation RightAnimation;

	Animation CurrAnimation;

	TextureRegion currentFrame;
	float stateTime = 0;
	float timechange = 0;

	private float FrameDura = 0.25f;
	private int speed = 100;
	private int direction = RIGHT;

	TiledMap tiledMap;
	TiledMapRenderer tileMapRenderer;
	TiledMapTileLayer directWayLayer;

	public EnemyImage(String monster) {
		super();
		
		
		this.setSize(WIDTH, HEIGHT);
		this.setPosition(0, 320);

		TextureRegion[] upFrame = new TextureRegion[] {
				new TextureRegion(new Texture(
						Gdx.files.internal("data/anim/mlen_1.png"))),
				new TextureRegion(new Texture(
						Gdx.files.internal("data/anim/mlen_1.png"))) };
		TextureRegion[] downFrame= new TextureRegion[] {
				new TextureRegion(new Texture(
						Gdx.files.internal("data/anim/mxuong_1.png"))),
				new TextureRegion(new Texture(
						Gdx.files.internal("data/anim/mxuong_1.png"))) };
		TextureRegion[] leftFrame= new TextureRegion[] {
				new TextureRegion(new Texture(
						Gdx.files.internal("data/anim/mtrai_1.png"))),
				new TextureRegion(new Texture(
						Gdx.files.internal("data/anim/mtrai_1.png"))) };
		TextureRegion[] rightFrame= new TextureRegion[] {
				new TextureRegion(new Texture(
						Gdx.files.internal("data/anim/mphai_1.png"))),
				new TextureRegion(new Texture(
						Gdx.files.internal("data/anim/mphai_1.png"))) };
		
		UpAnimation = new Animation(FrameDura, upFrame);
		DownAnimation = new Animation(FrameDura, downFrame);
		LeftAnimation = new Animation(FrameDura, leftFrame);
		RightAnimation = new Animation(FrameDura, rightFrame);

	}
	public void setTileMap(TiledMap tiledMap, TiledMapRenderer tileMapRenderer, TiledMapTileLayer directWayLayer)
    {
    	this.tiledMap = tiledMap;
    	this.tileMapRenderer = tileMapRenderer;
    	this.directWayLayer = directWayLayer;
    }
	
	// fix
	public void ChangeImage(TextureRegion region) {
		this.setDrawable(new TextureRegionDrawable(region));
	}
	
	public void setDirection(int direct) {
		this.direction = direct;
	}
	public void Moving(float delta) {

		if (direction == UP)
			MoveUp(delta);
		else if (direction == DOWN)
			MoveDown(delta);
		else if (direction == LEFT)
			MoveLeft(delta);
		else if (direction == RIGHT)
			MoveRight(delta);
	}
	private boolean isPass() {
		float x = this.getX();
    	float y = this.getY();

    	if (x < 0) return false;
    	else if (y < 0) return false;
    	if (x > BaseScreen.VIEWPORT_WIDTH) return false;
    	else if (y > BaseScreen.VIEWPORT_HEIGHT) return false;

    	boolean ispass = true;
		
		final int w = 32;
		Vector2[] v = new Vector2[3];
		v[0] = new Vector2(x, y);
		v[1] = new Vector2(x + w, y);
		v[2] = new Vector2(x + w + w, y);
		
		for (int i = 0; i < v.length; i++)
		{
			int row = (int) (BaseScreen.VIEWPORT_HEIGHT - v[i].y) / w;
			int col = (int) v[i].x / w;
			
			String str = "";
			try {
				directWayLayer.getCell(col, row).getTile().getProperties();
				str = "1";
			} catch (Exception e) {
				str = "0";
			}
			if ("0".equals(str))
			{
				ispass = false;
				break;
			}
		}
		
		return ispass;
	}
	private void MoveUp(float delta) {
		this.setY(this.getY() + (speed * delta));
		if (!isPass()) {
			return;
		}
		CurrAnimation = UpAnimation;
		MoveAnim(delta);
	}
	
	private void MoveDown(float delta) 
    {
    	this.setY(this.getY() - (speed * delta));
    	if (!isPass()) this.setY(this.getY() + (speed * delta));

    	CurrAnimation = DownAnimation;
    	MoveAnim(delta);
    }

    private void MoveRight(float delta) 
    {
    	this.setX(this.getX() + (speed * delta));
    	if (!isPass()) this.setX(this.getX() - (speed * delta));
    	
    	CurrAnimation = RightAnimation;
    	MoveAnim(delta);
    }
    
    private void MoveLeft(float delta) 
    {
    	this.setX(this.getX() - (speed * delta));
    	if (!isPass()) this.setX(this.getX() + (speed * delta));
    	
    	CurrAnimation = LeftAnimation;
    	MoveAnim(delta);
    }

    private void MoveAnim(float delta)
    {
    	stateTime += delta;
    	currentFrame = (TextureRegion) CurrAnimation.getKeyFrame(stateTime, true);
    	ChangeImage(currentFrame);
    }
}
