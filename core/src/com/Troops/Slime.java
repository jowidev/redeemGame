package com.Troops;

import com.MenuScreens.TeamSelScreen;
import com.Server.Client;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Constants;
import com.mygdx.game.TDGame;

public class Slime extends BaseTroop {

	private final TDGame game;
	public float stateTime;

	public Slime(TDGame game, int x, int y) {
		super(x, y,100);
		this.game = game;
		troopOnMouse = false;
		stateTime = 0;
		baseAnimation = new Animation<TextureRegion>(0.033f, TDGame.assets.slimewalk, PlayMode.LOOP);

		if (TDGame.assets.slimewalk.size==0)System.out.println("o");

	}
	public void update(Viewport viewport) {
		placeTroop(viewport, TeamSelScreen.Team.SLIME);

		//Client.placeObject(troopOnMouse, hitbox, "slime");
	}

	public void render() {
		stateTime += Gdx.graphics.getDeltaTime();
		TextureRegion currentFrame = baseAnimation.getKeyFrame(stateTime, true);
		TDGame.batch.draw(currentFrame, hitbox.x, hitbox.y, 2, 2);

	}
}
