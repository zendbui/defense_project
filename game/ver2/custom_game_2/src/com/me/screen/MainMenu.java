package com.me.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.me.mygdxgame.DefenseGame;
import com.me.setting.GameSetting;

public class MainMenu extends BaseScreen {

	Image background;
	Image play;
	Image highscore;
	Image music;
	Image sound;
	Image tips;
	Image back;
	
	Dialog dialogScore;
	
	Music bgMusic;
	Sound soundClick;

	public MainMenu(DefenseGame game) {
		super(game);

		setButton();
	}

	private void setButton() {

		background = new Image(new Texture(
				Gdx.files.internal("image/mainmenu/bg.png")));
		background.setSize(800, 480);
		background.setPosition(0, 0);

		play = new Image(new Texture(
				Gdx.files.internal("image/mainmenu/play.png")));
		play.setPosition(320, 175);
		play.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				Gdx.app.log("main menu", "Released");
				game.setScreen(new MapChoosenScreen(game));
				dispose();
			}
		});

		highscore = new Image(new Texture(
				Gdx.files.internal("image/mainmenu/score.png")));
		highscore.setPosition(345, 80);
		highscore.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				dialogScore = createDialog();
				stage.addActor(dialogScore);
			}
		});

		music = new Image(new Texture(
				Gdx.files.internal("image/mainmenu/music.png")));
		// music.setSize(50, 50);
		if (!GameSetting.GAME_MUSIC) {
			music.setColor(music.getColor().r, music.getColor().g,
					music.getColor().b, 0.5f);
		}
		music.setPosition(20, 20);
		music.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {

				if (music.getColor().a == 1.0f) {
					music.setColor(music.getColor().r, music.getColor().g,
							music.getColor().b, 0.5f);
					if (bgMusic.isPlaying()) {
						GameSetting.GAME_MUSIC = false;
						bgMusic.pause();
					}
				} else {
					soundClick.play();
					music.setColor(music.getColor().r, music.getColor().g,
							music.getColor().b, 1.0f);
					if (!bgMusic.isPlaying()) {
						GameSetting.GAME_MUSIC = true;
						bgMusic.play();
					}
				}
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
			}
		});
		sound = new Image(new Texture(
				Gdx.files.internal("image/mainmenu/sound.png")));
		if (!GameSetting.GAME_SOUND) {
			sound.setColor(sound.getColor().r, sound.getColor().g,
					sound.getColor().b, 0.5f);
		}
		// sound.setSize(50, 50);
		sound.setPosition(20, 70);
		sound.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {

				if (sound.getColor().a == 1.0f) {
					sound.setColor(sound.getColor().r, sound.getColor().g,
							sound.getColor().b, 0.5f);
					if (GameSetting.GAME_SOUND) {
						GameSetting.GAME_SOUND = false;
					}
				} else {
					sound.setColor(sound.getColor().r, sound.getColor().g,
							sound.getColor().b, 1.0f);
					soundClick.play();
					if (!GameSetting.GAME_SOUND) {
						GameSetting.GAME_SOUND = true;
					}
				}
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
			}
		});
		tips = new Image(new Texture(
				Gdx.files.internal("image/mainmenu/help.png")));
		// tips.setSize(50, 50);
		tips.setPosition(20, 120);
		tips.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (GameSetting.GAME_SOUND) {
					soundClick.play();
				}
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				Gdx.net.openURI("https://www.facebook.com/");
			}
		});

		stage.addActor(background);
		stage.addActor(play);
		stage.addActor(highscore);
		stage.addActor(music);
		stage.addActor(sound);
		stage.addActor(tips);

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
		} else {
			music.setColor(music.getColor().r, music.getColor().g,
					music.getColor().b, 0.5f);
		}
		if (!GameSetting.GAME_SOUND) {
			sound.setColor(sound.getColor().r, sound.getColor().g,
					sound.getColor().b, 0.5f);
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
			Gdx.app.log("MainMenu", "dispose bgMusic");

		}
		if (soundClick != null) {
			soundClick.dispose();
		}
	}
	private Dialog createDialog() {
		WindowStyle winstyle = new WindowStyle();
		winstyle.titleFont = getFont();
		Dialog dialog = new Dialog("", winstyle);
		int dialog_width = 398;
		int dialog_height = 232;
		int group_x = (int) (800 - dialog_width) / 2;
		int group_y = ((int) 480 / 2 - dialog_height) > 0 ? ((int) 480 / 2 - dialog_height)
				: 100;
		dialog.setSize(dialog_width, dialog_height);
		dialog.setPosition(group_x+30, 100);
		Image bg = new Image(new Texture(
				Gdx.files.internal("image/mainmenu/highscore.png")));
		dialog.setBackground(bg.getDrawable());

	
		back = new Image(new Texture(Gdx.files.internal("image/mainmenu/back.png")));
		back.setSize(60, 40);
		back.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				//TODO
				if (dialogScore != null) {
					dialogScore.remove();
					dialogScore = null;
				}
			}
		});
		
		dialog.add(back);
		dialog.padBottom(0);
		dialog.left();
		
		return dialog;

	}
}
