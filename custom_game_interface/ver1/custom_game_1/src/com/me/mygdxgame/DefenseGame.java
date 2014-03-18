package com.me.mygdxgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.me.screen.MainMenu;
import com.me.screen.SceenMap;

public class DefenseGame extends Game {

	@Override
	public void create() {
		this.setScreen(new SceenMap(this));
//		this.setScreen(new MainMenu(this));
	}
	
	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
	
//	public SceenMap getScreenMap() {
//		return new SceenMap(this);
//	}
//
//	@Override
//	public void create() {
//		this.setScreen(getScreenMap());
//
//	}
//
//	@Override
//	public void resize(int width, int height) {
//		super.resize(width, height);
//
//		if (getScreen() == null) {
//			setScreen(getScreenMap());
//		}
//	}
//
//	@Override
//	public void render() {
//		super.render();
//	}
//
//	@Override
//	public void pause() {
//		super.pause();
//	}
//
//	@Override
//	public void resume() {
//		super.resume();
//	}
//
//	@Override
//	public void setScreen(Screen screen) {
//		super.setScreen(screen);
//	}
//
//	@Override
//	public void dispose() {
//		super.dispose();
//	}
}
