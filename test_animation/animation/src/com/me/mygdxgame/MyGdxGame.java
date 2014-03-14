package com.me.mygdxgame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyGdxGame implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;

	private static final int FRAME_COLS = 6; // #1
	private static final int FRAME_ROWS = 5; // #2

	Animation walkAnimation; // #3
	Texture walkSheet; // #4
	TextureRegion[] walkFrames; // #5
	SpriteBatch spriteBatch; // #6
	TextureRegion currentFrame; // #7
	float stateTime; // #8
	
	int ox;
	int oy;
	
	@Override
	public void create() {
		// float w = Gdx.graphics.getWidth();
		// float h = Gdx.graphics.getHeight();
		//
		// camera = new OrthographicCamera(1, h / w);
		// batch = new SpriteBatch();
		//
		// texture = new Texture(Gdx.files.internal("data/libgdx.png"));
		// texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		//
		// TextureRegion region = new TextureRegion(texture, 0, 0, 512, 275);
		//
		// sprite = new Sprite(region);
		// sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
		// sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		// sprite.setPosition(-sprite.getWidth() / 2, -sprite.getHeight() / 2);

		walkSheet = new Texture(Gdx.files.internal("animation_sheet.png")); // #9
		TextureRegion[][] tmp = TextureRegion.split(walkSheet,
				walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight()
						/ FRAME_ROWS); // #10
		walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}
		walkAnimation = new Animation(0.25f, walkFrames); // #11
		spriteBatch = new SpriteBatch(); // #12
		stateTime = 0f;
		
		ox =0;
		oy =0;
	}

	@Override
	public void dispose() {
//		batch.dispose();
//		texture.dispose();
	}

	@Override
	public void render() {
		// Gdx.gl.glClearColor(1, 1, 1, 1);
		// Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		//
		// batch.setProjectionMatrix(camera.combined);
		// batch.begin();
		// sprite.draw(batch);
		// batch.end();

		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT); // #14
		stateTime += Gdx.graphics.getDeltaTime(); // #15
		currentFrame = walkAnimation.getKeyFrame(stateTime, true); // #16
		if (ox > (800 - walkSheet.getWidth() / FRAME_COLS)) {
			ox = 0;
		}else {
			ox +=1;
		}
		spriteBatch.begin();
		spriteBatch.draw(currentFrame, ox, 50); // #17
		spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
