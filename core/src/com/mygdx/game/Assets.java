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
	public final Music trumpsong;
	public final Music finalbattle;
	public final Sound slimeplaced;
	private final TextureAtlas atlas;
	public final Texture bgTxT;
	public final Sound boulderPlaced;
	public final Texture slimeCurr;
	public final Texture boulderCurr;
	public final Texture timerBg;
	public final Texture currBg;
	public final Texture troopBg;
	public final Texture bando;
	public final Texture mmBg;
	public final Texture mmBgg;
	public final Texture lawn;
	public final Texture bullet;
	public static final AssetDescriptor<Skin> SKIN = new AssetDescriptor<Skin>("flat-earth/skin/flat-earth-ui.json", Skin.class, new SkinLoader.SkinParameter("flat-earth/skin/flat-earth-ui.atlas"));
	public Assets() {
		loadToManager(); //meter aca lo que haya que cargar
		this.bullet = manager.get("miscAssets/monochurro.png");
		this.atlas = manager.get("game.atlas");
		this.lawn = manager.get("miscAssets/cortacesped.png");
		this.mmBgg = manager.get("miscAssets/promotionalbg2.png");
		this.mmBg = manager.get("miscAssets/promotionalbg.png");
		this.slimewalk = atlas.findRegions("slimes/planta");
		this.boulderwalk = atlas.findRegions("boulders/boulder");
		this.trumpsong= manager.get("miscAssets/gloriousmorning.mp3");
		this.finalbattle = manager.get("miscAssets/finalbattle.mp3");
		this.slimeplaced = manager.get("slimes/slimeplaced.mp3");
		this.boulderPlaced = manager.get("boulders/boulderPlaced.mp3");
		this.bgTxT = manager.get("miscAssets/logo.png");
		this.bando = manager.get("miscAssets/bando.png");
		this.slimeCurr = manager.get("slimes/slimeCurr.png");
		this.currBg = manager.get("miscAssets/currBg.png");
		this.boulderCurr = manager.get("boulders/boulderCurr.png");
		this.timerBg = manager.get("miscAssets/Timer.png");
		this.troopBg = manager.get("miscAssets/TroopBg.png");


		if (slimewalk.size==0) {
			System.out.println("a");
		}
	}
		private void loadToManager() {
			manager.load(SKIN);
			manager.load("miscAssets/monochurro.png", Texture.class);
			manager.load("game.atlas", TextureAtlas.class);
			manager.load("miscAssets/cortacesped.png", Texture.class);
			manager.load("miscAssets/promotionalbg2.png", Texture.class);
			manager.load("miscAssets/promotionalbg.png", Texture.class);
			manager.load("slimes/slimeCurr.png", Texture.class);
			manager.load("miscAssets/bando.png", Texture.class);
			manager.load("miscAssets/logo.png", Texture.class);
			manager.load("miscAssets/currBg.png", Texture.class);
			manager.load("miscAssets/gloriousmorning.mp3", Music.class);
			manager.load("miscAssets/finalbattle.mp3", Music.class);
			manager.load("slimes/slimeplaced.mp3", Sound.class);
			manager.load("boulders/boulderPlaced.mp3", Sound.class);
			manager.load("boulders/boulderCurr.png", Texture.class);
			manager.load("miscAssets/Timer.png", Texture.class);
			manager.load("miscAssets/TroopBg.png", Texture.class);

			manager.finishLoading();

		}

}
