package com.me.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.me.mygdxgame.DefenseGame;

public class MainMenu extends BaseScreen {
	
	Image background;
	Image play;
	Image highscore;
	Image music;
	Image sound;
	Image tips;

	public MainMenu(DefenseGame game) {
		super(game);

		setButton();
	}

	private void setButton() {
		
		background = new Image(new Texture(Gdx.files.internal("image/mainmenu/background.png")));
		background.setSize(800, 480);
		background.setPosition(0, 0);
		
		play = new Image(new Texture(Gdx.files.internal("image/mainmenu/play.png")));
		play.setSize(200, 80);
		play.setPosition(200, 200);
		play.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				Gdx.app.log("main menu", "Released");
				game.setScreen(new SceenMap(game));
				dispose();
			}
		});
		
		highscore = new Image(new Texture(Gdx.files.internal("image/mainmenu/play.png")));
		highscore.setSize(200, 80);
		highscore.setPosition(200, 200);
		highscore.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
			}
		});
		
		music = new Image(new Texture(Gdx.files.internal("image/mainmenu/play.png")));
		sound = new Image(new Texture(Gdx.files.internal("image/mainmenu/play.png")));
		tips = new Image(new Texture(Gdx.files.internal("image/mainmenu/play.png")));
		
		
		
		stage.addActor(play);
	}

	@Override
	public void show() {
		super.show();
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

}
