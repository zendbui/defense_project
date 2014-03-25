package com.me.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.me.mygdxgame.DefenseGame;
import com.me.setting.GameSetting;

public class MapChoosenScreen extends BaseScreen implements InputProcessor {
	Image bg;
	Image map1;
	Image map2;
	Image map3;
	Image back;

	Music bgMusic;
	Sound soundClick;

	public MapChoosenScreen(DefenseGame game) {
		super(game);
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchBackKey(true);
		setDisplay();
	}

	private void setDisplay() {
		bg = new Image(
				new Texture(Gdx.files.internal("image/mapselect/bg.png")));
		bg.setSize(800, 480);
		map1 = new Image(new Texture(
				Gdx.files.internal("image/mapselect/minimap_1.png")));
		map1.setSize(200, 150);
		map1.setPosition(50, 200);
		map1.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (GameSetting.GAME_SOUND) {
					soundClick.play();
				}
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new DifficultChoosenScreen(game));
				GameSetting.GAME_MAP = GameSetting.map1;
				dispose();
			}
		});

		map2 = new Image(new Texture(
				Gdx.files.internal("image/mapselect/minimap_2.png")));
		map2.setSize(200, 150);
		map2.setPosition(300, 200);
		map2.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (GameSetting.GAME_SOUND) {
					soundClick.play();
				}
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new DifficultChoosenScreen(game));
				GameSetting.GAME_MAP = GameSetting.map2;
				dispose();
			}
		});

		map3 = new Image(new Texture(
				Gdx.files.internal("image/mapselect/minimap_3.png")));
		map3.setSize(200, 150);
		map3.setPosition(550, 200);
		map3.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (GameSetting.GAME_SOUND) {
					soundClick.play();
				}
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new DifficultChoosenScreen(game));
				GameSetting.GAME_MAP = GameSetting.map3;
				dispose();
			}
		});

		back = new Image(new Texture(
				Gdx.files.internal("image/mapselect/back.png")));
		// back.setSize(100, 80);
		back.setPosition(640, 400);
		back.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (GameSetting.GAME_SOUND) {
					soundClick.play();
				}
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new MainMenu(game));
				dispose();
			}
		});

		stage.addActor(bg);
		stage.addActor(back);
		stage.addActor(map1);
		stage.addActor(map2);
		stage.addActor(map3);

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
			Gdx.app.log("Mapselect", "dispose bgMusic");
		}

		if (soundClick != null) {
			soundClick.dispose();
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.BACK) {
			System.out.println("key back");
			game.setScreen(new MainMenu(game));
			dispose();
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		if (keycode == Keys.BACK) {
			game.setScreen(new MainMenu(game));
			dispose();
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
