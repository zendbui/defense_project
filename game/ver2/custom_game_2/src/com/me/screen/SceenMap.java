package com.me.screen;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
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
import com.me.image.BuildingImage;
import com.me.image.BulletDrawer;
import com.me.image.MonsterHealthDrawer;
import com.me.image.MonsterImage;
import com.me.image.TowerImage;
import com.me.image.TowerRangeDrawer;
import com.me.mygdxgame.DefenseGame;
import com.me.setting.GameSetting;
import com.me.setting.GameSpecific;

public class SceenMap extends BaseScreen {
	int game_difficult;

	// menu
	Image pause;
	Image play;
	Dialog dialogMenu;
	Image resume;
	Image restart;
	Image quit;
	Image music;
	Image sound;
	boolean onPause = false;
	boolean onRestart = false;
	boolean onEndGame = false;

	// game over
	Dialog dialogOver;
	Image over_quit;
	Image over_retry;

	// win
	Dialog dialogWin;
	Image newMap;
	Image win_retry;
	Image win_quit;

	// Player attribute
	int originLive = 10;
	int originGold = 1000;
	int originWave;
	int currGold;
	int currLive;
	int currWave = 1;
	int monsterkilled = 0;
	int spawnMonster = 0;

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
	Image buildingChooser;
	float towerX;
	float towerY;

	Image tower1circle;
	Texture tower1;
	Image tower2circle;
	Texture tower2;
	Image tower3circle;
	Texture tower3;
	Image tower4circle;
	Texture tower4;

	Image build1circle;
	Texture build1;
	Image build2circle;
	Texture build2;
	Image build3circle;
	Texture build3;
	ArrayList<String> arrUpdate;
	float timeUpdateDisplay = 0.0f;

	Image sell;
	Image upgrade;
	Image max_upgrade;

	boolean onTowerDisplay = true; // display tower or building?

	boolean onTowerDrag = false; // drag image or not?
	Texture currTowerDrag;
	ShapeRenderer renderTowerFoot;
	float currOpa;
	float minOpa = 0.5f;
	float maxOpa = 1.0f;

	ArrayList<TowerImage> ArrTower;
	ArrayList<BuildingImage> ArrBuild;
	ArrayList<Point> listCurrTower;
	TowerRangeDrawer towerRangeDrawer;

	TowerImage currChoseTower;

	BulletDrawer bulletDrawer;

	ArrayList<MonsterImage> ArrEnemy;
	long lastEnemySpawnTime;
	MonsterHealthDrawer healthdrawer;

	Music bgMusic;

	Texture img_x10;
	Texture img_x1;
	Texture img_life;
	Texture img_gold;
	Texture img_wave;
	Texture img_curr_wave;
	Texture img_split;
	Texture img_max_wave;
	Texture img_number_0;
	Texture img_number_1;
	Texture img_number_2;
	Texture img_number_3;
	Texture img_number_4;
	Texture img_number_5;
	Texture img_number_6;
	Texture img_number_7;
	Texture img_number_8;
	Texture img_number_9;

	public SceenMap(DefenseGame game) {
		super(game);

		switch (GameSetting.GAME_MODE) {
		case GameSetting.EASY:
			this.game_difficult = GameSetting.EASY_WAVE;
			this.originWave = GameSetting.EASY_WAVE;
			break;
		case GameSetting.NORMAL:
			this.game_difficult = GameSetting.NORMAL_WAVE;
			this.originWave = GameSetting.NORMAL_WAVE;
			break;
		case GameSetting.INSANE:
			this.game_difficult = GameSetting.INSANE_WAVE;
			this.originWave = GameSetting.INSANE_WAVE;
			break;
		default:
			this.game_difficult = GameSetting.EASY_WAVE;
			this.originWave = GameSetting.EASY_WAVE;
			break;
		}

		displayWidth = Gdx.graphics.getWidth();
		displayHeight = Gdx.graphics.getHeight();

		cam = (OrthographicCamera) stage.getCamera();
		cam.setToOrtho(false, displayWidth, displayHeight);
		cam.update();

		tiledMap = new TmxMapLoader().load("tiled_map/" + GameSetting.GAME_MAP);
		tileMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		passLayer = (TiledMapTileLayer) tiledMap.getLayers().get(1);
		buildLayer = (TiledMapTileLayer) tiledMap.getLayers().get(2);
		// tiledMap.getLayers().remove(1);
		// tiledMap.getLayers().remove(2);

		createTowerChooser();
		createBuildingChooser();
		setChoosen();
		//
		ArrEnemy = new ArrayList<MonsterImage>();
		healthdrawer = new MonsterHealthDrawer();
		//
		ArrTower = new ArrayList<TowerImage>();
		towerRangeDrawer = new TowerRangeDrawer();
		bulletDrawer = new BulletDrawer();
		//
		ArrBuild = new ArrayList<BuildingImage>();
		//
		renderTowerFoot = new ShapeRenderer();
		createTowerAbilityImage();
		//
		spawnEnemy();

		createMenu();
		//
		createPlayerAttr(originLive, originGold);
		//
		createInforDrawer();
		//
		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("music/bg.ogg"));
		bgMusic.setLooping(true);

