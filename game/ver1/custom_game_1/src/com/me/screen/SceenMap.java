package com.me.screen;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.utils.TimeUtils;
import com.me.image.BulletDrawer;
import com.me.image.EnemyHealthDrawer;
import com.me.image.EnemyImage;
import com.me.image.Monster;
import com.me.image.TowerImage;
import com.me.image.TowerRangeDrawer;
import com.me.mygdxgame.DefenseGame;

public class SceenMap extends BaseScreen {
	// menu
	Image pause;
	Image play;
	Dialog dialogMenu;
	Image resume;
	Image restart;
	Image quit;
	boolean onPause = false;
	boolean onRestart = false;

	// Player attribute
	int originLive = 10;
	int originGold = 1000;
	int currGold;
	int currLive;

	ArrayList<EnemyImage> ArrEnemy;
	long lastEnemySpawnTime;
	EnemyHealthDrawer healthdrawer;

	TiledMap tiledMap;
	AtlasRegion tileAtlas;
	TiledMapRenderer tileMapRenderer;

	TiledMapTileLayer passLayer;
	TiledMapTileLayer buildLayer;

	OrthographicCamera cam;
	OrthoCamController camController;

	float displayWidth;
	float displayHeight;

	Image towerChooser;
	float towerX;
	float towerY;
	Image buildingChooser;
	boolean onTowerDrag = false;
	Texture towerImage;

	Image tower1;
	Image tower2;
	Image tower3;
	Image tower4;

	Image build1;
	Image build2;
	Image build3;
	Image build4;

	Image sell;
	Image upgrade;
	Image max_upgrade;

	boolean onTower;
	long timeBlend;
	float currOpa;
	float minOpa = 0.5f;
	ArrayList<TowerImage> ArrTower;
	ArrayList<Point> listCurrTower;
	TowerRangeDrawer towerRangeDrawer;
	TowerImage currChoseTower;
	ShapeRenderer renderTowerFoot;

	BulletDrawer bulletDrawer;
	Monster monster;

	public SceenMap(DefenseGame game) {
		super(game);
		
		displayWidth = Gdx.graphics.getWidth();
		displayHeight = Gdx.graphics.getHeight();

		cam = (OrthographicCamera) stage.getCamera();
		cam.setToOrtho(false, displayWidth, displayHeight);
		cam.update();

		tiledMap = new TmxMapLoader().load("tiled_map/castle_v2.tmx");
		tileMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		passLayer = (TiledMapTileLayer) tiledMap.getLayers().get(1);
		buildLayer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
		tiledMap.getLayers().remove(1);

		towerImage = new Texture(Gdx.files.internal("image/badge1.png"));

		createTowerChooser();
		createBuildingChooser();
		onTower = true;
		timeBlend = TimeUtils.nanoTime();
		currOpa = 1.0f;

		ArrEnemy = new ArrayList<EnemyImage>();
		healthdrawer = new EnemyHealthDrawer();

		ArrTower = new ArrayList<TowerImage>();
		towerRangeDrawer = new TowerRangeDrawer();
		bulletDrawer = new BulletDrawer();

		renderTowerFoot = new ShapeRenderer();
		createTowerAbilityImage();

		spawnEnemy();

		// monster = new Monster(new
		// TextureAtlas(Gdx.files.internal("monster/monster.txt")));
		// monster.setTileMap(tiledMap, tileMapRenderer, passLayer);
		// stage.addActor(monster);

		createMenu();

		createPlayerAttr(originLive, originGold);
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(800, 480, false);
	}

	@Override
	public void show() {
		super.show();

		
	}

	private void createPlayerAttr(int live, int money) {
		this.currLive = live;
		this.currGold = money;
	}

