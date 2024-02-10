package com.mygdx.game;

import com.MenuScreens.MainMenuScreen;
import com.MenuScreens.TeamScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TDGame extends Game {
	public static SpriteBatch batch;
	public static Assets assets;
	@Override
	public void create() {
		assets = new Assets();
		batch = new SpriteBatch();

		this.setScreen(new MainMenuScreen(this)); //va ultimo
	}
	public void resize(int w, int h) {}
	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {batch.dispose();}
}