		arrUpdate = new ArrayList<String>();

	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(800, 480, false);
	}

	@Override
	public void show() {
		super.show();

		if (GameSetting.GAME_MUSIC) {
			bgMusic.play();
		}

	}

	private void createPlayerAttr(int live, int money) {
		this.currLive = live;
		this.currGold = money;
	}

	private Dialog createDialog() {
		WindowStyle winstyle = new WindowStyle();
		winstyle.titleFont = getFont();
		Dialog dialog = new Dialog("", winstyle);
		int dialog_width = 250;
		int dialog_height = 320;
		int group_x = (int) (800 - dialog_width) / 2;
		int group_y = ((int) 480 / 2 - dialog_height) > 0 ? ((int) displayHeight / 2 - dialog_height)
				: 100;
		dialog.setSize(dialog_width, dialog_height);
		dialog.setPosition(group_x, group_y);
		Image bg = new Image(new Texture(
				Gdx.files.internal("image/game/bg.png")));
		dialog.setBackground(bg.getDrawable());

		resume = new Image(new Texture(
				Gdx.files.internal("image/game/resume.png")));
		resume.setSize(60, 40);
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
		restart = new Image(new Texture(
				Gdx.files.internal("image/game/restart.png")));
		restart.setSize(60, 40);
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
		quit = new Image(new Texture(Gdx.files.internal("image/game/quit.png")));
		quit.setSize(60, 40);
		quit.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new MainMenu(game));
				dispose();
			}
		});
		
		music= new Image(new Texture(Gdx.files.internal("image/game/music.png")));
		if (!GameSetting.GAME_MUSIC) {
			music.setColor(music.getColor().r, music.getColor().g,
					music.getColor().b, 0.5f);
		}
		music.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {

				if (music.getColor().a == 1.0f) {
					music.setColor(music.getColor().r, music.getColor().g,
							music.getColor().b, 0.5f);
					if (bgMusic.isPlaying()) {
						GameSetting.GAME_MUSIC = false;
						bgMusic.pause();
					}
				} else {
					music.setColor(music.getColor().r, music.getColor().g,
							music.getColor().b, 1.0f);
					if (!bgMusic.isPlaying()) {
						GameSetting.GAME_MUSIC = true;
						bgMusic.play();
					}
				}
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
			}
		});
		sound= new Image(new Texture(Gdx.files.internal("image/game/sound.png")));
		if (!GameSetting.GAME_SOUND) {
			sound.setColor(sound.getColor().r, sound.getColor().g,
					sound.getColor().b, 0.5f);
		}
		sound.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {

				if (sound.getColor().a == 1.0f) {
					sound.setColor(sound.getColor().r, sound.getColor().g,
							sound.getColor().b, 0.5f);
					if (GameSetting.GAME_SOUND) {
						GameSetting.GAME_SOUND = false;
					}
				} else {
					sound.setColor(sound.getColor().r, sound.getColor().g,
							sound.getColor().b, 1.0f);
					if (!GameSetting.GAME_SOUND) {
						GameSetting.GAME_SOUND = true;
					}
				}
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
			}
		});
		dialog.reset();
		dialog.row();
		dialog.add(resume).padLeft(40);
		dialog.row();
		dialog.add(restart).padLeft(40);
		dialog.row();
		dialog.add(quit).padLeft(40);
		dialog.row();
		dialog.add(music);
		dialog.add(sound);
		dialog.padTop(30);
