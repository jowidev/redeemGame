package com.mygdx.game;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

import java.util.concurrent.TimeUnit;


public class Assets {
	public static AssetManager manager = new AssetManager();
	public final Array<AtlasRegion> slimewalk;
	public final Array<AtlasRegion> boulderwalk;
	public final Music mainsong2;
	public final Music finalbattle;
	public final Sound slimeplaced;
	private final TextureAtlas atlas;
	public final Texture bgTxT;
	public final Sound boulderPlaced;
	public final Texture timerBg;
	public final Texture currBg;
	public final Texture mmBg;
	public final Texture mmBgg;
	public final Texture lawn;
	public final Texture bullet;
	public final Sound error;
	public static final AssetDescriptor<Skin> SKIN = new AssetDescriptor<Skin>("flat-earth/skin/flat-earth-ui.json", Skin.class, new SkinLoader.SkinParameter("flat-earth/skin/flat-earth-ui.atlas"));
	public Assets() {
		loadToManager(); //meter aca lo que haya que cargar
		this.bullet = manager.get("slimes/bullet.png");
		this.error = manager.get("miscAssets/error.mp3");
		this.atlas = manager.get("game.atlas");
		this.lawn = manager.get("miscAssets/cortacesped.png");
		this.mmBgg = manager.get("miscAssets/promotionalbg2.png");
		this.mmBg = manager.get("miscAssets/promotionalbg.png");
		this.slimewalk = atlas.findRegions("slimes/planta");
		this.boulderwalk = atlas.findRegions("boulders/boulder");
		this.mainsong2= manager.get("miscAssets/gloriousmorning.mp3");
		this.finalbattle = manager.get("miscAssets/finalbattle.mp3");
		this.slimeplaced = manager.get("slimes/slimeplaced.mp3");
		this.boulderPlaced = manager.get("boulders/boulderPlaced.mp3");
		this.bgTxT = manager.get("miscAssets/logo.png");
		this.currBg = manager.get("miscAssets/currBg.png");
		this.timerBg = manager.get("miscAssets/Timer.png");



		if (slimewalk.size==0) {
			System.out.println("a");
		}
	}
		private void loadToManager() {
			manager.load(SKIN);
			manager.load("slimes/bullet.png", Texture.class);
			manager.load("game.atlas", TextureAtlas.class);
			manager.load("miscAssets/error.mp3", Sound.class);
			manager.load("miscAssets/cortacesped.png", Texture.class);
			manager.load("miscAssets/promotionalbg2.png", Texture.class);
			manager.load("miscAssets/promotionalbg.png", Texture.class);
			manager.load("miscAssets/logo.png", Texture.class);
			manager.load("miscAssets/currBg.png", Texture.class);
			manager.load("miscAssets/gloriousmorning.mp3", Music.class);
			manager.load("miscAssets/finalbattle.mp3", Music.class);
			manager.load("slimes/slimeplaced.mp3", Sound.class);
			manager.load("boulders/boulderPlaced.mp3", Sound.class);
			manager.load("miscAssets/Timer.png", Texture.class);

			manager.finishLoading();

		}

}
