package com.me.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.me.image.EnemyImage;
import com.me.mygdxgame.DefenseGame;

public class SceenMap extends BaseScreen {
	
	EnemyImage enemy;

	TiledMap tiledMap;
	AtlasRegion tileAtlas;
	TiledMapRenderer tileMapRenderer;

	TiledMapTileLayer passLayer;

	OrthographicCamera cam;
	OrthoCamController camController;

	float displayWidth;
	float displayHeight;

	Texture tower;
	Texture building;
	Image towerChooser;
	Image buildingChooser;

	public SceenMap(DefenseGame game) {
		super(game);
	}

	@Override
	public void show() {
		super.show();

		displayWidth = Gdx.graphics.getWidth();
		displayHeight = Gdx.graphics.getHeight();
		
		cam = (OrthographicCamera) stage.getCamera();
		cam.setToOrtho(false, displayWidth, displayHeight);
		cam.update();

//		camController = new OrthoCamController(cam);
//		Gdx.input.setInputProcessor(camController);

		tiledMap = new TmxMapLoader().load("tiled_map/castle_v2.tmx");
		tileMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		passLayer = (TiledMapTileLayer) tiledMap.getLayers().get(1);
		tiledMap.getLayers().remove(1);

		tower = new Texture(Gdx.files.internal("image/badge1.png"));
		building = new Texture(Gdx.files.internal("image/badge2.png"));
		
		towerChooser = new Image(tower);
		towerChooser.setSize(64, 64);
		towerChooser.setPosition(700, 0);
		towerChooser.setColor(1.0f, 0.0f, 0.0f, 0.4f);
		towerChooser.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				System.out.println("down");
				return true;
				
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				System.out.println("up");
			}
			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				super.touchDragged(event, x, y, pointer);
			}
		});
		
		buildingChooser = new Image(building);
		buildingChooser.setSize(64, 64);
		buildingChooser.setPosition(50, 0);
		

		enemy = new EnemyImage(getAtlas());
		enemy.setTileMap(tiledMap, tileMapRenderer, passLayer);

		stage.addActor(enemy);
		stage.addActor(towerChooser);
		stage.addActor(buildingChooser);

	}

	public void ControlWalker() {

		if (enemy.isStanding()) {
			enemy.setStand(false);
			enemy.setSpeed(1);
		}

	}

	@Override
	public void render(float delta) {
		// if (enemy.isSuccess()) {
		// stage.getRoot().removeActor(enemy);
		// }
		enemy.Walking(delta);

		// ControlWalker();
		stage.act(delta);

		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		cam.update();
        
		tileMapRenderer.setView(cam);
		tileMapRenderer.render();

		stage.draw();
		
		this.getBatch().setProjectionMatrix(cam.combined);
        this.getBatch().begin();
        this.getFont().draw(this.getBatch(), "life", 40, 470);
        this.getBatch().end();
	}

}
