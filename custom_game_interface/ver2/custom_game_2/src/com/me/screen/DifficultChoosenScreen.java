package com.me.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.me.mygdxgame.DefenseGame;
import com.me.setting.GameSetting;

public class DifficultChoosenScreen extends BaseScreen {
	Image bg;
	Image easy;
	Image normal;
	Image insane;
	Image back;

	Music bgMusic;
	Sound soundClick;
	
	public DifficultChoosenScreen(DefenseGame game) {
		super(game);

		setDisplay();
	}

	private void setDisplay() {
		bg = new Image(new Texture(
				Gdx.files.internal("image/difficultselect/bg.png")));
		bg.setSize(800, 480);
		easy = new Image(new Texture(
				Gdx.files.internal("image/difficultselect/diff_1.png")));
		easy.setSize(200, 150);
		easy.setPosition(50, 200);
		easy.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (GameSetting.GAME_SOUND) {
					soundClick.play();
				}
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new SceenMap(game));
				GameSetting.GAME_MODE = GameSetting.EASY;
				dispose();
			}
		});

		normal = new Image(new Texture(
				Gdx.files.internal("image/difficultselect/diff_2.png")));
		normal.setSize(200, 150);
		normal.setPosition(300, 200);
		normal.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (GameSetting.GAME_SOUND) {
					soundClick.play();
				}
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new SceenMap(game));
				GameSetting.GAME_MODE = GameSetting.NORMAL;
				dispose();
			}
		});

		insane = new Image(new Texture(
				Gdx.files.internal("image/difficultselect/diff_3.png")));
		insane.setSize(200, 150);
		insane.setPosition(550, 200);
		insane.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (GameSetting.GAME_SOUND) {
					soundClick.play();
				}
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new SceenMap(game));
				GameSetting.GAME_MODE = GameSetting.INSANE;
				dispose();
			}
		});

		back = new Image(new Texture(
				Gdx.files.internal("image/mapselect/back.png")));
		back.setPosition(640, 400);
		back.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (GameSetting.GAME_SOUND) {
					soundClick.play();
				}
				game.setScreen(new MapChoosenScreen(game));
				dispose();
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
			}
		});

		stage.addActor(bg);
		stage.addActor(back);
		stage.addActor(easy);
		stage.addActor(normal);
		stage.addActor(insane);

		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("music/bg.ogg"));
		bgMusic.setLooping(true);
		soundClick = Gdx.audio.newSound(Gdx.files
				.internal("music/sound_right.ogg"));
	}

	@Override
	public void show() {
		super.show();
		if (GameSetting.GAME_MUSIC) {
			bgMusic.play();
		}
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(800, 480, false);
	}

	@Override
	public void render(float delta) {
		stage.act(delta);

		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.draw();
	}

	@Override
	public void dispose() {
		super.dispose();

		if (bgMusic != null) {
			bgMusic.dispose();
			Gdx.app.log("difficult select","dispose bgMusic");
		}
		
		if (soundClick != null) {
			soundClick.dispose();
		}
	}
}
