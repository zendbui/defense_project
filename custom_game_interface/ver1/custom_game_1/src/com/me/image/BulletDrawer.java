package com.me.image;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class BulletDrawer {
	ShapeRenderer render = new ShapeRenderer();
	float size=4.0f;
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
		render.setColor(223, 0, 41, 1.0f);
		while (iter.hasNext()) {
			Bullet bullet = (Bullet) iter.next();
			System.out.println("fire: " + bullet.getX() + ";" + bullet.getY());
			render.circle(bullet.getX(), bullet.getY(), this.size);
		}
		render.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
	}
}
