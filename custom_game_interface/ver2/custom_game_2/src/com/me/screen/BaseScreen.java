package com.me.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.me.mygdxgame.DefenseGame;

public abstract class BaseScreen implements Screen {

	public static final int VIEWPORT_WIDTH = 800, VIEWPORT_HEIGHT = 480;
	public static final int TILE = 16;

	protected final DefenseGame game;
	protected final Stage stage;

	private BitmapFont font;
	private SpriteBatch batch;
	protected TextureAtlas monsterAtlas;
	protected TextureAtlas towerAtlas;
	protected TextureAtlas buildingAtlas;
	private Skin skin;

	public BaseScreen(DefenseGame game) {
		this.game = game;
		this.stage = new Stage(0, 0, true);
	}

	public BitmapFont getFont() {
		if (font == null) {
			font = new BitmapFont();
			font.getRegion().getTexture()
					.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			// font = new BitmapFont(Gdx.files.internal("data/dfont.fnt"),
			// Gdx.files.internal("data/dfont_0.png"), false);
			font.setColor(Color.RED);
			font.setScale(2);
		}
		return font;
	}

	public SpriteBatch getBatch() {
		if (batch == null) {
			batch = new SpriteBatch();
		}
		return batch;
	}

	public TextureAtlas getMonsterAtlas() {
		if (monsterAtlas == null) {
			monsterAtlas = new TextureAtlas(
					Gdx.files.internal("textpacker/monster.pack"));
		}
		return monsterAtlas;
	}

	public TextureAtlas getTowerAtlas() {
		if (towerAtlas == null) {
			// TODO
			towerAtlas = new TextureAtlas(
					Gdx.files.internal("textpacker/tower.pack"));
		}
		return towerAtlas;
	}

	public TextureAtlas getBuildingAtlas() {
		if (buildingAtlas == null) {
			// TODO
			buildingAtlas = new TextureAtlas(
					Gdx.files.internal("monster/monster.pack"));
		}
		return buildingAtlas;
	}

	public Skin getSkin() {
		if (skin == null) {
			skin = new Skin(Gdx.files.internal("data/uiskin.json"),
					new TextureAtlas(Gdx.files.internal("data/uiskin.atlas")));
			// skin.addRegions(getAtlas());
		}
		return skin;
	}

	@Override
	public void show() {
		// set the stage as the input processor
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void resize(int width, int height) {
		// resize the stage
		stage.setViewport(width, height, true);
	}

	@Override
	public void render(float delta) {
		// (1) process the game logic

		// update the actors
		stage.act(delta);

		// (2) draw the result

		// clear the screen with the given RGB color (black)
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// draw the actors
		stage.draw();
	}

	@Override
	public void hide() {
		// dispose the resources by default
		// dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		// stage.dispose();
		if (font != null)
			font.dispose();
		if (batch != null)
			batch.dispose();
		if (monsterAtlas != null)
			monsterAtlas.dispose();
		if (towerAtlas != null)
			towerAtlas.dispose();
		if (buildingAtlas != null)
			buildingAtlas.dispose();
		if (skin != null)
			skin.dispose();
	}

}
