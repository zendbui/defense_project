package com.me.mygdxgame;

import com.badlogic.gdx.Game;
import com.me.screen.MainMenu;

public class DefenseGame extends Game {

	@Override
	public void create() {
		this.setScreen(new MainMenu(this));
	}
	
	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
	
	
	
}
