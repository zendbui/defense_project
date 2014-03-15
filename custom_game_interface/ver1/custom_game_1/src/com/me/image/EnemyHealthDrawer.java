package com.me.image;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class EnemyHealthDrawer {
	public static final int BAR_WIDTH = 30;
	public static final int BAR_HEIGHT = 2;

	ShapeRenderer render = new ShapeRenderer();

	public EnemyHealthDrawer() {
	}

	public void draw(ArrayList<EnemyImage> enemies, Camera camera) {
		render.setProjectionMatrix(camera.combined);
		render.begin(ShapeType.Filled);
		for (EnemyImage m : enemies) {
			// draw all enemy's health
			if (m.getHealthyRate() <= 1f) {
				render.setColor(Color.RED);
				render.rect(m.getX() + 5, m.getY() + m.getHeight() + 5,
						BAR_WIDTH, BAR_HEIGHT);
				render.setColor(Color.GREEN);
				render.rect(m.getX() + 5, m.getY() + m.getHeight() + 5,
						BAR_WIDTH * m.getHealthyRate()*0.5f, BAR_HEIGHT);
			}
		}
		render.end();
	}
}
