package com.me.mygdxgame;

import java.util.Random;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;

public class MyGdxGame implements ApplicationListener {
	// private TiledMap map;
	// private TiledMapTileLayer noPassLayer;
	// private TiledMapRenderer renderer;
	// private OrthographicCamera camera;
	// private OrthoCamController cameraController;
	// private BitmapFont font;
	// private SpriteBatch batch;
	//
	// Texture minionImage;
	// Rectangle minion;
	// long lastMovationTime;
	//
	// static final int UP = 1;
	// static final int DOWN = 2;
	// static final int RIGHT = 3;
	// static final int LEFT = 4;
	// int direction;
	//
	// float displayWidth;
	// float displayHeight;

	Stage stage;
	EnemyImage enemy;
	TiledMap tiledMap;
	TiledMapRenderer tileMapRenderer;
	TiledMapTileLayer directWayLayer;

	OrthographicCamera cam;
	OrthoCamController camController;

	@Override
	public void create() {
		// displayWidth = Gdx.graphics.getWidth();
		// displayHeight = Gdx.graphics.getHeight();
		//
		// camera = new OrthographicCamera();
		// camera.setToOrtho(false, displayWidth, displayHeight);
		// camera.update();
		//
		// cameraController = new OrthoCamController(camera);
		// Gdx.input.setInputProcessor(cameraController);
		//
		// font = new BitmapFont();
		// batch = new SpriteBatch();
		//
		// map = new TmxMapLoader().load("data/tiled_map/castle.tmx");
		// renderer = new OrthogonalTiledMapRenderer(map);
		//
		// noPassLayer = (TiledMapTileLayer) map.getLayers().get(3);
		// map.getLayers().remove(3);
		// try {
		// noPassLayer.getCell(2, 2).getTile().getProperties();
		// Gdx.app.log("cell", "block");
		// } catch (Exception e) {
		// Gdx.app.log("cell", "go");
		// }
		//
		// minionImage = new Texture(
		// Gdx.files.internal("data/image/minion_sad.png"));
		// minion = new Rectangle();
		// minion.x = 0;
		// minion.y = 0;
		//
		// minion.height = 32;
		// minion.width = 32;
		//
		// lastMovationTime = TimeUtils.nanoTime();
		stage = new Stage(0, 0, true);

		tiledMap = new TmxMapLoader().load("data/tiled_map/caste_v2.tmx");
		tileMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		
		directWayLayer = (TiledMapTileLayer) tiledMap.getLayers().get(1);
		tiledMap.getLayers().remove(1);

		cam = (OrthographicCamera) stage.getCamera();
		// cam.zoom = 0.5f;

		camController = new OrthoCamController(cam);
		Gdx.input.setInputProcessor(camController);

		enemy = new EnemyImage("abc");

		stage.addActor(enemy);

	}

	@Override
	public void dispose() {
		// batch.dispose();
		// minionImage.dispose();
	}

	@Override
	public void render() {

		// Gdx.gl.glClearColor(0.55f, 0.55f, 0.55f, 1f);
		// Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		// camera.update();
		// renderer.setView(camera);
		// renderer.render();
		// batch.begin();
		// batch.draw(minionImage, minion.x, minion.y);
		// font.draw(batch, "width: " + displayWidth + " -- height: "
		// + displayHeight, 10, 20);
		// font.draw(batch, "current location: " + minion.x + "-" + minion.y,
		// 10,
		// 40);
		// batch.end();
		//
		// if (TimeUtils.nanoTime() - lastMovationTime > 100000000) {
		// moveMinion();
		// }
		float delta = Gdx.graphics.getDeltaTime();
		enemy.Moving(delta);

		stage.act(delta);

		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		cam.update();
		tileMapRenderer.setView(cam);
		tileMapRenderer.render();

		stage.draw();

	}

	// public void moveMinion() {
	// lastMovationTime = TimeUtils.nanoTime();
	//
	// Random rand = new Random();
	// direction = rand.nextInt(4) + 1;
	//
	// switch (direction) {
	// case UP:
	// minion.y += 32;
	// break;
	// case DOWN:
	// minion.y -= 32;
	// break;
	// case LEFT:
	// minion.x -= 32;
	// break;
	// case RIGHT:
	// minion.x += 32;
	// break;
	// default:
	// minion.x = 0;
	// minion.y = 0;
	// break;
	// }
	//
	// if (minion.x >= displayWidth - 32) {
	// minion.x = displayWidth;
	// }
	//
	// if (minion.x <= 0) {
	// minion.x = 0;
	// }
	// if (minion.y >= displayHeight - 32) {
	// minion.y = displayHeight;
	// }
	// if (minion.y <= 0) {
	// minion.y = 0;
	// }
	// }

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
