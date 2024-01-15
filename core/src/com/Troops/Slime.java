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
import com.mygdx.game.GameScreen;
import com.mygdx.game.TDGame;

import java.util.ArrayList;

public class Slime extends BaseTroop {
	public Slime( int x, int y) {
		super(x, y,100);
		troopOnMouse = false;
		baseAnimation = new Animation<TextureRegion>(0.033f, TDGame.assets.slimewalk, PlayMode.LOOP);

		//if (TDGame.assets.slimewalk.size==0)System.out.println("o");
	}

	public void update(Viewport vp, ArrayList array) {
		placeTroop(vp, TeamSelScreen.Team.SLIME, array);

		//Client.placeObject(troopOnMouse, hitbox, "slime");
	}
}
