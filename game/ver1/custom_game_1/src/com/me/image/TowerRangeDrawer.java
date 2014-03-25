package com.me.image;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class TowerRangeDrawer {

	ShapeRenderer render = new ShapeRenderer();

	public TowerRangeDrawer() {
	}

	public void draw(TowerImage tower, Camera camera) {
		if (tower == null) {
			return;
		}
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		render.setProjectionMatrix(camera.combined);
		render.begin(ShapeType.Filled);
		render.setColor(0, 178, 191, 0.4f);
		render.circle(tower.centerX, tower.centerY, tower.range);
		render.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
	}
}
