package com.mygdx.game;

import com.MenuScreens.MainMenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Gamemap extends Game {
	public static SpriteBatch batch;
	public Assets assets;


	@Override
	public void create() {
		this.assets = new Assets();
		batch = new SpriteBatch();

		this.setScreen(new MainMenuScreen(this)); //va ultimo
		//Gdx.input.setInputProcessor(hud.timerTable.getStage());
	}



	public void resize(int w, int h) {

	}



	@Override
	public void render() {
		super.render(); //llama al render parent

	}

	@Override
	public void dispose() {
		batch.dispose();

	}
}