	private Dialog createDialog() {
		// TODO: check position
		WindowStyle winstyle = new WindowStyle();
		winstyle.titleFont = getFont();
		Dialog dialog = new Dialog("Pause", winstyle);
		int group_x = 330;
		int group_y = 150;
		dialog.setSize(80, 120);
		dialog.setPosition(group_x, group_y);

		resume = new Image(new Texture(Gdx.files.internal("image/resume.png")));
		resume.setSize(40, 20);
		resume.setPosition(20, 60);
		resume.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				System.out.println("resume game");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				onPause = false;
				if (dialogMenu != null) {
					dialogMenu.remove();
					dialogMenu = null;
				}
				pause.setVisible(true);
				play.setVisible(false);
			}
		});
		restart = new Image(
				new Texture(Gdx.files.internal("image/restart.png")));
		restart.setSize(40, 20);
		restart.setPosition(20, 40);
		restart.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				System.out.println("restart game");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				onPause = false;
				if (dialogMenu != null) {
					dialogMenu.remove();
					dialogMenu = null;
				}
				pause.setVisible(true);
				play.setVisible(false);
				restartGame();
			}
		});
		quit = new Image(new Texture(Gdx.files.internal("image/quit.png")));
		quit.setSize(40, 20);
		quit.setPosition(20, 20);
		quit.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				System.out.println("quit game");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				Gdx.app.exit();
			}
		});

		dialog.add(resume);
		dialog.row();
		dialog.add(restart);
		dialog.row();
		dialog.add(quit);

		return dialog;

	}

	private void restartGame() {
		Iterator<EnemyImage> iterEnemy = ArrEnemy.iterator();
		while (iterEnemy.hasNext()) {
			EnemyImage enemy = (EnemyImage) iterEnemy.next();
			stage.getRoot().removeActor(enemy);
			iterEnemy.remove();
		}
		Iterator<TowerImage> iterTower = ArrTower.iterator();
		while (iterTower.hasNext()) {
			TowerImage tower = (TowerImage) iterTower.next();
			stage.getRoot().removeActor(tower);
			iterTower.remove();
		}
		createPlayerAttr(20, 1000);

	}

	private void createMenu() {
		pause = new Image(new Texture(Gdx.files.internal("image/pause.png")));
		pause.setSize(32, 32);
		pause.setPosition(0, 0);
		pause.setVisible(true);
		pause.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				System.out.println("pause game");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				onPause = true;
				pause.setVisible(false);
				play.setVisible(true);
				dialogMenu = createDialog();
				stage.addActor(dialogMenu);
			}
		});
		play = new Image(new Texture(Gdx.files.internal("image/play.png")));
		play.setSize(32, 32);
		play.setPosition(0, 0);
		play.setVisible(false);
		play.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				System.out.println("play game");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				onPause = false;
				pause.setVisible(true);
				play.setVisible(false);
				if (dialogMenu != null) {
					dialogMenu.remove();
					dialogMenu = null;
				}
			}
		});
		stage.addActor(pause);
		stage.addActor(play);

	}

	private void createTowerAbilityImage() {
		sell = new Image(new Texture(Gdx.files.internal("image/coin.png")));
		sell.setSize(32, 32);
		sell.setVisible(false);
		sell.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;

			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				System.out.println("sell");
				if (currChoseTower != null) {
					currGold += currChoseTower.sold();
					ArrTower.remove(currChoseTower);
					stage.getRoot().removeActor(currChoseTower);
					currChoseTower = null;
				}
			}
		});

		upgrade = new Image(new Texture(Gdx.files.internal("image/up.png")));
		upgrade.setSize(32, 32);
		upgrade.setVisible(false);
		upgrade.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;

			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				System.out.println("upgrade");
				if (currChoseTower != null) {
					if (currGold >= currChoseTower.getUpgradeCost()) {
						currGold -= currChoseTower.getUpgradeCost();
						currChoseTower.upgrade();
						upgrade.setVisible(false);
						max_upgrade.setVisible(true);
					} else {
						// TODO:not enough gold
					}
				}
			}
		});

		max_upgrade = new Image(
				new Texture(Gdx.files.internal("image/max.png")));
		max_upgrade.setSize(32, 32);
		max_upgrade.setVisible(false);

		stage.addActor(sell);
		stage.addActor(upgrade);
		stage.addActor(max_upgrade);
	}

	private void createTowerChooser() {
		towerChooser = new Image(new Texture(
				Gdx.files.internal("image/badge1.png")));
		towerChooser.setSize(64, 64);
		towerChooser.setPosition(800 - 64, 0);
		towerChooser.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				System.out.println("1down: " + x + ";" + y);
				setTowerDisplay(true);
				setBuildingDisplay(false);
				currOpa = 1.0f;
				return true;

			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {

			}

			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {

				super.touchDragged(event, x, y, pointer);
			}
		});
		tower1 = new Image(new Texture(Gdx.files.internal("image/badge3.png")));
		tower1.setSize(64, 64);
		tower1.setPosition(800 - 64 * 5 - 40, 0);
		tower1.setVisible(true);
		tower1.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				System.out.println("tower1 down");
				return true;

			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				int colTower = (int) (x + 800 - 64 * 5 - 40) / 32;
				int rowTower = (int) y / 32;
				if (onTowerDrag) {
					createTower(0, colTower, rowTower);
				}
				onTowerDrag = false;
			}

			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				if (x > 0 && x < 64 && y > 0 && y < 64) {
					onTowerDrag = false;
				} else {
					towerX = x + 800 - 64 * 5 - 40 - 16;
					towerY = y - 16;
					onTowerDrag = true;
				}
				super.touchDragged(event, x, y, pointer);
			}
		});
		tower2 = new Image(new Texture(Gdx.files.internal("image/badge3.png")));
		tower2.setSize(64, 64);
		tower2.setPosition(800 - 64 * 4 - 30, 0);
		tower2.setVisible(true);
		tower2.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;

			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				int colTower = (int) (x + 800 - 64 * 4 - 30) / 32;
				int rowTower = (int) y / 32;
				if (onTowerDrag) {
					createTower(2, colTower, rowTower);
				}
				onTowerDrag = false;
			}

			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				if (x > 0 && x < 64 && y > 0 && y < 64) {
					onTowerDrag = false;
				} else {
					towerX = x + 800 - 64 * 4 - 30 - 16;
					towerY = y - 16;
					onTowerDrag = true;
				}
				super.touchDragged(event, x, y, pointer);
			}
		});
		tower3 = new Image(new Texture(Gdx.files.internal("image/badge3.png")));
		tower3.setSize(64, 64);
		tower3.setPosition(800 - 64 * 3 - 20, 0);
		tower3.setVisible(true);
		tower3.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;

			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				int colTower = (int) (x + 800 - 64 * 3 - 20) / 32;
				int rowTower = (int) y / 32;
				if (onTowerDrag) {
					createTower(3, colTower, rowTower);
				}
				onTowerDrag = false;
			}

			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				if (x > 0 && x < 64 && y > 0 && y < 64) {
					onTowerDrag = false;
				} else {
					towerX = x + 800 - 64 * 3 - 20 - 16;
					towerY = y - 16;
					onTowerDrag = true;
				}
				super.touchDragged(event, x, y, pointer);
			}
		});
		tower4 = new Image(new Texture(Gdx.files.internal("image/badge3.png")));
		tower4.setSize(64, 64);
		tower4.setPosition(800 - 64 * 2 - 10, 0);
		tower4.setVisible(true);
		tower4.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;

			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				int colTower = (int) (x + 800 - 64 * 2 - 10) / 32;
				int rowTower = (int) y / 32;
				if (onTowerDrag) {
					createTower(4, colTower, rowTower);
				}
				onTowerDrag = false;
			}

			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				if (x > 0 && x < 64 && y > 0 && y < 64) {
					onTowerDrag = false;
				} else {
					towerX = x + 800 - 64 * 2 - 10 - 16;
					towerY = y - 16;
					onTowerDrag = true;
				}
				super.touchDragged(event, x, y, pointer);
			}
		});

		stage.addActor(towerChooser);
		stage.addActor(tower1);
		stage.addActor(tower2);
		stage.addActor(tower3);
		stage.addActor(tower4);

	}

	private void createBuildingChooser() {
		buildingChooser = new Image(new Texture(
				Gdx.files.internal("image/badge2.png")));
		buildingChooser.setSize(64, 64);
		buildingChooser.setPosition(800 - 64, 80);
		buildingChooser.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				System.out.println("2down: " + x + ";" + y);
				setTowerDisplay(false);
				setBuildingDisplay(true);
				currOpa = 1.0f;
				return true;

			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				System.out.println("2up");
			}

		});

		build1 = new Image(new Texture(Gdx.files.internal("image/badge4.png")));
		build1.setSize(64, 64);
		build1.setPosition(800 - 64 * 5 - 40, 0);
		build1.setVisible(false);
		build1.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				System.out.println("build1 down");
				return true;

			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
			}

			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				super.touchDragged(event, x, y, pointer);
			}
		});
		build2 = new Image(new Texture(Gdx.files.internal("image/badge4.png")));
		build2.setSize(64, 64);
		build2.setPosition(800 - 64 * 4 - 30, 0);
		build2.setVisible(false);
		build2.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;

			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
			}

			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				super.touchDragged(event, x, y, pointer);
			}
		});
		build3 = new Image(new Texture(Gdx.files.internal("image/badge4.png")));
		build3.setSize(64, 64);
		build3.setPosition(800 - 64 * 3 - 20, 0);
		build3.setVisible(false);
		build3.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;

			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
			}

			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				super.touchDragged(event, x, y, pointer);
			}
		});
		build4 = new Image(new Texture(Gdx.files.internal("image/badge4.png")));
		build4.setSize(64, 64);
		build4.setPosition(800 - 64 * 2 - 10, 0);
		build4.setVisible(false);
		build4.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;

			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
			}

			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				super.touchDragged(event, x, y, pointer);
			}
		});

		stage.addActor(buildingChooser);
		stage.addActor(build1);
		stage.addActor(build2);
		stage.addActor(build3);
		stage.addActor(build4);
	}

	public void setTowerDisplay(boolean check) {
		tower1.setVisible(check);
		tower2.setVisible(check);
		tower3.setVisible(check);
		tower4.setVisible(check);
	}

	public void setTowerDisplay(float opa) {
		tower1.setColor(tower1.getColor().a, tower1.getColor().b,
				tower1.getColor().g, opa);
		tower2.setColor(tower2.getColor().a, tower2.getColor().b,
				tower2.getColor().g, opa);
		tower3.setColor(tower3.getColor().a, tower3.getColor().b,
				tower3.getColor().g, opa);
		tower4.setColor(tower4.getColor().a, tower4.getColor().b,
				tower4.getColor().g, opa);
	}

	public void setBuildingDisplay(boolean check) {
		build1.setVisible(check);
		build2.setVisible(check);
		build3.setVisible(check);
		build4.setVisible(check);

	}

	public void setBuildingDisplay(float opa) {
		build1.setColor(1.0f, 0.0f, 0.0f, opa);
		build2.setColor(1.0f, 0.0f, 0.0f, opa);
		build3.setColor(1.0f, 0.0f, 0.0f, opa);
		build4.setColor(1.0f, 0.0f, 0.0f, opa);
	}

	private void spawnEnemy() {
		EnemyImage enemy = new EnemyImage(getAtlas(), 0);
		enemy.setTileMap(tiledMap, tileMapRenderer, passLayer);
		ArrEnemy.add(enemy);
		stage.addActor(enemy);
		lastEnemySpawnTime = TimeUtils.nanoTime();
	}

	public void createTower(int type, int col, int row) {
		// check valid position
		if (!checkTowerPosition(col, row)) {
			System.out.println("position invalid");
			return;
		}
		// create tower
		final TowerImage tower = new TowerImage(getAtlas(), type, col, row);

		if (this.currGold < tower.getCost()) {
			// TODO:
			return;
		}

		this.currGold -= tower.getCost();

		tower.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (currChoseTower == null) {
					currChoseTower = tower;
				} else if (currChoseTower.equals(tower)) {
					currChoseTower = null;
				} else {
					currChoseTower = tower;
				}
				return false;
			}
		});
		ArrTower.add(tower);
		stage.addActor(tower);
	}

	public boolean checkTowerPosition(int col, int row) {
		try {
			buildLayer.getCell(col, row).getTile().getProperties();
		} catch (Exception e) {
			return false;
		}

		for (TowerImage tower : ArrTower) {
			if (tower.getCol() == col && tower.getRow() == row) {
				return false;
			}
		}
		return true;
	}

	public void createBuilding() {
		// TODO
	}

	public void setTowerFunction(boolean show) {
		if (!show) {
			sell.setVisible(false);
			upgrade.setVisible(false);
			max_upgrade.setVisible(false);
			return;
		}

		if (currChoseTower != null) {
			int sellCol = currChoseTower.getCol() * 32;
			int sellRow = currChoseTower.getRow() * 32;
			int upgradeCol = currChoseTower.getCol() * 32;
			int upgradeRow = currChoseTower.getRow() * 32;

			Image up;
			sell.setVisible(true);
			if (currChoseTower.isMaxLevel()) {
				up = max_upgrade;
				upgrade.setVisible(false);
			} else {
				up = upgrade;
				max_upgrade.setVisible(false);
			}
			up.setVisible(true);
			if (currChoseTower.getCol() == 0) {
				if (currChoseTower.getRow() == 14) {
					sell.setPosition(sellCol, sellRow - 32);
					up.setPosition(upgradeCol + 32, upgradeRow);
				} else {
					sell.setPosition(sellCol, sellRow + 32);
					up.setPosition(upgradeCol + 32, upgradeRow);
				}
			} else if (currChoseTower.getCol() == 24) {
				if (currChoseTower.getRow() == 14) {
					sell.setPosition(sellCol - 32, sellRow);
					up.setPosition(upgradeCol, upgradeRow - 32);
				} else {
					sell.setPosition(sellCol - 32, sellRow);
					up.setPosition(upgradeCol, upgradeRow + 32);
				}
			} else {
				sell.setPosition(sellCol - 32, sellRow);
				up.setPosition(upgradeCol + 32, upgradeRow);
			}

		} else {
			sell.setVisible(false);
			upgrade.setVisible(false);
			max_upgrade.setVisible(false);
		}
	}

	@Override
	public void render(float delta) {

		if (onPause) {
			cam.update();

			tileMapRenderer.setView(cam);
			tileMapRenderer.render();

			setTowerFunction(false);

			stage.draw();
			return;
		}
		if (TimeUtils.nanoTime() - lastEnemySpawnTime > 2000000000) {
			spawnEnemy();
		}
		Iterator<EnemyImage> iter = ArrEnemy.iterator();
		while (iter.hasNext()) {
			EnemyImage enemy = (EnemyImage) iter.next();
			enemy.Walking(delta);
			if (enemy.isSuccess() || !enemy.isAlive()) {
				if (enemy.isSuccess()) {
					// TODO: life lost
					this.currLive -= 1;
				} else {
					// TODO: gold bonus
					this.currGold += enemy.getGold();
				}
				stage.getRoot().removeActor(enemy);
				iter.remove();

			}
		}

		if (TimeUtils.nanoTime() - timeBlend > 100000000) {
			timeBlend = TimeUtils.nanoTime();
			if (currOpa > minOpa) {
				currOpa -= 0.01f;
				if (onTower) {
					setTowerDisplay(currOpa);
				} else {
					setBuildingDisplay(currOpa);
				}
			}
		}
		stage.act(delta);

		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		cam.update();

		tileMapRenderer.setView(cam);
		tileMapRenderer.render();

		setTowerFunction(true);

		stage.draw();

		if (onTowerDrag) {
			int start_x = ((int) (towerX + 16) / 32) * 32;
			int start_y = ((int) (towerY + 16) / 32) * 32;
			int col_x = (int) (towerX + 16) / 32;
			int row_y = (int) (towerY + 16) / 32;
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			renderTowerFoot.setProjectionMatrix(cam.combined);
			renderTowerFoot.begin(ShapeType.Filled);
			if (checkTowerPosition(col_x, row_y)) {
				renderTowerFoot.setColor(0, 178, 191, 0.4f);
			} else {
				renderTowerFoot.setColor(1, 0, 0, 0.4f);
			}
			renderTowerFoot.rect(start_x, start_y, 32, 32);
			renderTowerFoot.end();
			Gdx.gl.glDisable(GL20.GL_BLEND);
		}

		this.getBatch().setProjectionMatrix(cam.combined);
		this.getBatch().begin();
		this.getFont().draw(this.getBatch(), this.currLive + " life", 40, 470);
		this.getFont().draw(this.getBatch(), this.currGold + " $", 740, 470);
		if (onTowerDrag) {
			getBatch().draw(towerImage, towerX, towerY, 32, 32);
		}
		this.getBatch().end();

		healthdrawer.draw(ArrEnemy, cam);

		Iterator<TowerImage> iterTower = ArrTower.iterator();
		while (iterTower.hasNext()) {
			TowerImage tower = (TowerImage) iterTower.next();
			tower.fire(delta, ArrEnemy);
			bulletDrawer.draw(tower.getArrBullet(), cam);
		}

		if (currChoseTower != null) {
			towerRangeDrawer.draw(currChoseTower, cam);
		}
	}
}
