package com.me.image;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class BulletDrawer {
	ShapeRenderer render = new ShapeRenderer();
	float size=6.0f;
	public BulletDrawer() {
	}

	public void draw(ArrayList<Bullet> bullets, Camera camera) {
		if (bullets == null) {
			return;
		}
		
		Iterator<Bullet> iter = bullets.iterator();
		
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		render.setProjectionMatrix(camera.combined);
		render.begin(ShapeType.Filled);
		while (iter.hasNext()) {
			Bullet bullet = (Bullet) iter.next();
			if (bullet.getType() == 0) {
				render.setColor(Color.ORANGE);
//				render.setColor(236, 135, 14, 1.0f);
			}else if (bullet.getType() == 1) {
				render.setColor(Color.PINK);
//				render.setColor(223, 0, 41, 1.0f);
			}else if (bullet.getType() == 2) {
				render.setColor(Color.CYAN);
//				render.setColor(162, 0, 124, 1.0f);
			}else if (bullet.getType() == 3) {
				render.setColor(Color.MAGENTA );
//				render.setColor(252, 245, 76, 1.0f);
			}else {
				render.setColor(Color.GREEN);
//				render.setColor(0, 160, 107, 0.9f);
			}
			render.circle(bullet.getX(), bullet.getY(), this.size);
		}
		render.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
	}
}