//
		return dialog;

	}

	private void restartGame() {
		Iterator<MonsterImage> iterEnemy = ArrEnemy.iterator();
		while (iterEnemy.hasNext()) {
			MonsterImage enemy = (MonsterImage) iterEnemy.next();
			stage.getRoot().removeActor(enemy);
			iterEnemy.remove();
		}
		Iterator<TowerImage> iterTower = ArrTower.iterator();
		while (iterTower.hasNext()) {
			TowerImage tower = (TowerImage) iterTower.next();
			stage.getRoot().removeActor(tower);
			iterTower.remove();
		}
		Iterator<BuildingImage> iterBuild = ArrBuild.iterator();
		while (iterBuild.hasNext()) {
			BuildingImage build = (BuildingImage) iterBuild.next();
			stage.getRoot().removeActor(build);
			iterBuild.remove();
		}
		createPlayerAttr(originLive, originGold);

	}

	private void createMenu() {
		pause = new Image(new Texture(
				Gdx.files.internal("image/game/pause.png")));
		pause.setPosition(16, 16);
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
		play = new Image(new Texture(Gdx.files.internal("image/game/play.png")));
		play.setPosition(16, 16);
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
		sell = new Image(new Texture(Gdx.files.internal("image/game/sell.png")));
		sell.setSize(48, 48);
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

		upgrade = new Image(new Texture(Gdx.files.internal("image/game/upgrade.png")));
		upgrade.setSize(48, 48);
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
					}
				}
			}
		});

		max_upgrade = new Image(
				new Texture(Gdx.files.internal("image/game/max.png")));
		max_upgrade.setSize(48, 48);
		max_upgrade.setVisible(false);

		// stage.addActor(sell);
		// stage.addActor(upgrade);
		// stage.addActor(max_upgrade);
	}

	private void setChoosen() {
		towerChooser.setVisible(true);
		buildingChooser.setVisible(true);
		if (onTowerDisplay) {
			towerChooser.setColor(towerChooser.getColor().r,
					towerChooser.getColor().g, towerChooser.getColor().b,
					maxOpa);
			buildingChooser.setColor(buildingChooser.getColor().r,
					buildingChooser.getColor().g, buildingChooser.getColor().b,
					minOpa);
			setTowerDisplay(true);
			setBuildingDisplay(false);
		} else {
			towerChooser.setColor(towerChooser.getColor().r,
					towerChooser.getColor().g, towerChooser.getColor().b,
					minOpa);
			buildingChooser.setColor(buildingChooser.getColor().r,
					buildingChooser.getColor().g, buildingChooser.getColor().b,
					maxOpa);
			setTowerDisplay(false);
			setBuildingDisplay(true);
		}
	}

	private void createTowerChooser() {

		tower1 = new Texture(Gdx.files.internal("tower/tower1.png"));
		tower2 = new Texture(Gdx.files.internal("tower/tower2.png"));
		tower3 = new Texture(Gdx.files.internal("tower/tower3.png"));
		tower4 = new Texture(Gdx.files.internal("tower/tower4.png"));

		towerChooser = new Image(new Texture(
				Gdx.files.internal("image/game/towerChoosen.png")));
		towerChooser.setSize(64, 64);
		towerChooser.setPosition(800 - 64 - 32, 0);
		towerChooser.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				System.out.println("display tower");
				onTowerDisplay = true;
				setChoosen();
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
		tower1circle = new Image(new Texture(
				Gdx.files.internal("image/game/tower1circle.png")));
		tower1circle.setSize(64, 64);
		tower1circle.setPosition(800 - 64 * 5 - 40 - 32, 0);
		tower1circle.setVisible(true);
		tower1circle.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				onTowerDrag = true;
				currTowerDrag = tower1;
				towerX = x + 800 - 64 * 5 - 40 - 16 - 32;
				towerY = y + 16;
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				// int colTower = (int) (x + 800 - 64 * 5 - 40) / 32;
				// int rowTower = (int) (y + 32) / 32;
				int colTower = (int) (towerX + 16) / 32;
				int rowTower = (int) (towerY + 16) / 32;
				if (onTowerDrag) {
					createTower(0, colTower, rowTower);
				}
				onTowerDrag = false;
				currTowerDrag = null;
			}

			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				if (x > 0 && x < 64 && y > 0 && y < 64) {
					onTowerDrag = false;
				} else {
					towerX = x + 800 - 64 * 5 - 40 - 16 - 32;
					towerY = y + 16;
					onTowerDrag = true;
				}
				super.touchDragged(event, x, y, pointer);
			}
		});
		tower2circle = new Image(new Texture(
				Gdx.files.internal("image/game/tower2circle.png")));
		tower2circle.setSize(64, 64);
		tower2circle.setPosition(800 - 64 * 4 - 30 - 32, 0);
		tower2circle.setVisible(true);
		tower2circle.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				onTowerDrag = true;
				currTowerDrag = tower2;
				towerX = x + 800 - 64 * 4 - 30 - 16 - 32;
				towerY = y + 16;
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				// int colTower = (int) (x + 800 - 64 * 4 - 30) / 32;
				// int rowTower = (int) (y + 32) / 32;
				int colTower = (int) (towerX + 16) / 32;
				int rowTower = (int) (towerY + 16) / 32;
				if (onTowerDrag) {
					createTower(1, colTower, rowTower);
				}
				onTowerDrag = false;
				currTowerDrag = null;
			}

			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				if (x > 0 && x < 64 && y > 0 && y < 64) {
					onTowerDrag = false;
				} else {
					towerX = x + 800 - 64 * 4 - 30 - 32;
					towerY = y + 16;
					onTowerDrag = true;
				}
				super.touchDragged(event, x, y, pointer);
			}
		});
		tower3circle = new Image(new Texture(
				Gdx.files.internal("image/game/tower3circle.png")));
		tower3circle.setSize(64, 64);
		tower3circle.setPosition(800 - 64 * 3 - 20 - 32, 0);
		tower3circle.setVisible(true);
		tower3circle.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				onTowerDrag = true;
				currTowerDrag = tower3;
				towerX = x + 800 - 64 * 3 - 20 - 16 - 32;
				towerY = y + 16;
				return true;

			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				// int colTower = (int) (x + 800 - 64 * 3 - 20) / 32;
				// int rowTower = (int) (y + 32) / 32;
				int colTower = (int) (towerX + 16) / 32;
				int rowTower = (int) (towerY + 16) / 32;
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
					towerX = x + 800 - 64 * 3 - 20 - 32;
					towerY = y + 16;
					onTowerDrag = true;
				}
				super.touchDragged(event, x, y, pointer);
			}
		});
		tower4circle = new Image(new Texture(
				Gdx.files.internal("image/game/tower4circle.png")));
		tower4circle.setSize(64, 64);
		tower4circle.setPosition(800 - 64 * 2 - 10 - 32, 0);
		tower4circle.setVisible(true);
		tower4circle.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				onTowerDrag = true;
				currTowerDrag = tower4;
				towerX = x + 800 - 64 * 2 - 10 - 16 - 32;
				towerY = y + 16;
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				// int colTower = (int) (x + 800 - 64 * 2 - 10) / 32;
				// int rowTower = (int) (y + 32) / 32;
				int colTower = (int) (towerX + 16) / 32;
				int rowTower = (int) (towerY + 16) / 32;
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
					towerX = x + 800 - 64 * 2 - 10 - 32;
					towerY = y + 16;
					onTowerDrag = true;
				}
				super.touchDragged(event, x, y, pointer);
			}
		});

		stage.addActor(towerChooser);
		stage.addActor(tower1circle);
		stage.addActor(tower2circle);
		stage.addActor(tower3circle);
		stage.addActor(tower4circle);

	}

	private void createBuildingChooser() {
		build1 = new Texture(Gdx.files.internal("building/build1.png"));
		build2 = new Texture(Gdx.files.internal("building/build2.png"));
		build3 = new Texture(Gdx.files.internal("building/build3.png"));

		buildingChooser = new Image(new Texture(
				Gdx.files.internal("image/game/buildingChoosen.png")));
		buildingChooser.setSize(64, 64);
		buildingChooser.setPosition(800 - 64 - 32, 80);
		buildingChooser.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				onTowerDisplay = false;
				setChoosen();
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
			}
		});

		build1circle = new Image(new Texture(
				Gdx.files.internal("building/build1circle.png")));
		build1circle.setSize(64, 64);
		build1circle.setPosition(800 - 64 * 5 - 40, 0);
		build1circle.setVisible(false);
		build1circle.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				onTowerDrag = true;
				currTowerDrag = build1;
				towerX = x + 800 - 64 * 5 - 40 - 16;
				towerY = y + 16;
				onTowerDrag = true;
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				int colTower = (int) (towerX + 16) / 32;
				int rowTower = (int) (towerY + 16) / 32;
				if (onTowerDrag) {
					createBuilding(0, colTower, rowTower);
				}
				onTowerDrag = false;
				currTowerDrag = null;
			}

			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				if (x > 0 && x < 64 && y > 0 && y < 64) {
					onTowerDrag = false;
				} else {
					towerX = x + 800 - 64 * 5 - 40 - 16;
					towerY = y + 16;
					onTowerDrag = true;
				}
				super.touchDragged(event, x, y, pointer);

			}
		});
		build2circle = new Image(new Texture(
				Gdx.files.internal("building/build2circle.png")));
		build2circle.setSize(64, 64);
		build2circle.setPosition(800 - 64 * 4 - 30, 0);
		build2circle.setVisible(false);
		build2circle.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				onTowerDrag = true;
				currTowerDrag = build2;
				towerX = x + 800 - 64 * 4 - 30 - 16;
				towerY = y + 16;
				return true;

			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				int colTower = (int) (towerX + 16) / 32;
				int rowTower = (int) (towerY + 16) / 32;
				if (onTowerDrag) {
					createBuilding(1, colTower, rowTower);
				}
				onTowerDrag = false;
				currTowerDrag = null;
			}

			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				if (x > 0 && x < 64 && y > 0 && y < 64) {
					onTowerDrag = false;
				} else {
					towerX = x + 800 - 64 * 4 - 30 - 16;
					towerY = y + 16;
					onTowerDrag = true;
				}
				super.touchDragged(event, x, y, pointer);
			}
		});
		build3circle = new Image(new Texture(
				Gdx.files.internal("building/build3circle.png")));
		build3circle.setSize(64, 64);
		build3circle.setPosition(800 - 64 * 3 - 20, 0);
		build3circle.setVisible(false);
		build3circle.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				onTowerDrag = true;
				currTowerDrag = build3;
				towerX = x + 800 - 64 * 3 - 20 - 16;
				towerY = y + 16;
				return true;

			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				int colTower = (int) (towerX + 16) / 32;
				int rowTower = (int) (towerY + 16) / 32;
				if (onTowerDrag) {
					createBuilding(2, colTower, rowTower);
				}
				onTowerDrag = false;
				currTowerDrag = null;
			}

			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				if (x > 0 && x < 64 && y > 0 && y < 64) {
					onTowerDrag = false;
				} else {
					towerX = x + 800 - 64 * 3 - 20 - 16;
					towerY = y + 16;
					onTowerDrag = true;
				}
				super.touchDragged(event, x, y, pointer);
			}
		});

		stage.addActor(buildingChooser);
		stage.addActor(build1circle);
		stage.addActor(build2circle);
		stage.addActor(build3circle);
	}

	public void setTowerDisplay(boolean check) {
		tower1circle.setVisible(check);
		tower2circle.setVisible(check);
		tower3circle.setVisible(check);
		tower4circle.setVisible(check);
	}

	public void setTowerDisplay(float opa) {
		tower1circle.setColor(tower1circle.getColor().a,
				tower1circle.getColor().b, tower1circle.getColor().g, opa);
		tower2circle.setColor(tower2circle.getColor().a,
				tower2circle.getColor().b, tower2circle.getColor().g, opa);
		tower3circle.setColor(tower3circle.getColor().a,
				tower3circle.getColor().b, tower3circle.getColor().g, opa);
		tower4circle.setColor(tower4circle.getColor().a,
				tower4circle.getColor().b, tower4circle.getColor().g, opa);
	}

	public void setBuildingDisplay(boolean check) {
		build1circle.setVisible(check);
		build2circle.setVisible(check);
		build3circle.setVisible(check);

	}

	public void setBuildingDisplay(float opa) {
		build1circle.setColor(build1circle.getColor().a,
				build1circle.getColor().b, build1circle.getColor().g, opa);
		build2circle.setColor(build2circle.getColor().a,
				build2circle.getColor().b, build2circle.getColor().g, opa);
		build3circle.setColor(build3circle.getColor().a,
				build3circle.getColor().b, build3circle.getColor().g, opa);
	}

	private void spawnEnemy() {
		if (currWave > originWave) {
			return;
		}
		if (spawnMonster % 4 == 0 && spawnMonster != 0
				&& monsterkilled % 4 == 0 && monsterkilled != 0) {
			return;
		}
		spawnMonster++;
		Random rand = new Random();
		int monster = rand.nextInt(6);
		MonsterImage enemy = new MonsterImage(getMonsterAtlas(), monster);
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
		System.out.println("create new tower: type " + type);
		final TowerImage tower = new TowerImage(getTowerAtlas(), type, col, row);

		if (this.currGold < tower.getCost()) {
			return;
		}

		this.currGold -= tower.getCost();

		tower.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (currChoseTower == null) {
					currChoseTower = tower;
					setCurrChoseTowerFunc();
				} else if (currChoseTower.equals(tower)) {
					currChoseTower = null;
					clearCurrChoseTower();
				} else {
					currChoseTower = tower;
					setCurrChoseTowerFunc();
				}
				return false;
			}
		});
		ArrTower.add(tower);
		stage.addActor(tower);
	}

	public void createBuilding(int type, int col, int row) {
		// check valid position
		if (!checkTowerPosition(col, row)) {
			System.out.println("position invalid");
			return;
		}
		// create building
		System.out.println("create new building: type " + type);
		final BuildingImage build = new BuildingImage(type, col, row);

		if (this.currGold < build.getCost()) {
			return;
		}

		timeUpdateDisplay = TimeUtils.nanoTime();
		switch (type) {
		case 0:
			arrUpdate.add("tower damage increase 10%");
			break;
		case 1:
			arrUpdate.add("tower range increase 10%");
			break;
		case 2:
			arrUpdate.add("bonus 10% gold for each monster killed");
			break;
		default:
			break;
		}
		this.currGold -= build.getCost();
		ArrBuild.add(build);
		stage.addActor(build);
	}

	public boolean checkTowerPosition(int col, int row) {
		try {
			buildLayer.getCell(col, row).getTile().getProperties();
			buildLayer.getCell(col + 1, row).getTile().getProperties();
			buildLayer.getCell(col, row + 1).getTile().getProperties();
			buildLayer.getCell(col + 1, row + 1).getTile().getProperties();
		} catch (Exception e) {
			return false;
		}

		for (TowerImage tower : ArrTower) {
			if (tower.getCol() == col && tower.getRow() == row) {
				return false;
			} else if ((tower.getCol() + 1) == col && tower.getRow() == row) {
				return false;
			} else if (tower.getCol() == col && (tower.getRow() + 1) == row) {
				return false;
			} else if ((tower.getCol() + 1) == col
					&& (tower.getRow() + 1) == row) {
				return false;
			} else if (tower.getCol() == (col + 1) && tower.getRow() == row) {
				return false;
			} else if (tower.getCol() == col && tower.getRow() == (row + 1)) {
				return false;
			} else if (tower.getCol() == (col + 1)
					&& tower.getRow() == (row + 1)) {
				return false;
			} else if ((tower.getCol() + 1) == col
					&& tower.getRow() == (row + 1)) {
				return false;
			} else if (tower.getCol() == (col + 1)
					&& (tower.getRow() + 1) == row) {
				return false;
			}
		}

		for (BuildingImage build : ArrBuild) {
			if (build.getCol() == col && build.getRow() == row) {
				return false;
			} else if ((build.getCol() + 1) == col && build.getRow() == row) {
				return false;
			} else if (build.getCol() == col && (build.getRow() + 1) == row) {
				return false;
			} else if ((build.getCol() + 1) == col
					&& (build.getRow() + 1) == row) {
				return false;
			} else if (build.getCol() == (col + 1) && build.getRow() == row) {
				return false;
			} else if (build.getCol() == col && build.getRow() == (row + 1)) {
				return false;
			} else if (build.getCol() == (col + 1)
					&& build.getRow() == (row + 1)) {
				return false;
			} else if ((build.getCol() + 1) == col
					&& build.getRow() == (row + 1)) {
				return false;
			} else if (build.getCol() == (col + 1)
					&& (build.getRow() + 1) == row) {
				return false;
			}
		}
		return true;
	}

	public void setTowerFunction(boolean show) {
		if (!show) {
			sell.setVisible(false);
			upgrade.setVisible(false);
			max_upgrade.setVisible(false);
			return;
		}

		if (currChoseTower != null) {

			Image up;
			sell.setVisible(true);
			if (currChoseTower.isMaxLevel()) {
				up = max_upgrade;
				upgrade.setVisible(false);
			} else {
				up = upgrade;
				if (currGold < currChoseTower.getUpgradeCost()) {
					upgrade.setColor(upgrade.getColor().r,
							upgrade.getColor().g, upgrade.getColor().b, 0.1f);
				}
				max_upgrade.setVisible(false);
			}
			up.setVisible(true);
			float sell_x = currChoseTower.getCenterX() - 80;
			float sell_y = currChoseTower.getCenterY() - 32;
			float up_x = currChoseTower.getCenterX() + 32;
			float up_y = currChoseTower.getCenterY() - 32;
			sell.setPosition(sell_x, sell_y);
			up.setPosition(up_x, up_y);

		} else {
			sell.setVisible(false);
			upgrade.setVisible(false);
			max_upgrade.setVisible(false);
		}
	}

	@Override
	public void render(float delta) {

		if (onTowerDisplay) {
			checkTowerBuyable();
		} else {
			checkBuildingBuyable();
		}

		if (currLive == 0 && !onEndGame) {
			if (dialogOver == null) {
				dialogOver = createGameOverDialog();
				stage.addActor(dialogOver);
				onEndGame = true;
			}
		} else if (onEndGame && currLive != 0) {
			if (dialogWin == null) {
				dialogWin = createGameWinDialog();
				stage.addActor(dialogWin);
			}
		}

		if (onEndGame) {
			cam.update();

			tileMapRenderer.setView(cam);
			tileMapRenderer.render();

			setTowerFunction(false);

			stage.draw();

			return;
		}

		if (onPause) {
			cam.update();

			tileMapRenderer.setView(cam);
			tileMapRenderer.render();

			setTowerFunction(false);

			stage.draw();

			Iterator<TowerImage> iterTower = ArrTower.iterator();
			while (iterTower.hasNext()) {
				TowerImage tower = (TowerImage) iterTower.next();
				bulletDrawer.draw(tower.getArrBullet(), cam);
			}
			return;
		}

		if (TimeUtils.nanoTime() - lastEnemySpawnTime > 2000000000) {
			spawnEnemy();
		}

		Iterator<MonsterImage> iter = ArrEnemy.iterator();
		while (iter.hasNext()) {
			MonsterImage enemy = (MonsterImage) iter.next();
			enemy.Walking(delta);
			if (enemy.isSuccess() || !enemy.isAlive()) {
				if (enemy.isSuccess()) {
					this.currLive -= 1;
				} else {
					this.currGold += enemy.getGold();
					this.monsterkilled++;
					if (this.monsterkilled % 4 == 0) {
						this.currWave++;
					}
					if (this.monsterkilled == this.originWave * 4) {
						this.onEndGame = true;
					}
				}
				stage.getRoot().removeActor(enemy);
				iter.remove();
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
			clearCurrChoseTower();
			towerChooser.setVisible(false);
			buildingChooser.setVisible(false);
			setTowerDisplay(false);
			setBuildingDisplay(false);
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
			} else if (currChoseTower != null) {
				if (currChoseTower.getCost() > currGold) {
					renderTowerFoot.setColor(0, 178, 191, 0.4f);
				}
			} else {
				renderTowerFoot.setColor(1, 0, 0, 0.4f);
			}
			renderTowerFoot.rect(start_x, start_y, 64, 64);
			renderTowerFoot.end();
			Gdx.gl.glDisable(GL20.GL_BLEND);
		} else {
			setChoosen();
		}

		this.getBatch().setProjectionMatrix(cam.combined);
		this.getBatch().begin();
		drawCurrLife();
		drawCurrWave();
		drawCurrGold();
		if (arrUpdate.size() > 0) {
			this.getFont().draw(this.getBatch(), arrUpdate.get(0), 200, 200);
			if (TimeUtils.nanoTime() - timeUpdateDisplay > 1000000000) {
				if (arrUpdate.size() > 0) {
					arrUpdate.remove(0);
				}
				timeUpdateDisplay = TimeUtils.nanoTime();
			}
		}

		// display tower dragging
		if (onTowerDrag) {
			getBatch().draw(currTowerDrag, towerX, towerY, 64, 64);
		}
		this.getBatch().end();

		// display monster health
		healthdrawer.draw(ArrEnemy, cam);
		//
		Iterator<TowerImage> iterTower = ArrTower.iterator();
		while (iterTower.hasNext()) {
			TowerImage tower = (TowerImage) iterTower.next();
			tower.fire(delta, ArrEnemy);
			bulletDrawer.draw(tower.getArrBullet(), cam);
		}
		//
		if (currChoseTower != null) {
			towerRangeDrawer.draw(currChoseTower, cam);
		}
	}

	private void clearCurrChoseTower() {
		if (currChoseTower != null) {
			currChoseTower = null;
			sell.remove();
			upgrade.remove();
			max_upgrade.remove();
		}
	}

	private void setCurrChoseTowerFunc() {
		stage.addActor(sell);
		stage.addActor(upgrade);
		stage.addActor(max_upgrade);
	}

	private void createInforDrawer() {
		img_life = new Texture(Gdx.files.internal("image/number/life.png"));
		img_gold = new Texture(Gdx.files.internal("image/number/gold_icon.png"));
		img_wave = new Texture(Gdx.files.internal("image/number/wave.png"));
		img_split = new Texture(Gdx.files.internal("image/number/split.png"));
		img_number_0 = new Texture(Gdx.files.internal("image/number/0.png"));
		img_number_1 = new Texture(Gdx.files.internal("image/number/1.png"));
		img_number_2 = new Texture(Gdx.files.internal("image/number/2.png"));
		img_number_3 = new Texture(Gdx.files.internal("image/number/3.png"));
		img_number_4 = new Texture(Gdx.files.internal("image/number/4.png"));
		img_number_5 = new Texture(Gdx.files.internal("image/number/5.png"));
		img_number_6 = new Texture(Gdx.files.internal("image/number/6.png"));
		img_number_7 = new Texture(Gdx.files.internal("image/number/7.png"));
		img_number_8 = new Texture(Gdx.files.internal("image/number/8.png"));
		img_number_9 = new Texture(Gdx.files.internal("image/number/9.png"));
	}

	private void drawNumber(int num, int x, int y, int width, int height) {
		switch (num) {
		case 0:
			getBatch().draw(img_number_0, x, y, width, height);
			break;
		case 1:
			getBatch().draw(img_number_1, x, y, width, height);
			break;
		case 2:
			getBatch().draw(img_number_2, x, y, width, height);
			break;
		case 3:
			getBatch().draw(img_number_3, x, y, width, height);
			break;
		case 4:
			getBatch().draw(img_number_4, x, y, width, height);
			break;
		case 5:
			getBatch().draw(img_number_5, x, y, width, height);
			break;
		case 6:
			getBatch().draw(img_number_6, x, y, width, height);
			break;
		case 7:
			getBatch().draw(img_number_7, x, y, width, height);
			break;
		case 8:
			getBatch().draw(img_number_8, x, y, width, height);
			break;
		case 9:
			getBatch().draw(img_number_9, x, y, width, height);
			break;
		default:
			break;
		}
	}

	private void drawCurrLife() {
		int x10 = (int) currLive / 10;
		int x1 = (int) currLive % 10;
		if (x10 != 0 && x10 < 10) {
			drawNumber(x10, 680, 450, 20, 24);
		}
		drawNumber(x1, 700, 450, 20, 24);
		getBatch().draw(img_life, 730, 440, 40, 40);
	}

	private void drawCurrGold() {
		String str = currGold + "";
		int len = str.length();
		int curr_gold_x = 20;
		for (int i = 0; i < len; i++) {
			String s = str.substring(i, i + 1);
			drawNumber(Integer.parseInt(s), curr_gold_x, 450, 20, 24);
			curr_gold_x += 20;
		}

		getBatch().draw(img_gold, curr_gold_x, 435);
	}

	private void drawCurrWave() {
		getBatch().draw(img_wave, 330, 445);
		drawNumber(currWave, 415, 450, 20, 24);
		getBatch().draw(img_split, 440, 450, 20, 24);
		drawNumber(originWave, 460, 450, 20, 24);
	}

	@Override
	public void dispose() {
		super.dispose();
		if (tiledMap != null) {
			tiledMap.dispose();
		}
		if (currTowerDrag != null) {
			currTowerDrag.dispose();
		}
		if (tower1 != null) {
			tower1.dispose();
		}
		if (tower2 != null) {
			tower2.dispose();
		}
		if (tower3 != null) {
			tower3.dispose();
		}
		if (tower4 != null) {
			tower4.dispose();
		}
		if (build1 != null) {
			build1.dispose();
		}
		if (build2 != null) {
			build2.dispose();
		}
		if (build3 != null) {
			build3.dispose();
		}

		img_life.dispose();
		img_gold.dispose();
		img_wave.dispose();
		img_split.dispose();
		img_number_0.dispose();
		img_number_1.dispose();
		img_number_2.dispose();
		img_number_3.dispose();
		img_number_4.dispose();
		img_number_5.dispose();
		img_number_6.dispose();
		img_number_7.dispose();
		img_number_8.dispose();
		img_number_9.dispose();

		if (bgMusic != null) {
			bgMusic.dispose();
			Gdx.app.log("Sceen Map", "dispose bgMusic");
		}
	}

	private Dialog createGameOverDialog() {
		WindowStyle winstyle = new WindowStyle();
		winstyle.titleFont = getFont();
		Dialog dialog = new Dialog("", winstyle);
		int dialog_width = 250;
		int dialog_height = 300;
		int group_x = (int) (800 - dialog_width) / 2;
		int group_y = ((int) 480 / 2 - dialog_height) > 0 ? ((int) displayHeight / 2 - dialog_height)
				: 100;
		dialog.setSize(dialog_width, dialog_height);
		dialog.setPosition(group_x, group_y);

		Image bg = new Image(new Texture(
				Gdx.files.internal("image/game/gameover/bg.png")));
		dialog.setBackground(bg.getDrawable());

		over_retry = new Image(new Texture(
				Gdx.files.internal("image/game/gameover/retry.png")));
		over_retry.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				System.out.println("retry game");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				onPause = false;
				onEndGame = false;
				if (dialogOver != null) {
					dialogOver.remove();
					dialogOver = null;
				}
				pause.setVisible(true);
				play.setVisible(false);
				restartGame();
			}
		});
		over_quit = new Image(new Texture(
				Gdx.files.internal("image/game/gameover/quit.png")));
		// over_quit.setSize(60, 40);
		over_quit.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new MainMenu(game));
				dispose();
			}
		});
		dialog.reset();
		dialog.add(over_retry);
		dialog.add(over_quit);
		dialog.bottom();
		dialog.padBottom(20);

		return dialog;

	}

	private Dialog createGameWinDialog() {
		WindowStyle winstyle = new WindowStyle();
		winstyle.titleFont = getFont();
		Dialog dialog = new Dialog("", winstyle);
		int dialog_width = 250;
		int dialog_height = 300;
		int group_x = (int) (800 - dialog_width) / 2;
		int group_y = ((int) 480 / 2 - dialog_height) > 0 ? ((int) displayHeight / 2 - dialog_height)
				: 100;
		dialog.setSize(dialog_width, dialog_height);
		dialog.setPosition(group_x, group_y);
		Image bg = new Image(new Texture(
				Gdx.files.internal("image/game/gamewin/bg.png")));
		dialog.setBackground(bg.getDrawable());

		newMap = new Image(new Texture(
				Gdx.files.internal("image/game/gamewin/nextmap.png")));
		// newMap.setSize(60, 40);
		newMap.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				System.out.println("new game");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new MapChoosenScreen(game));
				dispose();
			}
		});
		win_retry = new Image(new Texture(
				Gdx.files.internal("image/game/gamewin/replay.png")));
		// win_retry.setSize(60, 40);
		win_retry.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				onPause = false;
				onEndGame = false;
				if (dialogWin != null) {
					dialogWin.remove();
					dialogWin = null;
				}
				pause.setVisible(true);
				play.setVisible(false);
				restartGame();
			}
		});
		win_quit = new Image(new Texture(
				Gdx.files.internal("image/game/gamewin/quit.png")));
		// over_quit.setSize(60, 40);
		win_quit.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new MainMenu(game));
				dispose();
			}
		});
		dialog.reset();
		dialog.add(newMap);
		dialog.add(win_retry);
		dialog.add(win_quit);
		dialog.bottom();
		dialog.padBottom(20);

		return dialog;

	}

	private void checkTowerBuyable() {
		if (currGold < GameSpecific.gunCost[0]) {
			if (tower1circle.getColor().a == 1.0f) {
				tower1circle.setColor(tower1circle.getColor().r,
						tower1circle.getColor().g, tower1circle.getColor().b,
						0.5f);
			}
		} else {
			if (tower1circle.getColor().a == 0.5f) {
				tower1circle.setColor(tower1circle.getColor().r,
						tower1circle.getColor().g, tower1circle.getColor().b,
						1.0f);
			}
		}
		if (currGold < GameSpecific.gunCost[1]) {
			if (tower2circle.getColor().a == 1.0f) {
				tower2circle.setColor(tower2circle.getColor().r,
						tower2circle.getColor().g, tower2circle.getColor().b,
						0.5f);
			}
		} else {
			if (tower2circle.getColor().a == 0.5f) {
				tower2circle.setColor(tower2circle.getColor().r,
						tower2circle.getColor().g, tower2circle.getColor().b,
						1.0f);
			}
		}
		if (currGold < GameSpecific.gunCost[2]) {
			if (tower3circle.getColor().a == 1.0f) {
				tower3circle.setColor(tower3circle.getColor().r,
						tower3circle.getColor().g, tower3circle.getColor().b,
						0.5f);
			}
		} else {
			if (tower3circle.getColor().a == 0.5f) {
				tower3circle.setColor(tower3circle.getColor().r,
						tower3circle.getColor().g, tower3circle.getColor().b,
						1.0f);
			}
		}
		if (currGold < GameSpecific.gunCost[3]) {
			if (tower4circle.getColor().a == 1.0f) {
				tower4circle.setColor(tower4circle.getColor().r,
						tower4circle.getColor().g, tower4circle.getColor().b,
						0.5f);
			}
		} else {
			if (tower4circle.getColor().a == 0.5f) {
				tower4circle.setColor(tower4circle.getColor().r,
						tower4circle.getColor().g, tower4circle.getColor().b,
						1.0f);
			}
		}
	}

	private void checkBuildingBuyable() {
		if (currGold < GameSpecific.buildingCost[0]) {
			build1circle.setColor(build1circle.getColor().r,
					build1circle.getColor().g, build1circle.getColor().b, 0.5f);
		} else {
			build1circle.setColor(build1circle.getColor().r,
					build1circle.getColor().g, build1circle.getColor().b, 1.0f);
		}
		if (currGold < GameSpecific.buildingCost[1]) {
			build2circle.setColor(build2circle.getColor().r,
					build2circle.getColor().g, build2circle.getColor().b, 0.5f);
		} else {
			build2circle.setColor(build2circle.getColor().r,
					build2circle.getColor().g, build2circle.getColor().b, 1.0f);
		}
		if (currGold < GameSpecific.buildingCost[2]) {
			build3circle.setColor(build3circle.getColor().r,
					build3circle.getColor().g, build3circle.getColor().b, 0.5f);
		} else {
			build3circle.setColor(build3circle.getColor().r,
					build3circle.getColor().g, build3circle.getColor().b, 1.0f);
		}

	}

}
